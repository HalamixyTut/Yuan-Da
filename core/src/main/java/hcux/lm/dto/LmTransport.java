package hcux.lm.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.hand.hap.core.BaseConstants;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import hcux.core.util.HcuxConstant;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@ExtensionAttribute(disable = true)
@Table(name = "HCUX_LM_TRANSPORT")
public class LmTransport extends BaseDTO {

    public static final String FIELD_TRANSPORT_ID = "transportId";
    public static final String FIELD_ORDER_ID = "orderId";
    public static final String FIELD_TRANSPORT_CODE = "transportCode";
    public static final String FIELD_TRANSPORT_STATUS = "transportStatus";
    public static final String FIELD_TRANSPORT_PRICE = "transportPrice";
    public static final String FIELD_DRIVER_NAME = "driverName";
    public static final String FIELD_DRIVER_MOBILE = "driverMobile";
    public static final String FIELD_DRIVER_ID = "driverId";
    public static final String FIELD_CAR_NUMBER = "carNumber";
    public static final String FIELD_TRAILER_CAR_NUMBER = "trailerCarNumber";
    public static final String FIELD_REMARK = "remark";
    public static final String FIELD_GOODS_NAME = "goodsName";
    public static final String FIELD_WEIGHT = "weight";
    public static final String FIELD_VOLUME = "volume";
    public static final String FIELD_DISPATCH_AMOUNT = "dispatchAmount";
    public static final String FIELD_UNIT = "unit";
    public static final String FIELD_DRAW_GOODS_AMOUNT = "drawGoodsAmount";
    public static final String FIELD_DRAW_GOODS_DATE = "drawGoodsDate";
    public static final String FIELD_SIGN_AMOUNT = "signAmount";
    public static final String FIELD_SIGN_DATE = "signDate";
    public static final String FIELD_SETTLEMENT_WAY = "settlementWay";
    public static final String FIELD_LINK = "link";
    public static final String FIELD_ZH_TRANSPORT_ID = "zhTransportId";
    public static final String FIELD_DELETE_FLAG = "deleteFlag";


    @Id
    @GeneratedValue
    private Long transportId;//表ID，主键，供其他表做外键

    private Long orderId;//委托单头表HCUX_LM_ORDER_HEADER.ORDER_ID

    @Length(max = 100)
    private String transportCode;//运输单号

    @Length(max = 100)
    private String transportStatus;//运输单状态

    private Double transportPrice;//运价

    @Length(max = 100)
    private String driverName;//司机名称

    @Length(max = 50)
    private String driverMobile;//司机电话

    @Length(max = 50)
    private String driverId;//司机身份证号

    @Length(max = 50)
    private String carNumber;//车牌号

    @Length(max = 50)
    private String trailerCarNumber;//挂车车牌号

    @Length(max = 2000)
    private String remark;//运输单备注

    @Length(max = 1000)
    private String goodsName;//货物名称

    private Double weight;//重量

    private Double volume;//体积

    private Double dispatchAmount;//调度数量

    @Length(max = 50)
    private String unit;//单位

    private Double drawGoodsAmount;//提货数量

    @DateTimeFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    @JsonFormat(pattern = BaseConstants.DATE_TIME_FORMAT, timezone = "GMT+8")
    private Date drawGoodsDate;//提货时间

    private Double signAmount;//签收数量

    @DateTimeFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    @JsonFormat(pattern = BaseConstants.DATE_TIME_FORMAT, timezone = "GMT+8")
    private Date signDate;//签收时间

    @Length(max = 50)
    private String settlementWay;//结算方式

    @Length(max = 1)
    private String deleteFlag = HcuxConstant.YES_NO.N;//

    private String link;//链接

    private String zhTransportId;//中化运输单ID

    @Transient
    private Long attachmentId; //运输回执单SYS_ATTACHMENT.ATTACHMENT_ID

    @Transient
    private Long imgCount;//图片数量

    @Transient
    private Double dispatchAmountFrom;//调度数量从

    @Transient
    private Double dispatchAmountTo;//调度数量至

