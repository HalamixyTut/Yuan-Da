package hcux.eps.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hand.hap.core.BaseConstants;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import hcux.core.util.HcuxConstant;
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
 * @description 收款明细表
 */
@ExtensionAttribute(disable = true)
@Table(name = "HCUX_EPS_RECEIPT_DETAIL")
public class EpsReceiptDetail extends BaseDTO {

    public static final String FIELD_RECEIPT_ID = "receiptId";
    public static final String FIELD_DATA_ID = "dataId";
    public static final String FIELD_DATA_ID2 = "dataId2";
    public static final String FIELD_DATA_ID3 = "dataId3";
    public static final String FIELD_DATA_ID4 = "dataId4";
    public static final String FIELD_PROJECT_NAME = "projectName";
    public static final String FIELD_GL_DATE = "glDate";
    public static final String FIELD_CURRENCY_CODE = "currencyCode";
    public static final String FIELD_CR_AMOUNT = "crAmount";
    public static final String FIELD_CR_BASE_AMOUNT = "crBaseAmount";
    public static final String FIELD_RECEIPT_NUMBER = "receiptNumber";
    public static final String FIELD_DELETE_FLAG = "deleteFlag";
    public static final String FIELD_METHOD_NAME = "methodName";


    @Id
    @GeneratedValue
    private Long receiptId;//ID

    private Long dataId;//ERP视图ID，多个ID组合确定唯一

    private Long dataId2;//ERP视图ID，多个ID组合确定唯一

    private Long dataId3;//ERP视图ID，多个ID组合确定唯一

    private Long dataId4;//ERP视图ID，多个ID组合确定唯一

    @Length(max = 200)
    private String projectName;//项目号

    private String glDate;//收款日期

    @Length(max = 200)
    private String currencyCode;//币种

    private Double crAmount;//收款金额

    private Double crBaseAmount;//收款本位币

    private Double exchangeRate;//汇率

    private String receiptsStatus; //收款通知单状态

    private Double crAmount1; //贷方金额

    private Double crBaseAmount1; //贷方折合金额

    private String receiptNumber; //收款编号
    @Transient
    private BigDecimal crBaseAmount1Total;
    @Transient
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT, timezone = "GMT+8")
    @DateTimeFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date glDateFrom;//收款日期从
    @Transient
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT, timezone = "GMT+8")
    @DateTimeFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date glDateTo;//收款日期至

    @Transient
    private String queryType; //查询类型（HAP、门户、微信）

    @Transient
    private Double totalAmount; //查询条件下的 收款金额 总金额

    @Transient
    private String projectNameFrom; //项目号从

    @Transient
    private String projectNameTo; //项目号至

    @Transient
    private String stringTotalAmount; //查询条件下的 收款金额 总金额

    private String methodName;//业务类型

    @Transient
    private BigDecimal totalCrAmount;//对账单增量计算收汇金额值1

    @Transient
    private BigDecimal totalReturnAmount;//对账单增量计算收汇金额值2

    public Long getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(Long receiptId) {
        this.receiptId = receiptId;
    }

    public Long getDataId() {
        return dataId;
    }

    public void setDataId(Long dataId) {
        this.dataId = dataId;
    }

    public Long getDataId2() {
        return dataId2;
    }

    public void setDataId2(Long dataId2) {
        this.dataId2 = dataId2;
    }

    public Long getDataId3() {
        return dataId3;
    }

    public void setDataId3(Long dataId3) {
        this.dataId3 = dataId3;
    }

    public Long getDataId4() {
        return dataId4;
    }

    public void setDataId4(Long dataId4) {
        this.dataId4 = dataId4;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getGlDate() {
        return glDate;
    }

    public void setGlDate(String glDate) {
        this.glDate = glDate;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
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

    public Double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(Double exchangeRate) {
        this.exchangeRate = exchangeRate;
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

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getReceiptsStatus() {
        return receiptsStatus;
    }

    public void setReceiptsStatus(String receiptsStatus) {
        this.receiptsStatus = receiptsStatus;
    }

    public Double getCrAmount1() {
        return crAmount1;
    }

    public void setCrAmount1(Double crAmount1) {
        this.crAmount1 = crAmount1;
    }

    public Double getCrBaseAmount1() {
        return crBaseAmount1;
    }

    public void setCrBaseAmount1(Double crBaseAmount1) {
        this.crBaseAmount1 = crBaseAmount1;
    }

    public String getReceiptNumber() { return receiptNumber; }

    public void setReceiptNumber(String receiptNumber) { this.receiptNumber = receiptNumber; }

    public String getProjectNameFrom() {
        return projectNameFrom;
    }

    public void setProjectNameFrom(String projectNameFrom) {
        this.projectNameFrom = projectNameFrom;
    }

    public String getProjectNameTo() {
        return projectNameTo;
    }

    public void setProjectNameTo(String projectNameTo) {
        this.projectNameTo = projectNameTo;
    }

    public String getStringTotalAmount() {
        return stringTotalAmount;
    }

    public void setStringTotalAmount(String stringTotalAmount) {
        this.stringTotalAmount = stringTotalAmount;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public BigDecimal getCrBaseAmount1Total() {
        return crBaseAmount1Total;
    }

    public void setCrBaseAmount1Total(BigDecimal crBaseAmount1Total) {
        this.crBaseAmount1Total = crBaseAmount1Total;
    }

    public BigDecimal getTotalCrAmount() {
        return totalCrAmount;
    }

    public void setTotalCrAmount(BigDecimal totalCrAmount) {
        this.totalCrAmount = totalCrAmount;
    }

    public BigDecimal getTotalReturnAmount() {
        return totalReturnAmount;
    }

    public void setTotalReturnAmount(BigDecimal totalReturnAmount) {
        this.totalReturnAmount = totalReturnAmount;
    }


}
