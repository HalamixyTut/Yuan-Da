package hcux.cs.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hand.hap.core.BaseConstants;
import com.hand.hap.mybatis.annotation.Condition;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import hcux.core.util.HcuxConstant;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@ExtensionAttribute(disable = true)
@Table(name = "HCUX_CS_OVERDUE")
public class CsOverdue extends BaseDTO {

    public static final String FIELD_OVERDUE_ID = "overdueId";
    public static final String FIELD_INVOICE_ID = "invoiceId";
    public static final String FIELD_ACCOUNT_NUMBER = "accountNumber";
    public static final String FIELD_PARTY_NAME = "partyName";
    public static final String FIELD_SO_PA_NUM = "soPaNum";
    public static final String FIELD_DOC_AMOUNT = "docAmount";
    public static final String FIELD_RETURN_AMOUNT = "returnAmount";
    public static final String FIELD_RECEIPT_AMOUNT = "receiptAmount";
    public static final String FIELD_LOCK_AMOUNT = "lockAmount";
    public static final String FIELD_EXTENDS_DAYS = "extendsDays";
    public static final String FIELD_INVOICE_NO = "invoiceNo";
    public static final String FIELD_SUBMIT_DATE = "submitDate";
    public static final String FIELD_EXPECT_RECEIVE_DATE = "expectReceiveDate";
    public static final String FIELD_CURRENCY_CODE = "currencyCode";
    public static final String FIELD_DELETE_FLAG = "deleteFlag";
    public static final String FIELD_ORG_NAME = "orgName";
    public static final String FIELD_DEPT_CODE = "deptCode";
    public static final String FIELD_LAST_UPDATE_DATE = "lastUpdateDate";

    @Id
    @GeneratedValue
    private Long overdueId;//表ID，主键，供其他表做外键

    private Long invoiceId;//ERP视图ID，确定唯一

    @Length(max = 200)
    private String accountNumber;//客户编码

    @Length(max = 500)
    private String partyName;//客户

    @Length(max = 200)
    private String soPaNum;//销售项目号

    private Float docAmount;//出库金额

    private Float returnAmount;//退货金额

    private Float receiptAmount;//收款金额

    private Float lockAmount;//额度占用

    private Float extendsDays;//逾期天数

    @Length(max = 200)
    private String invoiceNo;//出库单

    private String submitDate;//放货日期

    private String expectReceiveDate;//预计收款日期

    @Length(max = 100)
    private String currencyCode;//币种

    @Length(max = 1)
    private String deleteFlag = HcuxConstant.YES_NO.N;//
    @Transient
    private int pageSizeFrom;//用于分页区间
    @Transient
    private int pageSizeTo;//用于分页区间
    @Transient
    private int countCsOverdue;//ebs系统数据总数
    @Transient
    private Float extendsDaysFrom;//逾期天数从
    @Transient
    private Float extendsDaysTo;//逾期天数至
    @Transient
    private String expectReceiveDateFrom;//预计收款日期从
    @Transient
    private String expectReceiveDateTo;//预计收款日期至
    @Transient
    private Float sumLockAmount;//额度占用总计
    private String orgName;//业务实体
    private String deptCode;//部门编码

    @Transient
    private String queryType; //查询类型（HAP、门户、微信）
    @Transient
    private List<String> sectionList;//部门list

    @Column(updatable = false)
    @Condition(exclude = true)
    @JsonFormat(pattern = BaseConstants.DATE_TIME_FORMAT, timezone = "GMT+8")
    @DateTimeFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date lastUpdateDate;//最后更新时间

    @Transient
    private Float maxDays;//最大逾期天数

    public Long getOverdueId() {
        return overdueId;
    }

    public void setOverdueId(Long overdueId) {
        this.overdueId = overdueId;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setSoPaNum(String soPaNum) {
        this.soPaNum = soPaNum;
    }

    public String getSoPaNum() {
        return soPaNum;
    }

    public void setDocAmount(Float docAmount) {
        this.docAmount = docAmount;
    }

    public Float getDocAmount() {
        return docAmount;
    }

    public void setReturnAmount(Float returnAmount) {
        this.returnAmount = returnAmount;
    }

    public Float getReturnAmount() {
        return returnAmount;
    }

    public void setReceiptAmount(Float receiptAmount) {
        this.receiptAmount = receiptAmount;
    }

    public Float getReceiptAmount() {
        return receiptAmount;
    }

    public void setLockAmount(Float lockAmount) {
        this.lockAmount = lockAmount;
    }

    public Float getLockAmount() {
        return lockAmount;
    }

    public void setExtendsDays(Float extendsDays) {
        this.extendsDays = extendsDays;
    }

    public Float getExtendsDays() {
        return extendsDays;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public int getPageSizeFrom() {
        return pageSizeFrom;
    }

    public void setPageSizeFrom(int pageSizeFrom) {
        this.pageSizeFrom = pageSizeFrom;
    }

    public int getPageSizeTo() {
        return pageSizeTo;
    }

    public void setPageSizeTo(int pageSizeTo) {
        this.pageSizeTo = pageSizeTo;
    }

    public int getCountCsOverdue() {
        return countCsOverdue;
    }

    public void setCountCsOverdue(int countCsOverdue) {
        this.countCsOverdue = countCsOverdue;
    }

    public Float getExtendsDaysFrom() {
        return extendsDaysFrom;
    }

    public void setExtendsDaysFrom(Float extendsDaysFrom) {
        this.extendsDaysFrom = extendsDaysFrom;
    }

    public Float getExtendsDaysTo() {
        return extendsDaysTo;
    }

    public void setExtendsDaysTo(Float extendsDaysTo) {
        this.extendsDaysTo = extendsDaysTo;
    }

    public Float getSumLockAmount() {
        return sumLockAmount;
    }

    public void setSumLockAmount(Float sumLockAmount) {
        this.sumLockAmount = sumLockAmount;
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

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public List<String> getSectionList() {
        return sectionList;
    }

    public void setSectionList(List<String> sectionList) {
        this.sectionList = sectionList;
    }

    public String getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(String submitDate) {
        this.submitDate = submitDate;
    }

    public String getExpectReceiveDate() {
        return expectReceiveDate;
    }

    public void setExpectReceiveDate(String expectReceiveDate) {
        this.expectReceiveDate = expectReceiveDate;
    }

    public String getExpectReceiveDateFrom() {
        return expectReceiveDateFrom;
    }

    public void setExpectReceiveDateFrom(String expectReceiveDateFrom) {
        this.expectReceiveDateFrom = expectReceiveDateFrom;
    }

    public String getExpectReceiveDateTo() {
        return expectReceiveDateTo;
    }

    public void setExpectReceiveDateTo(String expectReceiveDateTo) {
        this.expectReceiveDateTo = expectReceiveDateTo;
    }

    @Override
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    @Override
    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Float getMaxDays() { return maxDays; }

    public void setMaxDays(Float maxDays) { this.maxDays = maxDays; }
}
