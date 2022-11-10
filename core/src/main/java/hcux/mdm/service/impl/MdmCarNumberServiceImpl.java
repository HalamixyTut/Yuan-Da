package hcux.mdm.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.ICodeService;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hcux.mdm.dto.MdmCarNumber;
import hcux.mdm.mapper.MdmCarNumberMapper;
import hcux.mdm.service.IMdmCarNumberService;
import hcux.util.excel.PoiUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

import static hcux.mdm.util.MdmConstant.HCUX_LM_TRANSPORT_STATUS;
import static hcux.mdm.util.MdmConstant.HCUX_MDM_CAR_ATTRIBUTE;

@Service
@Transactional(rollbackFor = Exception.class)
public class MdmCarNumberServiceImpl extends BaseServiceImpl<MdmCarNumber> implements IMdmCarNumberService {
    @Autowired
    private MdmCarNumberMapper mdmCarNumberMapper;
    @Autowired
    private ICodeService codeService;
    @Override
    public List<MdmCarNumber> selectData(IRequest requestContext, MdmCarNumber dto, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<MdmCarNumber> list =  mdmCarNumberMapper.selectList(dto);

        if (!list.isEmpty()){
            List<MdmCarNumber> list1 = mdmCarNumberMapper.totalCarNum(dto);
            Integer totalCarNum = 0;
            if (!list1.isEmpty()){
                totalCarNum = list1.get(0).getTotalCarNum();
            }
            list.get(0).setTotalCarNum(totalCarNum);
        }
        return list;
    }

    @Override
    public void exportExcel(IRequest iRequest, MdmCarNumber mdmCarNumber, HttpServletResponse response) throws Exception {
        // 总记录数
        PageHelper.startPage(1, 1);
        Page<MdmCarNumber> list = (Page<MdmCarNumber>) mdmCarNumberMapper.selectList(mdmCarNumber);
        long totalRowCount = list.getTotal();

        // 导出EXCEL文件名称
        String fileName = "车牌号统计" + DateFormatUtils.format(new Date(), "yyyyMMdd-HHmmss");

        // 标题
        String[] titles = {"车牌号", "提货时间", "运输公司", "产品名称", "数量", "运输单状态", "车辆属性"};

        // 开始导入
        PoiUtil.exportExcelToWebsite(response, totalRowCount, fileName, titles, (eachSheet, startRowCount, endRowCount, currentPage, pageSize) -> {

            PageHelper.startPage(currentPage, pageSize);
            List<MdmCarNumber> detailList = mdmCarNumberMapper.selectList(mdmCarNumber);

            if (!CollectionUtils.isEmpty(detailList)) {

                for (int i = startRowCount; i <= endRowCount; i++) {
                    SXSSFRow eachDataRow = eachSheet.createRow(i);
                    if ((i - startRowCount) < detailList.size()) {

                        MdmCarNumber dto = detailList.get(i - startRowCount);

                        eachDataRow.createCell(0).setCellValue(dto.getCarNumber() == null ? "" : dto.getCarNumber());

                        if (null != dto.getDrawGoodsDate()) {
                            eachDataRow.createCell(1).setCellValue(DateFormatUtils.ISO_DATE_FORMAT.format(dto.getDrawGoodsDate()));
                        }
                        eachDataRow.createCell(2).setCellValue(dto.getCarrier() == null ? "" : dto.getCarrier());
                        eachDataRow.createCell(3).setCellValue(dto.getGoodsName() == null ? "" : dto.getGoodsName());
                        eachDataRow.createCell(4).setCellValue(dto.getAmount() == null ? 0 : dto.getAmount());
                        if(dto.getTransportStatus()!=null){
                            String transportStatus = codeService.getCodeMeaningByValue(iRequest, HCUX_LM_TRANSPORT_STATUS, dto.getTransportStatus());
                            eachDataRow.createCell(5).setCellValue(transportStatus);
                        }
                        if (dto.getVehicleSource()!=null){
                            String vehicleSource = codeService.getCodeMeaningByValue(iRequest, HCUX_MDM_CAR_ATTRIBUTE, dto.getVehicleSource());
                            eachDataRow.createCell(6).setCellValue(vehicleSource);
                        }
                    }
                }
            }

        });
    }
}
