package hcux.eps.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hcux.core.util.HcuxConstant;
import hcux.eps.mapper.EpsCashFlowMapper;
import hcux.eps.util.CommonUtil;
import hcux.sys.dto.SysEntrustParty;
import hcux.sys.mapper.SysEntrustPartyMapper;
import hcux.util.excel.PoiUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hcux.eps.dto.EpsCashFlow;
import hcux.eps.service.IEpsCashFlowService;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class EpsCashFlowServiceImpl extends BaseServiceImpl<EpsCashFlow> implements IEpsCashFlowService {
    @Autowired
    private EpsCashFlowMapper epsCashFlowMapper;
    @Autowired
    private SysEntrustPartyMapper sysEntrustPartyMapper;

    @Override
    public int ebsDataToHap(IRequest iRequest) {
        epsCashFlowMapper.delete(new EpsCashFlow());
        List<EpsCashFlow> epsCashFlowList = new ArrayList<>();
        List<EpsCashFlow> epsCashFlowListWithoutReturnDate = epsCashFlowMapper.selectWithoutReturnDate();
        epsCashFlowListWithoutReturnDate.forEach(epsCashFlow -> {
            epsCashFlow.set__status(DTOStatus.ADD);
            List<EpsCashFlow> epsCashFlowListWithReceiptNumber = epsCashFlowMapper.selectWithReceiptSerialNumber(new EpsCashFlow() {{
                setReceiptNumber(epsCashFlow.getReceiptNumber());
                setSerialNumber(epsCashFlow.getSerialNumber());
            }});
            Set<Date> returnDateList = epsCashFlowListWithReceiptNumber.stream().filter(epsCashFlow1 -> epsCashFlow1.getReturnDate()!=null&&epsCashFlow1.getReturnAmount()!=null).map(EpsCashFlow::getReturnDate).collect(Collectors.toSet());
            if (returnDateList == null || returnDateList.isEmpty()) {
                epsCashFlowList.add(epsCashFlow);
            }else{
                for(Date returnDate:returnDateList){
                    Double totalReturnAmount=epsCashFlowListWithReceiptNumber.stream().filter(epsCashFlow1 -> epsCashFlow1.getReturnDate()==returnDate&&epsCashFlow1.getReturnAmount()!=null).mapToDouble(EpsCashFlow::getReturnAmount).sum();
                    if(totalReturnAmount!=null){
                        EpsCashFlow newEpsCashFlow=new EpsCashFlow(){{
                            setSerialNumber(epsCashFlow.getSerialNumber());
                            setReceiptNumber(epsCashFlow.getReceiptNumber());
                            setReturnDate(returnDate);
                            setReturnAmount(totalReturnAmount);
                            setBankName(epsCashFlow.getBankName());
                            setAmount(epsCashFlow.getAmount());
                            setNatureAmount(epsCashFlow.getNatureAmount());
                            setDescription(epsCashFlow.getDescription());
                            setExchangeRate(epsCashFlow.getExchangeRate());
                            setReceiptDate(epsCashFlow.getReceiptDate());
                            setCurrencyCode(epsCashFlow.getCurrencyCode());
                            set__status(DTOStatus.ADD);
                        }};
                        epsCashFlowList.add(newEpsCashFlow);
                    }
                }
            }
        });
        if (epsCashFlowList != null && !epsCashFlowList.isEmpty()) {
            self().batchUpdate(iRequest, epsCashFlowList);
        }
        return (epsCashFlowList == null ? 0 : (epsCashFlowList.size()));
    }

    @Override
    public List<EpsCashFlow> selectList(IRequest iRequest, EpsCashFlow epsCashFlow, int page, int pageSize) {
        SysEntrustParty sysEntrustParty = new SysEntrustParty();
        List<SysEntrustParty> sysEntrustPartyList = new ArrayList<>();

        if (epsCashFlow.getQueryType() != null) {
            if (epsCashFlow.getQueryType().equals(HcuxConstant.QUERY_TYPE.PORTAL)) {
                sysEntrustParty.setUserId(iRequest.getUserId());
            } else if (epsCashFlow.getQueryType().equals(HcuxConstant.QUERY_TYPE.WECHAT)) {
                sysEntrustParty.setUserName(epsCashFlow.getUserName());
            }
            if (sysEntrustParty.getUserId() != null || sysEntrustParty.getUserName() != null) {
                sysEntrustPartyList = sysEntrustPartyMapper.selectSerialNumberOuter(sysEntrustParty);

                List<String> serialNumberList = new ArrayList<>();
                if(sysEntrustPartyList!=null && !sysEntrustPartyList.isEmpty()){
                    serialNumberList = sysEntrustPartyList.stream().map(p -> p.getSerialNumber()).collect(Collectors.toList());
                }

                if(serialNumberList!=null&&!serialNumberList.isEmpty()){
                    epsCashFlow.setSerialNumberList(serialNumberList);
                } else {
                    return new ArrayList<>();
                }
            }
        }

        PageHelper.startPage(page, pageSize);
        List<EpsCashFlow> epsCashFlowList = epsCashFlowMapper.selectList(epsCashFlow);
        if (epsCashFlowList != null && !epsCashFlowList.isEmpty()) {
            List<EpsCashFlow> returnAmountList = epsCashFlowMapper.selectTotalReturnAmount(epsCashFlow);
            String stringTotalAmount = "";
            for (EpsCashFlow cashFlow : returnAmountList) {
                if (cashFlow.getTotalReturnAmount() != null) {
                    stringTotalAmount += (cashFlow.getCurrencyCode() + " " + cashFlow.getTotalReturnAmount() + ";  ");
                }
            }
            epsCashFlowList.get(0).setStringTotalReturnAmount(stringTotalAmount);

            EpsCashFlow epsCashFlowNature1 = epsCashFlowMapper.selectTotalNatureAmountOne(epsCashFlow);
            EpsCashFlow epsCashFlowNature2 = epsCashFlowMapper.selectTotalNatureAmountMore(epsCashFlow);
            if ((epsCashFlowNature1 != null) || epsCashFlowNature2 != null) {
                BigDecimal natureAmount1 = (epsCashFlowNature1 == null ? new BigDecimal(0) : CommonUtil.numberIsNull(epsCashFlowNature1.getTotalNatureAmount()));
                BigDecimal natureAmount2 = (epsCashFlowNature2 == null ? new BigDecimal(0) : CommonUtil.numberIsNull(epsCashFlowNature2.getTotalNatureAmount()));
                epsCashFlowList.get(0).setTotalNatureAmount(natureAmount1.add(natureAmount2));
            }
            List<EpsCashFlow> currencyCodeList = epsCashFlowMapper.selectCurrencyCode();
            List<String> currencyCodes = currencyCodeList.stream().map(EpsCashFlow::getCurrencyCode).collect(Collectors.toList());
            List<EpsCashFlow> receiptAmountList = epsCashFlowMapper.selectTotalAmountOne(epsCashFlow);
            List<EpsCashFlow> receiptAmountList2 = epsCashFlowMapper.selectTotalAmountMore(epsCashFlow);
            String stringTotalReceipt = "";
            for (String currencyCode : currencyCodes) {
                List<BigDecimal> cashList = receiptAmountList.stream().filter(cashFlow -> currencyCode.equals(cashFlow.getCurrencyCode()) && cashFlow.getTotalReceipt() != null).map(EpsCashFlow::getTotalReceipt).collect(Collectors.toList());
                List<BigDecimal> cashList2 = receiptAmountList2.stream().filter(cashFlow -> currencyCode.equals(cashFlow.getCurrencyCode()) && cashFlow.getTotalReceipt() != null).map(EpsCashFlow::getTotalReceipt).collect(Collectors.toList());
                if ((cashList != null && !cashList.isEmpty()) && !(cashList2 != null && !cashList2.isEmpty())) {
                    stringTotalReceipt += currencyCode + ":" + cashList.get(0) + "; ";
                }
                if (!(cashList != null && !cashList.isEmpty()) && (cashList2 != null && !cashList2.isEmpty())) {
                    stringTotalReceipt += currencyCode + ":" + cashList2.get(0) + "; ";
                }
                if ((cashList != null && !cashList.isEmpty()) && (cashList2 != null && !cashList2.isEmpty())) {
                    stringTotalReceipt += currencyCode + ":" + (cashList.get(0).add(cashList2.get(0))) + "; ";
                }
            }
            epsCashFlowList.get(0).setStringTotalReceipt(stringTotalReceipt);
        }
        return epsCashFlowList;
    }

    @Override
    public void exportExcel(IRequest iRequest, EpsCashFlow epsCashFlow, HttpServletResponse response) throws Exception {
        // 总记录数
        PageHelper.startPage(1, 1);
        Page<EpsCashFlow> list = (Page<EpsCashFlow>) epsCashFlowMapper.selectList(epsCashFlow);
        long totalRowCount = list.getTotal();

        // 导出EXCEL文件名称
        String fileName = "收款流水" + DateFormatUtils.format(new Date(), "yyyyMMdd-HHmmss");

        // 标题
        String[] titles = {"收款编号", "系列号","收款日期", "备注", "币种", "收款金额", "汇率", "收款本位币", "银行", "退款日期", "退款金额"};

        // 开始导入
        PoiUtil.exportExcelToWebsite(response, totalRowCount, fileName, titles, (eachSheet, startRowCount, endRowCount, currentPage, pageSize) -> {

            PageHelper.startPage(currentPage, pageSize);
            List<EpsCashFlow> detailList = epsCashFlowMapper.selectList(epsCashFlow);

            if (!CollectionUtils.isEmpty(detailList)) {

                for (int i = startRowCount; i <= endRowCount; i++) {
                    SXSSFRow eachDataRow = eachSheet.createRow(i);
                    if ((i - startRowCount) < detailList.size()) {

                        EpsCashFlow dto = detailList.get(i - startRowCount);

                        eachDataRow.createCell(0).setCellValue(dto.getReceiptNumber() == null ? "" : dto.getReceiptNumber());
                        eachDataRow.createCell(1).setCellValue(dto.getSerialNumber() == null ? "" : dto.getSerialNumber());
                        if (null != dto.getReceiptDate()) {
                            eachDataRow.createCell(2).setCellValue(DateFormatUtils.ISO_DATE_FORMAT.format(dto.getReceiptDate()));
                        }
                        eachDataRow.createCell(3).setCellValue(dto.getDescription() == null ? "" : dto.getDescription());
                        eachDataRow.createCell(4).setCellValue(dto.getCurrencyCode() == null ? "" : dto.getCurrencyCode());

                        if (dto.getAmount() == null) {
                            eachDataRow.createCell(5).setCellValue("");
                        } else {
                            eachDataRow.createCell(5).setCellValue(dto.getAmount().doubleValue());
                        }
                        if (dto.getExchangeRate() == null) {
                            eachDataRow.createCell(6).setCellValue("");
                        } else {
                            eachDataRow.createCell(6).setCellValue(dto.getExchangeRate().doubleValue());
                        }
                        if (dto.getNatureAmount() == null) {
                            eachDataRow.createCell(7).setCellValue("");
                        } else {
                            eachDataRow.createCell(7).setCellValue(dto.getNatureAmount().doubleValue());
                        }
                        eachDataRow.createCell(8).setCellValue(dto.getBankName() == null ? "" : dto.getBankName());
                        if (dto.getReturnAmount() != null) {
                            eachDataRow.createCell(9).setCellValue(DateFormatUtils.ISO_DATE_FORMAT.format(dto.getReturnDate()));
                        }
                        if (dto.getReturnAmount() == null) {
                            eachDataRow.createCell(10).setCellValue("");
                        } else {
                            eachDataRow.createCell(10).setCellValue(dto.getReturnAmount().doubleValue());
                        }
                    }
                }
            }

        });
    }

    @Override
    public void exportExcelForPortal(IRequest iRequest, EpsCashFlow epsCashFlow, HttpServletResponse response) throws Exception {
        SysEntrustParty sysEntrustParty = new SysEntrustParty();
        List<SysEntrustParty> sysEntrustPartyList = new ArrayList<>();

        if (epsCashFlow.getQueryType() != null) {
            if (epsCashFlow.getQueryType().equals(HcuxConstant.QUERY_TYPE.PORTAL)) {
                sysEntrustParty.setUserId(iRequest.getUserId());
                sysEntrustPartyList = sysEntrustPartyMapper.selectSerialNumberOuter(sysEntrustParty);

                List<String> serialNumberList = new ArrayList<>();
                if(sysEntrustPartyList!=null && !sysEntrustPartyList.isEmpty()){
                    serialNumberList = sysEntrustPartyList.stream().map(p -> p.getSerialNumber()).collect(Collectors.toList());
                }

                if(serialNumberList!=null&&!serialNumberList.isEmpty()){
                    epsCashFlow.setSerialNumberList(serialNumberList);
                }
            }
        }

        // 总记录数
        PageHelper.startPage(1, 1);
        Page<EpsCashFlow> list = (Page<EpsCashFlow>) epsCashFlowMapper.selectList(epsCashFlow);
        long totalRowCount = list.getTotal();

        // 导出EXCEL文件名称
        String fileName = "收款流水" + DateFormatUtils.format(new Date(), "yyyyMMdd-HHmmss");

        // 标题
        String[] titles = {"收款编号", "收款日期", "备注", "币种", "收款金额", "汇率", "收款本位币", "银行", "退款日期", "退款金额"};

        // 开始导入
        PoiUtil.exportExcelToWebsite(response, totalRowCount, fileName, titles, (eachSheet, startRowCount, endRowCount, currentPage, pageSize) -> {

            PageHelper.startPage(currentPage, pageSize);
            List<EpsCashFlow> detailList = epsCashFlowMapper.selectList(epsCashFlow);

            if (!CollectionUtils.isEmpty(detailList)) {

                for (int i = startRowCount; i <= endRowCount; i++) {
                    SXSSFRow eachDataRow = eachSheet.createRow(i);
                    if ((i - startRowCount) < detailList.size()) {

                        EpsCashFlow dto = detailList.get(i - startRowCount);

                        eachDataRow.createCell(0).setCellValue(dto.getReceiptNumber() == null ? "" : dto.getReceiptNumber());
                        if (null != dto.getReceiptDate()) {
                            eachDataRow.createCell(1).setCellValue(DateFormatUtils.ISO_DATE_FORMAT.format(dto.getReceiptDate()));
                        }
                        eachDataRow.createCell(2).setCellValue(dto.getDescription() == null ? "" : dto.getDescription());
                        eachDataRow.createCell(3).setCellValue(dto.getCurrencyCode() == null ? "" : dto.getCurrencyCode());

                        if (dto.getAmount() == null) {
                            eachDataRow.createCell(4).setCellValue("");
                        } else {
                            eachDataRow.createCell(4).setCellValue(dto.getAmount().doubleValue());
                        }
                        if (dto.getExchangeRate() == null) {
                            eachDataRow.createCell(5).setCellValue("");
                        } else {
                            eachDataRow.createCell(5).setCellValue(dto.getExchangeRate().doubleValue());
                        }
                        if (dto.getNatureAmount() == null) {
                            eachDataRow.createCell(6).setCellValue("");
                        } else {
                            eachDataRow.createCell(6).setCellValue(dto.getNatureAmount().doubleValue());
                        }
                        eachDataRow.createCell(7).setCellValue(dto.getBankName() == null ? "" : dto.getBankName());
                        if (dto.getReturnAmount() != null) {
                            eachDataRow.createCell(8).setCellValue(DateFormatUtils.ISO_DATE_FORMAT.format(dto.getReturnDate()));
                        }
                        if (dto.getReturnAmount() == null) {
                            eachDataRow.createCell(9).setCellValue("");
                        } else {
                            eachDataRow.createCell(9).setCellValue(dto.getReturnAmount().doubleValue());
                        }
                    }
                }
            }

        });
    }
}