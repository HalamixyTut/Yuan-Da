package hcux.mdm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hand.hap.core.BaseConstants;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import io.swagger.models.auth.In;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ExtensionAttribute(disable = true)
public class MdmCarNumber extends BaseDTO {

    private String carNumber;//车牌号

    @JsonFormat(pattern = BaseConstants.DATE_FORMAT, timezone = "GMT+8")
    @DateTimeFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date drawGoodsDate;//提货时间

    private String carrier;//运输公司

    private String goodsName;//产品名称

    private String transportStatus;//运输单状态

    private String vehicleSource;//车辆属性

    @JsonFormat(pattern = BaseConstants.DATE_FORMAT, timezone = "GMT+8")
    @DateTimeFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date drawGoodsDateFrom;//提货时间从

    @JsonFormat(pattern = BaseConstants.DATE_FORMAT, timezone = "GMT+8")
    @DateTimeFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date drawGoodsDateTo;//提货时间至

    @Transient
    private Integer totalCarNum; //车牌号统计

    @Transient
    private Double amount; //调度数量

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public Date getDrawGoodsDate() {
        return drawGoodsDate;
    }

    public void setDrawGoodsDate(Date drawGoodsDate) {
        this.drawGoodsDate = drawGoodsDate;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getTransportStatus() {
        return transportStatus;
    }

    public void setTransportStatus(String transportStatus) {
        this.transportStatus = transportStatus;
    }

    public Date getDrawGoodsDateFrom() {
        return drawGoodsDateFrom;
    }

    public void setDrawGoodsDateFrom(Date drawGoodsDateFrom) {
        this.drawGoodsDateFrom = drawGoodsDateFrom;
    }

    public Date getDrawGoodsDateTo() {
        return drawGoodsDateTo;
    }

    public void setDrawGoodsDateTo(Date drawGoodsDateTo) {
        this.drawGoodsDateTo = drawGoodsDateTo;
    }

    public String getVehicleSource() {
        return vehicleSource;
    }

    public void setVehicleSource(String vehicleSource) {
        this.vehicleSource = vehicleSource;
    }

    public Integer getTotalCarNum() {
        return totalCarNum;
    }

    public void setTotalCarNum(Integer totalCarNum) {
        this.totalCarNum = totalCarNum;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
