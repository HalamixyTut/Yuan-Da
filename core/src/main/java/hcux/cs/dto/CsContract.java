package hcux.cs.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hand.hap.core.BaseConstants;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import org.hibernate.validator.constraints.Length;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * 合同
 */
@ExtensionAttribute(disable = true)
@Table(name = "HCUX_CS_CONTRACT")
public class CsContract extends BaseDTO {

    public static final String FIELD_CONTRACT_ID = "contractId";
    public static final String FIELD_HEADER_ID = "headerId";
    public static final String FIELD_LINE_ID = "lineId";
    public static final String FIELD_ORG_NAME = "orgName";
    public static final String FIELD_CHINESE_ORG_NAME = "chineseOrgName";
    public static final String FIELD_CUST_PO_NUMBER = "custPoNumber";
    public static final String FIELD_CUSTOMER_NAME = "customerName";
    public static final String FIELD_ORDERED_DATE = "orderedDate";
    public static final String FIELD_ITEM_CODE = "itemCode";
    public static final String FIELD_ITEM_NAME = "itemName";
    public static final String FIELD_SO_UOM = "soUom";
    public static final String FIELD_SO_UOM_DESC = "soUomDesc";
    public static final String FIELD_SO_QTY = "soQty";
    public static final String FIELD_UNIT_SELLING_PRICE = "unitSellingPrice";
    public static final String FIELD_SO_AMOUNT = "soAmount";
    public static final String FIELD_SO_PERSON = "soPerson";
    public static final String FIELD_CREATE_PERSON = "createPerson";
    public static final String FIELD_ACTUAL_CONTRACT_NO = "actualContractNo";
    public static final String FIELD_LINE_CATEGORY_CODE = "lineCategoryCode";

    @Id
    @GeneratedValue
    private Long contractId;//表ID，主键，供其他表做外键

    private Long headerId;//ERP视图ID，多个ID组合确定唯一

    private Long lineId;//ERP视图ID，多个ID组合确定唯一

    @Length(max = 200)
    private String orgName;//业务实体

    @Length(max = 200)
    private String chineseOrgName;//公司名称

    @Length(max = 200)
    private String custPoNumber;//销售合同编号

    @Length(max = 200)
    private String customerName;//客户
    @DateTimeFormat(pattern = BaseConstants.DATE_FORMAT)
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT, timezone = "GMT+8")
    private Date orderedDate;//合同日期

    @Length(max = 200)
    private String itemCode;//物料编码

    @Length(max = 200)
    private String itemName;//品名规格

    @Length(max = 200)
    private String soUom;//单位

    @Length(max = 200)
    private String soUomDesc; //单位描述

    private Float soQty;//数量

    private Float unitSellingPrice;//单价

    private Float soAmount;//总金额

    @Length(max = 200)
    private String soPerson;//业务员

    @Length(max = 200)
    private String createPerson;//制单人

    @Length(max = 200)
    private String deptCode;//部门编码

    @Length(max = 200)
    private String customerCode;//客户编码

    @Length(max = 200)
    private String lineCategoryCode;//订单行类型

    @Transient
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT, timezone = "GMT+8")
    @DateTimeFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date orderedDateFrom;//合同日期从
    @Transient
    @JsonFormat(pattern = BaseConstants.DATE_FORMAT, timezone = "GMT+8")
    @DateTimeFormat(pattern = BaseConstants.DATE_TIME_FORMAT)
    private Date orderedDateTo;//合同日期至
    @Transient
    private Float outQuantity;//出库数量
    @Transient
    private Float unOutQuantity;//未出库数量
    @Transient
    private Float vatQuantity;//开票数量


    @Transient
    private String queryType; //查询类型（HAP、门户、微信）
    @Transient
    private List<String> sectionList;//部门list

    @Transient
    private String contractCodeOne;//合同号1
    @Transient
    private String contractCodeTwo;//合同号2
    @Transient
    private String contractCodeThree;//合同号3

    private String actualContractNo;//实际合同号

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public Long getHeaderId() {
        return headerId;
    }

    public void setHeaderId(Long headerId) {
        this.headerId = headerId;
    }

    public Long getLineId() {
        return lineId;
    }

    public void setLineId(Long lineId) {
        this.lineId = lineId;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setChineseOrgName(String chineseOrgName) {
        this.chineseOrgName = chineseOrgName;
    }

    public String getChineseOrgName() {
        return chineseOrgName;
    }

    public void setCustPoNumber(String custPoNumber) {
        this.custPoNumber = custPoNumber;
    }

    public String getCustPoNumber() {
        return custPoNumber;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setOrderedDate(Date orderedDate) {
        this.orderedDate = orderedDate;
    }

    public Date getOrderedDate() {
        return orderedDate;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setSoUom(String soUom) {
        this.soUom = soUom;
    }

    public String getSoUom() {
        return soUom;
    }

    public void setSoQty(Float soQty) {
        this.soQty = soQty;
    }

    public Float getSoQty() {
        return soQty;
    }

    public void setUnitSellingPrice(Float unitSellingPrice) {
        this.unitSellingPrice = unitSellingPrice;
    }

    public Float getUnitSellingPrice() {
        return unitSellingPrice;
    }

    public void setSoAmount(Float soAmount) {
        this.soAmount = soAmount;
    }

    public Float getSoAmount() {
        return soAmount;
    }

    public void setSoPerson(String soPerson) {
        this.soPerson = soPerson;
    }

    public String getSoPerson() {
        return soPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }

    public String getCreatePerson() {
        return createPerson;
    }

    public Date getOrderedDateFrom() {
        return orderedDateFrom;
    }

    public void setOrderedDateFrom(Date orderedDateFrom) {
        this.orderedDateFrom = orderedDateFrom;
    }

    public Date getOrderedDateTo() {
        return orderedDateTo;
    }

    public void setOrderedDateTo(Date orderedDateTo) {
        this.orderedDateTo = orderedDateTo;
    }

    public Float getOutQuantity() {
        return outQuantity;
    }

    public void setOutQuantity(Float outQuantity) {
        this.outQuantity = outQuantity;
    }

    public Float getUnOutQuantity() {
        return unOutQuantity;
    }

    public void setUnOutQuantity(Float unOutQuantity) {
        this.unOutQuantity = unOutQuantity;
    }

    public Float getVatQuantity() {
        return vatQuantity;
    }

    public void setVatQuantity(Float vatQuantity) {
        this.vatQuantity = vatQuantity;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public List<String> getSectionList() {
        return sectionList;
    }

    public void setSectionList(List<String> sectionList) { this.sectionList = sectionList; }

    public String getContractCodeOne() { return contractCodeOne; }

    public void setContractCodeOne(String contractCodeOne) { this.contractCodeOne = contractCodeOne; }

    public String getContractCodeTwo() { return contractCodeTwo; }

    public void setContractCodeTwo(String contractCodeTwo) { this.contractCodeTwo = contractCodeTwo; }

    public String getContractCodeThree() { return contractCodeThree; }

    public void setContractCodeThree(String contractCodeThree) { this.contractCodeThree = contractCodeThree; }

    public String getActualContractNo() {
        return actualContractNo;
    }

    public void setActualContractNo(String actualContractNo) {
        this.actualContractNo = actualContractNo;
    }

    public String getLineCategoryCode() { return lineCategoryCode; }

    public void setLineCategoryCode(String lineCategoryCode) { this.lineCategoryCode = lineCategoryCode; }

    public String getSoUomDesc() {
        return soUomDesc;
    }

    public void setSoUomDesc(String soUomDesc) {
        this.soUomDesc = soUomDesc;
    }
}
