package hcux.doc.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.annotation.Children;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import hcux.core.annotation.AutoUpper;
import hcux.core.util.HcuxConstant;
import hcux.doc.util.DocConstant;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;
import java.util.Set;


/**
 * @author feng.liu01@hand-china.com
 * @description 报关单头表
 */
@ExtensionAttribute(disable = true)
@Table(name = "HCUX_DOC_CUSTOMS_HEADER")
public class DocCustomsHeader extends BaseDTO {

    private static final long serialVersionUID = 1333162078219779426L;
    public static final String FIELD_CUSTOMS_ID = "customsId";
    public static final String FIELD_BOOKING_ID = "bookingId";
    public static final String FIELD_INVOICE_NUMBER = "invoiceNumber";
    public static final String FIELD_CONTRACT_NUMBER = "contractNumber";
    public static final String FIELD_INVOICE_DATE = "invoiceDate";
    public static final String FIELD_SAILING_DATE = "sailingDate";
    public static final String FIELD_SHIPPER_CODE = "shipperCode";
    public static final String FIELD_SHIPPER_NAME = "shipperName";
    public static final String FIELD_EXIT_PORT = "exitPort";
    public static final String FIELD_EXIT_DATE = "exitDate";
    public static final String FIELD_RECORD_NUMBER = "recordNumber";
    public static final String FIELD_RECORD_DATE = "recordDate";
    public static final String FIELD_CONSIGNEE = "consignee";
    public static final String FIELD_TRANSPORT_WAY = "transportWay";
    public static final String FIELD_VESSEL_VOYAGE = "vesselVoyage";
    public static final String FIELD_DELIVERY_NUMBER = "deliveryNumber";
    public static final String FIELD_PRODUCTION_UNIT_CODE = "productionUnitCode";
    public static final String FIELD_PRODUCTION_UNIT = "productionUnit";
    public static final String FIELD_SUPERVISION_MODE = "supervisionMode";
    public static final String FIELD_EXEMPTION_NATURE = "exemptionNature";
    public static final String FIELD_LICENSE_KEY = "licenseKey";
    public static final String FIELD_TRADING_COUNTRY = "tradingCountry";
    public static final String FIELD_ARRIVAL_COUNTRY = "arrivalCountry";
    public static final String FIELD_DISCHARGE_PORT = "dischargePort";
    public static final String FIELD_DESTINATION_PORT = "destinationPort";
    public static final String FIELD_DEPARTURE_PORT = "departurePort";
    public static final String FIELD_PACKAGE_TYPE = "packageType";
    public static final String FIELD_PACKAGE_NUMBER = "packageNumber";
    public static final String FIELD_PACKAGE_NUMBER_UNIT = "packageNumberUnit";
    public static final String FIELD_GROSS_WEIGHT = "grossWeight";
    public static final String FIELD_NET_WEIGHT = "netWeight";
    public static final String FIELD_TRANSACTION_MODE = "transactionMode";
    public static final String FIELD_FREIGHT = "freight";
    public static final String FIELD_PREMIUM = "premium";
    public static final String FIELD_INCIDENTAL = "incidental";
    public static final String FIELD_ATTACHED_DOCUMENT = "attachedDocument";
    public static final String FIELD_REMARK = "remark";
    public static final String FIELD_SPECIAL_RELATION = "specialRelation";
    public static final String FIELD_PRICE_IMPACT = "priceImpact";
    public static final String FIELD_PAYMENT_ROYALTIES = "paymentRoyalties";
    public static final String FIELD_STATUS = "status";
    public static final String FIELD_DELETE_FLAG = "deleteFlag";
    public static final String FIELD_SHIPPING_MARK = "shippingMark";
    public static final String FIELD_ATTACHMENT_ID = "attachmentId";
    public static final String FIELD_CURRENCY_SYSTEM = "currencySystem";

    @Id
    @GeneratedValue
    private Long customsId; // 表ID，主键，供其他表做外键

