package hcux.doc.dto;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import hcux.core.util.HcuxConstant;
import org.hibernate.validator.constraints.Length;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@ExtensionAttribute(disable = true)
@Table(name = "HCUX_DOC_SYNTHESIS_FILE")
public class DocSynthesisFile extends BaseDTO {

    public static final String FIELD_SYNTHESIS_FILE_ID = "synthesisFileId";
    public static final String FIELD_FILE_TYPE = "fileType";
    public static final String FIELD_REMARK = "remark";
    public static final String FIELD_DELETE_FLAG = "deleteFlag";
    public static final String FIELD_SYNTHESIS_ID = "synthesisId";


    @Id
    @GeneratedValue
    private Long synthesisFileId;

    @Length(max = 50)
    private String fileType;

    @Length(max = 4000)
    private String remark;

    @Length(max = 1)
    private String deleteFlag = HcuxConstant.YES_NO.N;
    @Transient
    private String fileName;//文件名称
    @Transient
    private Long fileId;

    private Long synthesisId;//综合查询平台表HCUX_DOC_SYNTHESIS.SYNTHESIS_ID

    public Long getSynthesisFileId() {
        return synthesisFileId;
    }

    public void setSynthesisFileId(Long synthesisFileId) {
        this.synthesisFileId = synthesisFileId;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileType() {
        return fileType;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getDeleteFlag() {
        return deleteFlag;
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

    public Long getSynthesisId() {
        return synthesisId;
    }

    public void setSynthesisId(Long synthesisId) {
        this.synthesisId = synthesisId;
    }
}
