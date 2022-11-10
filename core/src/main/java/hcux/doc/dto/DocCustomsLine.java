package hcux.doc.dto;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import hcux.core.annotation.AutoUpper;
import hcux.core.util.HcuxConstant;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author feng.liu01@hand-china.com
 * @description 报关单行表
 */
@ExtensionAttribute(disable = true)
@Table(name = "HCUX_DOC_CUSTOMS_LINE")
public class DocCustomsLine extends BaseDTO {

    public static final String FIELD_CUSTOMS_LINE_ID = "customsLineId";
    public static final String FIELD_CUSTOMS_ID = "customsId";
    public static final String FIELD_GOODS_NUMBER = "goodsNumber";
    public static final String FIELD_PRODUCT_NAME_CN = "productNameCn";
    public static final String FIELD_PRODUCT_NAME_EN = "productNameEn";
    public static final String FIELD_CUSTOMS_AMOUNT_ONE = "customsAmountOne";
    public static final String FIELD_CUSTOMS_UNIT_ONE = "customsUnitOne";
    public static final String FIELD_CUSTOMS_AMOUNT_TWO = "customsAmountTwo";
    public static final String FIELD_CUSTOMS_UNIT_TWO = "customsUnitTwo";
    public static final String FIELD_CUSTOMS_AMOUNT_THREE = "customsAmountThree";
    public static final String FIELD_CUSTOMS_UNIT_THREE = "customsUnitThree";
    public static final String FIELD_PACKAGE_NUMBER = "packageNumber";
    public static final String FIELD_PACKAGE_NUMBER_UNIT = "packageNumberUnit";
    public static final String FIELD_VALUATION_AMOUNT = "valuationAmount";
    public static final String FIELD_UNIT_PRICE = "unitPrice";
    public static final String FIELD_TOTAL_PRICE = "totalPrice";
    public static final String FIELD_GROSS_WEIGHT = "grossWeight";
    public static final String FIELD_NET_WEIGHT = "netWeight";
    public static final String FIELD_VOLUME = "volume";
    public static final String FIELD_ORIGIN_COUNTRY = "originCountry";
    public static final String FIELD_DESTINATION_COUNTRY = "destinationCountry";
    public static final String FIELD_ORIGIN_PLACE = "originPlace";
    public static final String FIELD_EXEMPTION_WAY = "exemptionWay";
    public static final String FIELD_DECLARATION_ELEMENT = "declarationElement";
    public static final String FIELD_DELETE_FLAG = "deleteFlag";
    public static final String FIELD_FACTORY_NAME = "factoryName";
    public static final String FIELD_PAYMENT_AMOUNT = "paymentAmount";
    public static final String FIELD_TAX_REBATE_RATE = "taxRebateRate";
    public static final String FIELD_EXCHANGE_RATE = "exchangeRate";

    @Id
    @GeneratedValue
    private Long customsLineId; // 表ID，主键，供其他表做外键

    private Long customsId; // 报关单头表HCUX_DOC_CUSTOMS_HEADER.CUSTOMS_ID

    @Length(max = 50)
    @AutoUpper
    private String goodsNumber; // 商品编码

    @Length(max = 200)
    @AutoUpper
    private String productNameCn; // 中文品名

    @Length(max = 200)
    @AutoUpper
    private String productNameEn; // 英文品名

    private Double customsAmountOne; // 报关数量

    @Length(max = 50)
    @AutoUpper
    private String customsUnitOne; // 报关单位

    private Double customsAmountTwo; // 报关数量2

    @Length(max = 50)
    @AutoUpper
    private String customsUnitTwo; // 报关单位2

    private Double customsAmountThree; //辅助数量

    @Length(max = 50)
    @AutoUpper
    private String customsUnitThree; //辅助单位

    private Double packageNumber; // 件数

    @Length(max = 50)
    private String packageNumberUnit; //件数单位

    @Transient
    private String packageNumberUnitMeaning; //件数单位含义

    private Double valuationAmount; // 计价数量

    private Double unitPrice; // 计价单价

    private Double totalPrice; // 总价/金额

    private Double grossWeight; // 毛重

    private Double netWeight; // 净重

    private Double volume; // 体积

    @Length(max = 200)
    @AutoUpper
    private String originCountry; // 原产国

    @Length(max = 200)
    @AutoUpper
    private String destinationCountry; // 最终目的国

    @Length(max = 200)
    @AutoUpper
    private String originPlace; // 境内货源地

    @Length(max = 30)
    private String exemptionWay; // 征免

    @Length(max = 200)
    @AutoUpper
    private String declarationElement; // 申报要素

    @Length(max = 1)
    private String deleteFlag = HcuxConstant.YES_NO.N; // 删除标记
    @Transient
    private String invoiceNumber; // 发票号/项目号

    @AutoUpper
    private String factoryName;//工厂名称

    private Double paymentAmount;//贷款金额

    private Double taxRebateRate;//退税率

    private Double exchangeRate;//换汇比

    @Transient
    private Long rowspan;

    @Transient
    private String currencySystem; // 币制

    @Transient
    private String departurePort; // 离境口岸

    @Transient
    private String dischargePort; // 指运港

