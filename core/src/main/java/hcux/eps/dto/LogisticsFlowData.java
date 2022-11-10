package hcux.eps.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LogisticsFlowData {

    @JsonProperty("vesselCode")
    private String vesselCode; //船舶UN代码
    @JsonProperty("vesselName")
    private String vesselName; //英文船名
    @JsonProperty("vesselNameCn")
    private String vesselNameCn; //中文船名
    @JsonProperty("voyageNo")
    private String voyageNo; //航次
    @JsonProperty("inVoyageNo")
    private String inVoyageNo; //进口船名
    @JsonProperty("ieFlag")
    private String ieFlag; //进出口标识E=出口 I=进口
    @JsonProperty("billNo")
    private String billNo; //报关提单号
    @JsonProperty("entryId")
    private String entryId; //报关单号
    @JsonProperty("ciqNo")
    private String ciqNo; //报检单号
    @JsonProperty("sealNo")
    private String sealNo; //铅封号
    @JsonProperty("carNo")
    private String carNo; //车牌号
    @JsonProperty("ctnNo")
    private String ctnNo; //箱号
    @JsonProperty("ctnType")
    private String ctnType; //箱型
    @JsonProperty("ctnOperator")
    private String ctnOperator; //箱经营人
    @JsonProperty("ctnWeight")
    private int ctnWeight; //箱重
    @JsonProperty("grossWeight")
    private double grossWeight; //货重
    @JsonProperty("volume")
    private int volume; //体积
    @JsonProperty("qty")
    private int qty; //件数
    @JsonProperty("cargoDesc")
    private String cargoDesc; //货物品名
    @JsonProperty("stateCode")
    private String stateCode; //状态代码
    @JsonProperty("state")
    private String state; //状态描述
    @JsonProperty("placeCode")
    private String placeCode; //码头场站代码
    @JsonProperty("place")
    private String place; //码头场站名称
    @JsonProperty("businessDate")
    private String businessDate; //业务处理时间
    @JsonProperty("remark")
    private String remark; //备注
    @JsonProperty("arriveTime")
    private String arriveTime; //实际靠泊时间
    @JsonProperty("preArriveTime")
    private String preArriveTime; //预计靠泊时间
    @JsonProperty("departureTime")
    private String departureTime; //实际离泊时间
    @JsonProperty("preDepartureTime")
    private String preDepartureTime; //预计离泊时间
    @JsonProperty("docCloseTime")
    private String docCloseTime; //截单时间
    @JsonProperty("ctnStartTime")
    private String ctnStartTime; //进港时间从
    @JsonProperty("ctnEndTime")
    private String ctnEndTime; //进港时间到
    @JsonProperty("cusCloseTime")
    private String cusCloseTime; //截止报关
    @JsonProperty("is986")
    private String is986; //是否过986

    public String getVesselCode() {
        return vesselCode;
    }

    public void setVesselCode(String vesselCode) {
        this.vesselCode = vesselCode;
    }

    public String getVesselName() {
        return vesselName;
    }

    public void setVesselName(String vesselName) {
        this.vesselName = vesselName;
    }

    public String getVoyageNo() {
        return voyageNo;
    }

    public void setVoyageNo(String voyageNo) {
        this.voyageNo = voyageNo;
    }

    public String getIeFlag() {
        return ieFlag;
    }

    public void setIeFlag(String ieFlag) {
        this.ieFlag = ieFlag;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getEntryId() {
        return entryId;
    }

    public void setEntryId(String entryId) {
        this.entryId = entryId;
    }

    public String getCiqNo() {
        return ciqNo;
    }

    public void setCiqNo(String ciqNo) {
        this.ciqNo = ciqNo;
    }

    public String getSealNo() {
        return sealNo;
    }

    public void setSealNo(String sealNo) {
        this.sealNo = sealNo;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public String getCtnNo() {
        return ctnNo;
    }

    public void setCtnNo(String ctnNo) {
        this.ctnNo = ctnNo;
    }

    public String getCtnType() {
        return ctnType;
    }

    public void setCtnType(String ctnType) {
        this.ctnType = ctnType;
    }

    public String getCtnOperator() {
        return ctnOperator;
    }

    public void setCtnOperator(String ctnOperator) {
        this.ctnOperator = ctnOperator;
    }

    public int getCtnWeight() {
        return ctnWeight;
    }

    public void setCtnWeight(int ctnWeight) {
        this.ctnWeight = ctnWeight;
    }

    public double getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(double grossWeight) {
        this.grossWeight = grossWeight;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getCargoDesc() {
        return cargoDesc;
    }

    public void setCargoDesc(String cargoDesc) {
        this.cargoDesc = cargoDesc;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPlaceCode() {
        return placeCode;
    }

    public void setPlaceCode(String placeCode) {
        this.placeCode = placeCode;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getBusinessDate() {
        return businessDate;
    }

    public void setBusinessDate(String businessDate) {
        this.businessDate = businessDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }

    public String getPreArriveTime() {
        return preArriveTime;
    }

    public void setPreArriveTime(String preArriveTime) {
        this.preArriveTime = preArriveTime;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getPreDepartureTime() {
        return preDepartureTime;
    }

    public void setPreDepartureTime(String preDepartureTime) {
        this.preDepartureTime = preDepartureTime;
    }

    public String getDocCloseTime() {
        return docCloseTime;
    }

    public void setDocCloseTime(String docCloseTime) {
        this.docCloseTime = docCloseTime;
    }

    public String getIs986() {
        return is986;
    }

    public void setIs986(String is986) {
        this.is986 = is986;
    }

    public String getVesselNameCn() { return vesselNameCn; }

    public void setVesselNameCn(String vesselNameCn) { this.vesselNameCn = vesselNameCn; }

    public String getInVoyageNo() { return inVoyageNo; }

    public void setInVoyageNo(String inVoyageNo) { this.inVoyageNo = inVoyageNo; }

    public String getCtnStartTime() { return ctnStartTime; }

    public void setCtnStartTime(String ctnStartTime) { this.ctnStartTime = ctnStartTime; }

    public String getCtnEndTime() { return ctnEndTime; }

    public void setCtnEndTime(String ctnEndTime) { this.ctnEndTime = ctnEndTime; }

    public String getCusCloseTime() { return cusCloseTime; }

    public void setCusCloseTime(String cusCloseTime) { this.cusCloseTime = cusCloseTime; }
}
