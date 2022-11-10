package hcux.doc.dto;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import org.hibernate.validator.constraints.Length;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * @author feng.liu01@hand-china.com
 * @description 报关单操作记录
 */
@ExtensionAttribute(disable = true)
@Table(name = "HCUX_DOC_CUSTOMS_RECORD")
public class DocCustomsRecord extends BaseDTO {

    public static final String FIELD_CUSTOMS_RECORD_ID = "customsRecordId";
    public static final String FIELD_CUSTOMS_ID = "customsId";
    public static final String FIELD_OPERATION_TYPE = "operationType";
    public static final String FIELD_OPERATION_DATE = "operationDate";
    public static final String FIELD_REMARK = "remark";
    public static final String FIELD_OPERATOR_ID = "operatorId";


    @Id
    @GeneratedValue
    private Long customsRecordId;//表ID，主键，供其他表做外键

    private Long customsId;//报关单头表HCUX_DOC_CUSTOMS_HEADER.CUSTOMS_ID

    @Length(max = 50)
    private String operationType;//操作类型

    private Date operationDate;//操作时间

    @Length(max = 4000)
    private String remark;//备注说明/审批意见

    private Long operatorId;//操作人HR_EMPLOYEE.EMPLOYEE_ID

    @Transient
    private String invoiceNumber;
    @Transient
    private String createdByName;//显示操作人名称

    public DocCustomsRecord() {
    }

    public DocCustomsRecord(Long customsId, String operationType) {
        this.customsId = customsId;
        this.operationType = operationType;
        this.operationDate = new Date();

    }

    public DocCustomsRecord(Long customsId, String operationType, String remark) {
        this.customsId = customsId;
        this.operationType = operationType;
        this.operationDate = new Date();
        this.remark = remark;
    }

    public Long getCustomsRecordId() {
        return customsRecordId;
    }

    public void setCustomsRecordId(Long customsRecordId) {
        this.customsRecordId = customsRecordId;
    }

    public Long getCustomsId() {
        return customsId;
    }

    public void setCustomsId(Long customsId) {
        this.customsId = customsId;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationDate(Date operationDate) {
        this.operationDate = operationDate;
    }

    public Date getOperationDate() {
        return operationDate;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    public Long getOperatorId() { return operatorId; }

    public void setOperatorId(Long operatorId) { this.operatorId = operatorId; }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getCreatedByName() { return createdByName; }

    public void setCreatedByName(String createdByName) { this.createdByName = createdByName; }
}
