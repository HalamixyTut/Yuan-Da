package hcux.eps.dto;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import org.hibernate.validator.constraints.Length;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * @author yexiang.ren@hand-china.com
 * @description 码表
 */
@ExtensionAttribute(disable = true)
@Table(name = "HCUX_EPS_CODE")
public class EpsCode extends BaseDTO {
    public static final String FIELD_CODE_ID = "codeId";
    public static final String FIELD_PROJECT_NUM = "projectNum";
    @Id
    @GeneratedValue
    private Long codeId;//表ID，主键，供其他表做外键

    @Length(max = 200)
    private String projectNum;//项目号

    public Long getCodeId() {
        return codeId;
    }

    public void setCodeId(Long codeId) {
        this.codeId = codeId;
    }

    public String getProjectNum() {
        return projectNum;
    }

    public void setProjectNum(String projectNum) {
        this.projectNum = projectNum;
    }
}
