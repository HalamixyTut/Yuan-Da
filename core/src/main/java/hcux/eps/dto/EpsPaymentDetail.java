package hcux.eps.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hand.hap.core.BaseConstants;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yexiang.ren@hand-china.com
 * @description 应付款表
 */
@ExtensionAttribute(disable = true)
@Table(name = "HCUX_EPS_PAYMENT_DETAIL")
public class EpsPaymentDetail extends BaseDTO {

    public static final String FIELD_PAYMENT_ID = "paymentId";
    public static final String FIELD_INVOICE_ID = "invoiceId";
    public static final String FIELD_LINE_NUMBER = "lineNumber";
    public static final String FIELD_VENDOR_NAME = "vendorName";
    public static final String FIELD_PROJECT_NUM = "projectNum";
    public static final String FIELD_PAYMENT_DATE = "paymentDate";
    public static final String FIELD_EXPENDITURE_TYPE = "expenditureType";
    public static final String FIELD_CURRENCY_CODE = "currencyCode";
    public static final String FIELD_AMOUNT = "amount";
    public static final String FIELD_BASE_AMOUNT = "baseAmount";


    @Id
    @GeneratedValue
    private Long paymentId;//Id

    private Long invoiceId;//ERP视图ID，确定唯一

    private Long lineNumber;//ERP视图行号，确定唯一

    private Long projectId;//ERP视图ID，确定唯一

    private Long vendorId;//ERP视图ID，确定唯一

    @Length(max = 200)
    private String vendorName;//收款单位

    @Length(max = 200)
    private String projectNum;//项目号
    @DateTimeFormat(pattern = BaseConstants.DATE_FORMAT)
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT, timezone = "GMT+8")
    private Date paymentDate;//付款日期

    @Length(max = 200)
    private String expenditureType;//支出类型

    @Length(max = 50)
    private String currencyCode;//币种

    private Double amount;//原币金额

    private Double baseAmount;//本位币金额

    private String status; //状态

    @Transient
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT, timezone = "GMT+8")
    @DateTimeFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date paymentDateFrom;

    @Transient
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT, timezone = "GMT+8")
    @DateTimeFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date paymentDateTo;

    @Transient
    private String queryType; //查询类型（HAP、门户、微信）

    @Transient
    private Double totalAmount; //查询条件下的原币金额总金额


    @Transient
    private String projectNumFrom; //项目号从

    @Transient
    private String projectNumTo; //项目号至

    @Transient
    private String stringTotalAmount;//查询条件下各种货币类型拼接的字符串

    @Transient
    private BigDecimal totalLineAmount;//对账单计算已付货款以及国内运费

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Long getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(Long lineNumber) {
        this.lineNumber = lineNumber;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setProjectNum(String projectNum) {
        this.projectNum = projectNum;
    }

    public String getProjectNum() {
        return projectNum;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setExpenditureType(String expenditureType) {
        this.expenditureType = expenditureType;
    }

    public String getExpenditureType() {
        return expenditureType;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getBaseAmount() {
        return baseAmount;
    }

    public void setBaseAmount(Double baseAmount) {
        this.baseAmount = baseAmount;
    }

    public Date getPaymentDateFrom() {
        return paymentDateFrom;
    }

    public void setPaymentDateFrom(Date paymentDateFrom) {
        this.paymentDateFrom = paymentDateFrom;
    }

    public Date getPaymentDateTo() {
        return paymentDateTo;
    }

    public void setPaymentDateTo(Date paymentDateTo) {
        this.paymentDateTo = paymentDateTo;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getStringTotalAmount() {
        return stringTotalAmount;
    }

    public void setStringTotalAmount(String stringTotalAmount) {
        this.stringTotalAmount = stringTotalAmount;
    }

    public BigDecimal getTotalLineAmount() {
        return totalLineAmount;
    }

    public void setTotalLineAmount(BigDecimal totalLineAmount) {
        this.totalLineAmount = totalLineAmount;
    }
}
