package hcux.cs.dto;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;

import java.io.Serializable;

/**
 * 信用余额报表
 */
public class CsCredit implements Serializable {
    private String tradeType;//交易类型
    private String headerName;//交易名称
    private float occupyAmount;//额度占用

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getHeaderName() {
        return headerName;
    }

    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }

    public float getOccupyAmount() {
        return occupyAmount;
    }

    public void setOccupyAmount(float occupyAmount) {
        this.occupyAmount = occupyAmount;
    }
}
