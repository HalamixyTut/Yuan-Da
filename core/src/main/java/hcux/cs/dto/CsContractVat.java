package hcux.cs.dto;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import hcux.core.util.HcuxConstant;
import org.hibernate.validator.constraints.Length;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@ExtensionAttribute(disable = true)
@Table(name = "HCUX_CS_CONTRACT_VAT")
public class CsContractVat extends BaseDTO {

    public static final String FIELD_CONTRACT_VAT_ID = "contractVatId";
    public static final String FIELD_VAT_HEADER_ID = "vatHeaderId";
    public static final String FIELD_VAT_LINE_ID = "vatLineId";
    public static final String FIELD_ORG_NAME = "orgName";
    public static final String FIELD_SO_PJ_NUM = "soPjNum";
    public static final String FIELD_ITEM_CODE = "itemCode";
    public static final String FIELD_ITEM_NAME = "itemName";
    public static final String FIELD_VAT_QUANTITY = "vatQuantity";
    public static final String FIELD_DELETE_FLAG = "deleteFlag";
    public static final String FIELD_LINE_CATEGORY_CODE = "lineCategoryCode";
    public static final String FIELD_ORDER_QUANTITY_UOM = "orderQuantityUom";


    @Id
    @GeneratedValue
    private Long contractVatId;//表ID，主键，供其他表做外键

    private Long vatHeaderId;//ERP视图ID，多个ID组合确定唯一

    private Long vatLineId;//ERP视图ID，多个ID组合确定唯一

    @Length(max = 200)
    private String orgName;//业务实体

    @Length(max = 200)
    private String soPjNum;//销售合同编号

    @Length(max = 200)
    private String itemCode;//物料编码

    @Length(max = 200)
    private String itemName;//品名规格

    private Float vatQuantity;//开票数量

    @Length(max = 1)
    private String deleteFlag = HcuxConstant.YES_NO.N;

    @Length(max = 200)
    private String lineCategoryCode;//订单行类型

    @Length(max = 200)
    private String orderQuantityUom;//单位


    public Long getContractVatId() {
        return contractVatId;
    }

    public void setContractVatId(Long contractVatId) {
        this.contractVatId = contractVatId;
    }

    public Long getVatHeaderId() {
        return vatHeaderId;
    }

    public void setVatHeaderId(Long vatHeaderId) {
        this.vatHeaderId = vatHeaderId;
    }

    public Long getVatLineId() {
        return vatLineId;
    }

    public void setVatLineId(Long vatLineId) {
        this.vatLineId = vatLineId;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setSoPjNum(String soPjNum) {
        this.soPjNum = soPjNum;
    }

    public String getSoPjNum() {
        return soPjNum;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setVatQuantity(Float vatQuantity) {
        this.vatQuantity = vatQuantity;
    }

    public Float getVatQuantity() {
        return vatQuantity;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public String getLineCategoryCode() { return lineCategoryCode; }

    public void setLineCategoryCode(String lineCategoryCode) { this.lineCategoryCode = lineCategoryCode; }

    public String getOrderQuantityUom() { return orderQuantityUom; }

    public void setOrderQuantityUom(String orderQuantityUom) { this.orderQuantityUom = orderQuantityUom; }
}
