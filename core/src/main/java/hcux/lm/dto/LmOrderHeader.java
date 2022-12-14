package hcux.lm.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.annotation.Children;
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
import java.util.List;

@ExtensionAttribute(disable = true)
@Table(name = "HCUX_LM_ORDER_HEADER")
public class LmOrderHeader extends BaseDTO {

    public static final String FIELD_ORDER_ID = "orderId";
    public static final String FIELD_CONTRACT_CODE_ONE = "contractCodeOne";
    public static final String FIELD_CONTRACT_CODE_TWO = "contractCodeTwo";
    public static final String FIELD_CONTRACT_CODE_THREE = "contractCodeThree";
    public static final String FIELD_ORDER_NUMBER = "orderNumber";
    public static final String FIELD_CARRIER = "carrier";
    public static final String FIELD_CONSIGNOR_NAME = "consignorName";
    public static final String FIELD_DRAW_GOODS_UNIT = "drawGoodsUnit";
    public static final String FIELD_DRAW_GOODS_ADDRESS = "drawGoodsAddress";
    public static final String FIELD_CONSIGNEE_UNIT = "consigneeUnit";
    public static final String FIELD_CONSIGNEE_ADDRESS = "consigneeAddress";
    public static final String FIELD_CONSIGNEE = "consignee";
    public static final String FIELD_CONSIGNEE_MOBILE = "consigneeMobile";
    public static final String FIELD_DRAW_GOODS_DATE = "drawGoodsDate";
    public static final String FIELD_ARRIVED_DATE = "arrivedDate";
    public static final String FIELD_REMARK = "remark";
    public static final String FIELD_DELETE_FLAG = "deleteFlag";
    public static final String FIELD_STATUS = "status";
    public static final String FIELD_LINK = "link";


    @Id
    @GeneratedValue
    private Long orderId;//???ID?????????????????????????????????

    @Length(max = 100)
    private String contractCodeOne;//?????????1

    @Length(max = 100)
    private String contractCodeTwo;//?????????2

    @Length(max = 100)
    private String contractCodeThree;//?????????3

    @Length(max = 100)
    private String orderNumber;//???????????????

    @Length(max = 200)
    private String carrier;//?????????

    @Length(max = 100)
    private String consignorName;//????????????

    @Length(max = 500)
    private String drawGoodsUnit;//????????????

    @Length(max = 50)
    private String drawGoodsProvince;//???????????????

    @Length(max = 50)
    private String drawGoodsCity;//???????????????

    @Length(max = 50)
    private String drawGoodsArea;//???????????????

    @Length(max = 500)
    private String drawGoodsAddress;//??????????????????

    @Length(max = 100)
    private String drawGoodsPeople;//?????????

    @Length(max = 50)
    private String drawGoodsMobile;//?????????????????????

    @Length(max = 500)
    private String consigneeUnit;//????????????

    @Length(max = 50)
    private String consigneeProvince;//???????????????

    @Length(max = 50)
    private String consigneeCity;//???????????????

    @Length(max = 50)
    private String consigneeArea;//???????????????

    @Length(max = 500)
    private String consigneeAddress;//??????????????????

    @Length(max = 100)
    private String consignee;//?????????

    @Length(max = 50)
    private String consigneeMobile;//????????????

    private String status;//??????

    private String link;//??????
    @DateTimeFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    @JsonFormat(pattern = BaseConstants.DATE_TIME_FORMAT, timezone = "GMT+8")
    private Date drawGoodsDate;//????????????
    @DateTimeFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    @JsonFormat(pattern = BaseConstants.DATE_TIME_FORMAT, timezone = "GMT+8")
    private Date arrivedDate;//????????????

    @Length(max = 2000)
    private String remark;//????????????

    @Length(max = 500)
    private String rejectReason;//????????????

    private String zhOrderId;//???????????????ID

    @Length(max = 200)
    private String zhOrderNumber;//??????????????????

    @Length(max = 200)
    private String consignorSelfNumber;//?????????????????????

    @Length(max = 1)
    private String deleteFlag = HcuxConstant.YES_NO.N;//

