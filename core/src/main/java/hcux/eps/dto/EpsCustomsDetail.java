package hcux.eps.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hand.hap.core.BaseConstants;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import hcux.core.annotation.AutoUpper;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Table;
import javax.persistence.Transient;

import com.hand.hap.system.dto.BaseDTO;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
/**
 * @author yexiang.ren@hand-china.com
 * @description 报关明细表
 */
@ExtensionAttribute(disable = true)
@Table(name = "HCUX_EPS_CUSTOMS_DETAIL")
public class EpsCustomsDetail extends BaseDTO {

    public static final String FIELD_CUSTOMS_DETAIL_ID = "customsDetailId";
    public static final String FIELD_CUSTOMS_NUMBER = "customsNumber";
    public static final String FIELD_IMPORT_EXPORT = "importExport";
    public static final String FIELD_MANUAL_NUMBER = "manualNumber";
    public static final String FIELD_DECLARE_DATE = "declareDate";
    public static final String FIELD_IMPORT_EXPORT_DATE = "importExportDate";
    public static final String FIELD_IMPORT_EXPORT_PORT = "importExportPort";
    public static final String FIELD_DECLARE_UNIT = "declareUnit";
    public static final String FIELD_CONTRACT_NUMBER = "contractNumber";
    public static final String FIELD_INVOICE_NUMBER = "invoiceNumber";
    public static final String FIELD_COUNTRY = "country";
    public static final String FIELD_TRANSACTION_MODE = "transactionMode";
    public static final String FIELD_DEAL_WAY = "dealWay";
    public static final String FIELD_PROJECT_NUMBER = "projectNumber";
    public static final String FIELD_GOODS_NAME = "goodsName";
    public static final String FIELD_GOODS_NUMBER = "goodsNumber";
    public static final String FIELD_SPECIFICATION_TYPE = "specificationType";
    public static final String FIELD_AMOUNT = "amount";
    public static final String FIELD_UNIT = "unit";
    public static final String FIELD_FIRST_AMOUNT = "firstAmount";
    public static final String FIELD_FIRST_UNIT = "firstUnit";
    public static final String FIELD_SECOND_AMOUNT = "secondAmount";
    public static final String FIELD_SECOND_UNIT = "secondUnit";
    public static final String FIELD_CURRENCY_SYSTEM = "currencySystem";
    public static final String FIELD_UNIT_PRICE = "unitPrice";
    public static final String FIELD_TOTAL_PRICE = "totalPrice";
    public static final String FIELD_BILL_NUMBER = "billNumber";
    public static final String FIELD_BOX_NUMBER = "boxNumber";
    public static final String FIELD_STATUS = "status";
    public static final String FIELD_MODIFY_FLAG = "modifyFlag";
    public static final String FIELD_VESSEL_VOYAGE = "vesselVoyage";
    public static final String FIELD_IS_EXPORTED = "isExported";

    @Id
    @GeneratedValue
    private Long customsDetailId;//表ID，主键，供其他表做外键

    @Length(max = 200)
    private String customsNumber;//报关单号

    @Length(max = 200)
    private String importExport;//进/出口

