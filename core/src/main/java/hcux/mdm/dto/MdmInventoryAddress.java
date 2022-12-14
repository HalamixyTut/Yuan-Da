package hcux.mdm.dto;

/**Auto Generated By Hap Code Generator**/
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import org.hibernate.validator.constraints.Length;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.hand.hap.system.dto.BaseDTO;

/**
 * @author haojie.zhang@hand-china.com
 * @description 仓库地址维护
 */
@ExtensionAttribute(disable=true)
@Table(name = "HCUX_MDM_INVENTORY_ADDRESS")
public class MdmInventoryAddress extends BaseDTO {

     public static final String FIELD_INV_ADDRESS_ID = "invAddressId";
     public static final String FIELD_MAREHOUSE_NAME = "marehouseName";
     public static final String FIELD_PROVINCE = "province";
     public static final String FIELD_CITY = "city";
     public static final String FIELD_AREA = "area";
     public static final String FIELD_ADDRESS = "address";
     public static final String FIELD_ENABLED_FLAG = "enabledFlag";
     public static final String FIELD_REMARK = "remark";
     public static final String FIELD_DELETE_FLAG = "deleteFlag";


     @Id
     @GeneratedValue
     private Long invAddressId; //表ID，主键，供其他表做外键

     @Length(max = 200)
     private String marehouseName; //仓库名称

     @Length(max = 50)
     private String province; //省份

     @Length(max = 50)
     private String city; //城市

     @Length(max = 50)
     private String area; //地区

     @Length(max = 500)
     private String address; //详细地址

     @Length(max = 1)
     private String enabledFlag; //启用标识

     @Length(max = 2000)
     private String remark; //备注说明

     @Length(max = 1)
     private String deleteFlag; //删除标记

     @Transient
     private String provinceMeaning; //省份含义

     @Transient
     private String cityMeaning; //城市含义

     @Transient
     private String areaMeaning; //地区含义

     @Transient
     private String drawGoodsUnit; //仓库名称

     public void setInvAddressId(Long invAddressId){
         this.invAddressId = invAddressId;
     }

     public Long getInvAddressId(){
         return invAddressId;
     }

     public void setMarehouseName(String marehouseName){
         this.marehouseName = marehouseName;
     }

     public String getMarehouseName(){
         return marehouseName;
     }

     public void setProvince(String province){
         this.province = province;
     }

     public String getProvince(){
         return province;
     }

     public void setCity(String city){
         this.city = city;
     }

     public String getCity(){
         return city;
     }

     public void setArea(String area){
         this.area = area;
     }

     public String getArea(){
         return area;
     }

     public void setAddress(String address){
         this.address = address;
     }

     public String getAddress(){
         return address;
     }

     public void setEnabledFlag(String enabledFlag){
         this.enabledFlag = enabledFlag;
     }

     public String getEnabledFlag(){
         return enabledFlag;
     }

     public void setRemark(String remark){
         this.remark = remark;
     }

     public String getRemark(){
         return remark;
     }

     public void setDeleteFlag(String deleteFlag){
         this.deleteFlag = deleteFlag;
     }

     public String getDeleteFlag(){
         return deleteFlag;
     }

    public String getProvinceMeaning() { return provinceMeaning; }

    public void setProvinceMeaning(String provinceMeaning) { this.provinceMeaning = provinceMeaning; }

    public String getCityMeaning() { return cityMeaning; }

    public void setCityMeaning(String cityMeaning) { this.cityMeaning = cityMeaning; }

    public String getAreaMeaning() { return areaMeaning; }

    public void setAreaMeaning(String areaMeaning) { this.areaMeaning = areaMeaning; }

    public String getDrawGoodsUnit() { return drawGoodsUnit; }

    public void setDrawGoodsUnit(String drawGoodsUnit) { this.drawGoodsUnit = drawGoodsUnit; }
}
