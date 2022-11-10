package hcux.eps.dto;


import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import org.hibernate.validator.constraints.Length;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author yexiang.ren@hand-china.com
 * @description 对账单表
 */
@ExtensionAttribute(disable = true)
@Table(name = "HCUX_EPS_STATEMENTS")
public class EpsStatements extends BaseDTO {

    public static final String FIELD_STATEMENTS_ID = "statementsId";
    public static final String FIELD_PROJECT_NUM = "projectNum";
    public static final String FIELD_EXPORT_ADJUSTMENT = "exportAdjustment";
    public static final String FIELD_CUSTOMS_AMOUNT = "customsAmount";
    public static final String FIELD_SPECIFIED_AMOUNT = "specifiedAmount";
    public static final String FIELD_AVERAGE_RATES = "averageRates";
    public static final String FIELD_EQUIVALENT_AMOUNT = "equivalentAmount";
    public static final String FIELD_HAS_PAYMENT = "hasPayment";
    public static final String FIELD_UN_PAYMENT = "unPayment";
    public static final String FIELD_PREPAID_FREIGHT = "prepaidFreight";
    public static final String FIELD_OTHER_EXPORT_EXPENSES = "otherExportExpenses";
    public static final String FIELD_TAX_REFUNDS = "taxRefunds";
    public static final String FIELD_AGENCY_FEE = "agencyFee";
    public static final String FIELD_BALANCE = "balance";
    public static final String FIELD_BALANCE1 = "balance1";
    public static final String FIELD_RECEIPT_CNY = "receiptCny";
    public static final String FIELD_DOMESTIC_FREIGHT = "domesticFreight";
    public static final String FIELD_INCOME_EXPENSES = "incomeExpenses";
    public static final String FIELD_FINANCIAL_COST = "financialCost";
    public static final String FIELD_REIMBURSEMENT = "reimbursement";
    public static final String FIELD_AGENCY_FEE1 = "agencyFee1";
    public static final String FIELD_REMARK = "remark";

    @Id
    @GeneratedValue
    private Long statementsId;//ID

    @Length(max = 200)
    private String projectNum;//项目号

    private BigDecimal exportAdjustment;// 出口调整项

    private BigDecimal customsAmount;//报关金额

    private BigDecimal specifiedAmount;//收汇金额

    private BigDecimal averageRates;//平均汇率

    private BigDecimal equivalentAmount;//等值金额

    private BigDecimal hasPayment;//已付货款

    private BigDecimal unPayment;//未付货款

    private BigDecimal prepaidFreight;//已付运费

    private BigDecimal otherExportExpenses;//其他出口费用

    private BigDecimal taxRefunds;//退税款

    private BigDecimal agencyFee;//代理费

    private BigDecimal balance;//余额

    private BigDecimal balance1;//余额1

    @Transient
    private String queryType; //查询类型（HAP、门户、微信）

    private BigDecimal receiptCny;//人民币收款

    private BigDecimal domesticFreight;//国内运费

    private BigDecimal incomeExpenses;//营业外收入/支出

    private BigDecimal financialCost;//财务费

    private BigDecimal reimbursement;//报销款

    private BigDecimal agencyFee1;//代理费1

    private String remark;//备注

    @Transient
    private String projectNumFrom; //项目号从

    @Transient
    private String projectNumTo; //项目号至

    @Transient
    private String serialNumber; //系列号

    @Transient
    private BigDecimal totalBalance;//总计余额

    @Transient
    private BigDecimal finalBalance;//最终余额

    @Transient
    private List<String> serialNumberList;//系列号集合

    public Long getStatementsId() { return statementsId; }

    public void setStatementsId(Long statementsId) { this.statementsId = statementsId; }

    public String getProjectNum() {
        return projectNum;
    }

    public void setProjectNum(String projectNum) {
        this.projectNum = projectNum;
    }

    public BigDecimal getCustomsAmount() {
        return customsAmount;
    }

    public void setCustomsAmount(BigDecimal customsAmount) {
        this.customsAmount = customsAmount;
    }

    public BigDecimal getSpecifiedAmount() {
        return specifiedAmount;
    }

