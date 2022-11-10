package hcux.cs.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.Code;
import com.hand.hap.system.dto.CodeValue;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.ICodeService;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hcux.cs.dto.CsOverdue;
import hcux.cs.mapper.CsOverdueMapper;
import hcux.cs.service.ICsOverdueService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class CsOverdueServiceImpl extends BaseServiceImpl<CsOverdue> implements ICsOverdueService {
    @Autowired
    private CsOverdueMapper csOverdueMapper;
    @Autowired
    private ICodeService codeService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int ebsDataInsertIntoHap(IRequest request) {
        //删除hap这边所有数据
        csOverdueMapper.deleteCsOverdue();
        //得到数据总数
//        int countCsOverdue = csOverdueMapper.selectCountCsOverdueFromEbs().getCountCsOverdue();
//        if (countCsOverdue > 0) {
//            int size = countCsOverdue % 1000 == 0 ? countCsOverdue / 1000 : countCsOverdue / 1000 + 1;
//            CsOverdue csOverdue = new CsOverdue();
//            for (int i = 0; i < size; i++) {
//                csOverdue.setPageSizeFrom(i * 1000);
//                csOverdue.setPageSizeTo(csOverdue.getPageSizeFrom() + 1000);
//                List<CsOverdue> csOverdueList = csOverdueMapper.selectCsOverdueFromEbs(csOverdue);
//                csOverdueList.forEach(x -> self().insertSelective(request, x));
//            }
//        }
        //直接同步全量
        return csOverdueMapper.insertAll();
    }

    @Override
    public List<CsOverdue> selectListsFromHap(IRequest request, CsOverdue csOverdue, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return csOverdueMapper.selectListsFromHap(csOverdue);
    }

    @Override
    public List<CsOverdue> selectListsFromEbs(IRequest request, CsOverdue csOverdue) {
        return csOverdueMapper.selectListsFromEbs(csOverdue);
    }

    @Override
    public CsOverdue selectSumLockAmountFromHap(IRequest request, CsOverdue csOverdue) {
        return csOverdueMapper.selectSumLockAmountFromHap(csOverdue);
    }

    @Override
    public void test(IRequest iRequest, MultipartFile file) {
        String jsonStr = "";
        try {

            InputStreamReader isr = new InputStreamReader(file.getInputStream(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String content = null;
            while ((content = br.readLine()) != null) {
                jsonStr += content;
            }

            JSONObject jsonObject = JSONObject.fromObject(jsonStr);
            JSONArray provinceArray = jsonObject.getJSONArray("province");
            Code code = new Code();
            code.setCode("HCUX_LM.ORDER_ADDRESS");
            code.setDescription("地址的省市区");
            code.setEnabledFlag("Y");
            List<CodeValue> codeValues = new ArrayList<CodeValue>();
            for (int i = 0; i < provinceArray.size() - 3; i++) {
                JSONObject object = provinceArray.getJSONObject(i);
                String province = object.getString("code");
                String provinceName = object.getString("name");
                JSONArray cityArray = object.getJSONArray("cityList");
                // if (cityArray.size() > 1) {
                CodeValue codeValueProvince = new CodeValue();
                codeValueProvince.setValue(province);
                codeValueProvince.setMeaning(provinceName);
                codeValueProvince.setTag("1");
                codeValues.add(codeValueProvince);
                // }
                for (int j = 0; j < cityArray.size(); j++) {
                    JSONObject cityObject = cityArray.getJSONObject(j);
                    String cityName = cityObject.getString("name");
                    String cityCode = cityObject.getString("code");
                    CodeValue codeValueCity = new CodeValue();
                    if (cityArray.size() > 1) {
                        codeValueCity.setValue(cityCode);
                        codeValueCity.setMeaning(cityName);
                        codeValueCity.setTag("2");
                        codeValueCity.setParentCodeValue(codeValueProvince.getValue());
                        codeValues.add(codeValueCity);
                    }
                    JSONArray areaArray = cityObject.getJSONArray("areaList");
                    for (int k = 0; k < areaArray.size(); k++) {
                        JSONObject areaObject = areaArray.getJSONObject(k);
                        String areaName = areaObject.getString("name");
                        String areaCode = areaObject.getString("code");
                        CodeValue codeValueArea = new CodeValue();
                        codeValueArea.setValue(areaCode);
                        codeValueArea.setMeaning(areaName);
                        codeValueArea.setTag("3");
                        if (cityArray.size() == 1) {
                            codeValueArea.setParentCodeValue(codeValueProvince.getValue());
                        } else {
                            codeValueArea.setParentCodeValue(codeValueCity.getValue());
                        }
                        codeValues.add(codeValueArea);
                    }
                }
            }
            code.setCodeValues(codeValues);
            codeService.batchUpdate(iRequest, new ArrayList<Code>() {{
                add(code);
            }});
            br.close();
            isr.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void updateHapData(IRequest request, CsOverdue csOverdue) {
        List<CsOverdue> csOverdueList = self().selectListsFromEbs(request, csOverdue);
        //数据更新
        if (csOverdueList.size() > 0) {
            csOverdueList.forEach(x -> {
                CsOverdue overdue = csOverdueMapper.selectByInvoiceId(x);
                if (overdue == null) {
                    self().insertSelective(request, x);
                } else {
                    x.setOverdueId(overdue.getOverdueId());
                    x.set__status(DTOStatus.UPDATE);
                    x.setObjectVersionNumber(overdue.getObjectVersionNumber());
                    x.setCreatedBy(overdue.getCreatedBy());
                    x.setCreationDate(overdue.getCreationDate());
                    x.setLastUpdateDate(new Date());
                    self().updateByPrimaryKey(request, x);
                }
            });
        }

    }
}