    @Length(max = 200)
    private String manualNumber;//手册号
    @DateTimeFormat(pattern = BaseConstants.DATE_FORMAT)
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT, timezone = "GMT+8")
    private Date declareDate;//申报日期
    @DateTimeFormat(pattern = BaseConstants.DATE_FORMAT)
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT, timezone = "GMT+8")
    private Date importExportDate;//进出口日期

    @Length(max = 200)
    private String importExportPort;//进出口口岸

    @Length(max = 200)
    private String declareUnit;//申报单位

    @Length(max = 200)
    private String contractNumber;//合同号

    @Length(max = 200)
    @AutoUpper
    private String invoiceNumber;//发票号

    @Length(max = 200)
    private String country;//国别

    @Length(max = 200)
    private String transactionMode;//贸易方式

    @Length(max = 200)
    private String dealWay;//成交方式

    @Length(max = 200)
    private String projectNumber;//项号

    @Length(max = 200)
    private String goodsName;//商品名称

    @Length(max = 200)
    private String goodsNumber;//商品编号

    @Length(max = 200)
    private String specificationType;//规格型号

    private Float amount;//数量

    @Length(max = 200)
    private String unit;//单位

    private Float firstAmount;//第一数量

    @Length(max = 200)
    private String firstUnit;//第一计量单位

    private Float secondAmount;//第二数量

    @Length(max = 200)
    private String secondUnit;//第二计量单位

    @Length(max = 200)
    private String currencySystem;//成交币制

    private Double unitPrice;//单价

    private Double totalPrice;//总价

    @Length(max = 200)
    private String billNumber;//提单号

    @Length(max = 200)
    private String boxNumber;//集装箱号
    @Transient
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT, timezone = "GMT+8")
    @DateTimeFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date declareDateFrom;//申报日期从
    @Transient
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT, timezone = "GMT+8")
    @DateTimeFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date declareDateTo;//申报日期至

    private String status;//状态

    @Transient
    private Double totalValue;//报关单号维度的总价之和

    private String modifyFlag;//是否改

    @Transient
    private Date maxDeclareDate;//最大申报日期

    @Transient
    private String queryType; //查询类型（HAP、门户、微信）

    @Transient
    private Double totalAmount; //查询条件下的总价总金额

    @Transient
    private String invoiceNumberFrom; //项目号从

    @Transient
    private String invoiceNumberTo; //项目号至

    @Transient
    private String stringTotalAmount; //查询条件下的 总价 总金额

    @Transient
    private String stringErrorTotalAmount; //报关明细头: 查询条件下的 总价 总金额

    @Length(max = 200)
    private String vesselVoyage; //船名航次

    private String isExported;//是否已经导出
    @Transient
    private Long userId;//用户id

    public Long getCustomsDetailId() {
        return customsDetailId;
    }

    public void setCustomsDetailId(Long customsDetailId) {
        this.customsDetailId = customsDetailId;
    }

    public void setCustomsNumber(String customsNumber) {
        this.customsNumber = customsNumber;
    }

    public String getCustomsNumber() {
        return customsNumber;
    }

    public void setImportExport(String importExport) {
        this.importExport = importExport;
    }

    public String getImportExport() {
        return importExport;
    }

    public void setManualNumber(String manualNumber) {
        this.manualNumber = manualNumber;
    }

    public String getManualNumber() {
        return manualNumber;
    }

    public void setDeclareDate(Date declareDate) {
        this.declareDate = declareDate;
    }

    public Date getDeclareDate() {
        return declareDate;
    }

    public void setImportExportDate(Date importExportDate) {
        this.importExportDate = importExportDate;
    }

    public Date getImportExportDate() {
        return importExportDate;
    }

    public void setImportExportPort(String importExportPort) {
        this.importExportPort = importExportPort;
    }

    public String getImportExportPort() {
        return importExportPort;
    }

    public void setDeclareUnit(String declareUnit) {
        this.declareUnit = declareUnit;
    }

    public String getDeclareUnit() {
        return declareUnit;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public void setTransactionMode(String transactionMode) {
        this.transactionMode = transactionMode;
    }

    public String getTransactionMode() {
        return transactionMode;
    }

    public void setDealWay(String dealWay) {
        this.dealWay = dealWay;
    }

    public String getDealWay() {
        return dealWay;
    }

    public void setProjectNumber(String projectNumber) {
        this.projectNumber = projectNumber;
    }

    public String getProjectNumber() {
        return projectNumber;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsNumber(String goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public String getGoodsNumber() {
        return goodsNumber;
    }

    public void setSpecificationType(String specificationType) {
        this.specificationType = specificationType;
    }

    public String getSpecificationType() {
        return specificationType;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Float getAmount() {
        return amount;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }

    public void setFirstAmount(Float firstAmount) {
        this.firstAmount = firstAmount;
    }

    public Float getFirstAmount() {
        return firstAmount;
    }

    public void setFirstUnit(String firstUnit) {
        this.firstUnit = firstUnit;
    }

    public String getFirstUnit() {
        return firstUnit;
    }

    public void setSecondAmount(Float secondAmount) {
        this.secondAmount = secondAmount;
    }

    public Float getSecondAmount() {
        return secondAmount;
    }

    public void setSecondUnit(String secondUnit) {
        this.secondUnit = secondUnit;
    }

    public String getSecondUnit() {
        return secondUnit;
    }

    public void setCurrencySystem(String currencySystem) {
        this.currencySystem = currencySystem;
    }

    public String getCurrencySystem() {
        return currencySystem;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBoxNumber(String boxNumber) {
        this.boxNumber = boxNumber;
    }

    public String getBoxNumber() {
        return boxNumber;
    }

    public Date getDeclareDateFrom() {
        return declareDateFrom;
    }

    public void setDeclareDateFrom(Date declareDateFrom) {
        this.declareDateFrom = declareDateFrom;
    }

    public Date getDeclareDateTo() {
        return declareDateTo;
    }

    public void setDeclareDateTo(Date declareDateTo) {
        this.declareDateTo = declareDateTo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Double totalValue) {
        this.totalValue = totalValue;
    }

    public String getModifyFlag() {
        return modifyFlag;
    }

    public void setModifyFlag(String modifyFlag) {
        this.modifyFlag = modifyFlag;
    }

    public Date getMaxDeclareDate() {
        return maxDeclareDate;
    }

    public void setMaxDeclareDate(Date maxDeclareDate) {
        this.maxDeclareDate = maxDeclareDate;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public Double getTotalAmount() { return totalAmount; }

    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }

    public String getInvoiceNumberFrom() {
        return invoiceNumberFrom;
    }

    public void setInvoiceNumberFrom(String invoiceNumberFrom) {
        this.invoiceNumberFrom = invoiceNumberFrom;
    }

    public String getInvoiceNumberTo() {
        return invoiceNumberTo;
    }

    public void setInvoiceNumberTo(String invoiceNumberTo) {
        this.invoiceNumberTo = invoiceNumberTo;
    }

    public String getStringTotalAmount() { return stringTotalAmount; }

    public void setStringTotalAmount(String stringTotalAmount) { this.stringTotalAmount = stringTotalAmount; }

    public String getStringErrorTotalAmount() { return stringErrorTotalAmount; }

    public void setStringErrorTotalAmount(String stringErrorTotalAmount) { this.stringErrorTotalAmount = stringErrorTotalAmount; }

    public String getVesselVoyage() { return vesselVoyage; }

    public void setVesselVoyage(String vesselVoyage) { this.vesselVoyage = vesselVoyage; }

    public String getIsExported() {
        return isExported;
    }

    public void setIsExported(String isExported) {
        this.isExported = isExported;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
