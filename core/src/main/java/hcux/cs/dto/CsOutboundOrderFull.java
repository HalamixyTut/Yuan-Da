package hcux.cs.dto;

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
import java.util.Date;
import java.util.List;

@ExtensionAttribute(disable = true)
@Table(name = "HCUX_CS_OUTBOUND_ORDER_FULL")
public class CsOutboundOrderFull extends BaseDTO {

    public static final String FIELD_ORDER_ALL_ID = "orderAllId";
    public static final String FIELD_INVOICE_ID = "invoiceId";
    public static final String FIELD_ORDER_TYPE_DESC = "orderTypeDesc";
    public static final String FIELD_SOURCE_SUBINV = "sourceSubinv";
    public static final String FIELD_TOTAL_INV = "totalInv";
    public static final String FIELD_INV_PRINT = "invPrint";
    public static final String FIELD_PARTY_NAME = "partyName";
    public static final String FIELD_CUSTOMER_NAME = "customerName";
    public static final String FIELD_INVOICE_NO = "invoiceNo";
    public static final String FIELD_OUTBOUND_TYPE = "outboundType";
    public static final String FIELD_EXPECTED_DEPOT_DATE = "expectedDepotDate";
    public static final String FIELD_EFFECTIVE_DATE = "effectiveDate";
    public static final String FIELD_PO_PROJECT_NUMBER = "poProjectNumber";
    public static final String FIELD_RECEIPT_CODE = "receiptCode";
    public static final String FIELD_RECEIPT_NAME = "receiptName";
    public static final String FIELD_WEB_LOCATION = "webLocation";
    public static final String FIELD_COMPANY = "company";
    public static final String FIELD_GROUP_NAME = "groupName";
    public static final String FIELD_CREATE_PERSON = "createPerson";
    public static final String FIELD_CONFIRM_RECEIPT = "confirmReceipt";
    public static final String FIELD_REMARK = "remark";

    @Id
    @GeneratedValue
    private Long orderAllId; //???ID?????????????????????????????????

    private Long invoiceId; //ERP??????ID???????????????

    @Length(max = 200)
    private String orderTypeDesc; //????????????

    @Length(max = 200)
    private String sourceSubinv; //??????

    @Length(max = 200)
    private String totalInv; //?????????

    @Length(max = 200)
    private String invPrint; //??????(??????)

    @Length(max = 200)
    private String customerName; //????????????

    @Length(max = 200)
    private String partyName; //????????????

    @Length(max = 200)
    private String invoiceNo; //????????????

    @Length(max = 200)
    private String outboundType; //???????????????

