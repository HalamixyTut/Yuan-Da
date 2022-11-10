package hcux.doc.dto;

import com.hand.hap.mybatis.annotation.Condition;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;
import hcux.core.annotation.AutoUpper;
import hcux.core.util.HcuxConstant;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.PictureData;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;

/**
 * @author feng.liu01@hand-china.com
 * @description 托单数据
 */
@ExtensionAttribute(disable = true)
@Table(name = "HCUX_DOC_BOOKING_NOTE")
public class DocBookingNote extends BaseDTO {
    private static final long serialVersionUID = -7201480214677884779L;

    public static final String FIELD_BOOKING_ID = "bookingId";
    public static final String FIELD_INVOICE_NUMBER = "invoiceNumber";
    public static final String FIELD_SHIPPER = "shipper";
    public static final String FIELD_SHIPPER_ADDRESS = "shipperAddress";
    public static final String FIELD_SHIPPER_MOBILE = "shipperMobile";
    public static final String FIELD_CUSTOMER_ID = "customerId";
    public static final String FIELD_CONSIGNEE = "consignee";
    public static final String FIELD_CONSIGNEE_ADDRESS = "consigneeAddress";
    public static final String FIELD_CONSIGNEE_MOBILE = "consigneeMobile";
    public static final String FIELD_NOTIFICATION_UNIT = "notificationUnit";
    public static final String FIELD_NOTIFICATION_ADDRESS = "notificationAddress";
    public static final String FIELD_NOTIFICATION_MOBILE = "notificationMobile";
    public static final String FIELD_TRANSPORT_WAY = "transportWay";
    public static final String FIELD_STOPPING_PLACE = "stoppingPlace";
    public static final String FIELD_SHIPMENT_PORT = "shipmentPort";
    public static final String FIELD_DISCHARGE_PORT = "dischargePort";
    public static final String FIELD_DESTINATION_PORT = "destinationPort";
    public static final String FIELD_FREIGHT_CLAUSE = "freightClause";
    public static final String FIELD_BILL_COPIES = "billCopies";
    public static final String FIELD_CARGO_AGENT_UNIT = "cargoAgentUnit";
    public static final String FIELD_CARGO_AGENT = "cargoAgent";
    public static final String FIELD_CARGO_AGENT_MOBILE = "cargoAgentMobile";
    public static final String FIELD_CARGO_AGENT_EMAIL = "cargoAgentEmail";
    public static final String FIELD_DECLARE_CUSTOM_LINE = "declareCustomLine";
    public static final String FIELD_STOWAGE_PLAN = "stowagePlan";
    public static final String FIELD_SAILING_DATE = "sailingDate";
    public static final String FIELD_BATCH = "batch";
    public static final String FIELD_TRANSFER = "transfer";
    public static final String FIELD_PRODUCT_NAME = "productName";
    public static final String FIELD_PACKAGE_NUMBER = "packageNumber";
    public static final String FIELD_PACKAGE_NUMBER_UNIT = "packageNumberUnit";
    public static final String FIELD_GROSS_WEIGHT = "grossWeight";
    public static final String FIELD_NET_WEIGHT = "netWeight";
    public static final String FIELD_VOLUME = "volume";
    public static final String FIELD_MONEY = "money";
    public static final String FIELD_CUSTOM_STATISTICS_CODE = "customStatisticsCode";
    public static final String FIELD_SHIPPING_MARK = "shippingMark";
    public static final String FIELD_ATTACHMENT_ID = "attachmentId";
    public static final String FIELD_CONTACT_PEOPLE = "contactPeople";
    public static final String FIELD_CONTACT_ADDRESS = "contactAddress";
    public static final String FIELD_CONTACT_MOBILE = "contactMobile";
    public static final String FIELD_CONTACT_EMAIL = "contactEmail";
    public static final String FIELD_CUSTOMER_ORDER_NUM = "customerOrderNum";
    public static final String FIELD_DELETE_FLAG = "deleteFlag";

    @Id
    @GeneratedValue
    private Long bookingId; //表ID，主键，供其他表做外键

    @Length(max = 30)
    @Condition(operator = LIKE)
    @Where(comparison = Comparison.LIKE)
    @AutoUpper
    private String invoiceNumber; //发票号/项目号

    @Length(max = 200)
    @AutoUpper
    private String shipper; //托运单位/发货人

    @Length(max = 1000)
    @AutoUpper
    private String shipperAddress; //托运单位地址

    @Length(max = 200)
    @AutoUpper
    private String shipperMobile; //托运单位电话

    private Long customerId; //客户数据表HCUX_MDM_CUSTOMER.CUSTOMER_ID

    @Length(max = 200)
    @Condition(operator = LIKE)
    @Where(comparison = Comparison.LIKE)
    @AutoUpper
    private String consignee; //收货单位/收货人

