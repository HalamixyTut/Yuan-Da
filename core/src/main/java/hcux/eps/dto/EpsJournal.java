package hcux.eps.dto;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * @author yexiang.ren@hand-china.com
 * @description 对账单对应的总账表视图（CUX_HAP_JOURNAL_V）
 */
public class EpsJournal implements Serializable {
    private String name;//日记账
    private String periodName;//期间
    private String currencyCode;//币种
    private String categoryName;//类别
    private String account;//账户
    private String projectNumber;//项目号
    private BigDecimal lossCompensationIncome;//营业外收入-损失赔偿收入
    private BigDecimal unablePay;//营业外收入-无法支付应付款项
    private BigDecimal other;//营业外支出-其他
    private BigDecimal impairmentLoss;//资产减值损失
    private BigDecimal interestPayments;//财务费用-利息支出
    private BigDecimal poundage;//财务费用-手续费
    private BigDecimal cityMaintenanceConstructionTax;//税金及附加-城市维护建设税
    private BigDecimal educationAttached;//税金及附加-教育费附加
    private BigDecimal tailAdjustment;//营业费用-尾差调整
    private BigDecimal office;//营业费用-办公费
    private BigDecimal storageCharges;//营业费用-仓储费
    private BigDecimal travelExpenses;//营业费用-差旅费
    private BigDecimal vehicleFee;//营业费用-车辆费
    private BigDecimal welfareFunds;//营业费用-福利费
    private BigDecimal inspectionFee;//营业费用-检验费
    private BigDecimal courierFee;//营业费用-快件费
    private BigDecimal training;//营业费用-培训费
    private BigDecimal sampleCharge;//营业费用-样品费
    private BigDecimal businessPublicity;//营业费用-业务宣传费
    private BigDecimal businessEntertainment;//营业费用-业务招待费
    private BigDecimal postalCharges;//营业费用-邮电通讯费
    private BigDecimal transportationCharges;//营业费用-运输装卸费
    private BigDecimal mailExhibitionFee;//营业费用-邮展览费

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    public BigDecimal getLossCompensationIncome() {
        return lossCompensationIncome;
    }

    public void setLossCompensationIncome(BigDecimal lossCompensationIncome) {
        this.lossCompensationIncome = lossCompensationIncome;
    }

    public BigDecimal getUnablePay() {
        return unablePay;
    }

    public void setUnablePay(BigDecimal unablePay) {
        this.unablePay = unablePay;
    }

    public BigDecimal getOther() {
        return other;
    }

    public void setOther(BigDecimal other) {
        this.other = other;
    }

    public BigDecimal getImpairmentLoss() {
        return impairmentLoss;
    }

    public void setImpairmentLoss(BigDecimal impairmentLoss) {
        this.impairmentLoss = impairmentLoss;
    }

    public BigDecimal getInterestPayments() {
        return interestPayments;
    }

    public void setInterestPayments(BigDecimal interestPayments) {
        this.interestPayments = interestPayments;
    }

    public BigDecimal getPoundage() {
        return poundage;
    }

    public void setPoundage(BigDecimal poundage) {
        this.poundage = poundage;
    }

    public BigDecimal getCityMaintenanceConstructionTax() {
        return cityMaintenanceConstructionTax;
    }

    public void setCityMaintenanceConstructionTax(BigDecimal cityMaintenanceConstructionTax) {
        this.cityMaintenanceConstructionTax = cityMaintenanceConstructionTax;
    }

    public BigDecimal getEducationAttached() {
        return educationAttached;
    }

    public void setEducationAttached(BigDecimal educationAttached) {
        this.educationAttached = educationAttached;
    }

    public BigDecimal getTailAdjustment() {
        return tailAdjustment;
    }

    public void setTailAdjustment(BigDecimal tailAdjustment) {
        this.tailAdjustment = tailAdjustment;
    }

    public BigDecimal getOffice() {
        return office;
    }

    public void setOffice(BigDecimal office) {
        this.office = office;
    }

    public BigDecimal getStorageCharges() {
        return storageCharges;
    }

    public void setStorageCharges(BigDecimal storageCharges) {
        this.storageCharges = storageCharges;
    }

    public BigDecimal getTravelExpenses() {
        return travelExpenses;
    }

    public void setTravelExpenses(BigDecimal travelExpenses) {
        this.travelExpenses = travelExpenses;
    }

    public BigDecimal getVehicleFee() {
        return vehicleFee;
    }

    public void setVehicleFee(BigDecimal vehicleFee) {
        this.vehicleFee = vehicleFee;
    }

    public BigDecimal getWelfareFunds() {
        return welfareFunds;
    }

    public void setWelfareFunds(BigDecimal welfareFunds) {
        this.welfareFunds = welfareFunds;
    }

    public BigDecimal getInspectionFee() {
        return inspectionFee;
    }

    public void setInspectionFee(BigDecimal inspectionFee) {
        this.inspectionFee = inspectionFee;
    }

    public BigDecimal getCourierFee() {
        return courierFee;
    }

    public void setCourierFee(BigDecimal courierFee) {
        this.courierFee = courierFee;
    }

    public BigDecimal getTraining() {
        return training;
    }

    public void setTraining(BigDecimal training) {
        this.training = training;
    }

    public BigDecimal getSampleCharge() {
        return sampleCharge;
    }

    public void setSampleCharge(BigDecimal sampleCharge) {
        this.sampleCharge = sampleCharge;
    }

    public BigDecimal getBusinessPublicity() {
        return businessPublicity;
    }

    public void setBusinessPublicity(BigDecimal businessPublicity) {
        this.businessPublicity = businessPublicity;
    }

    public BigDecimal getBusinessEntertainment() {
        return businessEntertainment;
    }

    public void setBusinessEntertainment(BigDecimal businessEntertainment) {
        this.businessEntertainment = businessEntertainment;
    }

    public BigDecimal getPostalCharges() {
        return postalCharges;
    }

    public void setPostalCharges(BigDecimal postalCharges) {
        this.postalCharges = postalCharges;
    }

    public BigDecimal getTransportationCharges() {
        return transportationCharges;
    }

    public void setTransportationCharges(BigDecimal transportationCharges) {
        this.transportationCharges = transportationCharges;
    }

    public BigDecimal getMailExhibitionFee() {
        return mailExhibitionFee;
    }

    public void setMailExhibitionFee(BigDecimal mailExhibitionFee) {
        this.mailExhibitionFee = mailExhibitionFee;
    }

    public String getPeriodName() {
        return periodName;
    }

    public void setPeriodName(String periodName) {
        this.periodName = periodName;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
}
