package hcux.eps.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author yexiang.ren@hand-china.com
 * @description 对账单的代理费视图(cux_0_agency_fee_v)
 */
public class EpsAgencyFee implements Serializable {
    private String projectNumber;//项目编号
    private BigDecimal backTax;//退税率
    private BigDecimal extendedPrice;//行总金额
    private BigDecimal invoiceAmount;//各档金额
    private BigDecimal inTaxPercent;//增值税率
    public String getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(String projectNumber) {
        this.projectNumber = projectNumber;
    }

    public BigDecimal getBackTax() {
        return backTax;
    }

    public void setBackTax(BigDecimal backTax) {
        this.backTax = backTax;
    }

    public BigDecimal getExtendedPrice() {
        return extendedPrice;
    }

    public void setExtendedPrice(BigDecimal extendedPrice) {
        this.extendedPrice = extendedPrice;
    }

    public BigDecimal getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(BigDecimal invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public BigDecimal getInTaxPercent() {
        return inTaxPercent;
    }

    public void setInTaxPercent(BigDecimal inTaxPercent) {
        this.inTaxPercent = inTaxPercent;
    }
}
