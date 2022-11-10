package hcux.pm.dto;

/**
 * @author haojie.zhang@hand-china.com
 * @description 使用帮助
 */

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

@ExtensionAttribute(disable = true)
@Table(name = "HCUX_PM_HELP")
public class PmHelp extends BaseDTO {

    public static final String FIELD_HELP_ID = "helpId";
    public static final String FIELD_TITLE = "title";
    public static final String FIELD_START_DATE = "startDate";
    public static final String FIELD_END_DATE = "endDate";
    public static final String FIELD_DISPLAY_RANGE = "displayRange";
    public static final String FIELD_CONTENT = "content";
    public static final String FIELD_DELETE_FLAG = "deleteFlag";


    @Id
    @GeneratedValue
    private Long helpId; //表ID，主键，供其他表做外键

    @Length(max = 200)
    private String title; //标题

    @JsonFormat(pattern = BaseConstants.DATE_FORMAT, timezone = "GMT+8")
    @DateTimeFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date startDate; //开始时间

    @JsonFormat(pattern = BaseConstants.DATE_FORMAT, timezone = "GMT+8")
    @DateTimeFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date endDate; //结束时间

    @Length(max = 200)
    private String displayRange; //显示范围

    @Length(max = 4000)
    private String content; //内容

    @Length(max = 1)
    private String deleteFlag; //删除标记

    @Transient
    private String userName;//创建人

    @Transient
    private Date createDate;//创建日期

    public Long getHelpId() { return helpId; }

    public void setHelpId(Long helpId) { this.helpId = helpId; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public Date getStartDate() { return startDate; }

    public void setStartDate(Date startDate) { this.startDate = startDate; }

    public Date getEndDate() { return endDate; }

    public void setEndDate(Date endDate) { this.endDate = endDate; }

    public String getDisplayRange() { return displayRange; }

    public void setDisplayRange(String displayRange) { this.displayRange = displayRange; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

    public String getDeleteFlag() { return deleteFlag; }

    public void setDeleteFlag(String deleteFlag) { this.deleteFlag = deleteFlag; }

    public String getUserName() { return userName; }

    public void setUserName(String userName) { this.userName = userName; }

    public Date getCreateDate() { return createDate; }

    public void setCreateDate(Date createDate) { this.createDate = createDate; }
}
