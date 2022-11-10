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
 * @description 板块部门维护
 */
@ExtensionAttribute(disable = true)
@Table(name = "HCUX_SYS_PLATE_SECTION")
public class SysPlateSection extends BaseDTO {

    public static final String FIELD_PLATE_SECTION_ID = "plateSectionId";
    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_PLATE = "plate";
    public static final String FIELD_SECTION = "section";
    public static final String FIELD_REMARK = "remark";
    public static final String FIELD_DELETE_FLAG = "deleteFlag";
    public static final String FIELD_COMPANY_NAME = "companyName";

    @Id
    @GeneratedValue
    @Where
    private Long plateSectionId; //表ID，主键，供其他表做外键

    @JoinTable(name = "companyJoin", joinMultiLanguageTable = true, target = Company.class, type = JoinType.LEFT, on = {@JoinOn(joinField = Company.FIELD_COMPANY_ID), @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
    @Where
    private Long companyId; //OU(公司FND_COMPANY_B.COMPANY_ID)

    @Length(max = 300)
    @Where
    private String plate; //板块

    @Length(max = 50)
    @Where
    private String section; //部门

    @Transient
    private String sectionMeaning; //部门

    @Length(max = 2000)
    private String remark; //备注说明

    @Length(max = 1)
    private String deleteFlag = HcuxConstant.YES_NO.N; //删除标记

    @Transient
    @JoinColumn(joinName = "companyJoin", field = Company.FIELD_COMPANY_FULL_NAME)
    @Where(comparison = Comparison.LIKE)
    private String companyName;

    public Long getPlateSectionId() {
        return plateSectionId;
    }

    public void setPlateSectionId(Long plateSectionId) {
        this.plateSectionId = plateSectionId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getPlate() {
        return plate;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getSection() {
        return section;
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
