package hcux.sys.dto;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author feng.liu01@hand-china.com
 * @description 增量JOB运行记录表
 */
@ExtensionAttribute(disable = true)
@Table(name = "HCUX_SYS_JOB_RECORD")
public class SysJobRecord extends BaseDTO {

    public static final String FIELD_JOB_RECORD_ID = "jobRecordId";
    public static final String FIELD_JOB_CODE = "jobCode";
    public static final String FIELD_PREVIOUS_FIRE_DATE = "previousFireDate";


    @Id
    @GeneratedValue
    private Long jobRecordId;

    private String jobCode;

    private Date previousFireDate;

    public SysJobRecord() {
    }

    public SysJobRecord(String jobCode) {
        this.jobCode = jobCode;
    }

    public Long getJobRecordId() {
        return jobRecordId;
    }

    public void setJobRecordId(Long jobRecordId) {
        this.jobRecordId = jobRecordId;
    }

    public String getJobCode() {
        return jobCode;
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    public Date getPreviousFireDate() {
        return previousFireDate;
    }

    public void setPreviousFireDate(Date previousFireDate) {
        this.previousFireDate = previousFireDate;
    }
}
