package hcux.eps.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hcux.eps.dto.*;
import hcux.eps.mapper.*;
import hcux.eps.service.IEpsStatementsService;
import hcux.eps.util.CommonUtil;
import hcux.sys.dto.SysEntrustParty;
import hcux.sys.mapper.SysEntrustPartyMapper;
import hcux.util.excel.PoiUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

import static hcux.eps.util.EpsConstant.*;
import static java.math.BigDecimal.ROUND_HALF_UP;

@Service
@Transactional(rollbackFor = Exception.class)
public class EpsStatementsServiceImpl  extends BaseServiceImpl<EpsStatements> implements IEpsStatementsService {
    @Autowired
    private EpsStatementsMapper epsStatementsMapper;
    @Autowired
    private EpsBalanceMapper epsBalanceMapper;
    @Autowired
    private EpsPurchaseDetailMapper epsPurchaseDetailMapper;
    @Autowired
    private EpsAgencyFeeMapper epsAgencyFeeMapper;
    @Autowired
    private EpsJournalMapper epsJournalMapper;
    @Autowired
    private EpsReceiptDetailMapper epsReceiptDetailMapper;
    @Autowired
    private EpsBillMapper epsBillMapper;
    @Autowired
    private SysEntrustPartyMapper sysEntrustPartyMapper;
    @Autowired
    private EpsPaymentDetailMapper epsPaymentDetailMapper;
    @Autowired
    private EpsProjectNumMapper epsProjectNumMapper;

    @Override
    public List<EpsStatements> queryEpsStatements(IRequest iRequest, EpsStatements epsStatements, int page, int pageSize) {
        SysEntrustParty sysEntrustParty=new SysEntrustParty();
        sysEntrustParty.setUserId(iRequest.getUserId());
        List<SysEntrustParty> sysEntrustPartyList=new ArrayList<SysEntrustParty>();
        if(epsStatements.getQueryType()!=null){
            sysEntrustPartyList=sysEntrustPartyMapper.selectSerialNumberOuter(sysEntrustParty);
        }else{
            sysEntrustPartyList=sysEntrustPartyMapper.selectSerialNumber(sysEntrustParty);
        }
        List<String> serialNumberList=new ArrayList<String>();
        if(sysEntrustPartyList!=null&&!sysEntrustPartyList.isEmpty()){
            serialNumberList=sysEntrustPartyList.stream().map(p -> p.getSerialNumber()).collect(Collectors.toList());
        }
        PageHelper.startPage(page,pageSize);
        if(serialNumberList!=null&&!serialNumberList.isEmpty()){
            epsStatements.setSerialNumberList(serialNumberList);
            List<EpsStatements> epsStatementsList=epsStatementsMapper.selectEditableEpsStatements(epsStatements);
            EpsStatements statements=epsStatementsMapper.selectEditableTotalBalance(epsStatements);
            if(epsStatementsList!=null&&!epsStatementsList.isEmpty()){
                if(statements!=null&&statements.getTotalBalance()!=null){
                    epsStatementsList.get(0).setTotalBalance(statements.getTotalBalance());
                }
            }
            return epsStatementsList;
        }else{
            return new ArrayList<EpsStatements>();
        }
        // List<EpsStatements> epsStatementsList=epsStatementsMapper.selectEpsStatements(epsStatements);
        // epsStatementsList.forEach(x->{
        //     //出口调整项
        //     EpsStatements epsStatement = epsStatementsMapper.queryStatementByProjectNum(x);
        //     if (epsStatement != null) {
        //         x.setExportAdjustment(epsStatement.getExportAdjustment());
        //     }
        //
        //     //平均汇率
        //     BigDecimal averageRates=new BigDecimal(0);
        //     if(x.getSpecifiedAmount()!=null&&!(x.getSpecifiedAmount().compareTo(BigDecimal.ZERO)==0)) {
        //         averageRates = x.getEquivalentAmount().divide(x.getSpecifiedAmount(), 4,ROUND_HALF_UP);
        //     }
        //     //设置平均汇率
        //     x.setAverageRates(averageRates);
        //     //设置退税款,收汇金额<报关金额时设置为0
        //     if(CommonUtil.numberIsNull(x.getSpecifiedAmount()).compareTo(CommonUtil.numberIsNull(x.getCustomsAmount()))==-1){
        //      x.setTaxRefunds(new BigDecimal(0));
        //     }else{
        //       EpsStatements statements=epsStatementsMapper.queryTaxRefunds(x);
        //       if(statements!=null) {
        //           x.setTaxRefunds(CommonUtil.numberIsNull(statements.getTaxRefunds()));
        //       }
        //     }
        //     List<EpsBalance> epsBalanceList=epsBalanceMapper.selectEpsBalanceFromEbs(new EpsBalance(){{setAgmNumber(x.getProjectNum().substring(0,11));}});
        //     List<String> agentTypeCodeList=epsBalanceList.stream().map(k->k.getAgentTypeCode()).collect(Collectors.toList());
        //     //BigDecimal sumAgentAmount = epsBalanceList.stream().map(m-> CommonUtil.numberIsNull(m.getAgentAmount())).reduce(BigDecimal.ZERO,BigDecimal::add);
        //     //设置代理费
        //     if(agentTypeCodeList.contains(FIXED_CNY_AMOUNT)){
        //         x.setAgencyFee(CommonUtil.numberIsNull(x.getCustomsAmount()).multiply(CommonUtil.numberIsNull(epsBalanceList.get(0).getAgentAmount())));
        //     }else if(agentTypeCodeList.contains(FIXED_INVOICE_PROPORTION)){
        //         EpsPurchaseDetail epsPurchaseDetail=epsPurchaseDetailMapper.querySumCrBaseAmount(new EpsPurchaseDetail(){{setProjectNum(x.getProjectNum());}});
        //         x.setAgencyFee(CommonUtil.numberIsNull(epsPurchaseDetail.getSumCrBaseAmount()).multiply(CommonUtil.numberIsNull(epsBalanceList.get(0).getAgentAmount())));
        //     }else if(agentTypeCodeList.contains(FIXED_PROPORTION)){
        //         x.setAgencyFee(CommonUtil.numberIsNull(x.getEquivalentAmount()).multiply(CommonUtil.numberIsNull(epsBalanceList.get(0).getAgentAmount())));
        //     }else if(agentTypeCodeList.contains(EXCHANGE_RATE)){
        //         BigDecimal sumBuyOutPrice=new BigDecimal(0);
        //         for(EpsBalance epsBalance:epsBalanceList){
        //             List<EpsAgencyFee> epsAgencyFeeList=epsAgencyFeeMapper.queryEpsAgencyFee(new EpsAgencyFee(){{
        //                 setBackTax(epsBalance.getRebateRate());
        //                 setProjectNumber(x.getProjectNum()); }});//各档开票金额
        //             if(epsAgencyFeeList!=null&&!epsAgencyFeeList.isEmpty()){
        //                 BigDecimal buyOutPrice=averageRates.multiply(epsBalance.getRetio()).divide(epsBalance.getRatioRate(),2,ROUND_HALF_UP);//买断价
        //                 sumBuyOutPrice.add((buyOutPrice.subtract(averageRates)).divide(buyOutPrice,2,ROUND_HALF_UP).multiply(epsAgencyFeeList.get(0).getExtendedPrice()));
        //             }
        //         }
        //         x.setAgencyFee(CommonUtil.numberIsNull(x.getTaxRefunds()).subtract(sumBuyOutPrice));
        //     }
        //     //余额1=等值金额+退税款-已付货款-代理费-已付运费-出口其他费用
        //     x.setBalance1(CommonUtil.numberIsNull(x.getEquivalentAmount()).add(CommonUtil.numberIsNull(x.getTaxRefunds())).subtract(CommonUtil.numberIsNull(x.getHasPayment())).
        //             subtract(CommonUtil.numberIsNull(x.getAgencyFee())).subtract(CommonUtil.numberIsNull(x.getPrepaidFreight())).subtract(CommonUtil.numberIsNull(x.getOtherExportExpenses())));
        //     //余额=余额1-出口调整
        //     x.setBalance(x.getBalance1().subtract(CommonUtil.numberIsNull(x.getExportAdjustment())));
        // });
    }

