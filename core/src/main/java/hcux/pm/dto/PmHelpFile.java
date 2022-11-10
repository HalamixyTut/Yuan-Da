package hcux.pm.dto;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import org.hibernate.validator.constraints.Length;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@ExtensionAttribute(disable = true)
@Table(name = "HCUX_PM_HELP_FILE")
public class PmHelpFile extends BaseDTO {

    public static final String FIELD_HELP_FILE_ID = "helpFileId";
    public static final String FIELD_REMARK = "remark";
    public static final String FIELD_HELP_ID = "helpId";


    @Id
    @GeneratedValue
    private Long helpFileId; //表ID，主键，供其他表做外键

    @Length(max = 2000)
    private String remark; //备注说明

    private Long helpId; //门户新闻表HCUX_PM_HELP.HELP_ID

    @Transient
    private String fileName; //文件名称

    @Transient
    private Long fileId; //文件ID

    public Long getHelpFileId() {
        return helpFileId;
    }

    public void setHelpFileId(Long helpFileId) {
        this.helpFileId = helpFileId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getHelpId() {
        return helpId;
    }

    public void setHelpId(Long helpId) {
        this.helpId = helpId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }
}
