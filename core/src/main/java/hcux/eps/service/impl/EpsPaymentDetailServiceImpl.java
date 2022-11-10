package hcux.eps.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hcux.eps.dto.EpsPaymentDetail;
import hcux.eps.mapper.EpsPaymentDetailMapper;
import hcux.eps.service.IEpsPaymentDetailService;
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
public class EpsPaymentDetailServiceImpl extends BaseServiceImpl<EpsPaymentDetail> implements IEpsPaymentDetailService {
    @Autowired
    private EpsPaymentDetailMapper epsPaymentDetailMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int ebsPaymentDetailToHap(IRequest iRequest, EpsPaymentDetail epsPaymentDetail) {

        /*List<EpsPaymentDetail> ebsPaymentList = epsPaymentDetailMapper.selectEpsPaymentDetailFromEbs(epsPaymentDetail);
        if (ebsPaymentList != null && !ebsPaymentList.isEmpty()) {
            ebsPaymentList.forEach(x ->
            {
                EpsPaymentDetail paymentDetail = epsPaymentDetailMapper.selectEpsPaymentDetailFromHap(x);
                if (paymentDetail != null) {
                    x.setPaymentId(paymentDetail.getPaymentId());
                    x.set__status(DTOStatus.UPDATE);
                    x.setObjectVersionNumber(paymentDetail.getObjectVersionNumber());
                    x.setCreatedBy(paymentDetail.getCreatedBy());
                    x.setCreationDate(paymentDetail.getCreationDate());
                    self().updateByPrimaryKeySelective(iRequest, x);
                } else {
                    self().insertSelective(iRequest, x);
                }
            });
        }
        return ebsPaymentList == null ? 0 : ebsPaymentList.size();*/
        epsPaymentDetailMapper.deleteAll();
        return epsPaymentDetailMapper.insertAll();
    }

    @Override
    public List<EpsPaymentDetail> selectData(IRequest requestContext, EpsPaymentDetail dto, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<EpsPaymentDetail> list = epsPaymentDetailMapper.queryEpsPaymentDetail(dto);
        if (!list.isEmpty()) {
            List<EpsPaymentDetail> list1 = epsPaymentDetailMapper.selectTotalAmount(dto);
            DecimalFormat decimalFormat=new DecimalFormat(".00");//add yexiang.ren 19200
            if (!list1.isEmpty()) {
                if (!list1.isEmpty()) {
                    double totalAmount=(double)(Math.round(list1.get(0).getTotalAmount()*100))/100;
                    list.get(0).setTotalAmount(totalAmount);
                    //add yexiang.ren 19200
                    String stringTotalAmount="";
                    for(EpsPaymentDetail paymentDetail:list1){
                        stringTotalAmount+=(paymentDetail.getCurrencyCode()+" "+decimalFormat.format(paymentDetail.getTotalAmount())+"; ");
                    }
                    list.get(0).setStringTotalAmount(stringTotalAmount);
                }
            }
        }

        return list;
    }

    @Override
    public List<EpsPaymentDetail> selectTotalAmount(IRequest requestContext, EpsPaymentDetail dto, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return epsPaymentDetailMapper.selectTotalAmount(dto);
    }

    @Override
    public void exportExcel(IRequest requestContext, EpsPaymentDetail epsPaymentDetail, HttpServletResponse response) throws Exception {

        // 总记录数
        PageHelper.startPage(1, 1);
        Page<EpsPaymentDetail> list = (Page<EpsPaymentDetail>) epsPaymentDetailMapper.queryEpsPaymentDetail(epsPaymentDetail);
        long totalRowCount = list.getTotal();

        // 导出EXCEL文件名称
        String fileName = "付款明细" + DateFormatUtils.format(new Date(), "yyyyMMdd-HHmmss");

        // 标题
        String[] titles = {"发票号", "收款单位", "支出类型", "付款日期", "币种", "原币金额"};

        // 开始导入
        PoiUtil.exportExcelToWebsite(response, totalRowCount, fileName, titles, (eachSheet, startRowCount, endRowCount, currentPage, pageSize) -> {

            PageHelper.startPage(currentPage, pageSize);
            List<EpsPaymentDetail> detailList = epsPaymentDetailMapper.queryEpsPaymentDetail(epsPaymentDetail);

            if (!CollectionUtils.isEmpty(detailList)) {

                for (int i = startRowCount; i <= endRowCount; i++) {
                    SXSSFRow eachDataRow = eachSheet.createRow(i);
                    if ((i - startRowCount) < detailList.size()) {

                        EpsPaymentDetail dto = detailList.get(i - startRowCount);

                        eachDataRow.createCell(0).setCellValue(dto.getProjectNum() == null ? "" : dto.getProjectNum());
                        eachDataRow.createCell(1).setCellValue(dto.getVendorName() == null ? "" : dto.getVendorName());
                        eachDataRow.createCell(2).setCellValue(dto.getExpenditureType() == null ? "" : dto.getExpenditureType());

                        if (null != dto.getPaymentDate()) {
                            eachDataRow.createCell(3).setCellValue(DateFormatUtils.ISO_DATE_FORMAT.format(dto.getPaymentDate()));
                        }
                        eachDataRow.createCell(4).setCellValue(dto.getCurrencyCode() == null ? "" : dto.getCurrencyCode());

                        if (null != dto.getAmount()) {
                            eachDataRow.createCell(5).setCellValue(dto.getAmount());
                        }
                    }
                }
            }

        });

    }
}