    @Transient
    private String userName;//?????????
    @Transient
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT, timezone = "GMT+8")
    @DateTimeFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date arrivedDateFrom;//???????????????
    @Transient
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT, timezone = "GMT+8")
    @DateTimeFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date arrivedDateTo;//???????????????
    @Transient
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT, timezone = "GMT+8")
    @DateTimeFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date drawGoodsDateFrom;//???????????????
    @Transient
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT, timezone = "GMT+8")
    @DateTimeFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date drawGoodsDateTo;//???????????????

    @Transient
    @Children
    private List<LmOrderLine> lineList;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getContractCodeOne() {
        return contractCodeOne;
    }

    public void setContractCodeOne(String contractCodeOne) {
        this.contractCodeOne = contractCodeOne;
    }

    public String getContractCodeTwo() {
        return contractCodeTwo;
    }

    public void setContractCodeTwo(String contractCodeTwo) {
        this.contractCodeTwo = contractCodeTwo;
    }

    public String getContractCodeThree() {
        return contractCodeThree;
    }

    public void setContractCodeThree(String contractCodeThree) {
        this.contractCodeThree = contractCodeThree;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getConsignorName() {
        return consignorName;
    }

    public void setConsignorName(String consignorName) {
        this.consignorName = consignorName;
    }

    public String getDrawGoodsUnit() {
        return drawGoodsUnit;
    }

    public void setDrawGoodsUnit(String drawGoodsUnit) {
        this.drawGoodsUnit = drawGoodsUnit;
    }

    public String getDrawGoodsProvince() {
        return drawGoodsProvince;
    }

    public void setDrawGoodsProvince(String drawGoodsProvince) {
        this.drawGoodsProvince = drawGoodsProvince;
    }

    public String getDrawGoodsCity() {
        return drawGoodsCity;
    }

    public void setDrawGoodsCity(String drawGoodsCity) {
        this.drawGoodsCity = drawGoodsCity;
    }

    public String getDrawGoodsArea() {
        return drawGoodsArea;
    }

    public void setDrawGoodsArea(String drawGoodsArea) {
        this.drawGoodsArea = drawGoodsArea;
    }

    public String getDrawGoodsAddress() {
        return drawGoodsAddress;
    }

    public void setDrawGoodsAddress(String drawGoodsAddress) {
        this.drawGoodsAddress = drawGoodsAddress;
    }

    public String getDrawGoodsPeople() {
        return drawGoodsPeople;
    }

    public void setDrawGoodsPeople(String drawGoodsPeople) {
        this.drawGoodsPeople = drawGoodsPeople;
    }

    public String getDrawGoodsMobile() {
        return drawGoodsMobile;
    }

    public void setDrawGoodsMobile(String drawGoodsMobile) {
        this.drawGoodsMobile = drawGoodsMobile;
    }

    public String getConsigneeUnit() {
        return consigneeUnit;
    }

    public void setConsigneeUnit(String consigneeUnit) {
        this.consigneeUnit = consigneeUnit;
    }

    public String getConsigneeProvince() {
        return consigneeProvince;
    }

    public void setConsigneeProvince(String consigneeProvince) {
        this.consigneeProvince = consigneeProvince;
    }

    public String getConsigneeCity() {
        return consigneeCity;
    }

    public void setConsigneeCity(String consigneeCity) {
        this.consigneeCity = consigneeCity;
    }

    public String getConsigneeArea() {
        return consigneeArea;
    }

    public void setConsigneeArea(String consigneeArea) {
        this.consigneeArea = consigneeArea;
    }

    public String getConsigneeAddress() {
        return consigneeAddress;
    }

    public void setConsigneeAddress(String consigneeAddress) {
        this.consigneeAddress = consigneeAddress;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getConsigneeMobile() {
        return consigneeMobile;
    }

    public void setConsigneeMobile(String consigneeMobile) {
        this.consigneeMobile = consigneeMobile;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Date getDrawGoodsDate() {
        return drawGoodsDate;
    }

    public void setDrawGoodsDate(Date drawGoodsDate) {
        this.drawGoodsDate = drawGoodsDate;
    }

    public Date getArrivedDate() {
        return arrivedDate;
    }

    public void setArrivedDate(Date arrivedDate) {
        this.arrivedDate = arrivedDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getZhOrderId() {
        return zhOrderId;
    }

    public void setZhOrderId(String zhOrderId) {
        this.zhOrderId = zhOrderId;
    }

    public String getZhOrderNumber() {
        return zhOrderNumber;
    }

    public void setZhOrderNumber(String zhOrderNumber) {
        this.zhOrderNumber = zhOrderNumber;
    }

    public String getConsignorSelfNumber() {
        return consignorSelfNumber;
    }

    public void setConsignorSelfNumber(String consignorSelfNumber) {
        this.consignorSelfNumber = consignorSelfNumber;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getArrivedDateFrom() {
        return arrivedDateFrom;
    }

    public void setArrivedDateFrom(Date arrivedDateFrom) {
        this.arrivedDateFrom = arrivedDateFrom;
    }

    public Date getArrivedDateTo() {
        return arrivedDateTo;
    }

    public void setArrivedDateTo(Date arrivedDateTo) {
        this.arrivedDateTo = arrivedDateTo;
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

    public List<LmOrderLine> getLineList() {
        return lineList;
    }

    public void setLineList(List<LmOrderLine> lineList) {
        this.lineList = lineList;
    }
}
