package hcux.cs.dto;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import hcux.core.util.HcuxConstant;
import org.hibernate.validator.constraints.Length;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@ExtensionAttribute(disable = true)
@Table(name = "HCUX_CS_TRANSPORT_INFO")
public class CsTransportInfo extends BaseDTO {

    public static final String FIELD_TRANSPORT_ID = "transportId";
    public static final String FIELD_AMOUNT = "amount";
    public static final String FIELD_DRIVER_NAME = "driverName";
    public static final String FIELD_DRIVER_ID = "driverId";
    public static final String FIELD_CAR_NUMBER = "carNumber";
    public static final String FIELD_SEND_DATE = "sendDate";
    public static final String FIELD_DELETE_FLAG = "deleteFlag";
    public static final String FIELD_CUST_PO_NUMBER = "custPoNumber";
    public static final String FIELD_ITEM_CODE = "itemCode";
    public static final String FIELD_ITEM_NAME = "itemName";
    public static final String FIELD_TRANSPORT_CODE = "transportCode";
    public static final String FIELD_REMARK = "remark";


    @Id
    @GeneratedValue
    private Long transportId;//表ID，主键，供其他表做外键

    private Float amount;//数量

    @Length(max = 50)
    private String driverName;//司机姓名

    @Length(max = 20)
    private String driverId;//司机身份证号

    @Length(max = 50)
    private String carNumber;//车船号

    private Date sendDate;//送到日期

    @Length(max = 1)
    private String deleteFlag = HcuxConstant.YES_NO.N;

    @Length(max = 100)
    private String custPoNumber;//销售合同编号，用于查询时条件

    @Length(max = 100)
    private String itemCode;//物料编码

    @Length(max = 200)
    private String itemName;//品名规格

    @Length(max = 100)
    private String transportCode;//运输单号

    @Length(max = 2000)
    private String remark;//备注

    public Long getTransportId() {
        return transportId;
    }

    public void setTransportId(Long transportId) {
        this.transportId = transportId;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Float getAmount() {
        return amount;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public String getCustPoNumber() {
        return custPoNumber;
    }

    public void setCustPoNumber(String custPoNumber) {
        this.custPoNumber = custPoNumber;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getTransportCode() { return transportCode; }

    public void setTransportCode(String transportCode) { this.transportCode = transportCode; }

    public String getRemark() { return remark; }

    public void setRemark(String remark) { this.remark = remark; }
}
