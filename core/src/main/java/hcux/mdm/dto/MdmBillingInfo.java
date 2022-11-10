package hcux.mdm.dto;

import com.hand.hap.mybatis.annotation.Condition;
import com.hand.hap.system.dto.BaseDTO;
import hcux.core.util.HcuxConstant;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Date;


/**
 * @author feng.liu01@hand-china.com
 * @description 客户开票资料
 */
@Table(name = "HCUX_MDM_BILLING_INFO")
public class MdmBillingInfo extends BaseDTO {

    public static final String FIELD_BILLING_ID = "billingId";
    public static final String FIELD_COMPANY_NAME = "companyName";
    public static final String FIELD_CUSTOMER_CODE = "customerCode";
    public static final String FIELD_CUSTOMER_NAME = "customerName";
    public static final String FIELD_TAXPAYER_ID = "taxpayerId";
    public static final String FIELD_BILLING_ADDRESS = "billingAddress";
    public static final String FIELD_MOBILE = "mobile";
    public static final String FIELD_BANK = "bank";
    public static final String FIELD_BANK_NUMBER = "bankNumber";
    public static final String FIELD_STATUS = "status";
    public static final String FIELD_DELETE_FLAG = "deleteFlag";
    public static final String FIELD_PARTY_ID = "partyId";
    public static final String FIELD_EXT_BANK_ACCOUNT_ID = "extBankAccountId";

    @Id
    @GeneratedValue
    private Long billingId;

    @Length(max = 100)
    private String companyName;

    @Length(max = 30)
    private String customerCode;

    @Length(max = 100)
    private String customerName;

    @Length(max = 50)
    private String taxpayerId;

    @Length(max = 150)
    private String billingAddress;

    @Length(max = 20)
    private String mobile;

    @Length(max = 100)
    private String bank;

    @Length(max = 30)
    private String bankNumber;

    @Length(max = 20)
    private String status;

    @Length(max = 1)
    private String deleteFlag = HcuxConstant.YES_NO.N;

    private Float partyId;

    private Float extBankAccountId;
    @Transient
    private String returnStatus;
    @Transient
    private Integer msgCount;
    @Transient
    private String msgData;

    @Column
    @Condition(exclude = true)
    private Date lastUpdateDate;

    @Column
    @Condition(exclude = true)
    private Long lastUpdatedBy;

    @Transient
    private String queryType; //查询类型（HAP、门户、微信）

    public Long getBillingId() {
        return billingId;
    }

    public void setBillingId(Long billingId) {
        this.billingId = billingId;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setTaxpayerId(String taxpayerId) {
        this.taxpayerId = taxpayerId;
    }

    public String getTaxpayerId() {
        return taxpayerId;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBank() {
        return bank;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public Float getPartyId() {
        return partyId;
    }

    public void setPartyId(Float partyId) {
        this.partyId = partyId;
    }

    public Float getExtBankAccountId() {
        return extBankAccountId;
    }

    public void setExtBankAccountId(Float extBankAccountId) {
        this.extBankAccountId = extBankAccountId;
    }

    public void setReturnStatus(String returnStatus) {
        this.returnStatus = returnStatus;
    }

    public Integer getMsgCount() {
        return msgCount;
    }

    public void setMsgCount(Integer msgCount) {
        this.msgCount = msgCount;
    }

    public String getMsgData() {
        return msgData;
    }

    public void setMsgData(String msgData) {
        this.msgData = msgData;
    }

    public String getReturnStatus() {
        return returnStatus;
    }

    @Override
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    @Override
    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @Override
    public Long getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    @Override
    public void setLastUpdatedBy(Long lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }
}
