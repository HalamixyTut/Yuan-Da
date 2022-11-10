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
 * @description 应收预付往来明细表
 */
@ExtensionAttribute(disable = true)
@Table(name = "HCUX_EPS_PURCHASE_DETAIL")
public class EpsPurchaseDetail extends BaseDTO {

    public static final String FIELD_PURCHASE_ID = "purchaseId";
    public static final String FIELD_INVOICE_ID = "invoiceId";
    public static final String FIELD_LINE_NUMBER = "lineNumber";
    public static final String FIELD_PROJECT_NUM = "projectNum";
    public static final String FIELD_VENDOR_NAME = "vendorName";
    public static final String FIELD_INVOICE_TYPE = "invoiceType";
    public static final String FIELD_GL_DATE = "glDate";
    public static final String FIELD_INVOICE_CURRENCY_CODE = "invoiceCurrencyCode";
    public static final String FIELD_CR_AMOUNT = "crAmount";
    public static final String FIELD_CR_BASE_AMOUNT = "crBaseAmount";


    @Id
    @GeneratedValue
    private Long purchaseId;//Id

    private Long invoiceId;//ERP视图ID，与 LINE_NUMBER 一起确定唯一

    private Long lineNumber;//ERP视图行号，与 INVOICE_ID 一起确定唯一

    @Length(max = 200)
    private String projectNum;//项目号

    @Length(max = 200)
    private String vendorName;//贸易伙伴

    @Length(max = 200)
    private String invoiceType;//业务类型
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT, timezone = "GMT+8")
    @DateTimeFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date glDate;//业务日期

    @Length(max = 200)
    private String invoiceCurrencyCode;//币种

    private Double crAmount;//贷方金额

    private Double crBaseAmount;//贷方折合金额
    @Transient
    private BigDecimal sumCrBaseAmount;//在某个项目号确认情况下，贷方折合金额的总和
    @Transient
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT, timezone = "GMT+8")
    @DateTimeFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date glDateFrom;//业务日期从
    @Transient
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT, timezone = "GMT+8")
    @DateTimeFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date glDateTo;//业务日期至

    @Transient
    private String queryType; //查询类型（HAP、门户、微信）

    @Transient
    private Double totalAmount; //查询条件下的贷方金额总金额

    @Transient
    private String projectNumFrom; //项目号从

    @Transient
    private String projectNumTo; //项目号至

    @Transient
    private String stringTotalAmount; //查询条件下的各种货币的金额总和

    public Long getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Long purchaseId) {
        this.purchaseId = purchaseId;
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

    public String getProjectNum() {
        return projectNum;
    }

    public void setProjectNum(String projectNum) {
        this.projectNum = projectNum;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public Date getGlDate() {
        return glDate;
    }

    public void setGlDate(Date glDate) {
        this.glDate = glDate;
    }

    public String getInvoiceCurrencyCode() {
        return invoiceCurrencyCode;
    }

    public void setInvoiceCurrencyCode(String invoiceCurrencyCode) {
        this.invoiceCurrencyCode = invoiceCurrencyCode;
    }

    public Double getCrAmount() {
        return crAmount;
    }

    public void setCrAmount(Double crAmount) {
        this.crAmount = crAmount;
    }

    public Double getCrBaseAmount() {
        return crBaseAmount;
    }

    public void setCrBaseAmount(Double crBaseAmount) {
        this.crBaseAmount = crBaseAmount;
    }

    public BigDecimal getSumCrBaseAmount() {
        return sumCrBaseAmount;
    }

    public void setSumCrBaseAmount(BigDecimal sumCrBaseAmount) {
        this.sumCrBaseAmount = sumCrBaseAmount;
    }

    public Date getGlDateFrom() {
        return glDateFrom;
    }

    public void setGlDateFrom(Date glDateFrom) {
        this.glDateFrom = glDateFrom;
    }

    public Date getGlDateTo() {
        return glDateTo;
    }

    public void setGlDateTo(Date glDateTo) {
        this.glDateTo = glDateTo;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public Double getTotalAmount() { return totalAmount; }

    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }

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
}
