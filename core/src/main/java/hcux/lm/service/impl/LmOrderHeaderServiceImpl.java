package hcux.lm.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.system.service.ICodeService;
import com.hand.hap.system.service.IProfileService;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hcux.core.exception.HcuxException;
import hcux.doc.util.DocConstant;
import hcux.lm.components.HttpUtil;
import hcux.lm.dto.*;
import hcux.lm.mapper.LmOrderHeaderMapper;
import hcux.lm.mapper.LmOrderLineMapper;
import hcux.lm.service.ILmOrderLineService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hcux.lm.service.ILmOrderHeaderService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class LmOrderHeaderServiceImpl extends BaseServiceImpl<LmOrderHeader> implements ILmOrderHeaderService {
    @Autowired
    private LmOrderHeaderMapper mapper;
    @Autowired
    private LmOrderHeaderMapper lmOrderHeaderMapper;
    @Autowired
    private LmOrderLineMapper lmOrderLineMapper;
    @Autowired
    private HttpUtil httpUtil;
    @Autowired
    private IProfileService profileService;
    @Autowired
    private ILmOrderLineService lmOrderLineService;
    @Autowired
    private ICodeService codeService;

    @Override
    public List<LmOrderHeader> selectLists(IRequest iRequest, LmOrderHeader lmOrderHeader, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return lmOrderHeaderMapper.selectLists(lmOrderHeader);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<LmOrderHeader> batchUpdateHeaderAndLine(IRequest requestCtx, List<LmOrderHeader> headerList) {
        for (int i = 0; i < headerList.size(); i++) {
            LmOrderHeader header = headerList.get(i);

            if (DTOStatus.ADD.equals(header.get__status())) {
                String orderNumber = "";
                LmOrderHeader maxOrderNumber = mapper.queryMaxOrderNumber(header);
                if (maxOrderNumber == null) {
                    orderNumber = header.getContractCodeOne() + "01";
                } else {
                    orderNumber = maxOrderNumber.getOrderNumber();
                    int maxNum = Integer.parseInt(orderNumber.substring(15)) +1;
                    String strMaxNum = "";
                    if (maxNum < 10) {
                        strMaxNum = "0" + maxNum;
                    } else {
                        strMaxNum = String.valueOf(maxNum);
                    }
                    orderNumber = header.getContractCodeOne() + strMaxNum;
                }

                header.setOrderNumber(orderNumber);
                // 保存头
                self().insertSelective(requestCtx, header);
                // 保存行
                updateLines(requestCtx, header);
            } else if (DTOStatus.UPDATE.equals(header.get__status())) {
                header.setLastUpdateDate(new Date());
                self().updateByPrimaryKey(requestCtx, header);
                updateLines(requestCtx, header);
            }

            List<LmOrderHeader> list = mapper.selectLists(header);
            if (!list.isEmpty()) {
                header = list.get(0);
            }
            headerList.set(i, header);
        }

        return headerList;
    }

    /**
     * 更新行
     *
     * @param requestCtx
     * @param header
     */
    private void updateLines(IRequest requestCtx, LmOrderHeader header) {
        List<LmOrderLine> lineList = header.getLineList();
        if (lineList != null) {
            for (LmOrderLine line : lineList) {
                line.setOrderId(header.getOrderId());
                if (DTOStatus.ADD.equals(line.get__status())) {
                    lmOrderLineService.insertSelective(requestCtx, line);
                } else if (DTOStatus.UPDATE.equals(line.get__status())) {
                    lmOrderLineService.updateByPrimaryKeySelective(requestCtx, line);
                }
            }
        }
    }

    @Override
    public LmOrderHeader selectOne(IRequest requestContext, LmOrderHeader dto) {
        List<LmOrderHeader> list = mapper.selectLists(dto);

        if (!list.isEmpty()) {
            dto = list.get(0);
        }
        return dto;
    }

    @Override
    public ResponseData submitToZh(IRequest requestCtx, @RequestBody LmOrderHeader dto) throws HcuxException {
        ResponseData rd = new ResponseData();
        // 获取URL
        String url = profileService.getProfileValue(requestCtx, "HCUX_LM_ORDER_CREATE_URL");
        //委托单接口地址
        String linkUrl = profileService.getProfileValue(requestCtx, "HCUX_LM_ORDER_ZH_PC");

        dto = lmOrderHeaderMapper.selectByPrimaryKey(dto);

        LmOrderLine lmOrderLine = new LmOrderLine();
        lmOrderLine.setOrderId(dto.getOrderId());
        List<LmOrderLine> lineList = lmOrderLineMapper.select(lmOrderLine);

        LmOrderHeader dto1 = new LmOrderHeader();
        BeanUtils.copyProperties(dto,dto1);

        List<ZhOrderLine> zhOrderLineList = new ArrayList<>();

        for (LmOrderLine line : lineList) {
            if (line.getDangerGoodsUn() != null) {
                String s = codeService.getCodeMeaningByValue(requestCtx, DocConstant.CODE_HCUX_DANGEROUS_GOODS_UN_NO, line.getDangerGoodsUn());
                line.setDangerGoodsUn(s);
            }
            if (line.getDangerGoodsCat() != null) {
                String s = codeService.getCodeMeaningByValue(requestCtx, DocConstant.CODE_HCUX_DANGEROUS_GOODS_GENRES, line.getDangerGoodsCat());
                line.setDangerGoodsCat(s);
            }
            ZhOrderLine zhOrderLine = new ZhOrderLine(requestCtx,line);
            zhOrderLineList.add(zhOrderLine);
        }

        if (dto.getDrawGoodsProvince() != null) {
            String s = codeService.getCodeMeaningByValue(requestCtx, DocConstant.CODE_HCUX_LM_ORDER_ADDRESS, dto.getDrawGoodsProvince());
            dto.setDrawGoodsProvince(s);
        }
        if (dto.getDrawGoodsCity() != null) {
            String s = codeService.getCodeMeaningByValue(requestCtx, DocConstant.CODE_HCUX_LM_ORDER_ADDRESS, dto.getDrawGoodsCity());
            dto.setDrawGoodsCity(s);
        }
        if (dto.getDrawGoodsArea() != null) {
            String s = codeService.getCodeMeaningByValue(requestCtx, DocConstant.CODE_HCUX_LM_ORDER_ADDRESS, dto.getDrawGoodsArea());
            dto.setDrawGoodsArea(s);
        }
        if (dto.getConsigneeProvince() != null) {
            String s = codeService.getCodeMeaningByValue(requestCtx, DocConstant.CODE_HCUX_LM_ORDER_ADDRESS, dto.getConsigneeProvince());
            dto.setConsigneeProvince(s);
        }
        if (dto.getConsigneeCity() != null) {
            String s = codeService.getCodeMeaningByValue(requestCtx, DocConstant.CODE_HCUX_LM_ORDER_ADDRESS, dto.getConsigneeCity());
            dto.setConsigneeCity(s);
        }
        if (dto.getConsigneeArea() != null) {
            String s = codeService.getCodeMeaningByValue(requestCtx, DocConstant.CODE_HCUX_LM_ORDER_ADDRESS, dto.getConsigneeArea());
            dto.setConsigneeArea(s);
        }
        ZhOrderHeader zhOrderHeader = new ZhOrderHeader(requestCtx, dto);

        zhOrderHeader.setGoodsList(zhOrderLineList);
        try {
            ZhOrderResponse response = httpUtil.post(zhOrderHeader, url, "创建委托单接口", ZhOrderResponse.class);
            if (response.getStatus().equals("20000")) {
                ZhOrderHeader data = response.getData();
                dto1.setZhOrderId(data.getEntrustId());
                dto1.setZhOrderNumber(data.getEntrustNo());
                dto1.setStatus("1");
                dto1.setRejectReason("");
                dto1.setLink(linkUrl + data.getEntrustId());
                self().updateByPrimaryKeySelective(requestCtx, dto1);
            } else {
                rd.setSuccess(false);
                rd.setCode(response.getStatus());
                rd.setMessage(response.getMsg());
            }
            return rd;
        } catch (HcuxException e) {
            e.printStackTrace();
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
            throw new HcuxException("接口调用出错");
        }
    }
}