    @Override
    public EpsStatements queryStatementByProjectNum (IRequest requestContext, EpsStatements epsStatement) {
        return epsStatementsMapper.queryStatementByProjectNum(epsStatement);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void epsStatementsToHap(IRequest iRequest,EpsStatements epsStatements1) {
        //删掉数据库所有数据
//        epsStatementsMapper.delete(new EpsStatements());
        //全量数据
        List<EpsStatements> epsStatementsList=epsStatementsMapper.selectEpsStatementsFromEbs();
        //增量数据
        List<EpsStatements> statementsList=epsStatementsMapper.selectEpsStatementsAddFromEbs();

        //白条发票包含的项目号的退税款统一置为空
        List<EpsProjectNum> epsProjectNums = epsProjectNumMapper.selectAll();
        Set<String> projectNums = epsProjectNums.stream().map(k -> k.getProjectNum()).collect(Collectors.toSet());

        if(epsStatementsList!=null&&!epsStatementsList.isEmpty()){
            epsStatementsList.forEach(epsStatements -> {
                        EpsStatements oneStatements=epsStatementsMapper.selectStatementsByProjectNum(epsStatements);
                        if(oneStatements!=null){
                            //epsStatements.setStatementsId(oneStatements.getStatementsId());
                            epsStatements.setExportAdjustment(oneStatements.getExportAdjustment());
                            epsStatements.setRemark(oneStatements.getRemark());
                            //epsStatements.setObjectVersionNumber(oneStatements.getObjectVersionNumber());
                            epsStatementsMapper.deleteByProject(oneStatements);
                        }
                        EpsStatements statements = epsStatementsMapper.selectCalculateStatementsFromEbs(epsStatements);
                        if (statements != null) {
                            epsStatements.setCustomsAmount(statements.getCustomsAmount() == null ? null : statements.getCustomsAmount());
                            epsStatements.setSpecifiedAmount(statements.getSpecifiedAmount() == null ? null : statements.getSpecifiedAmount());
                            epsStatements.setReceiptCny(statements.getReceiptCny() == null ? null : statements.getReceiptCny());
                            epsStatements.setEquivalentAmount(statements.getEquivalentAmount() == null ? null : statements.getEquivalentAmount());
                            epsStatements.setHasPayment(statements.getHasPayment() == null ? null : statements.getHasPayment());
                            epsStatements.setUnPayment(statements.getUnPayment() == null ? null : statements.getUnPayment());
                            epsStatements.setDomesticFreight(statements.getDomesticFreight() == null ? null : statements.getDomesticFreight());
                            epsStatements.setOtherExportExpenses(statements.getOtherExportExpenses() == null ? null : statements.getOtherExportExpenses());
                            epsStatements.setTaxRefunds(statements.getTaxRefunds() == null ? null : statements.getTaxRefunds());

                        }
                        BigDecimal averageRates = new BigDecimal(0);
                        if (epsStatements.getSpecifiedAmount() != null && !(epsStatements.getSpecifiedAmount().compareTo(BigDecimal.ZERO) == 0)) {
                            if (epsStatements.getEquivalentAmount() != null) {
                                averageRates = epsStatements.getEquivalentAmount().divide(epsStatements.getSpecifiedAmount(), 4, ROUND_HALF_UP);
                            }
                        }
                        //设置平均汇率
                        epsStatements.setAverageRates(averageRates);
                        //设置退税金额
                        if (projectNums.contains(epsStatements.getProjectNum())) {
                            epsStatements.setTaxRefunds(null);
                        } else {
                            EpsBill epsBill = epsBillMapper.selectTotalRemainAmount(new EpsBill() {{
                                setProjectName(epsStatements.getProjectNum());
                            }});
                            if (epsBill != null && epsBill.getTotalRemainAmount() != null && epsBill.getTotalRemainAmount().compareTo(BigDecimal.ZERO) == 1) {
                                epsStatements.setTaxRefunds(null);
                            } else {
                                EpsStatements statements1 = epsStatementsMapper.queryTaxRefunds(epsStatements);
                                if (statements1 != null) {
                                    epsStatements.setTaxRefunds(CommonUtil.numberIsNull(statements1.getTaxRefunds()));
                                }
                            }
                        }

                        //设置代理费1
                        List<EpsBalance> epsBalanceList = epsBalanceMapper.selectEpsBalanceFromEbs(new EpsBalance() {{
                            setAgmNumber(epsStatements.getProjectNum().substring(0, 11));
                        }});
                      if(epsStatements.getTaxRefunds()!=null){
                        if (epsBalanceList != null && !epsBalanceList.isEmpty()) {
                            List<String> agentTypeCodeList = epsBalanceList.stream().map(k -> k.getAgentTypeCode()).collect(Collectors.toList());
                            if (agentTypeCodeList.contains(FIXED_CNY_AMOUNT)) {
                                if (epsStatements.getCustomsAmount() != null && epsBalanceList.get(0).getAgentAmount() != null) {
                                    epsStatements.setAgencyFee1((epsStatements.getCustomsAmount().multiply(epsBalanceList.get(0).getAgentAmount())).setScale(2, ROUND_HALF_UP));
                                    epsStatements.setAgencyFee(epsStatements.getAgencyFee1());
                                }
                            } else if (agentTypeCodeList.contains(FIXED_INVOICE_PROPORTION)) {
                                EpsPurchaseDetail epsPurchaseDetail = epsPurchaseDetailMapper.querySumCrBaseAmount(new EpsPurchaseDetail() {{
                                    setProjectNum(epsStatements.getProjectNum());
                                }});
                                if (epsPurchaseDetail != null) {
                                    if (epsPurchaseDetail.getSumCrBaseAmount() != null && epsBalanceList.get(0).getAgentAmount() != null) {
                                        epsStatements.setAgencyFee1((epsPurchaseDetail.getSumCrBaseAmount().multiply(epsBalanceList.get(0).getAgentAmount()).divide(new BigDecimal(100), 2, ROUND_HALF_UP)));
                                        epsStatements.setAgencyFee(epsStatements.getAgencyFee1());
                                    }
                                }
                            } else if (agentTypeCodeList.contains(FIXED_PROPORTION)) {
                                EpsReceiptDetail epsReceiptDetail = epsReceiptDetailMapper.selectTotalBaseAmount1(new EpsReceiptDetail() {{
                                    setProjectName(epsStatements.getProjectNum());
                                }});
                                if (epsReceiptDetail != null) {
                                    if (epsReceiptDetail.getCrBaseAmount1Total() != null && epsBalanceList.get(0).getAgentAmount() != null) {
                                        epsStatements.setAgencyFee1((epsReceiptDetail.getCrBaseAmount1Total().multiply(epsBalanceList.get(0).getAgentAmount()).divide(new BigDecimal(100), 2, ROUND_HALF_UP)));
                                        epsStatements.setAgencyFee(epsStatements.getAgencyFee1());
                                    }
                                }
                            } else if (agentTypeCodeList.contains(EXCHANGE_RATE)) {
                                SysEntrustParty sysEntrustParty = sysEntrustPartyMapper.selectPriceDecimal(new SysEntrustParty() {{
                                    setSerialNumber(epsStatements.getProjectNum().substring(1, 9));
                                }});
                                int priceDecimal = 0;
                                if (sysEntrustParty != null && sysEntrustParty.getPriceDecimal() != null) {
                                    priceDecimal = Integer.parseInt(sysEntrustParty.getPriceDecimal());
                                }
                                for (EpsBalance epsBalance : epsBalanceList) {
                                    List<EpsAgencyFee> epsAgencyFeeList = epsAgencyFeeMapper.queryInvoiceAmount(new EpsAgencyFee() {{
                                        setProjectNumber(epsStatements.getProjectNum());
                                        setBackTax(epsBalance.getRebateRate());
                                        setInTaxPercent(epsBalance.getVat());
                                    }});//各档开票金额
                                    if (epsAgencyFeeList != null && !epsAgencyFeeList.isEmpty()) {
                                        BigDecimal buyOutPrice = new BigDecimal(0);
                                        if (priceDecimal == 0 || priceDecimal == 1) {
                                            if (epsBalance.getRatioRate() != null && CommonUtil.numberIsNull(epsBalance.getRatioRate()).compareTo(new BigDecimal(0)) != 0) {
                                                buyOutPrice = averageRates.multiply(CommonUtil.numberIsNull(epsBalance.getRetio())).divide(epsBalance.getRatioRate(), 2, ROUND_HALF_UP);//买断价
                                            }
                                        } else if (priceDecimal == 2) {
                                            if (epsBalance.getRatioRate() != null && CommonUtil.numberIsNull(epsBalance.getRatioRate()).compareTo(new BigDecimal(0)) != 0) {
                                                buyOutPrice = averageRates.multiply(CommonUtil.numberIsNull(epsBalance.getRetio())).divide(epsBalance.getRatioRate(), 4, ROUND_HALF_UP);//买断价
                                            }
                                        } else if (priceDecimal == 3) {
                                            if (epsBalance.getRatioRate() != null && CommonUtil.numberIsNull(epsBalance.getRatioRate()).compareTo(new BigDecimal(0)) != 0) {
                                                buyOutPrice = averageRates.multiply(CommonUtil.numberIsNull(epsBalance.getRetio())).divide(epsBalance.getRatioRate(), 2, BigDecimal.ROUND_DOWN);//买断价
                                            }
                                        } else if (priceDecimal == 4) {
                                            if (epsBalance.getRatioRate() != null && CommonUtil.numberIsNull(epsBalance.getRatioRate()).compareTo(new BigDecimal(0)) != 0) {
                                                buyOutPrice = averageRates.multiply(CommonUtil.numberIsNull(epsBalance.getRetio())).divide(epsBalance.getRatioRate(), 4, BigDecimal.ROUND_DOWN);//买断价
                                            }
                                        }
                                        for (EpsAgencyFee epsAgencyFee : epsAgencyFeeList) {
                                            if (buyOutPrice.compareTo(BigDecimal.ZERO) != 0) {
                                                BigDecimal sumBuyOutPrice = (buyOutPrice.subtract(averageRates)).multiply(CommonUtil.numberIsNull(epsAgencyFee.getInvoiceAmount()).divide(buyOutPrice, 2, ROUND_HALF_UP));
                                                epsStatements.setAgencyFee1(CommonUtil.numberIsNull(epsStatements.getAgencyFee1()).add(sumBuyOutPrice).setScale(2, ROUND_HALF_UP));
                                            }
                                        }
                                    }
                                }
                                epsStatements.setAgencyFee1((CommonUtil.numberIsNull(epsStatements.getTaxRefunds()).subtract(CommonUtil.numberIsNull(epsStatements.getAgencyFee1()))).setScale(2, ROUND_HALF_UP));
                            }
                            if (CommonUtil.numberIsNull(epsStatements.getAgencyFee1()).compareTo(CommonUtil.numberIsNull(epsBalanceList.get(0).getMinAgentAmount())) == -1) {
                                epsStatements.setAgencyFee1(CommonUtil.numberIsNull(epsBalanceList.get(0).getMinAgentAmount()).setScale(2, ROUND_HALF_UP));
                            }
                            if (epsStatements.getAgencyFee() != null) {
                                if (epsStatements.getAgencyFee().compareTo(CommonUtil.numberIsNull(epsBalanceList.get(0).getMinAgentAmount())) == -1) {
                                    epsStatements.setAgencyFee(CommonUtil.numberIsNull(epsBalanceList.get(0).getMinAgentAmount()).setScale(2, ROUND_HALF_UP));
                                }
                            }
                        }
                    }
                List<EpsJournal> epsJournalList=epsJournalMapper.selectEpsJournal(new EpsJournal(){{setProjectNumber(epsStatements.getProjectNum());}});
                if(epsJournalList!=null&&!epsJournalList.isEmpty()&&epsJournalList.get(0)!=null){
                    EpsJournal epsJournal=epsJournalList.get(0);
                    //营业外收入/支出
                    epsStatements.setIncomeExpenses((CommonUtil.numberIsNull(epsJournal.getLossCompensationIncome()).add(CommonUtil.numberIsNull(epsJournal.getUnablePay())).
                            subtract(CommonUtil.numberIsNull(epsJournal.getOther())).subtract(CommonUtil.numberIsNull(epsJournal.getImpairmentLoss()))).setScale(2,ROUND_HALF_UP));
                    //财务费
                    epsStatements.setFinancialCost((CommonUtil.numberIsNull(epsJournal.getInterestPayments()).add(CommonUtil.numberIsNull(epsJournal.getPoundage())).
                            add(CommonUtil.numberIsNull(epsJournal.getCityMaintenanceConstructionTax())).add(CommonUtil.numberIsNull(epsJournal.getEducationAttached()))
                            .add(CommonUtil.numberIsNull(epsJournal.getTailAdjustment()))).setScale(2,ROUND_HALF_UP));
                    //报销款
                    epsStatements.setReimbursement((CommonUtil.numberIsNull(epsJournal.getOffice()).add(CommonUtil.numberIsNull(epsJournal.getStorageCharges()))
                            .add(CommonUtil.numberIsNull(epsJournal.getTravelExpenses())).add(CommonUtil.numberIsNull(epsJournal.getVehicleFee()))
                            .add(CommonUtil.numberIsNull(epsJournal.getWelfareFunds())).add(CommonUtil.numberIsNull(epsJournal.getInspectionFee()))
                            .add(CommonUtil.numberIsNull(epsJournal.getCourierFee()).add(CommonUtil.numberIsNull(epsJournal.getTraining())))
                            .add(CommonUtil.numberIsNull(epsJournal.getSampleCharge())).add(CommonUtil.numberIsNull(epsJournal.getBusinessPublicity()))
                            .add(CommonUtil.numberIsNull(epsJournal.getBusinessEntertainment())).add(CommonUtil.numberIsNull(epsJournal.getPostalCharges()))
                            .add(CommonUtil.numberIsNull(epsJournal.getTransportationCharges())).add(CommonUtil.numberIsNull(epsJournal.getMailExhibitionFee()))
                    ).setScale(2,ROUND_HALF_UP));
                }
                //余额1
                epsStatements.setBalance1((CommonUtil.numberIsNull(epsStatements.getEquivalentAmount()).add(CommonUtil.numberIsNull(epsStatements.getReceiptCny()))
                        .add(CommonUtil.numberIsNull(epsStatements.getTaxRefunds()))
                        .add(CommonUtil.numberIsNull(epsStatements.getIncomeExpenses())).subtract(CommonUtil.numberIsNull(epsStatements.getHasPayment())).
                                subtract(CommonUtil.numberIsNull(epsStatements.getAgencyFee1())).subtract(CommonUtil.numberIsNull(epsStatements.getDomesticFreight()))
                        .subtract(CommonUtil.numberIsNull(epsStatements.getOtherExportExpenses())).subtract(CommonUtil.numberIsNull(epsStatements.getFinancialCost()))
                        .subtract(CommonUtil.numberIsNull(epsStatements.getReimbursement()))).setScale(2,ROUND_HALF_UP));
                //余额
                epsStatements.setBalance(CommonUtil.numberIsNull((epsStatements.getBalance1()).subtract(CommonUtil.numberIsNull(epsStatements.getExportAdjustment()))).setScale(2,ROUND_HALF_UP));
                //插入数据
                self().insertSelective(iRequest, epsStatements);
            });
        }
        if(statementsList!=null&&!statementsList.isEmpty()){
            statementsList.forEach(epsStatements -> {
                EpsStatements oneStatements=epsStatementsMapper.selectStatementsByProjectNum(epsStatements);
                if(oneStatements!=null){
                    //epsStatements.setStatementsId(oneStatements.getStatementsId());
                    epsStatements.setExportAdjustment(oneStatements.getExportAdjustment());
                    epsStatements.setRemark(oneStatements.getRemark());
                    //epsStatements.setObjectVersionNumber(oneStatements.getObjectVersionNumber());
                    epsStatementsMapper.deleteByProject(oneStatements);
                }
                EpsStatements statements=epsStatementsMapper.selectCalculateStatementsAddFromEbs(epsStatements);
                if(statements!=null) {
//                    epsStatements.setCustomsAmount(statements.getCustomsAmount() == null ? null : statements.getCustomsAmount());
                    epsStatements.setSpecifiedAmount(statements.getSpecifiedAmount() == null ? null : statements.getSpecifiedAmount());
                    epsStatements.setReceiptCny(statements.getReceiptCny() == null ? null : statements.getReceiptCny());
                    epsStatements.setEquivalentAmount(statements.getEquivalentAmount() == null ? null : statements.getEquivalentAmount());
                    epsStatements.setHasPayment(statements.getHasPayment() == null ? null : statements.getHasPayment());
//                    epsStatements.setUnPayment(statements.getUnPayment() == null ? null : statements.getUnPayment());
                    epsStatements.setDomesticFreight(statements.getDomesticFreight() == null ? null : statements.getDomesticFreight());
//                    epsStatements.setOtherExportExpenses(statements.getOtherExportExpenses() == null ? null : statements.getOtherExportExpenses());
//                    epsStatements.setTaxRefunds(statements.getTaxRefunds() == null ? null : statements.getTaxRefunds());
                }
                //计算收汇金额
                EpsReceiptDetail receiptDetail=new EpsReceiptDetail(){{setProjectName(epsStatements.getProjectNum());}};
                EpsReceiptDetail receiptDetailOfCrAmount=epsReceiptDetailMapper.selectTotalCrAmount(receiptDetail);
                EpsReceiptDetail receiptDetailOfReturnAmount=epsReceiptDetailMapper.selectTotalReturnAmount(receiptDetail);
                if(epsStatements.getSpecifiedAmount()!=null||receiptDetailOfCrAmount!=null||receiptDetailOfReturnAmount!=null){
                    epsStatements.setSpecifiedAmount((CommonUtil.numberIsNull(epsStatements.getSpecifiedAmount())
                            .add(receiptDetailOfCrAmount==null?new BigDecimal(0):CommonUtil.numberIsNull(receiptDetailOfCrAmount.getTotalCrAmount()
                                    .add(receiptDetailOfReturnAmount==null?new BigDecimal(0):CommonUtil.numberIsNull(receiptDetailOfReturnAmount.getTotalReturnAmount()))))).setScale(2,ROUND_HALF_UP));
                }
                //计算人民币收款
                EpsReceiptDetail receiptDetailOfCrAmountCny=epsReceiptDetailMapper.selectTotalCrAmountCny(receiptDetail);
                EpsReceiptDetail receiptDetailOfReturnAmountCny=epsReceiptDetailMapper.selectTotalReturnAmountCny(receiptDetail);
                if(epsStatements.getReceiptCny()!=null||receiptDetailOfCrAmountCny!=null||receiptDetailOfReturnAmountCny!=null){
                    epsStatements.setReceiptCny((CommonUtil.numberIsNull(epsStatements.getReceiptCny())
                            .add(receiptDetailOfCrAmountCny==null?new BigDecimal(0):CommonUtil.numberIsNull(receiptDetailOfCrAmountCny.getTotalCrAmount()
                            .add(receiptDetailOfReturnAmountCny==null?new BigDecimal(0):CommonUtil.numberIsNull(receiptDetailOfReturnAmountCny.getTotalReturnAmount()))))).setScale(2,ROUND_HALF_UP));
                }
                //计算等值金额
                EpsReceiptDetail receiptDetailOfReturnAmountEqu=epsReceiptDetailMapper.selectTotalReturnAmountEqu(receiptDetail);
                EpsReceiptDetail receiptDetailOfCrBaseAmount1=epsReceiptDetailMapper.selectTotalCrBaseAmount1(receiptDetail);
                if(epsStatements.getEquivalentAmount()!=null||receiptDetailOfCrBaseAmount1!=null||receiptDetailOfReturnAmountEqu!=null){
                    epsStatements.setEquivalentAmount((CommonUtil.numberIsNull(epsStatements.getEquivalentAmount())
                            .add(receiptDetailOfCrBaseAmount1==null?new BigDecimal(0):CommonUtil.numberIsNull(receiptDetailOfCrBaseAmount1.getTotalCrAmount()
                            .add(receiptDetailOfReturnAmountEqu==null?new BigDecimal(0):CommonUtil.numberIsNull(receiptDetailOfReturnAmountEqu.getTotalReturnAmount()))))).setScale(2,ROUND_HALF_UP));
                }
                //设置平均汇率
                BigDecimal averageRates=new BigDecimal(0);
                if(epsStatements.getSpecifiedAmount()!=null&&!(epsStatements.getSpecifiedAmount().compareTo(BigDecimal.ZERO)==0)) {
                    if(epsStatements.getEquivalentAmount()!=null){
                        averageRates = epsStatements.getEquivalentAmount().divide(epsStatements.getSpecifiedAmount(), 4,ROUND_HALF_UP);
                    }
                }
                epsStatements.setAverageRates(averageRates);
                //已付货款
                EpsPaymentDetail epsPaymentDetail=new EpsPaymentDetail();
                epsPaymentDetail.setExpenditureType("货款");
                epsPaymentDetail.setProjectNum(epsStatements.getProjectNum());
                EpsPaymentDetail paymentDetail=epsPaymentDetailMapper.selectTotalLineAmount(epsPaymentDetail);
                if(epsStatements.getHasPayment()!=null||paymentDetail!=null){
                    epsStatements.setHasPayment((CommonUtil.numberIsNull(epsStatements.getHasPayment()).add(paymentDetail==null?new BigDecimal(0):CommonUtil.numberIsNull(paymentDetail.getTotalLineAmount()))).setScale(2,ROUND_HALF_UP));
                }
                //国内运费
                epsPaymentDetail.setExpenditureType("出口-国内运费");
                EpsPaymentDetail paymentDetail2=epsPaymentDetailMapper.selectTotalLineAmount(epsPaymentDetail);
                if(epsStatements.getDomesticFreight()!=null||paymentDetail2!=null){
                    epsStatements.setDomesticFreight((CommonUtil.numberIsNull(epsStatements.getDomesticFreight()).add(paymentDetail2==null?new BigDecimal(0):CommonUtil.numberIsNull(paymentDetail2.getTotalLineAmount()))).setScale(2,ROUND_HALF_UP));
                }
                //设置退税金额
                // EpsBill epsBill=epsBillMapper.selectTotalRemainAmount(new EpsBill(){{setProjectName(epsStatements.getProjectNum());}});
                // if(epsBill!=null&&epsBill.getTotalRemainAmount()!=null&&epsBill.getTotalRemainAmount().compareTo(BigDecimal.ZERO)==1){
                //     epsStatements.setTaxRefunds(BigDecimal.ZERO);
                // }else{
                //     EpsStatements statements1=epsStatementsMapper.queryTaxRefunds(epsStatements);
                //     if(statements1!=null) {
                //         epsStatements.setTaxRefunds(CommonUtil.numberIsNull(statements1.getTaxRefunds()));
                //     }
                // }
                //设置代理费1
                // List<EpsBalance> epsBalanceList=epsBalanceMapper.selectEpsBalanceFromEbs(new EpsBalance(){{setAgmNumber(epsStatements.getProjectNum().substring(0,11));}});
                // if(epsBalanceList!=null&&!epsBalanceList.isEmpty()){
                //     List<String> agentTypeCodeList=epsBalanceList.stream().map(k->k.getAgentTypeCode()).collect(Collectors.toList());
                //     if(agentTypeCodeList.contains(FIXED_CNY_AMOUNT)){
                //         if(epsStatements.getCustomsAmount()!=null&&epsBalanceList.get(0).getAgentAmount()!=null){
                //             epsStatements.setAgencyFee1((epsStatements.getCustomsAmount().multiply(epsBalanceList.get(0).getAgentAmount())).divide(new BigDecimal(100),2,ROUND_HALF_UP));
                //             epsStatements.setAgencyFee(epsStatements.getAgencyFee1());
                //         }
                //     }else if(agentTypeCodeList.contains(FIXED_INVOICE_PROPORTION)){
                //         EpsPurchaseDetail epsPurchaseDetail=epsPurchaseDetailMapper.querySumCrBaseAmount(new EpsPurchaseDetail(){{setProjectNum(epsStatements.getProjectNum());}});
                //         if(epsPurchaseDetail!=null){
                //             if(epsPurchaseDetail.getSumCrBaseAmount()!=null&&epsBalanceList.get(0).getAgentAmount()!=null){
                //                 epsStatements.setAgencyFee1((epsPurchaseDetail.getSumCrBaseAmount().multiply(epsBalanceList.get(0).getAgentAmount()).divide(new BigDecimal(100),2,ROUND_HALF_UP)));
                //                 epsStatements.setAgencyFee(epsStatements.getAgencyFee1());
                //             }
                //         }
                //     }else if(agentTypeCodeList.contains(FIXED_PROPORTION)){
                //         EpsReceiptDetail epsReceiptDetail=epsReceiptDetailMapper.selectTotalBaseAmount1(new EpsReceiptDetail(){{setProjectName(epsStatements.getProjectNum());}});
                //         if(epsReceiptDetail!=null){
                //             if(epsReceiptDetail.getCrBaseAmount1Total()!=null&&epsBalanceList.get(0).getAgentAmount()!=null){
                //                 epsStatements.setAgencyFee1((epsReceiptDetail.getCrBaseAmount1Total().multiply(epsBalanceList.get(0).getAgentAmount())).divide(new BigDecimal(100),2,ROUND_HALF_UP));
                //                 epsStatements.setAgencyFee(epsStatements.getAgencyFee1());
                //             }
                //         }
                //     }else if(agentTypeCodeList.contains(EXCHANGE_RATE)){
                //         BigDecimal sumBuyOutPrice=new BigDecimal(0);
                //         for(EpsBalance epsBalance:epsBalanceList) {
                //             List<EpsAgencyFee> epsAgencyFeeList=epsAgencyFeeMapper.queryInvoiceAmount(new EpsAgencyFee(){{
                //                 setProjectNumber(epsStatements.getProjectNum());
                //                 setBackTax(epsBalance.getRebateRate());
                //                 setInTaxPercent(epsBalance.getVat());
                //             }});//各档开票金额
                //             if(epsAgencyFeeList!=null&&!epsAgencyFeeList.isEmpty()){
                //                 BigDecimal buyOutPrice=averageRates.multiply(epsBalance.getRetio()).divide(epsBalance.getRatioRate(),2,ROUND_HALF_UP);//买断价
                //                 for(EpsAgencyFee epsAgencyFee:epsAgencyFeeList){
                //                     if(buyOutPrice.compareTo(BigDecimal.ZERO)!=0){
                //                         sumBuyOutPrice.add((buyOutPrice.subtract(averageRates)).divide(buyOutPrice,2,ROUND_HALF_UP).multiply(CommonUtil.numberIsNull(epsAgencyFee.getExtendedPrice())));
                //                     }
                //                 }
                //             }
                //         }
                //         epsStatements.setAgencyFee1((CommonUtil.numberIsNull(epsStatements.getTaxRefunds()).subtract(sumBuyOutPrice)).setScale(2,ROUND_HALF_UP));
                //     }
                //     if(CommonUtil.numberIsNull(epsStatements.getAgencyFee1()).compareTo(CommonUtil.numberIsNull(epsBalanceList.get(0).getMinAgentAmount()))==-1){
                //         epsStatements.setAgencyFee1((epsBalanceList.get(0).getMinAgentAmount()).setScale(2,ROUND_HALF_UP));
                //     }
                //     if(epsStatements.getAgencyFee()!=null){
                //         if(epsStatements.getAgencyFee().compareTo(CommonUtil.numberIsNull(epsBalanceList.get(0).getMinAgentAmount()))==-1){
                //             epsStatements.setAgencyFee((epsBalanceList.get(0).getMinAgentAmount()).setScale(2,ROUND_HALF_UP));
                //         }
                //     }
                // }
                // List<EpsJournal> epsJournalList=epsJournalMapper.selectEpsJournal(new EpsJournal(){{setProjectNumber(epsStatements.getProjectNum());}});
                // if(epsJournalList!=null&&!epsJournalList.isEmpty()&&epsJournalList.get(0)!=null){
                //     EpsJournal epsJournal=epsJournalList.get(0);
                //     //营业外收入/支出
                //     epsStatements.setIncomeExpenses((CommonUtil.numberIsNull(epsJournal.getLossCompensationIncome()).add(CommonUtil.numberIsNull(epsJournal.getUnablePay())).
                //             subtract(CommonUtil.numberIsNull(epsJournal.getOther())).subtract(CommonUtil.numberIsNull(epsJournal.getImpairmentLoss()))).setScale(2,ROUND_HALF_UP));
                //     //财务费
                //     epsStatements.setFinancialCost((CommonUtil.numberIsNull(epsJournal.getInterestPayments()).add(CommonUtil.numberIsNull(epsJournal.getPoundage())).
                //             add(CommonUtil.numberIsNull(epsJournal.getCityMaintenanceConstructionTax())).add(CommonUtil.numberIsNull(epsJournal.getEducationAttached()))
                //             .add(CommonUtil.numberIsNull(epsJournal.getTailAdjustment()))).setScale(2,ROUND_HALF_UP));
                //     //报销款
                //     epsStatements.setReimbursement((CommonUtil.numberIsNull(epsJournal.getOffice()).add(CommonUtil.numberIsNull(epsJournal.getStorageCharges()))
                //             .add(CommonUtil.numberIsNull(epsJournal.getTravelExpenses())).add(CommonUtil.numberIsNull(epsJournal.getVehicleFee()))
                //             .add(CommonUtil.numberIsNull(epsJournal.getWelfareFunds())).add(CommonUtil.numberIsNull(epsJournal.getInspectionFee()))
                //             .add(CommonUtil.numberIsNull(epsJournal.getCourierFee()).add(CommonUtil.numberIsNull(epsJournal.getTraining())))
                //             .add(CommonUtil.numberIsNull(epsJournal.getSampleCharge())).add(CommonUtil.numberIsNull(epsJournal.getBusinessPublicity()))
                //             .add(CommonUtil.numberIsNull(epsJournal.getBusinessEntertainment())).add(CommonUtil.numberIsNull(epsJournal.getPostalCharges()))
                //             .add(CommonUtil.numberIsNull(epsJournal.getTransportationCharges())).add(CommonUtil.numberIsNull(epsJournal.getMailExhibitionFee()))
                //                 ).setScale(2,ROUND_HALF_UP));
                //}
                //余额1
                epsStatements.setBalance1((CommonUtil.numberIsNull(epsStatements.getEquivalentAmount()).add(CommonUtil.numberIsNull(epsStatements.getReceiptCny()))
                        .add(CommonUtil.numberIsNull(epsStatements.getTaxRefunds()))
                        .add(CommonUtil.numberIsNull(epsStatements.getIncomeExpenses())).subtract(CommonUtil.numberIsNull(epsStatements.getHasPayment()))
                        .subtract(CommonUtil.numberIsNull(epsStatements.getAgencyFee1())).subtract(CommonUtil.numberIsNull(epsStatements.getDomesticFreight()))
                        .subtract(CommonUtil.numberIsNull(epsStatements.getOtherExportExpenses())).subtract(CommonUtil.numberIsNull(epsStatements.getFinancialCost()))
                        .subtract(CommonUtil.numberIsNull(epsStatements.getReimbursement()))).setScale(2,ROUND_HALF_UP));
                //余额
                epsStatements.setBalance(CommonUtil.numberIsNull((epsStatements.getBalance1()).subtract(CommonUtil.numberIsNull(epsStatements.getExportAdjustment()))).setScale(2,ROUND_HALF_UP));
                //插入数据

                self().insertSelective(iRequest, epsStatements);
            });
         }
    }

    @Override
    public void exportExcel(IRequest iRequest, EpsStatements epsStatements, HttpServletResponse response) throws Exception {
        // 总记录数
        SysEntrustParty sysEntrustParty=new SysEntrustParty();
        sysEntrustParty.setUserId(iRequest.getUserId());
        List<SysEntrustParty> sysEntrustPartyList=sysEntrustPartyMapper.selectSerialNumber(sysEntrustParty);
        List<String> serialNumberList=new ArrayList<String>();
        if(sysEntrustPartyList!=null&&!sysEntrustPartyList.isEmpty()){
            serialNumberList=sysEntrustPartyList.stream().map(p -> p.getSerialNumber()).collect(Collectors.toList());
        }
        long totalRowCount =0;
        PageHelper.startPage(1, 1);
        if(serialNumberList!=null&&!serialNumberList.isEmpty()) {
            epsStatements.setSerialNumberList(serialNumberList);
            Page<EpsStatements> list = (Page<EpsStatements>)epsStatementsMapper.selectEditableEpsStatements(epsStatements);
            totalRowCount = list.getTotal();
        }

        // 导出EXCEL文件名称
        String fileName = "对账单" + DateFormatUtils.format(new Date(), "yyyyMMdd-HHmmss");
        // 标题
        String[] titles = {"发票号", "报关金额", "收汇金额", "人民币收款", "平均汇率", "等值金额", "已付货款","未付货款","退税款","国内运费",
                           "其他出口费用","代理费","营业外收入/支出","财务费","报销款","出口调整项","余额","备注"};
        // 开始导入
        PoiUtil.exportExcelToWebsite(response, totalRowCount, fileName, titles, (eachSheet, startRowCount, endRowCount, currentPage, pageSize) -> {
            List<EpsStatements> epsStatementsList=new ArrayList<>();
            SysEntrustParty entrustParty=new SysEntrustParty();
            entrustParty.setUserId(iRequest.getUserId());
            List<SysEntrustParty> sysEntrustPartys=sysEntrustPartyMapper.selectSerialNumber(entrustParty);
            List<String> serialNumbers=new ArrayList<String>();
            if(sysEntrustPartyList!=null&&!sysEntrustPartyList.isEmpty()){
                serialNumbers=sysEntrustPartys.stream().map(p -> p.getSerialNumber()).collect(Collectors.toList());
            }
            PageHelper.startPage(currentPage, pageSize);
            if(serialNumbers!=null&&!serialNumbers.isEmpty()) {
                epsStatements.setSerialNumberList(serialNumbers);
                epsStatementsList=epsStatementsMapper.selectEditableEpsStatements(epsStatements);
            }
            if (!CollectionUtils.isEmpty(epsStatementsList)) {

                for (int i = startRowCount; i <= endRowCount; i++) {
                    SXSSFRow eachDataRow = eachSheet.createRow(i);
                    if ((i - startRowCount) < epsStatementsList.size()) {

                        EpsStatements dto = epsStatementsList.get(i - startRowCount);

                        eachDataRow.createCell(0).setCellValue(dto.getProjectNum() == null ? "" : dto.getProjectNum());
                        if(dto.getCustomsAmount() == null){
                            eachDataRow.createCell(1).setCellValue("");
                        }else{
                            eachDataRow.createCell(1).setCellValue(dto.getCustomsAmount().doubleValue());
                        }

                        if(dto.getSpecifiedAmount() == null){
                            eachDataRow.createCell(2).setCellValue("");
                        }else{
                            eachDataRow.createCell(2).setCellValue(dto.getSpecifiedAmount().doubleValue());
                        }
                        if(dto.getReceiptCny() == null){
                            eachDataRow.createCell(3).setCellValue("");
                        }else{
                            eachDataRow.createCell(3).setCellValue(dto.getReceiptCny().doubleValue());
                        }
                        if(dto.getAverageRates() == null){
                            eachDataRow.createCell(4).setCellValue("");
                        }else{
                            eachDataRow.createCell(4).setCellValue(dto.getAverageRates().doubleValue());
                        }
                        if(dto.getEquivalentAmount() == null){
                            eachDataRow.createCell(5).setCellValue("");
                        }else{
                            eachDataRow.createCell(5).setCellValue(dto.getEquivalentAmount().doubleValue());
                        }
                        if(dto.getHasPayment() == null){
                            eachDataRow.createCell(6).setCellValue("");
                        }else{
                            eachDataRow.createCell(6).setCellValue(dto.getHasPayment().doubleValue());
                        }
                        if(dto.getUnPayment() == null){
                            eachDataRow.createCell(7).setCellValue("");
                        }else{
                            eachDataRow.createCell(7).setCellValue(dto.getUnPayment().doubleValue());
                        }
                        if(dto.getTaxRefunds() == null){
                            eachDataRow.createCell(8).setCellValue("");
                        }else{
                            eachDataRow.createCell(8).setCellValue(dto.getTaxRefunds().doubleValue());
                        }
                        if(dto.getDomesticFreight() == null){
                            eachDataRow.createCell(9).setCellValue("");
                        }else{
                            eachDataRow.createCell(9).setCellValue(dto.getDomesticFreight().doubleValue());
                        }
                        if(dto.getOtherExportExpenses() == null){
                            eachDataRow.createCell(10).setCellValue("");
                        }else{
                            eachDataRow.createCell(10).setCellValue(dto.getOtherExportExpenses().doubleValue());
                        }
                        if(dto.getAgencyFee() == null){
                            eachDataRow.createCell(11).setCellValue("");
                        }else{
                            eachDataRow.createCell(11).setCellValue(dto.getAgencyFee().doubleValue());
                        }
                        if(dto.getIncomeExpenses() == null){
                            eachDataRow.createCell(12).setCellValue("");
                        }else{
                            eachDataRow.createCell(12).setCellValue(dto.getIncomeExpenses().doubleValue());
                        }
                        if(dto.getFinancialCost() == null){
                            eachDataRow.createCell(13).setCellValue("");
                        }else{
                            eachDataRow.createCell(13).setCellValue(dto.getFinancialCost().doubleValue());
                        }
                        if(dto.getReimbursement() == null){
                            eachDataRow.createCell(14).setCellValue("");
                        }else{
                            eachDataRow.createCell(14).setCellValue(dto.getReimbursement().doubleValue());
                        }
                        if(dto.getExportAdjustment() == null){
                            eachDataRow.createCell(15).setCellValue("");
                        }else{
                            eachDataRow.createCell(15).setCellValue(dto.getExportAdjustment().doubleValue());
                        }
                        if(dto.getBalance() == null){
                            eachDataRow.createCell(16).setCellValue("");
                        }else{
                            eachDataRow.createCell(16).setCellValue(dto.getBalance().doubleValue());
                        }
                        eachDataRow.createCell(17).setCellValue(dto.getRemark() == null ? "" : dto.getRemark().toString());
                    }
                }
            }

        });
    }

    @Override
    public void exportExcelOuter(IRequest iRequest, EpsStatements epsStatements, HttpServletResponse response) throws Exception {
        // 总记录数
        SysEntrustParty sysEntrustParty=new SysEntrustParty();
        sysEntrustParty.setUserId(iRequest.getUserId());
        List<SysEntrustParty> sysEntrustPartyList=sysEntrustPartyMapper.selectSerialNumberOuter(sysEntrustParty);
        List<String> serialNumberList=new ArrayList<String>();
        if(sysEntrustPartyList!=null&&!sysEntrustPartyList.isEmpty()){
            serialNumberList=sysEntrustPartyList.stream().map(p -> p.getSerialNumber()).collect(Collectors.toList());
        }
        long totalRowCount =0;
        PageHelper.startPage(1, 1);
        if(serialNumberList!=null&&!serialNumberList.isEmpty()) {
            epsStatements.setSerialNumberList(serialNumberList);
            Page<EpsStatements> list = (Page<EpsStatements>)epsStatementsMapper.selectEditableEpsStatements(epsStatements);
            totalRowCount = list.getTotal();
        }

        // 导出EXCEL文件名称
        String fileName = "对账单" + DateFormatUtils.format(new Date(), "yyyyMMdd-HHmmss");
        // 标题
        String[] titles = {"发票号", "报关金额", "收汇金额", "人民币收款", "平均汇率", "等值金额", "已付货款","未付货款","退税款","国内运费",
                "其他出口费用","代理费","营业外收入/支出","财务费","报销款","出口调整项","余额","备注"};
        // 开始导入
        PoiUtil.exportExcelToWebsite(response, totalRowCount, fileName, titles, (eachSheet, startRowCount, endRowCount, currentPage, pageSize) -> {
            List<EpsStatements> epsStatementsList=new ArrayList<>();
            SysEntrustParty entrustParty=new SysEntrustParty();
            entrustParty.setUserId(iRequest.getUserId());
            List<SysEntrustParty> sysEntrustPartys=sysEntrustPartyMapper.selectSerialNumberOuter(entrustParty);
            List<String> serialNumbers=new ArrayList<String>();
            if(sysEntrustPartyList!=null&&!sysEntrustPartyList.isEmpty()){
                serialNumbers=sysEntrustPartys.stream().map(p -> p.getSerialNumber()).collect(Collectors.toList());
            }
            PageHelper.startPage(currentPage, pageSize);
            if(serialNumbers!=null&&!serialNumbers.isEmpty()) {
                epsStatements.setSerialNumberList(serialNumbers);
                epsStatementsList=epsStatementsMapper.selectEditableEpsStatements(epsStatements);
            }
            if (!CollectionUtils.isEmpty(epsStatementsList)) {

                for (int i = startRowCount; i <= endRowCount; i++) {
                    SXSSFRow eachDataRow = eachSheet.createRow(i);
                    if ((i - startRowCount) < epsStatementsList.size()) {

                        EpsStatements dto = epsStatementsList.get(i - startRowCount);

                        eachDataRow.createCell(0).setCellValue(dto.getProjectNum() == null ? "" : dto.getProjectNum());
                        if(dto.getCustomsAmount() == null){
                            eachDataRow.createCell(1).setCellValue("");
                        }else{
                            eachDataRow.createCell(1).setCellValue(dto.getCustomsAmount().doubleValue());
                        }

                        if(dto.getSpecifiedAmount() == null){
                            eachDataRow.createCell(2).setCellValue("");
                        }else{
                            eachDataRow.createCell(2).setCellValue(dto.getSpecifiedAmount().doubleValue());
                        }
                        if(dto.getReceiptCny() == null){
                            eachDataRow.createCell(3).setCellValue("");
                        }else{
                            eachDataRow.createCell(3).setCellValue(dto.getReceiptCny().doubleValue());
                        }
                        if(dto.getAverageRates() == null){
                            eachDataRow.createCell(4).setCellValue("");
                        }else{
                            eachDataRow.createCell(4).setCellValue(dto.getAverageRates().doubleValue());
                        }
                        if(dto.getEquivalentAmount() == null){
                            eachDataRow.createCell(5).setCellValue("");
                        }else{
                            eachDataRow.createCell(5).setCellValue(dto.getEquivalentAmount().doubleValue());
                        }
                        if(dto.getHasPayment() == null){
                            eachDataRow.createCell(6).setCellValue("");
                        }else{
                            eachDataRow.createCell(6).setCellValue(dto.getHasPayment().doubleValue());
                        }
                        if(dto.getUnPayment() == null){
                            eachDataRow.createCell(7).setCellValue("");
                        }else{
                            eachDataRow.createCell(7).setCellValue(dto.getUnPayment().doubleValue());
                        }
                        if(dto.getTaxRefunds() == null){
                            eachDataRow.createCell(8).setCellValue("");
                        }else{
                            eachDataRow.createCell(8).setCellValue(dto.getTaxRefunds().doubleValue());
                        }
                        if(dto.getDomesticFreight() == null){
                            eachDataRow.createCell(9).setCellValue("");
                        }else{
                            eachDataRow.createCell(9).setCellValue(dto.getDomesticFreight().doubleValue());
                        }
                        if(dto.getOtherExportExpenses() == null){
                            eachDataRow.createCell(10).setCellValue("");
                        }else{
                            eachDataRow.createCell(10).setCellValue(dto.getOtherExportExpenses().doubleValue());
                        }
                        if(dto.getAgencyFee() == null){
                            eachDataRow.createCell(11).setCellValue("");
                        }else{
                            eachDataRow.createCell(11).setCellValue(dto.getAgencyFee().doubleValue());
                        }
                        if(dto.getIncomeExpenses() == null){
                            eachDataRow.createCell(12).setCellValue("");
                        }else{
                            eachDataRow.createCell(12).setCellValue(dto.getIncomeExpenses().doubleValue());
                        }
                        if(dto.getFinancialCost() == null){
                            eachDataRow.createCell(13).setCellValue("");
                        }else{
                            eachDataRow.createCell(13).setCellValue(dto.getFinancialCost().doubleValue());
                        }
                        if(dto.getReimbursement() == null){
                            eachDataRow.createCell(14).setCellValue("");
                        }else{
                            eachDataRow.createCell(14).setCellValue(dto.getReimbursement().doubleValue());
                        }
                        if(dto.getExportAdjustment() == null){
                            eachDataRow.createCell(15).setCellValue("");
                        }else{
                            eachDataRow.createCell(15).setCellValue(dto.getExportAdjustment().doubleValue());
                        }
                        if(dto.getBalance() == null){
                            eachDataRow.createCell(16).setCellValue("");
                        }else{
                            eachDataRow.createCell(16).setCellValue(dto.getBalance().doubleValue());
                        }
                        eachDataRow.createCell(17).setCellValue(dto.getRemark() == null ? "" : dto.getRemark().toString());
                    }
                }
            }

        });
    }

    @Override
    public List<EpsStatements> queryEpsStatementsOnly(IRequest iRequest, EpsStatements epsStatements, int page, int pageSize) {
        PageHelper.startPage(page,pageSize);
        List<EpsStatements> epsStatementsList=epsStatementsMapper.selectEpsStatementsFromHap(epsStatements);
        EpsStatements statements=epsStatementsMapper.selectTotalBalance(epsStatements);
        if(epsStatementsList!=null&&!epsStatementsList.isEmpty()){
            if(statements!=null&&statements.getTotalBalance()!=null){
                epsStatementsList.get(0).setTotalBalance(statements.getTotalBalance());
            }
        }
        return epsStatementsList;
    }

    @Override
    public void readExportExcel(IRequest iRequest, EpsStatements epsStatements, HttpServletResponse response) throws Exception {
        // 总记录数
        PageHelper.startPage(1, 1);
        Page<EpsStatements> list=(Page<EpsStatements>)epsStatementsMapper.selectEpsStatementsFromHap(epsStatements);

        long totalRowCount = list.getTotal();
        // 导出EXCEL文件名称
        String fileName = "对账单" + DateFormatUtils.format(new Date(), "yyyyMMdd-HHmmss");
        // 标题
        String[] titles = {"发票号", "报关金额", "收汇金额", "人民币收款", "平均汇率", "等值金额", "已付货款","未付货款","退税款","国内运费",
                "其他出口费用","代理费","营业外收入/支出","财务费","报销款","出口调整项","余额","备注"};
        // 开始导入
        PoiUtil.exportExcelToWebsite(response, totalRowCount, fileName, titles, (eachSheet, startRowCount, endRowCount, currentPage, pageSize) -> {

            PageHelper.startPage(currentPage, pageSize);
            List<EpsStatements> epsStatementsList=epsStatementsMapper.selectEpsStatementsFromHap(epsStatements);
            if (!CollectionUtils.isEmpty(epsStatementsList)) {

                for (int i = startRowCount; i <= endRowCount; i++) {
                    SXSSFRow eachDataRow = eachSheet.createRow(i);
                    if ((i - startRowCount) < epsStatementsList.size()) {

                        EpsStatements dto = epsStatementsList.get(i - startRowCount);

                        eachDataRow.createCell(0).setCellValue(dto.getProjectNum() == null ? "" : dto.getProjectNum());
                        if(dto.getCustomsAmount() == null){
                            eachDataRow.createCell(1).setCellValue("");
                        }else{
                            eachDataRow.createCell(1).setCellValue(dto.getCustomsAmount().doubleValue());
                        }

                        if(dto.getSpecifiedAmount() == null){
                            eachDataRow.createCell(2).setCellValue("");
                        }else{
                            eachDataRow.createCell(2).setCellValue(dto.getSpecifiedAmount().doubleValue());
                        }
                        if(dto.getReceiptCny() == null){
                            eachDataRow.createCell(3).setCellValue("");
                        }else{
                            eachDataRow.createCell(3).setCellValue(dto.getReceiptCny().doubleValue());
                        }
                        if(dto.getAverageRates() == null){
                            eachDataRow.createCell(4).setCellValue("");
                        }else{
                            eachDataRow.createCell(4).setCellValue(dto.getAverageRates().doubleValue());
                        }
                        if(dto.getEquivalentAmount() == null){
                            eachDataRow.createCell(5).setCellValue("");
                        }else{
                            eachDataRow.createCell(5).setCellValue(dto.getEquivalentAmount().doubleValue());
                        }
                        if(dto.getHasPayment() == null){
                            eachDataRow.createCell(6).setCellValue("");
                        }else{
                            eachDataRow.createCell(6).setCellValue(dto.getHasPayment().doubleValue());
                        }
                        if(dto.getUnPayment() == null){
                            eachDataRow.createCell(7).setCellValue("");
                        }else{
                            eachDataRow.createCell(7).setCellValue(dto.getUnPayment().doubleValue());
                        }
                        if(dto.getTaxRefunds() == null){
                            eachDataRow.createCell(8).setCellValue("");
                        }else{
                            eachDataRow.createCell(8).setCellValue(dto.getTaxRefunds().doubleValue());
                        }
                        if(dto.getDomesticFreight() == null){
                            eachDataRow.createCell(9).setCellValue("");
                        }else{
                            eachDataRow.createCell(9).setCellValue(dto.getDomesticFreight().doubleValue());
                        }
                        if(dto.getOtherExportExpenses() == null){
                            eachDataRow.createCell(10).setCellValue("");
                        }else{
                            eachDataRow.createCell(10).setCellValue(dto.getOtherExportExpenses().doubleValue());
                        }
                        if(dto.getAgencyFee() == null){
                            eachDataRow.createCell(11).setCellValue("");
                        }else{
                            eachDataRow.createCell(11).setCellValue(dto.getAgencyFee().doubleValue());
                        }
                        if(dto.getIncomeExpenses() == null){
                            eachDataRow.createCell(12).setCellValue("");
                        }else{
                            eachDataRow.createCell(12).setCellValue(dto.getIncomeExpenses().doubleValue());
                        }
                        if(dto.getFinancialCost() == null){
                            eachDataRow.createCell(13).setCellValue("");
                        }else{
                            eachDataRow.createCell(13).setCellValue(dto.getFinancialCost().doubleValue());
                        }
                        if(dto.getReimbursement() == null){
                            eachDataRow.createCell(14).setCellValue("");
                        }else{
                            eachDataRow.createCell(14).setCellValue(dto.getReimbursement().doubleValue());
                        }
                        if(dto.getExportAdjustment() == null){
                            eachDataRow.createCell(15).setCellValue("");
                        }else{
                            eachDataRow.createCell(15).setCellValue(dto.getExportAdjustment().doubleValue());
                        }
                        if(dto.getBalance() == null){
                            eachDataRow.createCell(16).setCellValue("");
                        }else{
                            eachDataRow.createCell(16).setCellValue(dto.getBalance().doubleValue());
                        }
                        eachDataRow.createCell(17).setCellValue(dto.getRemark() == null ? "" : dto.getRemark().toString());
                    }
                }
            }

        });
    }

    @Override
    public void portalExportExcel(IRequest iRequest, EpsStatements epsStatements, HttpServletResponse response) throws Exception {
        // 总记录数
        PageHelper.startPage(1, 1);
        Page<EpsStatements> list=(Page<EpsStatements>)epsStatementsMapper.selectEpsStatementsFromHap(epsStatements);

        long totalRowCount = list.getTotal();
        // 导出EXCEL文件名称
        String fileName = "对账单" + DateFormatUtils.format(new Date(), "yyyyMMdd-HHmmss");
        // 标题
        String[] titles = {"发票号", "报关金额", "收汇金额", "人民币收款", "平均汇率", "等值金额", "已付货款","未付货款","退税款","国内运费",
                "其他出口费用","代理费","营业外收入/支出","财务费","报销款","出口调整项","余额"};
        // 开始导入
        PoiUtil.exportExcelToWebsite(response, totalRowCount, fileName, titles, (eachSheet, startRowCount, endRowCount, currentPage, pageSize) -> {

            PageHelper.startPage(currentPage, pageSize);
            List<EpsStatements> epsStatementsList=epsStatementsMapper.selectEpsStatementsFromHap(epsStatements);
            if (!CollectionUtils.isEmpty(epsStatementsList)) {

                for (int i = startRowCount; i <= endRowCount; i++) {
                    SXSSFRow eachDataRow = eachSheet.createRow(i);
                    if ((i - startRowCount) < epsStatementsList.size()) {

                        EpsStatements dto = epsStatementsList.get(i - startRowCount);
                        eachDataRow.createCell(0).setCellValue(dto.getProjectNum() == null ? "" : dto.getProjectNum());
                        if(dto.getCustomsAmount() == null){
                            eachDataRow.createCell(1).setCellValue("");
                        }else{
                            eachDataRow.createCell(1).setCellValue(dto.getCustomsAmount().doubleValue());
                        }

                        if(dto.getSpecifiedAmount() == null){
                            eachDataRow.createCell(2).setCellValue("");
                        }else{
                            eachDataRow.createCell(2).setCellValue(dto.getSpecifiedAmount().doubleValue());
                        }
                        if(dto.getReceiptCny() == null){
                            eachDataRow.createCell(3).setCellValue("");
                        }else{
                            eachDataRow.createCell(3).setCellValue(dto.getReceiptCny().doubleValue());
                        }
                        if(dto.getAverageRates() == null){
                            eachDataRow.createCell(4).setCellValue("");
                        }else{
                            eachDataRow.createCell(4).setCellValue(dto.getAverageRates().doubleValue());
                        }
                        if(dto.getEquivalentAmount() == null){
                            eachDataRow.createCell(5).setCellValue("");
                        }else{
                            eachDataRow.createCell(5).setCellValue(dto.getEquivalentAmount().doubleValue());
                        }
                        if(dto.getHasPayment() == null){
                            eachDataRow.createCell(6).setCellValue("");
                        }else{
                            eachDataRow.createCell(6).setCellValue(dto.getHasPayment().doubleValue());
                        }
                        if(dto.getUnPayment() == null){
                            eachDataRow.createCell(7).setCellValue("");
                        }else{
                            eachDataRow.createCell(7).setCellValue(dto.getUnPayment().doubleValue());
                        }
                        if(dto.getTaxRefunds() == null){
                            eachDataRow.createCell(8).setCellValue("");
                        }else{
                            eachDataRow.createCell(8).setCellValue(dto.getTaxRefunds().doubleValue());
                        }
                        if(dto.getDomesticFreight() == null){
                            eachDataRow.createCell(9).setCellValue("");
                        }else{
                            eachDataRow.createCell(9).setCellValue(dto.getDomesticFreight().doubleValue());
                        }
                        if(dto.getOtherExportExpenses() == null){
                            eachDataRow.createCell(10).setCellValue("");
                        }else{
                            eachDataRow.createCell(10).setCellValue(dto.getOtherExportExpenses().doubleValue());
                        }
                        if(dto.getAgencyFee() == null){
                            eachDataRow.createCell(11).setCellValue("");
                        }else{
                            eachDataRow.createCell(11).setCellValue(dto.getAgencyFee().doubleValue());
                        }
                        if(dto.getIncomeExpenses() == null){
                            eachDataRow.createCell(12).setCellValue("");
                        }else{
                            eachDataRow.createCell(12).setCellValue(dto.getIncomeExpenses().doubleValue());
                        }
                        if(dto.getFinancialCost() == null){
                            eachDataRow.createCell(13).setCellValue("");
                        }else{
                            eachDataRow.createCell(13).setCellValue(dto.getFinancialCost().doubleValue());
                        }
                        if(dto.getReimbursement() == null){
                            eachDataRow.createCell(14).setCellValue("");
                        }else{
                            eachDataRow.createCell(14).setCellValue(dto.getReimbursement().doubleValue());
                        }
                        if(dto.getExportAdjustment() == null){
                            eachDataRow.createCell(15).setCellValue("");
                        }else{
                            eachDataRow.createCell(15).setCellValue(dto.getExportAdjustment().doubleValue());
                        }
                        if(dto.getBalance() == null){
                            eachDataRow.createCell(16).setCellValue("");
                        }else{
                            eachDataRow.createCell(16).setCellValue(dto.getBalance().doubleValue());
                        }
                    }
                }
            }

        });
    }

//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public List<EpsStatements> batchUpdate(IRequest requestContext, List<EpsStatements> epsStatements) {
//        for (EpsStatements epsStatement : epsStatements) {
//            if (epsStatement.get__status() != null) {
//                /*switch (epsStatement.get__status()) {
//                    case DTOStatus.ADD:
//                        epsStatementsMapper.insertEpsStatement(epsStatement);
//                        break;
//                    case DTOStatus.UPDATE:
//                        epsStatementsMapper.updateEpsStatement(epsStatement);
//                        break;
//                    default:
//                        break;
//                }*/
//                if (epsStatement.get__status() == DTOStatus.UPDATE) {
//                    epsStatementsMapper.updateEpsStatement(epsStatement);
//                }
//            }
//        }
//        return epsStatements;
//    }
}
