package hcux.lm.dto;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import hcux.core.util.HcuxConstant;
import org.hibernate.validator.constraints.Length;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@ExtensionAttribute(disable = true)
@Table(name = "HCUX_LM_TRANSPORT_IMG")
public class LmTransportImg extends BaseDTO {

    public static final String FIELD_TRANSPORT_IMG_ID = "transportImgId";
    public static final String FIELD_TRANSPORT_ID = "transportId";
    public static final String FIELD_IMG_TYPE = "imgType";
    public static final String FIELD_IMG_URL = "imgUrl";
    public static final String FIELD_ATTACHMENT_ID = "attachmentId";
    public static final String FIELD_DELETE_FLAG = "deleteFlag";


    @Id
    @GeneratedValue
    private Long transportImgId; //表ID，主键，供其他表做外键

    private Long transportId; //运输单表HCUX_LM_TRANSPORT.TRANSPORT_ID

    @Length(max = 200)
    private String imgType; //图片类型

    @Length(max = 200)
    private String imgUrl; //图片地址

    private Long attachmentId; //运输回执单SYS_ATTACHMENT.ATTACHMENT_ID

    @Length(max = 1)
    private String deleteFlag = HcuxConstant.YES_NO.N; //删除标记

    @Transient
    private String fileName; //图片名称

    @Transient
    private Long fileId; //文件ID

    public Long getTransportImgId() { return transportImgId; }

    public void setTransportImgId(Long transportImgId) { this.transportImgId = transportImgId; }

    public Long getTransportId() { return transportId; }

    public void setTransportId(Long transportId) { this.transportId = transportId; }

    public String getImgType() { return imgType; }

    public void setImgType(String imgType) { this.imgType = imgType; }

    public String getImgUrl() { return imgUrl; }

    public void setImgUrl(String imgUrl) { this.imgUrl = imgUrl; }

    public Long getAttachmentId() { return attachmentId; }

    public void setAttachmentId(Long attachmentId) { this.attachmentId = attachmentId; }

    public String getDeleteFlag() { return deleteFlag; }

    public void setDeleteFlag(String deleteFlag) { this.deleteFlag = deleteFlag; }

    public String getFileName() { return fileName; }

    public void setFileName(String fileName) { this.fileName = fileName; }

    public Long getFileId() { return fileId; }

    public void setFileId(Long fileId) { this.fileId = fileId; }
}
