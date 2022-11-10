package hcux.sys.dto;

import com.hand.hap.mybatis.annotation.Condition;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import hcux.core.util.HcuxConstant;
import org.hibernate.validator.constraints.Length;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author feng.liu01@hand-china.com
 * @description 委托方用户
 */
@ExtensionAttribute(disable = true)
@Table(name = "HCUX_SYS_ENTRUST_PARTY")
public class SysEntrustParty extends BaseDTO {

    public static final String FIELD_ENTRUST_ID = "entrustId";
    public static final String FIELD_ENTRUST_CODE = "entrustCode";
    public static final String FIELD_ENTRUST_NAME = "entrustName";
    public static final String FIELD_SERIAL_NUMBER = "serialNumber";
    public static final String FIELD_PRICE_DECIMAL = "priceDecimal";
    public static final String FIELD_MIN_AGENCY_FEE = "minAgencyFee";
    public static final String FIELD_STATEMENT_FLAG = "statementFlag";
    public static final String FIELD_DELETE_FLAG = "deleteFlag";


    @Id
    @GeneratedValue
    private Long entrustId; //表ID，主键，供其他表做外键

    @Length(max = 100)
    @Condition(operator = LIKE)
    private String entrustCode; //委托方编号

    @Length(max = 200)
    @Condition(operator = LIKE)
    private String entrustName; //委托方名称

    @Length(max = 20)
    @Condition(operator = LIKE)
    private String serialNumber; //系列号

    @Length(max = 50)
    private String priceDecimal; //买断价保留小数位

    private Double minAgencyFee; //最低代理费

    private Long salesmanId; //业务员HR_EMPLOYEE.EMPLOYEE_ID

    @Transient
    private String salesmanName; //业务员

    @Length(max = 1)
    private String statementFlag; //对账单对账标记

    @Length(max = 1)
    private String deleteFlag = HcuxConstant.YES_NO.N; //删除标记

    @Transient
    private Long userId;//用户id

    @Transient
    private Double totalMinAgencyFee; //最低代理费总和

    @Transient
    private Long employeeId;//员工Id

    @Transient
    private String userName;//用户名

    public Long getEntrustId() {
        return entrustId;
    }

    public void setEntrustId(Long entrustId) {
        this.entrustId = entrustId;
    }

    public String getEntrustCode() {
        return entrustCode;
    }

    public void setEntrustCode(String entrustCode) {
        this.entrustCode = entrustCode;
    }

    public String getEntrustName() {
        return entrustName;
    }

    public void setEntrustName(String entrustName) {
        this.entrustName = entrustName;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getPriceDecimal() {
        return priceDecimal;
    }

    public void setPriceDecimal(String priceDecimal) {
        this.priceDecimal = priceDecimal;
    }

    public Double getMinAgencyFee() {
        return minAgencyFee;
    }

    public void setMinAgencyFee(Double minAgencyFee) {
        this.minAgencyFee = minAgencyFee;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Long getSalesmanId() {
        return salesmanId;
    }

    public void setSalesmanId(Long salesmanId) {
        this.salesmanId = salesmanId;
    }

    public String getSalesmanName() {
        return salesmanName;
    }

    public void setSalesmanName(String salesmanName) {
        this.salesmanName = salesmanName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getTotalMinAgencyFee() {
        return totalMinAgencyFee;
    }

    public void setTotalMinAgencyFee(Double totalMinAgencyFee) {
        this.totalMinAgencyFee = totalMinAgencyFee;
    }
    public String getStatementFlag() { return statementFlag; }

    public void setStatementFlag(String statementFlag) { this.statementFlag = statementFlag; }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
