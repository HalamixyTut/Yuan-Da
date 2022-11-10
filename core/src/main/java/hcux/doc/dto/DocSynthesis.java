package hcux.doc.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hand.hap.core.BaseConstants;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import hcux.core.annotation.AutoUpper;
import hcux.core.util.HcuxConstant;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

@ExtensionAttribute(disable = true)
@Table(name = "HCUX_DOC_SYNTHESIS")
public class DocSynthesis extends BaseDTO {

    public static final String FIELD_SYNTHESIS_ID = "synthesisId";
    public static final String FIELD_CUSTOMS_ID = "customsId";
    public static final String FIELD_DECLARE_CUSTOM_LINE = "declareCustomLine";
    public static final String FIELD_CARGO_AGENT_UNIT = "cargoAgentUnit";
    public static final String FIELD_BOX_DATE = "boxDate";
    public static final String FIELD_SENDING_DATE = "sendingDate";
    public static final String FIELD_SAILING_DATE = "sailingDate";
    public static final String FIELD_BOX_TYPE = "boxType";
    public static final String FIELD_FACTORY = "factory";
    public static final String FIELD_CARGO_AGENT_CODE = "cargoAgentCode";
    public static final String FIELD_BILL_NUMBER = "billNumber";
    public static final String FIELD_VESSEL_VOYAGE = "vesselVoyage";
    public static final String FIELD_ETD = "etd";
    public static final String FIELD_ATD = "atd";
    public static final String FIELD_STATUS = "status";
    public static final String FIELD_CABIN_FLAG = "cabinFlag";
    public static final String FIELD_FREIGHT_FLAG = "freightFlag";
    public static final String FIELD_CANCEL_FLAG = "cancelFlag";
    public static final String FIELD_DELETE_FLAG = "deleteFlag";


    @Id
    @GeneratedValue
    private Long synthesisId;//表ID，主键，供其他表做外键

    private Long customsId;//报关单头表HCUX_DOC_CUSTOMS_HEADER.CUSTOMS_ID

    @Length(max = 200)
    @AutoUpper
    private String declareCustomLine;//报关行

    @Transient
    private String cargoAgentUnit;//货代简称

    @DateTimeFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    @JsonFormat(pattern = BaseConstants.DATE_TIME_FORMAT, timezone = "GMT+8")
    private Date boxDate;//做箱时间
    @DateTimeFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    @JsonFormat(pattern = BaseConstants.DATE_TIME_FORMAT, timezone = "GMT+8")
    private Date sendingDate;//寄单时间
    @DateTimeFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    @JsonFormat(pattern = BaseConstants.DATE_TIME_FORMAT, timezone = "GMT+8")
    private Date sailingDate;//船期

    @Transient
    private String boxType;//箱型
    @Transient
    private String cabinet; //进仓或装柜

    @Length(max = 100)
    @AutoUpper
    private String cargoAgentCode;//货代编码

    @Length(max = 100)
    @AutoUpper
    private String billNumber;//提单号

    @Length(max = 200)
    @AutoUpper
    private String vesselVoyage;//船名航次
    @DateTimeFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    @JsonFormat(pattern = BaseConstants.DATE_TIME_FORMAT, timezone = "GMT+8")
    private Date etd;//ETD
    @DateTimeFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    @JsonFormat(pattern = BaseConstants.DATE_TIME_FORMAT, timezone = "GMT+8")
    private Date atd;//ATD

    @Length(max = 50)
    private String status;//状态

    @Length(max = 1)
    private String cabinFlag;//已放舱

    @Length(max = 1)
    private String freightFlag;//运费已付

    @Length(max = 1)
    private String cancelFlag;//已作废

    @Length(max = 1)
    private String deleteFlag = HcuxConstant.YES_NO.N;//删除标记

    @Transient
    private String invoiceNumber;//项目号

    @Transient
    private String consignee; // 收货人

