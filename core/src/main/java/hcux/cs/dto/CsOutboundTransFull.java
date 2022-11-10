package hcux.cs.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hand.hap.core.BaseConstants;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import org.hibernate.validator.constraints.Length;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.hand.hap.system.dto.BaseDTO;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@ExtensionAttribute(disable=true)
@Table(name = "HCUX_CS_OUTBOUND_TRANS_FULL")
public class CsOutboundTransFull extends BaseDTO {

     public static final String FIELD_TRANS_FULL_ID = "transFullId";
     public static final String FIELD_INVOICE_ID = "invoiceId";
     public static final String FIELD_INVOICE_NO = "invoiceNo";
     public static final String FIELD_DRIVER_NAME = "driverName";
     public static final String FIELD_ID_NUMBER = "idNumber";
     public static final String FIELD_TRANSPORT_NUM = "transportNum";
     public static final String FIELD_PLATE_NUMBER = "plateNumber";
     public static final String FIELD_CONTACT_INFO = "contactInfo";
     public static final String FIELD_ITEM_CODE = "itemCode";
     public static final String FIELD_ITEM_DESC = "itemDesc";
     public static final String FIELD_QUANTITY = "quantity";
     public static final String FIELD_PACKET_CNT = "packetCnt";
     public static final String FIELD_TANK_NUM = "tankNum";
     public static final String FIELD_REMARK = "remark";
     public static final String FIELD_TRANS_ID = "transId";


     @Id
     @GeneratedValue
     private Long transFullId; //表ID，主键，供其他表做外键

     private Long invoiceId; //出库单全量头表HCUX_CS_OUTBOUND_ORDER_ALL.INVOICE_ID

     @Length(max = 200)
     private String invoiceNo; //出库编号

     @Length(max = 100)
     private String driverName; //司机姓名

     @Length(max = 50)
     private String idNumber; //身份证号

     @Length(max = 100)
     private String transportNum; //运输单号

     @Length(max = 100)
     private String plateNumber; //车船号

     @Length(max = 200)
     private String contactInfo; //联系方式

     @Length(max = 200)
     private String itemCode; //物料编码

     @Length(max = 2000)
     private String itemDesc; //品名

     private Float quantity; //数量

     private Float packetCnt; //包数

     @Length(max = 100)
     private String tankNum; //仓库识别号/罐号

     @Length(max = 2000)
     private String remark; //备注

     private Long transId; //ERP视图ID,确定唯一

     @Transient
     private Float actInvQty; //仓库实际出库数量

     @Transient
     private String link; //物流信息

     @Transient
     private String zhTransportId;//中化运输单ID

     @Transient
     private String queryType; //查询类型（HAP、门户、微信）

     @Transient
     @DateTimeFormat(pattern = BaseConstants.DATE_FORMAT)
     @JsonFormat(pattern = BaseConstants.DATE_FORMAT, timezone = "GMT+8")
     private Date expectedDepotDate; //预计出库日期

     @Transient
     @DateTimeFormat(pattern = BaseConstants.DATE_FORMAT)
     @JsonFormat(pattern = BaseConstants.DATE_FORMAT, timezone = "GMT+8")
     private Date expectedDepotDateFrom; //预计出库日期从

     @Transient
     @DateTimeFormat(pattern = BaseConstants.DATE_FORMAT)
     @JsonFormat(pattern = BaseConstants.DATE_FORMAT, timezone = "GMT+8")
     private Date expectedDepotDateTo; //预计出库日期至

     @Transient
     private List<String> sectionList;//部门list

     public Long getTransFullId() {
        return transFullId;
    }

     public void setTransFullId(Long transFullId) {
        this.transFullId = transFullId;
    }

     public void setInvoiceId(Long invoiceId){
         this.invoiceId = invoiceId;
     }

     public Long getInvoiceId(){
         return invoiceId;
     }

     public void setInvoiceNo(String invoiceNo){
         this.invoiceNo = invoiceNo;
     }

     public String getInvoiceNo(){
         return invoiceNo;
     }

     public void setDriverName(String driverName){
         this.driverName = driverName;
     }

     public String getDriverName(){
         return driverName;
     }

     public void setIdNumber(String idNumber){
         this.idNumber = idNumber;
     }

     public String getIdNumber(){
         return idNumber;
     }

     public void setTransportNum(String transportNum){
         this.transportNum = transportNum;
     }

     public String getTransportNum(){
         return transportNum;
     }

     public void setPlateNumber(String plateNumber){
         this.plateNumber = plateNumber;
     }

     public String getPlateNumber(){
         return plateNumber;
     }

     public void setContactInfo(String contactInfo){
         this.contactInfo = contactInfo;
     }

     public String getContactInfo(){
         return contactInfo;
     }

     public void setItemCode(String itemCode){
         this.itemCode = itemCode;
     }

     public String getItemCode(){
         return itemCode;
     }

     public void setItemDesc(String itemDesc){
         this.itemDesc = itemDesc;
     }

     public String getItemDesc(){
         return itemDesc;
     }

     public void setQuantity(Float quantity){
         this.quantity = quantity;
     }

     public Float getQuantity(){
         return quantity;
     }

     public void setPacketCnt(Float packetCnt){
         this.packetCnt = packetCnt;
     }

     public Float getPacketCnt(){
         return packetCnt;
     }

     public void setTankNum(String tankNum){
         this.tankNum = tankNum;
     }

     public String getTankNum(){
         return tankNum;
     }

     public void setRemark(String remark){
         this.remark = remark;
     }

     public String getRemark(){
         return remark;
     }

     public Float getActInvQty() { return actInvQty; }

     public void setActInvQty(Float actInvQty) { this.actInvQty = actInvQty; }

    public String getLink() { return link; }

    public void setLink(String link) { this.link = link; }

    public String getZhTransportId() { return zhTransportId; }

    public void setZhTransportId(String zhTransportId) { this.zhTransportId = zhTransportId; }

    public String getQueryType() { return queryType; }

    public void setQueryType(String queryType) { this.queryType = queryType; }

    public Date getExpectedDepotDate() { return expectedDepotDate; }

    public void setExpectedDepotDate(Date expectedDepotDate) { this.expectedDepotDate = expectedDepotDate; }

    public Date getExpectedDepotDateFrom() { return expectedDepotDateFrom; }

    public void setExpectedDepotDateFrom(Date expectedDepotDateFrom) { this.expectedDepotDateFrom = expectedDepotDateFrom; }

    public Date getExpectedDepotDateTo() { return expectedDepotDateTo; }

    public void setExpectedDepotDateTo(Date expectedDepotDateTo) { this.expectedDepotDateTo = expectedDepotDateTo; }

    public List<String> getSectionList() { return sectionList; }

    public void setSectionList(List<String> sectionList) { this.sectionList = sectionList; }

    public Long getTransId() { return transId; }

    public void setTransId(Long transId) { this.transId = transId; }
}
