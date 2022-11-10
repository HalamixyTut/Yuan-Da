package hcux.pm.dto;

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

@ExtensionAttribute(disable = true)
@Table(name = "HCUX_PM_NEWS")
public class PmNews extends BaseDTO {

    public static final String FIELD_NEWS_ID = "newsId";
    public static final String FIELD_TITLE = "title";
    public static final String FIELD_START_DATE = "startDate";
    public static final String FIELD_END_DATE = "endDate";
    public static final String FIELD_DISPLAY_RANGE = "displayRange";
    public static final String FIELD_CONTENT = "content";
    public static final String FIELD_DELETE_FLAG = "deleteFlag";
    public static final String FIELD_DOWNLOAD_FLAG = "downloadFlag";


    @Id
    @GeneratedValue
    private Long newsId;//表ID，主键，供其他表做外键

    @Length(max = 200)
    private String title;//新闻标题
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT, timezone = "GMT+8")
    @DateTimeFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date startDate;//开始时间
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT, timezone = "GMT+8")
    @DateTimeFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date endDate;//结束时间

    @Length(max = 200)
    private String displayRange;//显示范围

    @Length(max = 4000)
    private String content;//新闻内容

    @Length(max = 1)
    private String deleteFlag = HcuxConstant.YES_NO.N;

    @Length(max = 1)
    private String downloadFlag = HcuxConstant.YES_NO.Y;

    @Transient
    private String userName;//创建人
    @Transient
    private String isEffective;//是否有效
    @Column(updatable = false)
    @Condition(exclude = true)
    private Date creationDate;//创建日期

    public Long getNewsId() {
        return newsId;
    }

    public void setNewsId(Long newsId) {
        this.newsId = newsId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setDisplayRange(String displayRange) {
        this.displayRange = displayRange;
    }

    public String getDisplayRange() {
        return displayRange;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIsEffective() {
        return isEffective;
    }

    public void setIsEffective(String isEffective) {
        this.isEffective = isEffective;
    }

    @Override
    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getDownloadFlag() { return downloadFlag; }

    public void setDownloadFlag(String downloadFlag) { this.downloadFlag = downloadFlag; }
}
