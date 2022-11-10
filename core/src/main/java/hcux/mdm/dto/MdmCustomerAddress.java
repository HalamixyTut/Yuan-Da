package hcux.mdm.dto;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.system.dto.BaseDTO;
import hcux.core.util.HcuxConstant;
import org.hibernate.validator.constraints.Length;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author feng.liu01@hand-china.com
 * @description 客户收件人地址
 */
@ExtensionAttribute(disable = true)
@Table(name = "HCUX_MDM_CUSTOMER_ADDRESS")
public class MdmCustomerAddress extends BaseDTO {

    private static final long serialVersionUID = 2839165862901494965L;

    public static final String FIELD_ADDRESS_ID = "addressId";
    public static final String FIELD_COMPANY_ID = "companyId";
    public static final String FIELD_PLATE = "plate";
    public static final String FIELD_CUSTOMER_CODE = "customerCode";
    public static final String FIELD_CUSTOMER_NAME = "customerName";
    public static final String FIELD_RECEIVER = "receiver";
    public static final String FIELD_MOBILE = "mobile";
    public static final String FIELD_ADDRESS = "address";
    public static final String FIELD_DELETE_FLAG = "deleteFlag";
    public static final String FIELD_TELEPHONE = "telephone";

    @Id
    @GeneratedValue
    private Long addressId;

    //    @JoinTable(name = "companyJoin", joinMultiLanguageTable = true, target = Company.class, type = JoinType.LEFT, on = {@JoinOn(joinField = Company.FIELD_COMPANY_ID), @JoinOn(joinField = BaseDTO.FIELD_LANG, joinExpression = BaseConstants.PLACEHOLDER_LOCALE)})
//    @Where
//    private Long companyId;

    @Length(max = 300)
    @Where
    private String plate; // 板块

    @Length(max = 30)
    private String customerCode;

    @Length(max = 100)
    @Where(comparison = Comparison.LIKE)
    private String customerName;

    @Length(max = 30)
    private String receiver;

    @Length(max = 20)
    private String mobile; //手机号

    @Length(max = 500)
    private String address;

    @Length(max = 1)
    private String deleteFlag = HcuxConstant.YES_NO.N;

    //    @Transient
//    @JoinColumn(joinName = "companyJoin", field = Company.FIELD_COMPANY_FULL_NAME)
//    private String companyName;
    @Transient
    private String queryType; //查询类型（HAP、门户、微信）

    @Length(max = 20)
    private String telephone;//固定电话

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