    @Transient
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT, timezone = "GMT+8")
    @DateTimeFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date sailingDateFrom;//船期从

    @Transient
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT, timezone = "GMT+8")
    @DateTimeFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date sailingDateTo;//船期至

    @Transient
    private String departurePort; //起运港

    @Transient
    private String dischargePort; // 卸货港

    @Transient
    private String headCreatedBy;//单证员

    @Transient
    private String bookCreatedBy;//储运员

    @Transient
    private Long bookingId;//托单ID

    @Transient
    private String customsStatus; //报关状态

    @Transient
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT, timezone = "GMT+8")
    @DateTimeFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date sendingDateFrom;//寄单时间从

    @Transient
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT, timezone = "GMT+8")
    @DateTimeFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date sendingDateTo;//寄单时间至

    @Transient
    private String currentUserName; //保存储运员/单证员名称
    //add yexiang.ren 19200 start
    @Transient
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT, timezone = "GMT+8")
    @DateTimeFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date boxDateFrom;//截货日期从

    @Transient
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT, timezone = "GMT+8")
    @DateTimeFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date boxDateTo;//截货日期至

    @Transient
    private List<String> statusList; // 保存前台多选的状态值
    @Transient
    private int page = 1;

    @Transient
    private int pagesize = 10;
    //add yexiang.ren 19200 end
    public Long getSynthesisId() {
        return synthesisId;
    }

    public void setSynthesisId(Long synthesisId) {
        this.synthesisId = synthesisId;
    }

    public Long getCustomsId() {
        return customsId;
    }

    public void setCustomsId(Long customsId) {
        this.customsId = customsId;
    }

    public void setDeclareCustomLine(String declareCustomLine) {
        this.declareCustomLine = declareCustomLine;
    }

    public String getDeclareCustomLine() {
        return declareCustomLine;
    }

    public void setBoxDate(Date boxDate) {
        this.boxDate = boxDate;
    }

    public Date getBoxDate() {
        return boxDate;
    }

    public void setSendingDate(Date sendingDate) {
        this.sendingDate = sendingDate;
    }

    public Date getSendingDate() {
        return sendingDate;
    }

    public void setSailingDate(Date sailingDate) {
        this.sailingDate = sailingDate;
    }

    public Date getSailingDate() {
        return sailingDate;
    }

    public void setBoxType(String boxType) {
        this.boxType = boxType;
    }

    public String getBoxType() {
        return boxType;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setVesselVoyage(String vesselVoyage) {
        this.vesselVoyage = vesselVoyage;
    }

    public String getVesselVoyage() {
        return vesselVoyage;
    }

    public void setEtd(Date etd) {
        this.etd = etd;
    }

    public Date getEtd() {
        return etd;
    }

    public void setAtd(Date atd) {
        this.atd = atd;
    }

    public Date getAtd() {
        return atd;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setCabinFlag(String cabinFlag) {
        this.cabinFlag = cabinFlag;
    }

    public String getCabinFlag() {
        return cabinFlag;
    }

    public void setFreightFlag(String freightFlag) {
        this.freightFlag = freightFlag;
    }

    public String getFreightFlag() {
        return freightFlag;
    }

    public void setCancelFlag(String cancelFlag) {
        this.cancelFlag = cancelFlag;
    }

    public String getCancelFlag() {
        return cancelFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public Date getSailingDateFrom() {
        return sailingDateFrom;
    }

    public void setSailingDateFrom(Date sailingDateFrom) {
        this.sailingDateFrom = sailingDateFrom;
    }

    public Date getSailingDateTo() {
        return sailingDateTo;
    }

    public void setSailingDateTo(Date sailingDateTo) {
        this.sailingDateTo = sailingDateTo;
    }

    public String getDeparturePort() {
        return departurePort;
    }

    public void setDeparturePort(String departurePort) {
        this.departurePort = departurePort;
    }

    public String getDischargePort() {
        return dischargePort;
    }

    public void setDischargePort(String dischargePort) {
        this.dischargePort = dischargePort;
    }

    public String getHeadCreatedBy() {
        return headCreatedBy;
    }

    public void setHeadCreatedBy(String headCreatedBy) {
        this.headCreatedBy = headCreatedBy;
    }

    public String getBookCreatedBy() {
        return bookCreatedBy;
    }

    public void setBookCreatedBy(String bookCreatedBy) {
        this.bookCreatedBy = bookCreatedBy;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public String getCargoAgentCode() {
        return cargoAgentCode;
    }

    public void setCargoAgentCode(String cargoAgentCode) {
        this.cargoAgentCode = cargoAgentCode;
    }

    public String getCargoAgentUnit() {
        return cargoAgentUnit;
    }

    public void setCargoAgentUnit(String cargoAgentUnit) {
        this.cargoAgentUnit = cargoAgentUnit;
    }

    public String getCabinet() {
        return cabinet;
    }

    public void setCabinet(String cabinet) {
        this.cabinet = cabinet;
    }

    public String getCustomsStatus() {
        return customsStatus;
    }

    public void setCustomsStatus(String customsStatus) {
        this.customsStatus = customsStatus;
    }

    public Date getSendingDateFrom() {
        return sendingDateFrom;
    }

    public void setSendingDateFrom(Date sendingDateFrom) {
        this.sendingDateFrom = sendingDateFrom;
    }

    public Date getSendingDateTo() {
        return sendingDateTo;
    }

    public void setSendingDateTo(Date sendingDateTo) {
        this.sendingDateTo = sendingDateTo;
    }

    public String getCurrentUserName() {
        return currentUserName;
    }

    public void setCurrentUserName(String currentUserName) {
        this.currentUserName = currentUserName;
    }

    public Date getBoxDateFrom() {
        return boxDateFrom;
    }

    public void setBoxDateFrom(Date boxDateFrom) {
        this.boxDateFrom = boxDateFrom;
    }

    public Date getBoxDateTo() {
        return boxDateTo;
    }

    public void setBoxDateTo(Date boxDateTo) {
        this.boxDateTo = boxDateTo;
    }

    public List<String> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<String> statusList) {
        this.statusList = statusList;
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
}
