package hcux.eps.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hcux.eps.dto.EpsPurchaseDetail;
import hcux.eps.mapper.EpsPurchaseDetailMapper;
import hcux.eps.service.IEpsPurchaseDetailService;
import hcux.util.excel.PoiUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class EpsPurchaseDetailServiceImpl extends BaseServiceImpl<EpsPurchaseDetail> implements IEpsPurchaseDetailService {
    @Autowired
    private EpsPurchaseDetailMapper epsPurchaseDetailMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int ebsDataToHap(IRequest iRequest, EpsPurchaseDetail epsPurchaseDetail) {
        List<EpsPurchaseDetail> ebsPurchaseDetailList = epsPurchaseDetailMapper.selectEpsPurchaseDetailFromEbs(epsPurchaseDetail);
        if (ebsPurchaseDetailList != null && !ebsPurchaseDetailList.isEmpty()) {
            ebsPurchaseDetailList.forEach(x ->
            {
                EpsPurchaseDetail purchaseDetail = epsPurchaseDetailMapper.selectEpsPurchaseDetailFromHap(x);
                if (purchaseDetail != null) {
                    purchaseDetail.set__status(DTOStatus.UPDATE);
                    purchaseDetail.setProjectNum(x.getProjectNum());
                    purchaseDetail.setVendorName(x.getVendorName());
                    purchaseDetail.setInvoiceType(x.getInvoiceType());
                    purchaseDetail.setGlDate(x.getGlDate());
                    purchaseDetail.setInvoiceCurrencyCode(x.getInvoiceCurrencyCode());
                    purchaseDetail.setCrAmount(x.getCrAmount());
                    purchaseDetail.setCrBaseAmount(x.getCrBaseAmount());
                    self().updateByPrimaryKeySelective(iRequest, purchaseDetail);
                } else {
                    self().insertSelective(iRequest, x);
                }
            });
        }
        return ebsPurchaseDetailList == null ? 0 : ebsPurchaseDetailList.size();

    }

    @Override
    public List<EpsPurchaseDetail> queryEpsPurchaseDetail(IRequest iRequest, EpsPurchaseDetail epsPurchaseDetail, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<EpsPurchaseDetail> list = epsPurchaseDetailMapper.queryEpsPurchaseDetail(epsPurchaseDetail);
        DecimalFormat decimalFormat=new DecimalFormat(".00");//add yexiang.ren 19200
        if (!list.isEmpty()) {
            List<EpsPurchaseDetail> list1 = epsPurchaseDetailMapper.selectTotalAmount(epsPurchaseDetail);
            if (!list1.isEmpty()) {
                double totalAmount=(double)(Math.round(list1.get(0).getTotalAmount()*100))/100;
                list.get(0).setTotalAmount(totalAmount);
                //add yexiang.ren 19200
                String stringTotalAmount="";
                for(EpsPurchaseDetail purchaseDetail:list1){
                    stringTotalAmount+=(purchaseDetail.getInvoiceCurrencyCode()+" "+decimalFormat.format(purchaseDetail.getTotalAmount())+"; ");
                }
                list.get(0).setStringTotalAmount(stringTotalAmount);
            }
        }
        return list;
    }

    @Override
    public List<EpsPurchaseDetail> selectTotalAmount(IRequest iRequest, EpsPurchaseDetail epsPurchaseDetail, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return epsPurchaseDetailMapper.selectTotalAmount(epsPurchaseDetail);
    }

    @Override
    public void exportExcel(IRequest requestContext, EpsPurchaseDetail epsPurchaseDetail, HttpServletResponse response) throws Exception {

        // 总记录数
        PageHelper.startPage(1, 1);
        Page<EpsPurchaseDetail> list = (Page<EpsPurchaseDetail>) epsPurchaseDetailMapper.queryEpsPurchaseDetail(epsPurchaseDetail);
        long totalRowCount = list.getTotal();

        // 导出EXCEL文件名称
        String fileName = "采购明细" + DateFormatUtils.format(new Date(), "yyyyMMdd-HHmmss");

        // 标题
        String[] titles = {"发票号", "贸易伙伴", "业务类型", "业务日期", "币种", "贷方金额", "贷方折合金额"};

        // 开始导入
        PoiUtil.exportExcelToWebsite(response, totalRowCount, fileName, titles, (eachSheet, startRowCount, endRowCount, currentPage, pageSize) -> {

            PageHelper.startPage(currentPage, pageSize);
            List<EpsPurchaseDetail> detailList = epsPurchaseDetailMapper.queryEpsPurchaseDetail(epsPurchaseDetail);

            if (!CollectionUtils.isEmpty(detailList)) {

                for (int i = startRowCount; i <= endRowCount; i++) {
                    SXSSFRow eachDataRow = eachSheet.createRow(i);
                    if ((i - startRowCount) < detailList.size()) {

                        EpsPurchaseDetail dto = detailList.get(i - startRowCount);

                        eachDataRow.createCell(0).setCellValue(dto.getProjectNum() == null ? "" : dto.getProjectNum());
                        eachDataRow.createCell(1).setCellValue(dto.getVendorName() == null ? "" : dto.getVendorName());
                        eachDataRow.createCell(2).setCellValue(dto.getInvoiceType() == null ? "" : dto.getInvoiceType());
                        if (null != dto.getGlDate()) {
                            eachDataRow.createCell(3).setCellValue(DateFormatUtils.ISO_DATE_FORMAT.format(dto.getGlDate()));
                        }
                        eachDataRow.createCell(4).setCellValue(dto.getInvoiceCurrencyCode() == null ? "" : dto.getInvoiceCurrencyCode());

                        if (null != dto.getCrAmount()) {
                            eachDataRow.createCell(5).setCellValue(dto.getCrAmount());
                        }

                        if (null != dto.getCrBaseAmount()) {
                            eachDataRow.createCell(6).setCellValue(dto.getCrBaseAmount());
                        }
                    }
                }
            }

        });
    }
}