    @Transient
    private String consignee; // 境外收货人

    @Transient
    private String shippingMark;//唛头

    public void setDefaultValue() {
        if (StringUtils.isBlank(this.customsUnitOne)) {
            this.customsUnitOne = "PCS";
        }
        if (StringUtils.isBlank(this.packageNumberUnit)) {
            this.packageNumberUnit = "1";
        }
        if (StringUtils.isBlank(this.originCountry)) {
            this.originCountry = "中国";
        }
        if (StringUtils.isBlank(this.exemptionWay)) {
            this.exemptionWay = "1";
        }
    }

    public Long getCustomsLineId() {
        return customsLineId;
    }

    public void setCustomsLineId(Long customsLineId) {
        this.customsLineId = customsLineId;
    }

    public Long getCustomsId() {
        return customsId;
    }

    public void setCustomsId(Long customsId) {
        this.customsId = customsId;
    }

    public String getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(String goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public String getProductNameCn() {
        return productNameCn;
    }

    public void setProductNameCn(String productNameCn) {
        this.productNameCn = productNameCn;
    }

    public String getProductNameEn() {
        return productNameEn;
    }

    public void setProductNameEn(String productNameEn) {
        this.productNameEn = productNameEn;
    }

    public Double getCustomsAmountOne() {
        return customsAmountOne;
    }

    public void setCustomsAmountOne(Double customsAmountOne) {
        this.customsAmountOne = customsAmountOne;
    }

    public String getCustomsUnitOne() {
        return customsUnitOne;
    }

    public void setCustomsUnitOne(String customsUnitOne) {
        this.customsUnitOne = customsUnitOne;
    }

    public Double getCustomsAmountTwo() {
        return customsAmountTwo;
    }

    public void setCustomsAmountTwo(Double customsAmountTwo) {
        this.customsAmountTwo = customsAmountTwo;
    }

    public String getCustomsUnitTwo() {
        return customsUnitTwo;
    }

    public void setCustomsUnitTwo(String customsUnitTwo) {
        this.customsUnitTwo = customsUnitTwo;
    }

    public Double getPackageNumber() {
        return packageNumber;
    }

    public void setPackageNumber(Double packageNumber) {
        this.packageNumber = packageNumber;
    }

    public Double getValuationAmount() {
        return valuationAmount;
    }

    public void setValuationAmount(Double valuationAmount) {
        this.valuationAmount = valuationAmount;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(Double grossWeight) {
        this.grossWeight = grossWeight;
    }

    public Double getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(Double netWeight) {
        this.netWeight = netWeight;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public String getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }

    public String getDestinationCountry() {
        return destinationCountry;
    }

    public void setDestinationCountry(String destinationCountry) {
        this.destinationCountry = destinationCountry;
    }

    public String getOriginPlace() {
        return originPlace;
    }

    public void setOriginPlace(String originPlace) {
        this.originPlace = originPlace;
    }

    public String getExemptionWay() {
        return exemptionWay;
    }

    public void setExemptionWay(String exemptionWay) {
        this.exemptionWay = exemptionWay;
    }

    public String getDeclarationElement() {
        return declarationElement;
    }

    public void setDeclarationElement(String declarationElement) {
        this.declarationElement = declarationElement;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Double getCustomsAmountThree() {
        return customsAmountThree;
    }

    public void setCustomsAmountThree(Double customsAmountThree) {
        this.customsAmountThree = customsAmountThree;
    }

    public String getCustomsUnitThree() {
        return customsUnitThree;
    }

    public void setCustomsUnitThree(String customsUnitThree) {
        this.customsUnitThree = customsUnitThree;
    }

    public String getPackageNumberUnit() {
        return packageNumberUnit;
    }

    public void setPackageNumberUnit(String packageNumberUnit) {
        this.packageNumberUnit = packageNumberUnit;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    public Double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public Double getTaxRebateRate() {
        return taxRebateRate;
    }

    public void setTaxRebateRate(Double taxRebateRate) {
        this.taxRebateRate = taxRebateRate;
    }

    public Double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(Double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public Long getRowspan() {
        return rowspan;
    }

    public void setRowspan(Long rowspan) {
        this.rowspan = rowspan;
    }

    public String getPackageNumberUnitMeaning() {
        return packageNumberUnitMeaning;
    }

    public void setPackageNumberUnitMeaning(String packageNumberUnitMeaning) {
        this.packageNumberUnitMeaning = packageNumberUnitMeaning;
    }

    public String getCurrencySystem() { return currencySystem; }

    public void setCurrencySystem(String currencySystem) { this.currencySystem = currencySystem; }

    public String getDeparturePort() { return departurePort; }

    public void setDeparturePort(String departurePort) { this.departurePort = departurePort; }

    public String getDischargePort() { return dischargePort; }

    public void setDischargePort(String dischargePort) { this.dischargePort = dischargePort; }

    public String getConsignee() { return consignee; }

    public void setConsignee(String consignee) { this.consignee = consignee; }

    public String getShippingMark() { return shippingMark; }

    public void setShippingMark(String shippingMark) { this.shippingMark = shippingMark; }
}