    @Transient
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT, timezone = "GMT+8")
    @DateTimeFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date arrivedDate;//送达日期

    @Transient
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT, timezone = "GMT+8")
    @DateTimeFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date arrivedDateFrom;//送达日期从

    @Transient
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT, timezone = "GMT+8")
    @DateTimeFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date arrivedDateTo;//送达日期至

    private String changeFlag; //是否变更车辆司机

    private String vehicleSource; //承运方车辆关系

    public Long getTransportId() {
        return transportId;
    }

    public void setTransportId(Long transportId) {
        this.transportId = transportId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getTransportCode() {
        return transportCode;
    }

    public void setTransportCode(String transportCode) {
        this.transportCode = transportCode;
    }

    public String getTransportStatus() {
        return transportStatus;
    }

    public void setTransportStatus(String transportStatus) {
        this.transportStatus = transportStatus;
    }

    public Double getTransportPrice() {
        return transportPrice;
    }

    public void setTransportPrice(Double transportPrice) {
        this.transportPrice = transportPrice;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverMobile() {
        return driverMobile;
    }

    public void setDriverMobile(String driverMobile) {
        this.driverMobile = driverMobile;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getTrailerCarNumber() {
        return trailerCarNumber;
    }

    public void setTrailerCarNumber(String trailerCarNumber) {
        this.trailerCarNumber = trailerCarNumber;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public Double getDispatchAmount() {
        return dispatchAmount;
    }

    public void setDispatchAmount(Double dispatchAmount) {
        this.dispatchAmount = dispatchAmount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getDrawGoodsAmount() {
        return drawGoodsAmount;
    }

    public void setDrawGoodsAmount(Double drawGoodsAmount) {
        this.drawGoodsAmount = drawGoodsAmount;
    }

    public Date getDrawGoodsDate() {
        return drawGoodsDate;
    }

    public void setDrawGoodsDate(Date drawGoodsDate) {
        this.drawGoodsDate = drawGoodsDate;
    }

    public Double getSignAmount() {
        return signAmount;
    }

    public void setSignAmount(Double signAmount) {
        this.signAmount = signAmount;
    }

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    public String getSettlementWay() {
        return settlementWay;
    }

    public void setSettlementWay(String settlementWay) {
        this.settlementWay = settlementWay;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) { this.link = link; }

    public Long getAttachmentId() { return attachmentId; }

    public void setAttachmentId(Long attachmentId) { this.attachmentId = attachmentId; }

    public String getZhTransportId() { return zhTransportId; }

    public void setZhTransportId(String zhTransportId) { this.zhTransportId = zhTransportId; }

    public Long getImgCount() {return imgCount; }

    public void setImgCount(Long imgCount) { this.imgCount = imgCount; }

    public Double getDispatchAmountFrom() { return dispatchAmountFrom; }

    public void setDispatchAmountFrom(Double dispatchAmountFrom) { this.dispatchAmountFrom = dispatchAmountFrom; }

    public Double getDispatchAmountTo() { return dispatchAmountTo; }

    public void setDispatchAmountTo(Double dispatchAmountTo) { this.dispatchAmountTo = dispatchAmountTo; }

    public Date getArrivedDateFrom() { return arrivedDateFrom; }

    public void setArrivedDateFrom(Date arrivedDateFrom) { this.arrivedDateFrom = arrivedDateFrom; }

    public Date getArrivedDateTo() { return arrivedDateTo; }

    public void setArrivedDateTo(Date arrivedDateTo) { this.arrivedDateTo = arrivedDateTo; }

    public Date getArrivedDate() { return arrivedDate; }

    public void setArrivedDate(Date arrivedDate) { this.arrivedDate = arrivedDate; }

    public String getChangeFlag() {
        return changeFlag;
    }

    public void setChangeFlag(String changeFlag) {
        this.changeFlag = changeFlag;
    }

    public String getVehicleSource() {
        return vehicleSource;
    }

    public void setVehicleSource(String vehicleSource) {
        this.vehicleSource = vehicleSource;
    }
}
