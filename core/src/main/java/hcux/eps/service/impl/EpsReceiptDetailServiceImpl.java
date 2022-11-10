package hcux.eps.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hcux.eps.dto.EpsReceiptDetail;
import hcux.eps.mapper.EpsReceiptDetailMapper;
import hcux.eps.service.IEpsReceiptDetailService;
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
import java.util.Optional;


@Service
@Transactional(rollbackFor = Exception.class)
public class EpsReceiptDetailServiceImpl extends BaseServiceImpl<EpsReceiptDetail> implements IEpsReceiptDetailService {
    @Autowired
    private EpsReceiptDetailMapper epsReceiptDetailMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int epsReceiptDetailToHap(IRequest iRequest, EpsReceiptDetail epsReceiptDetail) {
        List<EpsReceiptDetail> epsReceiptDetails = epsReceiptDetailMapper.selectEpsReceiptDetailFromEbs(epsReceiptDetail);
        Optional<List<EpsReceiptDetail>> receiptDetailOptional = Optional.ofNullable(epsReceiptDetails);
        if (receiptDetailOptional.isPresent()) {
            epsReceiptDetails.forEach(x -> {
                EpsReceiptDetail epsReceiptDetail1 = epsReceiptDetailMapper.selectEpsReceiptDetailFromHap(x);
                Optional<EpsReceiptDetail> optional = Optional.ofNullable(epsReceiptDetail1);
                if (optional.isPresent()) {
                    epsReceiptDetail1.setProjectName(x.getProjectName());
                    epsReceiptDetail1.setGlDate(x.getGlDate());
                    epsReceiptDetail1.setCurrencyCode(x.getCurrencyCode());
                    epsReceiptDetail1.setCrAmount(x.getCrAmount());
                    epsReceiptDetail1.setCrBaseAmount(x.getCrBaseAmount());
                    epsReceiptDetail1.setMethodName(x.getMethodName());

                    epsReceiptDetail1.setExchangeRate(x.getExchangeRate());
                    epsReceiptDetail1.setReceiptsStatus(x.getReceiptsStatus());
                    epsReceiptDetail1.setCrAmount1(x.getCrAmount1());
                    epsReceiptDetail1.setCrBaseAmount1(x.getCrBaseAmount1());
                    epsReceiptDetail1.setReceiptNumber(x.getReceiptNumber());

                    epsReceiptDetail1.set__status(DTOStatus.UPDATE);
                    self().updateByPrimaryKeySelective(iRequest, epsReceiptDetail1);
                } else {
                    self().insertSelective(iRequest, x);
                }
            });
        }
        return epsReceiptDetails == null ? 0 : epsReceiptDetails.size();

    }

    @Override
    public List<EpsReceiptDetail> queryEpsReceiptDetail(IRequest iRequest, EpsReceiptDetail epsReceiptDetail, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<EpsReceiptDetail> list = epsReceiptDetailMapper.queryEpsReceiptDetail(epsReceiptDetail);
        DecimalFormat decimalFormat=new DecimalFormat(".00");//add yexiang.ren 19200
        if (!list.isEmpty()) {
            List<EpsReceiptDetail> list1 = epsReceiptDetailMapper.selectTotalAmount(epsReceiptDetail);
            if (!list1.isEmpty()) {
                double totalAmount=(double)(Math.round(list1.get(0).getTotalAmount()*100))/100;

                list.get(0).setTotalAmount(totalAmount);
                String stringTotalAmount="";
                for(EpsReceiptDetail receiptDetail:list1){
                    stringTotalAmount+=(receiptDetail.getCurrencyCode()+" "+decimalFormat.format(receiptDetail.getTotalAmount())+"; ");
                }
                list.get(0).setStringTotalAmount(stringTotalAmount);
            }
        }
        return list;
    }

    @Override
    public List<EpsReceiptDetail> selectTotalAmount(IRequest iRequest, EpsReceiptDetail epsReceiptDetail, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return epsReceiptDetailMapper.selectTotalAmount(epsReceiptDetail);
    }

    @Override
    public void exportExcel(IRequest requestContext, EpsReceiptDetail epsReceiptDetail, HttpServletResponse response) throws Exception {

        // 总记录数
        PageHelper.startPage(1, 1);
        Page<EpsReceiptDetail> list = (Page<EpsReceiptDetail>) epsReceiptDetailMapper.queryEpsReceiptDetail(epsReceiptDetail);
        long totalRowCount = list.getTotal();

        // 导出EXCEL文件名称
        String fileName = "收款明细" + DateFormatUtils.format(new Date(), "yyyyMMdd-HHmmss");

        // 标题
        String[] titles = {"发票号", "收款日期", "币种", "收款金额", "汇率"};

        // 开始导入
        PoiUtil.exportExcelToWebsite(response, totalRowCount, fileName, titles, (eachSheet, startRowCount, endRowCount, currentPage, pageSize) -> {

            PageHelper.startPage(currentPage, pageSize);
            List<EpsReceiptDetail> detailList = epsReceiptDetailMapper.queryEpsReceiptDetail(epsReceiptDetail);

            if (!CollectionUtils.isEmpty(detailList)) {

                for (int i = startRowCount; i <= endRowCount; i++) {
                    SXSSFRow eachDataRow = eachSheet.createRow(i);
                    if ((i - startRowCount) < detailList.size()) {

                        EpsReceiptDetail dto = detailList.get(i - startRowCount);

                        eachDataRow.createCell(0).setCellValue(dto.getProjectName() == null ? "" : dto.getProjectName());
                        eachDataRow.createCell(1).setCellValue(dto.getGlDate() == null ? "" : dto.getGlDate());
                        eachDataRow.createCell(2).setCellValue(dto.getCurrencyCode() == null ? "" : dto.getCurrencyCode());

                        if (null != dto.getCrAmount()) {
                            eachDataRow.createCell(3).setCellValue(dto.getCrAmount());
                        }

                        if (null != dto.getExchangeRate()) {
                            eachDataRow.createCell(4).setCellValue(dto.getExchangeRate());
                        }
                    }
                }
            }

        });
    }
}