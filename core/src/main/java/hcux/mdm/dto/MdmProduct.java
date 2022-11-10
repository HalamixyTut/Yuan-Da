package hcux.mdm.dto;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import hcux.core.util.HcuxConstant;
import org.hibernate.validator.constraints.Length;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 产品信息表
 */
@ExtensionAttribute(disable = true)
@Table(name = "HCUX_MDM_PRODUCT")
public class MdmProduct extends BaseDTO {

    public static final String FIELD_PRODUCT_ID = "productId";
    public static final String FIELD_PRODUCT = "product";
    public static final String FIELD_SALESPERSON = "salesperson";
    public static final String FIELD_MOBILE = "mobile";
    public static final String FIELD_DELETE_FLAG = "deleteFlag";


    @Id
    @GeneratedValue
    private Long productId;//产品Id

    @Length(max = 240)
    private String product;//产品

    @Length(max = 50)
    private String salesperson;//销售人员

    @Length(max = 30)
    private String mobile;//电话

    @Length(max = 1)
    private String deleteFlag = HcuxConstant.YES_NO.N;


    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getProduct() {
        return product;
    }

    public void setSalesperson(String salesperson) {
        this.salesperson = salesperson;
    }

    public String getSalesperson() {
        return salesperson;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

}