    @Length(max = 1000)
    @AutoUpper
    private String consigneeAddress; //收货单位地址

    @Length(max = 200)
    @AutoUpper
    private String consigneeMobile; //收货单位电话

    @Length(max = 200)
    @AutoUpper
    private String notificationUnit; //通知单位/通知人

    @Length(max = 1000)
    @AutoUpper
    private String notificationAddress; //通知单位地址

    @Length(max = 200)
    @AutoUpper
    private String notificationMobile; //通知单位电话

    @Length(max = 30)
    private String transportWay; //运输方式

    @Length(max = 200)
    @AutoUpper
    private String stoppingPlace; //停货地点

    @Length(max = 200)
    @AutoUpper
    private String shipmentPort; //起运港

    @Length(max = 200)
    @AutoUpper
    private String dischargePort; // 卸货港

    @Length(max = 200)
    @AutoUpper
    private String destinationPort; //目的港

    @Length(max = 30)
    private String freightClause; //运费条款

    @Length(max = 30)
    @AutoUpper
    private String billCopies; //提单份数

    @Length(max = 100)
    private String cargoAgent; //货代联系人

    @Length(max = 100)
    @AutoUpper
    private String cargoAgentUnit; //货代简称

    @Length(max = 100)
    @AutoUpper
    private String cargoAgentMobile; //货代电话

    @Length(max = 100)
    @AutoUpper
    private String cargoAgentEmail; //货代邮件

    @Length(max = 200)
    @AutoUpper
    private String declareCustomLine; //报关行

    @Length(max = 200)
    @AutoUpper
    private String stowagePlan; //配载要求

    private Date sailingDate; //船期

    @Length(max = 100)
    @AutoUpper
    private String batch; //分批

    @Length(max = 100)
    @AutoUpper
    private String transfer; //转运

    @Length(max = 500)
    @AutoUpper
    private String productName; //品名

    private Double packageNumber; //件数

    @Length(max = 50)
    private String packageNumberUnit; //件数单位

    private Double grossWeight; //毛重

    private Double netWeight; //净重

    private Double volume; //体积

    private Double money; //金额

    @Length(max = 100)
    @AutoUpper
    private String customStatisticsCode; //海关统计编码

    @AutoUpper
    private String shippingMark; //唛头

    private Long attachmentId; //唛头图片，SYS_ATTACHMENT.ATTACHMENT_ID

    @Length(max = 100)
    @AutoUpper
    private String contactPeople; //联系人

    @Length(max = 200)
    @AutoUpper
    private String contactAddress; //联络地址

    @Length(max = 100)
    @AutoUpper
    private String contactMobile; //联系电话

    @Length(max = 100)
    @AutoUpper
    private String contactEmail; //联系邮箱

    @Length(max = 100)
    @AutoUpper
    private String customerOrderNum; //客户订单号

    private Date goodsDate; //货好日

    @Length(max = 200)
    @AutoUpper
    private String boxType; //箱型

    @Length(max = 200)
    @AutoUpper
    private String cabinet; //进仓或装柜

    @Length(max = 200)
    @AutoUpper
    private String cabinetAddress; //装柜地址及联系方式

    @Length(max = 200)
    @AutoUpper
    private String remark; //其他备注信息

    @Length(max = 1)
    private String deleteFlag = HcuxConstant.YES_NO.N; // 删除标记

    @Column(updatable = false)
    @Condition(exclude = true)
    private Date creationDate;

    @Transient
    private String country; //国别
    @Transient
    Map<String, PictureData> pictureDataMap;//临时字段，用于excel导入唛头时用

    @Transient
    private Long fileId; //文件ID

    @Transient
    private String fileName; //文件名

    @Transient
    private String createdByName; // 创建人
    @Transient
    private String downloadUrl; //下载URL

    public DocBookingNote() {
    }

