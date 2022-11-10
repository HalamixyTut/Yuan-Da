package hcux.lm.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hand.hap.core.IRequest;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ZhOrderLine {

    private String goodsName; //货物名称（品名）
    private String weight; //重量（数量）
    private String unit; //单位
    private String settlementType; //结算模式
    private String unitPrice; //运价
    private String remark; //货物备注
    private String dangerGoodsUN; //危险货物UN编号
    private String dangerGoodsCat; //危险货物类别及项别
    private String dangerGoodsPacageCat; //危险货物包装类别及规格
    private String dangerGoodsSecurity; //危险货物安全信息

    public ZhOrderLine() {
    }

    public ZhOrderLine(IRequest requestCtx, LmOrderLine dto) {
        this.goodsName = dto.getGoodsName();
        this.weight = dto.getGoodsNumber() + "";
        this.unit = dto.getUnit();
        this.settlementType = dto.getSettlementWay();
        this.unitPrice = dto.getFreightPrice() + "";
        this.remark = dto.getRemark();
        this.dangerGoodsUN = dto.getDangerGoodsUn();
        this.dangerGoodsCat = dto.getDangerGoodsCat();
        this.dangerGoodsPacageCat = "散装";
        this.dangerGoodsSecurity = "委托方已向承运方说明危险货物的品名、数量、危害、应急措施等情况，并提交了与托运的危险化学品完全一致的安全技术说明书和安全标签。请承运方确保其运输车辆携带和所装载货物相一致的安全技术说明书和安全标签。";
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSettlementType() {
        return settlementType;
    }

    public void setSettlementType(String settlementType) {
        this.settlementType = settlementType;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDangerGoodsUN() {
        return dangerGoodsUN;
    }

    public void setDangerGoodsUN(String dangerGoodsUN) {
        this.dangerGoodsUN = dangerGoodsUN;
    }

    public String getDangerGoodsCat() {
        return dangerGoodsCat;
    }

    public void setDangerGoodsCat(String dangerGoodsCat) {
        this.dangerGoodsCat = dangerGoodsCat;
    }

    public String getDangerGoodsPacageCat() {
        return dangerGoodsPacageCat;
    }

    public void setDangerGoodsPacageCat(String dangerGoodsPacageCat) {
        this.dangerGoodsPacageCat = dangerGoodsPacageCat;
    }

    public String getDangerGoodsSecurity() {
        return dangerGoodsSecurity;
    }

    public void setDangerGoodsSecurity(String dangerGoodsSecurity) {
        this.dangerGoodsSecurity = dangerGoodsSecurity;
    }
}
