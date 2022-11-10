package hcux.cs.dto;

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
import java.util.Date;

/**
 * @author feng.liu01@hand-china.com
 * @description 发票快递单号
 */
@ExtensionAttribute(disable = true)
@Table(name = "HCUX_CS_INVOICE_EXPRESS")
public class CsInvoiceExpress extends BaseDTO {

    private static final long serialVersionUID = -9081673928455575467L;

    public static final String FIELD_EXPRESS_ID = "expressId";
    public static final String FIELD_PLATE = "plate";
    public static final String FIELD_CUSTOMER_CODE = "customerCode";
    public static final String FIELD_CUSTOMER_NAME = "customerName";
    public static final String FIELD_INVOICE_DATE = "invoiceDate";
    public static final String FIELD_EXPRESS_NUMBER = "expressNumber";
    public static final String FIELD_EXPRESS_COMPANY = "expressCompany";
    public static final String FIELD_REMARK = "remark";
    public static final String FIELD_RECEIVER = "receiver";
    public static final String FIELD_RECEIVER_MOBILE = "receiverMobile";
    public static final String FIELD_SENDER = "sender";
    public static final String FIELD_SENDER_MOBILE = "senderMobile";
    public static final String FIELD_DELETE_FLAG = "deleteFlag";

    @Id
    @GeneratedValue
    private Long expressId; //表ID，主键，供其他表做外键

    @Length(max = 300)
    private String plate; // 板块

    @Length(max = 30)
    private String customerCode; //客户编码

    @Length(max = 100)
    private String customerName; //客户名称

    @DateTimeFormat(pattern = BaseConstants.DATE_TIME_FORMAT)//前端传给后台转为日期格式，Excel导出识别参数为yyyy/MM/dd HH:mm:ss格式
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT, timezone = "GMT+8")
    private Date invoiceDate; //发票日期

    @Length(max = 30)
    private String expressNumber; //快递单号

    @Length(max = 50)
    private String expressCompany; //快递公司

    @Length(max = 4000)
    private String remark; //备注说明

    @Length(max = 1)
    private String deleteFlag = HcuxConstant.YES_NO.N;

    @Length(max = 100)
    private String receiver; //收件人

    @Length(max = 100)
    private String receiverMobile; //收件人联系方式

    @Length(max = 100)
    private String sender; //寄件人

    @Length(max = 100)
    private String senderMobile; //寄件人联系方式

    @Transient
    @DateTimeFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    @JsonFormat(pattern = BaseConstants.DATE_TIME_FORMAT, timezone = "GMT+8")
    private Date invoiceDateFrom;

    @Transient
    @DateTimeFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    @JsonFormat(pattern = BaseConstants.DATE_TIME_FORMAT, timezone = "GMT+8")
    private Date invoiceDateTo;

    @Transient
    private String queryType; //查询类型（HAP、门户、微信）

    @Transient
    private String logisticsFlow; //门户物流连接

    public Long getExpressId() {
        return expressId;
    }

    public void setExpressId(Long expressId) {
        this.expressId = expressId;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getExpressNumber() {
        return expressNumber;
    }

    public void setExpressNumber(String expressNumber) {
        this.expressNumber = expressNumber;
    }

    public String getExpressCompany() {
        return expressCompany;
    }

    public void setExpressCompany(String expressCompany) {
        this.expressCompany = expressCompany;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
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

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public String getReceiver() { return receiver; }

    public void setReceiver(String receiver) { this.receiver = receiver; }

    public String getReceiverMobile() { return receiverMobile; }

    public void setReceiverMobile(String receiverMobile) { this.receiverMobile = receiverMobile; }

    public String getSender() { return sender; }

    public void setSender(String sender) { this.sender = sender; }

    public String getSenderMobile() { return senderMobile; }

    public void setSenderMobile(String senderMobile) { this.senderMobile = senderMobile; }

    public String getLogisticsFlow() { return logisticsFlow; }

    public void setLogisticsFlow(String logisticsFlow) { this.logisticsFlow = logisticsFlow; }
}