    public void setDefaultValue() {
        if (StringUtils.isBlank(this.shipper)) {
            this.shipper = "浙江新景";
        }
        if (StringUtils.isBlank(this.transportWay)) {
            this.transportWay = "2";
        }
        if (StringUtils.isBlank(this.freightClause)) {
            this.freightClause = "0";
        }
        if (StringUtils.isBlank(this.notificationUnit)) {
            this.notificationUnit = "SAME AS CONSIGNEE";
        }
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

    public String getShipper() {
        return shipper;
    }

    public void setShipper(String shipper) {
        this.shipper = shipper;
    }

    public String getShipperAddress() {
        return shipperAddress;
    }

    public void setShipperAddress(String shipperAddress) {
        this.shipperAddress = shipperAddress;
    }

    public String getShipperMobile() {
        return shipperMobile;
    }

    public void setShipperMobile(String shipperMobile) {
        this.shipperMobile = shipperMobile;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getConsigneeAddress() {
        return consigneeAddress;
    }

    public void setConsigneeAddress(String consigneeAddress) {
        this.consigneeAddress = consigneeAddress;
    }

    public String getConsigneeMobile() {
        return consigneeMobile;
    }

    public void setConsigneeMobile(String consigneeMobile) {
        this.consigneeMobile = consigneeMobile;
    }

    public String getNotificationUnit() {
        return notificationUnit;
    }

    public void setNotificationUnit(String notificationUnit) {
        this.notificationUnit = notificationUnit;
    }

    public String getNotificationAddress() {
        return notificationAddress;
    }

    public void setNotificationAddress(String notificationAddress) {
        this.notificationAddress = notificationAddress;
    }

    public String getNotificationMobile() {
        return notificationMobile;
    }

    public void setNotificationMobile(String notificationMobile) {
        this.notificationMobile = notificationMobile;
    }

    public String getTransportWay() {
        return transportWay;
    }

    public void setTransportWay(String transportWay) {
        this.transportWay = transportWay;
    }

    public String getStoppingPlace() {
        return stoppingPlace;
    }

    public void setStoppingPlace(String stoppingPlace) {
        this.stoppingPlace = stoppingPlace;
    }

    public String getShipmentPort() {
        return shipmentPort;
    }

    public void setShipmentPort(String shipmentPort) {
        this.shipmentPort = shipmentPort;
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

    public String getFreightClause() {
        return freightClause;
    }

    public void setFreightClause(String freightClause) {
        this.freightClause = freightClause;
    }

    public String getBillCopies() {
        return billCopies;
    }

    public void setBillCopies(String billCopies) {
        this.billCopies = billCopies;
    }

    public String getCargoAgent() {
        return cargoAgent;
    }

    public void setCargoAgent(String cargoAgent) {
        this.cargoAgent = cargoAgent;
    }

    public String getCargoAgentUnit() {
        return cargoAgentUnit;
    }

    public void setCargoAgentUnit(String cargoAgentUnit) {
        this.cargoAgentUnit = cargoAgentUnit;
    }

    public String getCargoAgentMobile() {
        return cargoAgentMobile;
    }

    public void setCargoAgentMobile(String cargoAgentMobile) {
        this.cargoAgentMobile = cargoAgentMobile;
    }

    public String getCargoAgentEmail() {
        return cargoAgentEmail;
    }

    public void setCargoAgentEmail(String cargoAgentEmail) {
        this.cargoAgentEmail = cargoAgentEmail;
    }

    public String getDeclareCustomLine() {
        return declareCustomLine;
    }

    public void setDeclareCustomLine(String declareCustomLine) {
        this.declareCustomLine = declareCustomLine;
    }

    public String getStowagePlan() {
        return stowagePlan;
    }

    public void setStowagePlan(String stowagePlan) {
        this.stowagePlan = stowagePlan;
    }

    public Date getSailingDate() {
        return sailingDate;
    }

    public void setSailingDate(Date sailingDate) {
        this.sailingDate = sailingDate;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getTransfer() {
        return transfer;
    }

    public void setTransfer(String transfer) {
        this.transfer = transfer;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getCustomStatisticsCode() {
        return customStatisticsCode;
    }

    public void setCustomStatisticsCode(String customStatisticsCode) {
        this.customStatisticsCode = customStatisticsCode;
    }

    public String getShippingMark() {
        return shippingMark;
    }

    public void setShippingMark(String shippingMark) {
        this.shippingMark = shippingMark;
    }

    public String getContactPeople() {
        return contactPeople;
    }

    public void setContactPeople(String contactPeople) {
        this.contactPeople = contactPeople;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    @Override
    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCustomerOrderNum() {
        return customerOrderNum;
    }

    public void setCustomerOrderNum(String customerOrderNum) {
        this.customerOrderNum = customerOrderNum;
    }

    public Long getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Long attachmentId) {
        this.attachmentId = attachmentId;
    }

    public Map<String, PictureData> getPictureDataMap() {
        return pictureDataMap;
    }

    public void setPictureDataMap(Map<String, PictureData> pictureDataMap) {
        this.pictureDataMap = pictureDataMap;
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

    public Date getGoodsDate() {
        return goodsDate;
    }

    public void setGoodsDate(Date goodsDate) {
        this.goodsDate = goodsDate;
    }

    public String getBoxType() {
        return boxType;
    }

    public void setBoxType(String boxType) {
        this.boxType = boxType;
    }

    public String getCabinet() {
        return cabinet;
    }

    public void setCabinet(String cabinet) {
        this.cabinet = cabinet;
    }

    public String getCabinetAddress() {
        return cabinetAddress;
    }

    public void setCabinetAddress(String cabinetAddress) {
        this.cabinetAddress = cabinetAddress;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}
