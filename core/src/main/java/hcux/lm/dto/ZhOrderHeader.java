package hcux.lm.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.ICodeService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ZhOrderHeader {

    private String consigName; //货主公司名称
    private String fleetName; //物流公司全称
    private String consigNo; //货主自定义单号
    private String expectPickTime; //预计提货时间
    private String expectArriveTime; //	预计送达时间
    private String sendCompany; //发货单位名称
    private String sendProvince; //	发货单位省
    private String sendCity; //发货单位市
    private String sendDistrict; //	发货单位区/县
    private String sendAddress; //发货单位详情地址
    private String sendName; //发货人联系人
    private String sendPhone; //发货人联系方式
    private String recvCompany; //收货单位名称
    private String recvProvince; //收货单位省
    private String recvCity; //收货单位市
    private String recvDistrict; //	收货单位区/县
    private String recvAddress; //收货单位详情地址
    private String recvName; //收货人姓名
    private String recvPhone; //收货人联系方式
    private String emergencyCall; //24小时应急联系电话
    private String remark; //描述

    private String entrustId; // 委托单ID
    private String entrustNo; // 委托单号

    @Autowired
    private ICodeService codeService;

    public ZhOrderHeader() {
    }

    public ZhOrderHeader(IRequest requestCtx, LmOrderHeader dto) {
        this.consigName = dto.getConsignorName();
        this.fleetName = dto.getCarrier();
        this.consigNo = dto.getOrderNumber();
        this.expectPickTime = DateFormatUtils.format(dto.getDrawGoodsDate(), BaseConstants.DATE_FORMAT);
        this.expectArriveTime = DateFormatUtils.format(dto.getArrivedDate(), BaseConstants.DATE_FORMAT);
        this.sendCompany = dto.getDrawGoodsUnit();
        this.sendProvince = dto.getDrawGoodsProvince();
        this.sendCity = dto.getDrawGoodsCity();
        this.sendDistrict = dto.getDrawGoodsArea();
        this.sendAddress = dto.getDrawGoodsAddress();
        this.sendName = dto.getDrawGoodsPeople();
        this.sendPhone = dto.getDrawGoodsMobile();
        this.recvCompany = dto.getConsigneeUnit();
        this.recvProvince = dto.getConsigneeProvince();
        this.recvCity = dto.getConsigneeCity();
        this.recvDistrict = dto.getConsigneeArea();
        this.recvAddress = dto.getConsigneeAddress();
        this.recvName = dto.getConsignee();
        this.recvPhone = dto.getConsigneeMobile();
        this.emergencyCall = "";
        this.remark = dto.getRemark();

    }

    private List<ZhOrderLine> goodsList = new ArrayList<>(); //货物列表

    public String getConsigName() {
        return consigName;
    }

    public void setConsigName(String consigName) {
        this.consigName = consigName;
    }

    public String getFleetName() {
        return fleetName;
    }

    public void setFleetName(String fleetName) {
        this.fleetName = fleetName;
    }

    public String getConsigNo() {
        return consigNo;
    }

    public void setConsigNo(String consigNo) {
        this.consigNo = consigNo;
    }

    public String getExpectPickTime() {
        return expectPickTime;
    }

    public void setExpectPickTime(String expectPickTime) {
        this.expectPickTime = expectPickTime;
    }

    public String getExpectArriveTime() {
        return expectArriveTime;
    }

    public void setExpectArriveTime(String expectArriveTime) {
        this.expectArriveTime = expectArriveTime;
    }

    public String getSendCompany() {
        return sendCompany;
    }

    public void setSendCompany(String sendCompany) {
        this.sendCompany = sendCompany;
    }

    public String getSendProvince() {
        return sendProvince;
    }

    public void setSendProvince(String sendProvince) {
        this.sendProvince = sendProvince;
    }

    public String getSendCity() {
        return sendCity;
    }

    public void setSendCity(String sendCity) {
        this.sendCity = sendCity;
    }

    public String getSendDistrict() {
        return sendDistrict;
    }

    public void setSendDistrict(String sendDistrict) {
        this.sendDistrict = sendDistrict;
    }

    public String getSendAddress() {
        return sendAddress;
    }

    public void setSendAddress(String sendAddress) {
        this.sendAddress = sendAddress;
    }

    public String getSendName() {
        return sendName;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    public String getSendPhone() {
        return sendPhone;
    }

    public void setSendPhone(String sendPhone) {
        this.sendPhone = sendPhone;
    }

    public String getRecvCompany() {
        return recvCompany;
    }

    public void setRecvCompany(String recvCompany) {
        this.recvCompany = recvCompany;
    }

    public String getRecvProvince() {
        return recvProvince;
    }

    public void setRecvProvince(String recvProvince) {
        this.recvProvince = recvProvince;
    }

    public String getRecvCity() {
        return recvCity;
    }

    public void setRecvCity(String recvCity) {
        this.recvCity = recvCity;
    }

    public String getRecvDistrict() {
        return recvDistrict;
    }

    public void setRecvDistrict(String recvDistrict) {
        this.recvDistrict = recvDistrict;
    }

    public String getRecvAddress() {
        return recvAddress;
    }

    public void setRecvAddress(String recvAddress) {
        this.recvAddress = recvAddress;
    }

    public String getRecvName() {
        return recvName;
    }

    public void setRecvName(String recvName) {
        this.recvName = recvName;
    }

    public String getRecvPhone() {
        return recvPhone;
    }

    public void setRecvPhone(String recvPhone) {
        this.recvPhone = recvPhone;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<ZhOrderLine> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<ZhOrderLine> goodsList) {
        this.goodsList = goodsList;
    }

    public String getEntrustId() {
        return entrustId;
    }

    public void setEntrustId(String entrustId) {
        this.entrustId = entrustId;
    }

    public String getEntrustNo() {
        return entrustNo;
    }

    public void setEntrustNo(String entrustNo) {
        this.entrustNo = entrustNo;
    }

    public String getEmergencyCall() {
        return emergencyCall;
    }

    public void setEmergencyCall(String emergencyCall) {
        this.emergencyCall = emergencyCall;
    }
}
