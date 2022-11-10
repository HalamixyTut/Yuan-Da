package hcux.doc.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.hand.hap.mybatis.annotation.Condition;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import org.hibernate.validator.constraints.Length;
import javax.persistence.Table;
import com.hand.hap.system.dto.BaseDTO;
@ExtensionAttribute(disable=true)
@Table(name = "HCUX_DOC_CUSTOMS_CODE")
public class DocCustomsCode extends BaseDTO {

     public static final String FIELD_CUSTOMS_CODE_ID = "customsCodeId";
     public static final String FIELD_GOODS_CODE = "goodsCode";
     public static final String FIELD_GOODS_NAME = "goodsName";
     public static final String FIELD_DECLARATION_ELEMENT = "declarationElement";
     public static final String FIELD_LOWEST_TAX_RATE = "lowestTaxRate";
     public static final String FIELD_COMMON_TAX_RATE = "commonTaxRate";
     public static final String FIELD_VAT_RATE = "vatRate";
     public static final String FIELD_CONSUMPTION_TAX_RATE = "consumptionTaxRate";
     public static final String FIELD_EXPORT_TAX_RATE = "exportTaxRate";
     public static final String FIELD_EXPORT_REBATE_RATE = "exportRebateRate";
     public static final String FIELD_LEGAL_UNIT = "legalUnit";
     public static final String FIELD_LEGAL_SECOND_UNIT = "legalSecondUnit";
     public static final String FIELD_REGULATION_CODE = "regulationCode";
     public static final String FIELD_INSPECTION_TYPE = "inspectionType";


     @Id
     @GeneratedValue
     private Long customsCodeId; //表ID，主键，供其他表做外键

     @Length(max = 200)
     @Condition(operator = LIKE)
     private String goodsCode; //商品编码

     @Length(max = 1000)
     @Condition(operator = LIKE)
     private String goodsName; //商品名称

     @Length(max = 2000)
     private String declarationElement; //申报要素

     private Float lowestTaxRate; //最惠税率

     private Float commonTaxRate; //普通税率

     private Float vatRate; //增值税率

     private Float consumptionTaxRate; //消费税率

     private Float exportTaxRate; //出口税率

     private Float exportRebateRate; //出口退税率

     @Length(max = 100)
     private String legalUnit; //法定计量单位

     @Length(max = 100)
     private String legalSecondUnit; //法定第二计量单位

     @Length(max = 100)
     private String regulationCode; //监管条件代码

     @Length(max = 100)
     private String inspectionType; //检验检疫类别


     public void setCustomsCodeId(Long customsCodeId){
         this.customsCodeId = customsCodeId;
     }

     public Long getCustomsCodeId(){
         return customsCodeId;
     }

     public void setGoodsCode(String goodsCode){
         this.goodsCode = goodsCode;
     }

     public String getGoodsCode(){
         return goodsCode;
     }

     public void setGoodsName(String goodsName){
         this.goodsName = goodsName;
     }

     public String getGoodsName(){
         return goodsName;
     }

     public void setDeclarationElement(String declarationElement){
         this.declarationElement = declarationElement;
     }

     public String getDeclarationElement(){
         return declarationElement;
     }

     public void setLowestTaxRate(Float lowestTaxRate){
         this.lowestTaxRate = lowestTaxRate;
     }

     public Float getLowestTaxRate(){
         return lowestTaxRate;
     }

     public void setCommonTaxRate(Float commonTaxRate){
         this.commonTaxRate = commonTaxRate;
     }

     public Float getCommonTaxRate(){
         return commonTaxRate;
     }

     public void setVatRate(Float vatRate){
         this.vatRate = vatRate;
     }

     public Float getVatRate(){
         return vatRate;
     }

     public void setConsumptionTaxRate(Float consumptionTaxRate){
         this.consumptionTaxRate = consumptionTaxRate;
     }

     public Float getConsumptionTaxRate(){
         return consumptionTaxRate;
     }

     public void setExportTaxRate(Float exportTaxRate){
         this.exportTaxRate = exportTaxRate;
     }

     public Float getExportTaxRate(){
         return exportTaxRate;
     }

     public void setExportRebateRate(Float exportRebateRate){
         this.exportRebateRate = exportRebateRate;
     }

     public Float getExportRebateRate(){
         return exportRebateRate;
     }

     public void setLegalUnit(String legalUnit){
         this.legalUnit = legalUnit;
     }

     public String getLegalUnit(){
         return legalUnit;
     }

     public void setLegalSecondUnit(String legalSecondUnit){
         this.legalSecondUnit = legalSecondUnit;
     }

     public String getLegalSecondUnit(){
         return legalSecondUnit;
     }

     public void setRegulationCode(String regulationCode){
         this.regulationCode = regulationCode;
     }

     public String getRegulationCode(){
         return regulationCode;
     }

     public void setInspectionType(String inspectionType){
         this.inspectionType = inspectionType;
     }

     public String getInspectionType(){
         return inspectionType;
     }

     }
