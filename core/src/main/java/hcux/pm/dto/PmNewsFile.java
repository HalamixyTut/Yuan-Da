package hcux.pm.dto;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import org.hibernate.validator.constraints.Length;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author haojie.zhang@hand-china.com
 * @description 门户新闻附件
 */
@ExtensionAttribute(disable = true)
@Table(name = "HCUX_PM_NEWS_FILE")
public class PmNewsFile extends BaseDTO {

    public static final String FIELD_NEWS_FILE_ID = "newsFileId";
    public static final String FIELD_REMARK = "remark";
    public static final String FIELD_NEWS_ID = "newsId";


    @Id
    @GeneratedValue
    private Long newsFileId; //表ID，主键，供其他表做外键

    @Length(max = 2000)
    private String remark; //备注说明

    private Long newsId; //门户新闻表HCUX_PM_NEWS.NEWS_ID

    @Transient
    private String fileName; //文件名称

    @Transient
    private Long fileId; //文件ID

    public Long getNewsFileId() { return newsFileId; }

    public void setNewsFileId(Long newsFileId) { this.newsFileId = newsFileId; }

    public String getRemark() { return remark; }

    public void setRemark(String remark) { this.remark = remark; }

    public Long getNewsId() { return newsId; }

    public void setNewsId(Long newsId) { this.newsId = newsId; }

    public String getFileName() { return fileName; }

    public void setFileName(String fileName) { this.fileName = fileName; }

    public Long getFileId() { return fileId; }

    public void setFileId(Long fileId) { this.fileId = fileId; }
}