    private Long bookingId; // 托单数据表HCUX_DOC_BOOKING_NOTE.BOOKING_ID

    @Length(max = 30)
    @AutoUpper
    private String invoiceNumber; // 发票号/项目号

    @Length(max = 30)
    @AutoUpper
    private String contractNumber; // 合同协议号

    @DateTimeFormat(pattern = BaseConstants.DATE_FORMAT)
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT, timezone = "GMT+8")
    private Date invoiceDate; // 发票日期

    @DateTimeFormat(pattern = BaseConstants.DATE_FORMAT)
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT, timezone = "GMT+8")
    private Date sailingDate; // 船期

    @Length(max = 50)
    @AutoUpper
    private String shipperCode; // 境内发货人编码

    @Length(max = 250)
    @AutoUpper
    private String shipperName; // 发货人名称

    @Length(max = 100)
    private String currencySystem; // 币制

    @Length(max = 250)
    @AutoUpper
    private String exitPort; // 出境关别

    private Date exitDate; // 出口日期

    @Length(max = 50)
    @AutoUpper
    private String recordNumber; // 备案号

    private Date recordDate; // 申报日期

    @Length(max = 200)
    @AutoUpper
    private String consignee; // 境外收货人

    @Length(max = 30)
    private String transportWay; // 运输方式

    @Length(max = 200)
    @AutoUpper
    private String vesselVoyage; // 船名航次

    @Length(max = 100)
    @AutoUpper
    private String deliveryNumber; // 提运单号

    @Length(max = 300)
    @AutoUpper
    private String productionUnitCode; // 销售单位编码

    @Length(max = 200)
    private String productionUnit; // 生产销售单位

    @Length(max = 30)
    private String supervisionMode; // 监管方式

    @Length(max = 30)
    private String exemptionNature; // 征免性质

    @Length(max = 100)
    private String licenseKey; // 许可证号

    @Length(max = 200)
    @AutoUpper
    private String tradingCountry; // 贸易国

    @Length(max = 200)
    @AutoUpper
    private String arrivalCountry; // 运抵国

    @Length(max = 200)
    @AutoUpper
    private String dischargePort; // 指运港

    @Length(max = 200)
    @AutoUpper
    private String destinationPort; // 目的港

    @Length(max = 200)
    @AutoUpper
    private String departurePort; // 离境口岸

    @Length(max = 50)
    private String packageType; // 包装种类

    @AutoUpper
    private String otherPackageType; //其他包装种类

    @Transient
    private Double packageNumber; // 件数

    @Transient
    @AutoUpper
    private String packageNumberUnit; //件数单位

    @Transient
    private Double grossWeight; // 毛重（KG）

    @Transient
    private Double netWeight; // 净重（KG）

    @Length(max = 50)
    private String transactionMode; // 成交方式

    private Double freight; // 运费

    private Double premium; // 保费

    private Double incidental; // 杂费

    @Length(max = 200)
    @AutoUpper
    private String attachedDocument; // 随附单证及编号

    @Length(max = 250)
    @AutoUpper
    private String remark; // 标记唛码及备注

    @Length(max = 1)
    private String specialRelation; // 特殊关系确认

    @Length(max = 1)
    private String priceImpact; // 价格影响确认

    @Length(max = 1)
    private String paymentRoyalties; // 支付特许权使用费确认

    @Length(max = 50)
    private String status; // 状态

    @Length(max = 1)
    private String deleteFlag = HcuxConstant.YES_NO.N; // 删除标记

    @Transient
    private String bookingFlag; //来自托单
    @Transient
    private String createdByName; // 创建人

    @Transient
    @DateTimeFormat(pattern = BaseConstants.DATE_FORMAT)
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT, timezone = "GMT+8")
    private Date invoiceDateFrom;

    @Transient
    @DateTimeFormat(pattern = BaseConstants.DATE_FORMAT)
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT, timezone = "GMT+8")
    private Date invoiceDateTo;

    @Transient
    @Children
    private List<DocCustomsLine> lineList;

    @Transient
    private Double totalPrice; // 总价
    @Transient
    private String productName; // 中文品名/货名
    @Transient
    private Set<String> invoiceNumberSet;//项目号的集合，用于excel导入时使用

    private Long attachmentId;//上传唛头图片，用于存放唛头的附件ID

    @AutoUpper
    private String shippingMark;//唛头

    private Long documentId; // 单证员ID，HR_EMPLOYEE.EMPLOYEE_ID

    private Long approvalId; // 复核员ID，HR_EMPLOYEE.EMPLOYEE_ID

    private Long customsModifySn; //海关改单序列号

    @Transient
    private String documentName; // 单证员姓名
    @Transient
    private String approvalName; // 复核员姓名

    @Transient
    private List<DocBookingNote> docBookingNoteList;//用于批量上传校验有重复数据时，传递数据

    @Transient
    private Double volume; // 体积
    @Transient
    private Long fileId; //文件ID

    @Transient
    private String fileName; //文件名

    @Transient
    private String downloadUrl; //下载URL
    @Transient
    private Date invoiceDateBefore;//31天前的发票日期，PDF打印合同用
    @Transient
    private Date invoiceDateAfter;//45天后的发票日期，PDF打印合同用

    @Transient
    private Double totalAmount; // 总计金额
    @Transient
    private Integer randomImg; //合同打印图片随机数

    @Transient
    private List<String> statusList; // 保存前台多选的状态值

    @Transient
    private String currentUserName; //保存单证员或者复核员的临时值

    @Transient
    private int page = 1;

    @Transient
    private int pagesize = 10;

    @Transient
    @AutoUpper
    private String declareCustomLine;//综合查询的报关行

    @Transient
    @AutoUpper
    private String billNumber;//综合查询的提单号

    @Transient
    @AutoUpper
    private String goodsNumber; //报关行的商品编码

    @Transient
    @AutoUpper
    private String productNameCn; //报关行的中文品名

    @Transient
    private String stringTotalAmount; //查询条件下的 报关单 总金额

    public void setDefaultValue() {
        this.invoiceDate = new Date();
        this.status = DocConstant.HCUX_DOC_STATUS.A;
        if (StringUtils.isBlank(this.contractNumber)) {
            this.contractNumber = this.invoiceNumber;
        }
        if (StringUtils.isBlank(this.productionUnitCode)) {
            this.productionUnitCode = "3302210027 浙江新景进出口有限公司";
        }
        if (StringUtils.isBlank(this.productionUnit)) {
            this.productionUnit = "AEOCN3302210027,商检3801600511,社会信用913302067960082341";
        }
        if (StringUtils.isBlank(this.supervisionMode)) {
            this.supervisionMode = "0110";
        }

        if (StringUtils.isBlank(this.exemptionNature)) {
            this.exemptionNature = "101";
        }
        if (StringUtils.isBlank(this.packageType)) {
            this.packageType = "22";
        }
        if (StringUtils.isBlank(this.transactionMode)) {
            this.transactionMode = "3";
        }

        if (StringUtils.isBlank(this.shippingMark)) {
            this.shippingMark = "N/M";
        }
        if (StringUtils.isBlank(this.remark)) {
            this.remark = "唛头见发票";
        }
        if (StringUtils.isBlank(this.specialRelation)) {
            this.specialRelation = "N";
        }
        if (StringUtils.isBlank(this.priceImpact)) {
            this.priceImpact = "N";
        }
        if (StringUtils.isBlank(this.paymentRoyalties)) {
            this.paymentRoyalties = "N";

        }

        if (StringUtils.isBlank(this.currencySystem)) {
            this.currencySystem = "USD";
        }
    }

    public Long getCustomsId() {
        return customsId;
    }

    public void setCustomsId(Long customsId) {
        this.customsId = customsId;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Date getSailingDate() {
        return sailingDate;
    }

    public void setSailingDate(Date sailingDate) {
        this.sailingDate = sailingDate;
    }

    public String getShipperCode() {
        return shipperCode;
    }

    public void setShipperCode(String shipperCode) {
        this.shipperCode = shipperCode;
    }

    public String getShipperName() {
        return shipperName;
    }

    public void setShipperName(String shipperName) {
        this.shipperName = shipperName;
    }

    public String getExitPort() {
        return exitPort;
    }

    public void setExitPort(String exitPort) {
        this.exitPort = exitPort;
    }

    public Date getExitDate() {
        return exitDate;
    }

    public void setExitDate(Date exitDate) {
        this.exitDate = exitDate;
    }

    public String getRecordNumber() {
        return recordNumber;
    }

    public void setRecordNumber(String recordNumber) {
        this.recordNumber = recordNumber;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getTransportWay() {
        return transportWay;
    }

    public void setTransportWay(String transportWay) {
        this.transportWay = transportWay;
    }

    public String getVesselVoyage() {
        return vesselVoyage;
    }

    public void setVesselVoyage(String vesselVoyage) {
        this.vesselVoyage = vesselVoyage;
    }

    public String getDeliveryNumber() {
        return deliveryNumber;
    }

    public void setDeliveryNumber(String deliveryNumber) {
        this.deliveryNumber = deliveryNumber;
    }

    public String getProductionUnitCode() {
        return productionUnitCode;
    }

    public void setProductionUnitCode(String productionUnitCode) {
        this.productionUnitCode = productionUnitCode;
    }

    public String getProductionUnit() {
        return productionUnit;
    }

    public void setProductionUnit(String productionUnit) {
        this.productionUnit = productionUnit;
    }

    public String getSupervisionMode() {
        return supervisionMode;
    }

    public void setSupervisionMode(String supervisionMode) {
        this.supervisionMode = supervisionMode;
    }

    public String getExemptionNature() {
        return exemptionNature;
    }

    public void setExemptionNature(String exemptionNature) {
        this.exemptionNature = exemptionNature;
    }

    public String getLicenseKey() {
        return licenseKey;
    }

    public void setLicenseKey(String licenseKey) {
        this.licenseKey = licenseKey;
    }

    public String getTradingCountry() {
        return tradingCountry;
    }

    public void setTradingCountry(String tradingCountry) {
        this.tradingCountry = tradingCountry;
    }

    public String getArrivalCountry() {
        return arrivalCountry;
    }

    public void setArrivalCountry(String arrivalCountry) {
        this.arrivalCountry = arrivalCountry;
    }

    public String getDischargePort() {
        return dischargePort;
    }

    public void setDischargePort(String dischargePort) {
        this.dischargePort = dischargePort;
    }

    public String getDestinationPort() {
        return destinationPort;
    }

    public void setDestinationPort(String destinationPort) {
        this.destinationPort = destinationPort;
    }

    public String getDeparturePort() {
        return departurePort;
    }

    public void setDeparturePort(String departurePort) {
        this.departurePort = departurePort;
    }

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public Double getPackageNumber() {
        return packageNumber;
    }

    public void setPackageNumber(Double packageNumber) {
        this.packageNumber = packageNumber;
    }

    public String getPackageNumberUnit() {
        return packageNumberUnit;
    }

    public void setPackageNumberUnit(String packageNumberUnit) {
        this.packageNumberUnit = packageNumberUnit;
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

    public String getTransactionMode() {
        return transactionMode;
    }

    public void setTransactionMode(String transactionMode) {
        this.transactionMode = transactionMode;
    }

    public Double getFreight() {
        return freight;
    }

    public void setFreight(Double freight) {
        this.freight = freight;
    }

    public Double getPremium() {
        return premium;
    }

    public void setPremium(Double premium) {
        this.premium = premium;
    }

    public Double getIncidental() {
        return incidental;
    }

    public void setIncidental(Double incidental) {
        this.incidental = incidental;
    }

    public String getAttachedDocument() {
        return attachedDocument;
    }

    public void setAttachedDocument(String attachedDocument) {
        this.attachedDocument = attachedDocument;
    }

    public String getShippingMark() {
        return shippingMark;
    }

    public void setShippingMark(String shippingMark) {
        this.shippingMark = shippingMark;
    }

    public String getSpecialRelation() {
        return specialRelation;
    }

    public void setSpecialRelation(String specialRelation) {
        this.specialRelation = specialRelation;
    }

    public String getPriceImpact() {
        return priceImpact;
    }

    public void setPriceImpact(String priceImpact) {
        this.priceImpact = priceImpact;
    }

    public String getPaymentRoyalties() {
        return paymentRoyalties;
    }

    public void setPaymentRoyalties(String paymentRoyalties) {
        this.paymentRoyalties = paymentRoyalties;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getBookingFlag() {
        return bookingFlag;
    }

    public void setBookingFlag(String bookingFlag) {
        this.bookingFlag = bookingFlag;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public Date getInvoiceDateFrom() {
        return invoiceDateFrom;
    }

    public void setInvoiceDateFrom(Date invoiceDateFrom) {
        this.invoiceDateFrom = invoiceDateFrom;
    }

    public Date getInvoiceDateTo() {
        return invoiceDateTo;
    }

    public void setInvoiceDateTo(Date invoiceDateTo) {
        this.invoiceDateTo = invoiceDateTo;
    }

    public List<DocCustomsLine> getLineList() {
        return lineList;
    }

    public void setLineList(List<DocCustomsLine> lineList) {
        this.lineList = lineList;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Set<String> getInvoiceNumberSet() {
        return invoiceNumberSet;
    }

    public void setInvoiceNumberSet(Set<String> invoiceNumberSet) {
        this.invoiceNumberSet = invoiceNumberSet;
    }

    public Long getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Long attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<DocBookingNote> getDocBookingNoteList() {
        return docBookingNoteList;
    }

    public void setDocBookingNoteList(List<DocBookingNote> docBookingNoteList) {
        this.docBookingNoteList = docBookingNoteList;
    }

    public String getCurrencySystem() {
        return currencySystem;
    }

    public void setCurrencySystem(String currencySystem) {
        this.currencySystem = currencySystem;
    }

    public Long getApprovalId() {
        return approvalId;
    }

    public void setApprovalId(Long approvalId) {
        this.approvalId = approvalId;
    }

    public String getApprovalName() {
        return approvalName;
    }

    public void setApprovalName(String approvalName) {
        this.approvalName = approvalName;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getOtherPackageType() {
        return otherPackageType;
    }

    public void setOtherPackageType(String otherPackageType) {
        this.otherPackageType = otherPackageType;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public Date getInvoiceDateBefore() {
        return invoiceDateBefore;
    }

    public void setInvoiceDateBefore(Date invoiceDateBefore) {
        this.invoiceDateBefore = invoiceDateBefore;
    }

    public Date getInvoiceDateAfter() {
        return invoiceDateAfter;
    }

    public void setInvoiceDateAfter(Date invoiceDateAfter) {
        this.invoiceDateAfter = invoiceDateAfter;
    }

    public Long getCustomsModifySn() {
        return customsModifySn;
    }

    public void setCustomsModifySn(Long customsModifySn) {
        this.customsModifySn = customsModifySn;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public Integer getRandomImg() {
        return randomImg;
    }

    public void setRandomImg(Integer randomImg) {
        this.randomImg = randomImg;
    }

    public List<String> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<String> statusList) {
        this.statusList = statusList;
    }

    public String getCurrentUserName() {
        return currentUserName;
    }

    public void setCurrentUserName(String currentUserName) {
        this.currentUserName = currentUserName;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public String getDeclareCustomLine() {
        return declareCustomLine;
    }

    public void setDeclareCustomLine(String declareCustomLine) {
        this.declareCustomLine = declareCustomLine;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
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

    public String getStringTotalAmount() { return stringTotalAmount; }

    public void setStringTotalAmount(String stringTotalAmount) { this.stringTotalAmount = stringTotalAmount; }
}
