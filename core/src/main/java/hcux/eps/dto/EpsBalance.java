package hcux.eps.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author yexiang.ren@hand-china.com
 * @description 对账单的余额视图(cux_0_hap_bal_v)
 */
public class EpsBalance implements Serializable {
    private String orgId;//业务实体ID
    private String orgName;//业务实体
    private String agmNumber;//协议编号
    private String agentTypeCode;//代理费
    private BigDecimal agentAmount;//代理费值
    private BigDecimal ratioRate;//基础汇率
    private BigDecimal vat;//增值税率
    private BigDecimal rebateRate;//退税率
    private BigDecimal retio;//结汇比
    private String agentType;//代理费
    private BigDecimal minAgentAmount;//最低代理费
    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getAgmNumber() {
        return agmNumber;
    }

    public void setAgmNumber(String agmNumber) {
        this.agmNumber = agmNumber;
    }

    public String getAgentTypeCode() {
        return agentTypeCode;
    }

    public void setAgentTypeCode(String agentTypeCode) {
        this.agentTypeCode = agentTypeCode;
    }

    public BigDecimal getAgentAmount() {
        return agentAmount;
    }

    public void setAgentAmount(BigDecimal agentAmount) {
        this.agentAmount = agentAmount;
    }

    public BigDecimal getRatioRate() {
        return ratioRate;
    }

    public void setRatioRate(BigDecimal ratioRate) {
        this.ratioRate = ratioRate;
    }

    public BigDecimal getVat() {
        return vat;
    }

    public void setVat(BigDecimal vat) {
        this.vat = vat;
    }

    public BigDecimal getRebateRate() {
        return rebateRate;
    }

    public void setRebateRate(BigDecimal rebateRate) {
        this.rebateRate = rebateRate;
    }

    public BigDecimal getRetio() {
        return retio;
    }

    public void setRetio(BigDecimal retio) {
        this.retio = retio;
    }

    public String getAgentType() {
        return agentType;
    }

    public void setAgentType(String agentType) {
        this.agentType = agentType;
    }

    public BigDecimal getMinAgentAmount() {
        return minAgentAmount;
    }

    public void setMinAgentAmount(BigDecimal minAgentAmount) {
        this.minAgentAmount = minAgentAmount;
    }
}
