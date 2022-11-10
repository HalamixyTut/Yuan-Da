package hcux.eps.dto;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yexiang.ren@hand-china.com
 * @description 总账表
 */
@ExtensionAttribute(disable = true)
@Table(name = "HCUX_EPS_GENERAL_LEDGER")
public class EpsGeneralLedger {
    public static final String FIELD_LEDGER_ID = "ledgerId";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_LEDGER_NAME = "ledgerName";
    public static final String FIELD_CURRENCY_CODE = "currencyCode";
    public static final String FIELD_CATEGORY_NAME = "categoryName";
    public static final String FIELD_EFFECTIVE_DATE = "effectiveDate";
    public static final String FIELD_ACCOUNT = "account";
    public static final String FIELD_PROJECT_NUMBER = "projectNumber";
    public static final String FIELD_PERIOD_NAME = "periodName";
    public static final String FIELD_ENTERED_DR = "enteredDr";

    @Id
    @GeneratedValue
    private String ledgerId;//
    private String name;//日记账
    private String ledgerName;//分类账
    private String currencyCode;//币制
    private String categoryName;//类别
    private Date effectiveDate;//有效日期
    private String account;//账户
    private String projectNumber;//项目号
    private String periodName;//期间
    private BigDecimal enteredDr;//金额

    public String getLedgerId() {
        return ledgerId;
    }

    public void setLedgerId(String ledgerId) {
        this.ledgerId = ledgerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLedgerName() {
        return ledgerName;
    }

    public void setLedgerName(String ledgerName) {
        this.ledgerName = ledgerName;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(String projectNumber) {
        this.projectNumber = projectNumber;
    }

    public String getPeriodName() {
        return periodName;
    }

    public void setPeriodName(String periodName) {
        this.periodName = periodName;
    }

    public BigDecimal getEnteredDr() {
        return enteredDr;
    }

    public void setEnteredDr(BigDecimal enteredDr) {
        this.enteredDr = enteredDr;
    }
}