    public void setSpecifiedAmount(BigDecimal specifiedAmount) {
        this.specifiedAmount = specifiedAmount;
    }

    public BigDecimal getAverageRates() {
        return averageRates;
    }

    public void setAverageRates(BigDecimal averageRates) {
        this.averageRates = averageRates;
    }

    public BigDecimal getEquivalentAmount() {
        return equivalentAmount;
    }

    public void setEquivalentAmount(BigDecimal equivalentAmount) {
        this.equivalentAmount = equivalentAmount;
    }

    public BigDecimal getHasPayment() {
        return hasPayment;
    }

    public void setHasPayment(BigDecimal hasPayment) {
        this.hasPayment = hasPayment;
    }

    public BigDecimal getUnPayment() {
        return unPayment;
    }

    public void setUnPayment(BigDecimal unPayment) {
        this.unPayment = unPayment;
    }

    public BigDecimal getPrepaidFreight() {
        return prepaidFreight;
    }

    public void setPrepaidFreight(BigDecimal prepaidFreight) {
        this.prepaidFreight = prepaidFreight;
    }

    public BigDecimal getOtherExportExpenses() {
        return otherExportExpenses;
    }

    public void setOtherExportExpenses(BigDecimal otherExportExpenses) {
        this.otherExportExpenses = otherExportExpenses;
    }

    public BigDecimal getTaxRefunds() {
        return taxRefunds;
    }

    public void setTaxRefunds(BigDecimal taxRefunds) {
        this.taxRefunds = taxRefunds;
    }

    public BigDecimal getExportAdjustment() {
        return exportAdjustment;
    }

    public void setExportAdjustment(BigDecimal exportAdjustment) {
        this.exportAdjustment = exportAdjustment;
    }

    public BigDecimal getAgencyFee() {
        return agencyFee;
    }

    public void setAgencyFee(BigDecimal agencyFee) {
        this.agencyFee = agencyFee;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getBalance1() {
        return balance1;
    }

    public void setBalance1(BigDecimal balance1) {
        this.balance1 = balance1;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public BigDecimal getReceiptCny() {
        return receiptCny;
    }

    public void setReceiptCny(BigDecimal receiptCny) {
        this.receiptCny = receiptCny;
    }

    public BigDecimal getDomesticFreight() {
        return domesticFreight;
    }

    public void setDomesticFreight(BigDecimal domesticFreight) {
        this.domesticFreight = domesticFreight;
    }

    public BigDecimal getIncomeExpenses() {
        return incomeExpenses;
    }

    public void setIncomeExpenses(BigDecimal incomeExpenses) {
        this.incomeExpenses = incomeExpenses;
    }

    public BigDecimal getFinancialCost() {
        return financialCost;
    }

    public void setFinancialCost(BigDecimal financialCost) {
        this.financialCost = financialCost;
    }

    public BigDecimal getReimbursement() {
        return reimbursement;
    }

    public void setReimbursement(BigDecimal reimbursement) {
        this.reimbursement = reimbursement;
    }

    public BigDecimal getAgencyFee1() {
        return agencyFee1;
    }

    public void setAgencyFee1(BigDecimal agencyFee1) {
        this.agencyFee1 = agencyFee1;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getProjectNumFrom() {
        return projectNumFrom;
    }

    public void setProjectNumFrom(String projectNumFrom) {
        this.projectNumFrom = projectNumFrom;
    }

    public String getProjectNumTo() {
        return projectNumTo;
    }

    public void setProjectNumTo(String projectNumTo) {
        this.projectNumTo = projectNumTo;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public BigDecimal getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(BigDecimal totalBalance) {
        this.totalBalance = totalBalance;
    }

    public BigDecimal getFinalBalance() {
        return finalBalance;
    }

    public void setFinalBalance(BigDecimal finalBalance) {
        this.finalBalance = finalBalance;
    }

    public List<String> getSerialNumberList() {
        return serialNumberList;
    }

    public void setSerialNumberList(List<String> serialNumberList) {
        this.serialNumberList = serialNumberList;
    }
}

