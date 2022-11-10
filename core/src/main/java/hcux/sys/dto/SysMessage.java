package hcux.sys.dto;

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

/**
 * @author feng.liu01@hand-china.com
 * @description 系统消息表
 */
@ExtensionAttribute(disable = true)
@Table(name = "HCUX_SYS_MESSAGE")
public class SysMessage extends BaseDTO {

    public static final String FIELD_MESSAGE_ID = "messageId";
    public static final String FIELD_CONTENT = "content";
    public static final String FIELD_SOURCE_TYPE = "sourceType";
    public static final String FIELD_SOURCE_KEY = "sourceKey";
    public static final String FIELD_OWNER_ID = "ownerId";
    public static final String FIELD_OWNER_NAME = "ownerName";
    public static final String FIELD_READ_FLAG = "readFlag";


    @Id
    @GeneratedValue
    private Long messageId; //表ID，主键，供其他表做外键

    @Length(max = 2000)
    private String content; //提醒消息内容

    @Length(max = 100)
    private String sourceType; //对应业务类型

    private Long sourceKey; //业务主键

    private Long ownerId; //消息所有者ID(SYS_USER.USER_ID)

    @Transient
    private String ownerName; //消息所有者NAME(SYS_USER.USER_NAME)

    @Transient
    private Date createDate;

    @Transient
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT, timezone = "GMT+8")
    @DateTimeFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date creationDateFrom; //创建时间从

    @Transient
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT, timezone = "GMT+8")
    @DateTimeFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date creationDateTo; //创建时间至

    @Length(max = 1)
    private String readFlag; //读取标识

    private Long changeKey; //变更运输单ID

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public Long getSourceKey() {
        return sourceKey;
    }

    public void setSourceKey(Long sourceKey) {
        this.sourceKey = sourceKey;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreaeDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getReadFlag() {
        return readFlag;
    }

    public void setReadFlag(String readFlag) {
        this.readFlag = readFlag;
    }

    public Date getCreationDateFrom() {
        return creationDateFrom;
    }

    public void setCreationDateFrom(Date creationDateFrom) {
        this.creationDateFrom = creationDateFrom;
    }

    public Date getCreationDateTo() {
        return creationDateTo;
    }

    public void setCreationDateTo(Date creationDateTo) {
        this.creationDateTo = creationDateTo;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getChangeKey() {
        return changeKey;
    }

    public void setChangeKey(Long changeKey) {
        this.changeKey = changeKey;
    }
}
