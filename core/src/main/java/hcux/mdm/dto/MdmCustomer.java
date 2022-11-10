package hcux.mdm.dto;


import com.hand.hap.mybatis.annotation.Condition;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import hcux.core.annotation.AutoUpper;
import org.hibernate.validator.constraints.Length;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author feng.liu01@hand-china.com
 * @description 客户数据
 */
@ExtensionAttribute(disable = true)
@Table(name = "HCUX_MDM_CUSTOMER")
public class MdmCustomer extends BaseDTO {

    private static final long serialVersionUID = -428738165291097320L;

    public static final String FIELD_CUSTOMER_ID = "customerId";
    public static final String FIELD_CUSTOMER_CODE = "customerCode";
    public static final String FIELD_CUSTOMER_NAME = "customerName";
    public static final String FIELD_OUTSIDE_CONSIGNEE = "outsideConsignee";
    public static final String FIELD_ADDRESS = "address";
    public static final String FIELD_COUNTRY = "country";
    public static final String FIELD_CONSIGNEE_CODE = "consigneeCode";
    public static final String FIELD_CITIC_AMOUNT = "citicAmount";
    public static final String FIELD_MOBILE = "mobile";
    public static final String FIELD_REMARK = "remark";
    public static final String DELETE_FLAG = "deleteFlag";

    @Id
    @GeneratedValue
    private Long customerId; //表ID，主键，供其他表做外键

    @Length(max = 10)
    @Condition(operator = LIKE)
    @AutoUpper
    private String customerCode;  //收货人编码

    @Length(max = 100)
    @Condition(operator = LIKE)
    @AutoUpper
    private String customerName; //收货人简称

    @Length(max = 100)
    @Condition(operator = LIKE)
    @AutoUpper
    private String outsideConsignee; //境外收货人

    @Length(max = 500)
    @AutoUpper
    private String address; //地址

    @Length(max = 50)
    @AutoUpper
    private String country; //国别

    @Length(max = 30)
    @AutoUpper
    private String consigneeCode; //收货人代码

    private Double citicAmount; //中信保额度

    @Length(max = 20)
    @AutoUpper
    private String mobile; //联系电话

    @Length(max = 4000)
    @AutoUpper
    private String remark; //备注说明

    private String deleteFlag; //删除标记：默认为N，只查询未删除的数据

    public MdmCustomer() {
        this.deleteFlag = "N";
    }

    public MdmCustomer(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setOutsideConsignee(String outsideConsignee) {
        this.outsideConsignee = outsideConsignee;
    }

    public String getOutsideConsignee() {
        return outsideConsignee;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public void setConsigneeCode(String consigneeCode) {
        this.consigneeCode = consigneeCode;
    }

    public String getConsigneeCode() {
        return consigneeCode;
    }

    public Double getCiticAmount() {
        return citicAmount;
    }

    public void setCiticAmount(Double citicAmount) {
        this.citicAmount = citicAmount;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}
