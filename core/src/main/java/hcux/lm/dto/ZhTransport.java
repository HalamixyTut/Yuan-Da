package hcux.lm.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ZhTransport {
    private String entrustId; //委托单ID
    private String consigNo; //货主自定义单号
    private String changeFlag; //是否变更车辆司机
    private String transportId; //中化运输单ID
    private String transportNo; //运输单号
    private String status; //运输单状态
    private String truckerName; //司机名称
    private String truckerPhone; //司机电话
    private String truckerIdNumber; //司机身份证号
    private String vehicleNo; //车牌号
    private String vehicleSource; //承运方车辆关系
    private String trailerVehicleNumber; //挂车车牌号
    private String remark; //运输单备注
    private String goodsName; //货品名称
    private Double weight; //调度数量
    private String unit; //单位
    private Double weightSend; //提货数量
    private String sendTime; //提货时间
    private Double weightGet; //签收数量
    private String settlementType; //结算模式
    private Double unitPrice; //运价
    private String getTime; //签收时间

    private List<ZhTransportImg> billProgressLogVoList = new ArrayList<>();//图片集合

    public String getEntrustId() {
        return entrustId;
    }

    public void setEntrustId(String entrustId) {
        this.entrustId = entrustId;
    }

    public String getConsigNo() {
        return consigNo;
    }

    public void setConsigNo(String consigNo) {
        this.consigNo = consigNo;
    }

    public String getTransportId() {
        return transportId;
    }

    public void setTransportId(String transportId) {
        this.transportId = transportId;
    }

    public String getTransportNo() {
        return transportNo;
    }

    public void setTransportNo(String transportNo) {
        this.transportNo = transportNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTruckerName() {
        return truckerName;
    }

    public void setTruckerName(String truckerName) {
        this.truckerName = truckerName;
    }

    public String getTruckerPhone() {
        return truckerPhone;
    }

    public void setTruckerPhone(String truckerPhone) {
        this.truckerPhone = truckerPhone;
    }

    public String getTruckerIdNumber() {
        return truckerIdNumber;
    }

    public void setTruckerIdNumber(String truckerIdNumber) {
        this.truckerIdNumber = truckerIdNumber;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getTrailerVehicleNumber() {
        return trailerVehicleNumber;
    }

    public void setTrailerVehicleNumber(String trailerVehicleNumber) {
        this.trailerVehicleNumber = trailerVehicleNumber;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getWeightSend() {
        return weightSend;
    }

    public void setWeightSend(Double weightSend) {
        this.weightSend = weightSend;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public Double getWeightGet() {
        return weightGet;
    }

    public void setWeightGet(Double weightGet) {
        this.weightGet = weightGet;
    }

    public String getSettlementType() {
        return settlementType;
    }

    public void setSettlementType(String settlementType) {
        this.settlementType = settlementType;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getGetTime() {
        return getTime;
    }

    public void setGetTime(String getTime) {
        this.getTime = getTime;
    }

    public List<ZhTransportImg> getBillProgressLogVoList() { return billProgressLogVoList; }

    public void setBillProgressLogVoList(List<ZhTransportImg> billProgressLogVoList) { this.billProgressLogVoList = billProgressLogVoList;}

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
