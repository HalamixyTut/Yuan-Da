package hcux.lm.dto;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import hcux.core.util.HcuxConstant;
import org.hibernate.validator.constraints.Length;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@ExtensionAttribute(disable = true)
@Table(name = "HCUX_LM_ORDER_LINE")
public class LmOrderLine extends BaseDTO {

    public static final String FIELD_ORDER_LINE_ID = "orderLineId";
    public static final String FIELD_ORDER_ID = "orderId";
    public static final String FIELD_GOODS_CODE = "goodsCode";
    public static final String FIELD_GOODS_NAME = "goodsName";
    public static final String FIELD_GOODS_NUMBER = "goodsNumber";
    public static final String FIELD_PACKAGE_NUMBER = "packageNumber";
    public static final String FIELD_REMARK = "remark";
    public static final String FIELD_UNIT = "unit";
    public static final String FIELD_SETTLEMENT_WAY = "settlementWay";
    public static final String FIELD_FREIGHT_PRICE = "freightPrice";
    public static final String FIELD_DELETE_FLAG = "deleteFlag";
    public static final String FIELD_DANGER_GOODS_UN = "dangerGoodsUn";
    public static final String FIELD_DANGER_GOODS_CAT = "dangerGoodsCat";


    @Id
    @GeneratedValue
    private Long orderLineId;//表ID，主键，供其他表做外键

    private Long orderId;//委托单头表HCUX_LM_ORDER_HEADER.ORDER_ID

    @Length(max = 200)
    private String goodsCode;//货物编码

    @Length(max = 1000)
    private String goodsName;//货物名称

    private Double goodsNumber;//货物数量

    private String unit;//单位

    private String settlementWay;//结算方式

    private Double freightPrice;//运价

    @Length(max = 2000)
    private String remark;//备注说明

    @Length(max = 1)
    private String deleteFlag = HcuxConstant.YES_NO.N;//

    private String dangerGoodsUn; //危险货物UN号

    private String dangerGoodsCat; //危险货物类别及项别


    public Long getOrderLineId() {
        return orderLineId;
    }

    public void setOrderLineId(Long orderLineId) {
        this.orderLineId = orderLineId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Double getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(Double goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSettlementWay() {
        return settlementWay;
    }

    public void setSettlementWay(String settlementWay) {
        this.settlementWay = settlementWay;
    }

    public Double getFreightPrice() {
        return freightPrice;
    }

    public void setFreightPrice(Double freightPrice) {
        this.freightPrice = freightPrice;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getDangerGoodsUn() {
        return dangerGoodsUn;
    }

    public void setDangerGoodsUn(String dangerGoodsUn) {
        this.dangerGoodsUn = dangerGoodsUn;
    }

    public String getDangerGoodsCat() {
        return dangerGoodsCat;
    }

    public void setDangerGoodsCat(String dangerGoodsCat) {
        this.dangerGoodsCat = dangerGoodsCat;
    }
}
