package hcux.sys.dto;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.fnd.dto.Company;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.*;
import com.hand.hap.system.dto.BaseDTO;
import hcux.core.util.HcuxConstant;
import org.hibernate.validator.constraints.Length;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.criteria.JoinType;

/**
 * @author feng.liu01@hand-china.com
 * @description 用户部门
 */
@ExtensionAttribute(disable = true)
@Table(name = "HCUX_SYS_USER_SECTION")
public class SysUserSection extends BaseDTO {

    public static final String FIELD_USER_SECTION_ID = "userSectionId";
    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_USER_ID = "userId";
    public static final String FIELD_SECTION = "section";
    public static final String FIELD_PLATE_SECTION_ID = "plateSectionId";
    public static final String FIELD_DELETE_FLAG = "deleteFlag";
    public static final String FIELD_COMPANY_NAME = "companyName";

    @Id
    @GeneratedValue
    private Long userSectionId; //表ID，主键，供其他表做外键

    @JoinTable(name = "companyJoin", joinMultiLanguageTable = true, target = Company.class, type = JoinType.LEFT, on = {@JoinOn(joinField = Company.FIELD_COMPANY_ID), @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    @Where
    private Long companyId; //业务实体(公司FND_COMPANY_B.COMPANY_ID)

    @Where
    private Long userId; //SYS_USER.USER_ID

    @Length(max = 50)
    private String section; //部门

    private Long plateSectionId; //板块部门维护表HCUX_SYS_PLATE_SECTION.PLATE_SECTION_ID

    @Length(max = 1)
    private String deleteFlag = HcuxConstant.YES_NO.N; //删除标记

    @Transient
    @JoinColumn(joinName = "companyJoin", field = Company.FIELD_COMPANY_FULL_NAME)
    @Where(comparison = Comparison.LIKE)
    private String companyName;

    @Transient
    private String sectionMeaning; //部门

    public Long getUserSectionId() {
        return userSectionId;
    }

    public void setUserSectionId(Long userSectionId) {
        this.userSectionId = userSectionId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public Long getPlateSectionId() {
        return plateSectionId;
    }

    public void setPlateSectionId(Long plateSectionId) {
        this.plateSectionId = plateSectionId;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSectionMeaning() {
        return sectionMeaning;
    }

    public void setSectionMeaning(String sectionMeaning) {
        this.sectionMeaning = sectionMeaning;
    }
}
