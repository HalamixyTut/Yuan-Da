package hcux.mdm.dto;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import org.hibernate.validator.constraints.Length;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

@ExtensionAttribute(disable = true)
@Table(name = "HCUX_MDM_DANGEROUS_GOODS")
public class MdmDangerousGoods extends BaseDTO {

    @Id
    @GeneratedValue
    private Long goodsId;

    @Length(max = 200)
    private String itemNum;

    @Length(max = 200)
    private String itemDesc;

    @Length(max = 200)
    private String dangerGoodsUn;

    @Length(max = 200)
    private String dangerGoodsCat;

    @Length(max = 1)
    private String enabledFlag;

    @Length(max = 2000)
    private String remark;

    @Transient
    private List<String> itemNums;

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getItemNum() {
        return itemNum;
    }

    public void setItemNum(String itemNum) {
        this.itemNum = itemNum;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
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

    public String getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(String enabledFlag) {
        this.enabledFlag = enabledFlag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<String> getItemNums() {
        return itemNums;
    }

    public void setItemNums(List<String> itemNums) {
        this.itemNums = itemNums;
    }
}