    @DateTimeFormat(pattern = BaseConstants.DATE_FORMAT)
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT, timezone = "GMT+8")
    private Date expectedDepotDate; //??????????????????

    @DateTimeFormat(pattern = BaseConstants.DATE_FORMAT)
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT, timezone = "GMT+8")
    private Date effectiveDate; //????????????

    @Length(max = 500)
    private String poProjectNumber; //???????????????

    @Length(max = 200)
    private String receiptCode; //???????????????

    @Length(max = 200)
    private String receiptName; //???????????????

    @Length(max = 500)
    private String webLocation; //????????????

    @Length(max = 200)
    private String company; //????????????

    @Length(max = 200)
    private String groupName; //??????

    @Length(max = 200)
    private String orgName; //????????????
    @Length(max = 200)
    private String deptCode; //????????????
    @Length(max = 200)
    private String accountNumber; //????????????

    @Length(max = 200)
    private String createPerson;//?????????

    @Length(max = 1)
    private String confirmReceipt;//????????????

    @Length(max = 2000)
    private String remark;//??????

    private Long oeHeaderId;

    @Transient
    private String queryType; //???????????????HAP?????????????????????
    @Transient
    private List<String> sectionList;//??????list

    @Transient
    @Length(max = 200)
    private String itemNo; //????????????

    @Transient
    @Length(max = 2000)
    private String itemDesc; //??????

    @Transient
    private Float exchangeQty; //??????

    @Transient
    private Float outboundQty; //????????????

    @Transient
    private Float quantityPcs; //????????????

    @Transient
    private Float actInvQty; //????????????????????????

    @Transient
    @DateTimeFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    @JsonFormat(pattern = BaseConstants.DATE_TIME_FORMAT, timezone = "GMT+8")
    private Date expectedDepotDateFrom; //?????????????????????

    @Transient
    @DateTimeFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    @JsonFormat(pattern = BaseConstants.DATE_TIME_FORMAT, timezone = "GMT+8")
    private Date expectedDepotDateTo; //?????????????????????

    @Transient
    private String invStatus; //??????????????????

    @Transient
    private String uomCode; //??????

    @Transient
    private String unitOfMeasure; //??????????????????

    @Transient
    private String actualContractNo; //???????????????

    public void setOrderAllId(Long orderAllId) {
        this.orderAllId = orderAllId;
    }

    public Long getOrderAllId() {
        return orderAllId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setOrderTypeDesc(String orderTypeDesc) {
        this.orderTypeDesc = orderTypeDesc;
    }

    public String getOrderTypeDesc() {
        return orderTypeDesc;
    }

    public void setSourceSubinv(String sourceSubinv) {
        this.sourceSubinv = sourceSubinv;
    }

    public String getSourceSubinv() {
        return sourceSubinv;
    }

    public void setTotalInv(String totalInv) {
        this.totalInv = totalInv;
    }

    public String getTotalInv() {
        return totalInv;
    }

    public void setInvPrint(String invPrint) {
        this.invPrint = invPrint;
    }

    public String getInvPrint() {
        return invPrint;
    }

    public String getPartyName() { return partyName; }

    public void setPartyName(String partyName) { this.partyName = partyName; }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setOutboundType(String outboundType) {
        this.outboundType = outboundType;
    }

    public String getOutboundType() {
        return outboundType;
    }

    public void setExpectedDepotDate(Date expectedDepotDate) {
        this.expectedDepotDate = expectedDepotDate;
    }

    public Date getExpectedDepotDate() {
        return expectedDepotDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setPoProjectNumber(String poProjectNumber) {
        this.poProjectNumber = poProjectNumber;
    }

    public String getPoProjectNumber() {
        return poProjectNumber;
    }

    public void setReceiptCode(String receiptCode) {
        this.receiptCode = receiptCode;
    }

    public String getReceiptCode() {
        return receiptCode;
    }

    public void setReceiptName(String receiptName) {
        this.receiptName = receiptName;
    }

    public String getReceiptName() {
        return receiptName;
    }

    public void setWebLocation(String webLocation) {
        this.webLocation = webLocation;
    }

    public String getWebLocation() {
        return webLocation;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompany() {
        return company;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getItemNo() { return itemNo; }

    public void setItemNo(String itemNo) { this.itemNo = itemNo; }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public Float getExchangeQty() {
        return exchangeQty;
    }

    public void setExchangeQty(Float exchangeQty) {
        this.exchangeQty = exchangeQty;
    }

    public Date getExpectedDepotDateFrom() {
        return expectedDepotDateFrom;
    }

    public void setExpectedDepotDateFrom(Date expectedDepotDateFrom) {
        this.expectedDepotDateFrom = expectedDepotDateFrom;
    }

    public Date getExpectedDepotDateTo() {
        return expectedDepotDateTo;
    }

    public void setExpectedDepotDateTo(Date expectedDepotDateTo) {
        this.expectedDepotDateTo = expectedDepotDateTo;
    }

    public Float getActInvQty() {
        return actInvQty;
    }

    public void setActInvQty(Float actInvQty) {
        this.actInvQty = actInvQty;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getCreatePerson() { return createPerson; }

    public void setCreatePerson(String createPerson) { this.createPerson = createPerson; }

    public String getConfirmReceipt() { return confirmReceipt; }

    public void setConfirmReceipt(String confirmReceipt) { this.confirmReceipt = confirmReceipt; }

    public String getRemark() { return remark; }

    public void setRemark(String remark) { this.remark = remark; }

    public List<String> getSectionList() {
        return sectionList;
    }

    public void setSectionList(List<String> sectionList) {
        this.sectionList = sectionList;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public Float getOutboundQty() {
        return outboundQty;
    }

    public void setOutboundQty(Float outboundQty) {
        this.outboundQty = outboundQty;
    }

    public String getInvStatus() { return invStatus; }

    public void setInvStatus(String invStatus) { this.invStatus = invStatus; }

    public Float getQuantityPcs() { return quantityPcs; }

    public void setQuantityPcs(Float quantityPcs) { this.quantityPcs = quantityPcs; }

    public String getUomCode() { return uomCode; }

    public void setUomCode(String uomCode) { this.uomCode = uomCode; }

    public String getUnitOfMeasure() { return unitOfMeasure; }

    public void setUnitOfMeasure(String unitOfMeasure) { this.unitOfMeasure = unitOfMeasure; }

    public String getActualContractNo() { return actualContractNo; }

    public void setActualContractNo(String actualContractNo) { this.actualContractNo = actualContractNo; }

    public Long getOeHeaderId() {
        return oeHeaderId;
    }

    public void setOeHeaderId(Long oeHeaderId) {
        this.oeHeaderId = oeHeaderId;
    }
}
