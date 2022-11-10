package hcux.cs.dto;

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import org.hibernate.validator.constraints.Length;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@ExtensionAttribute(disable = true)
@Table(name = "HCUX_CS_WAREHOUSE_LINK")
public class CsWarehouseLink extends BaseDTO {

    @Id
    @GeneratedValue
    private Long warehouseLinkId;

    @Length(max = 200)
    private String warehouseName;

    @Length(max = 200)
    private String warehouseLink;

    public Long getWarehouseLinkId() {
        return warehouseLinkId;
    }

    public void setWarehouseLinkId(Long warehouseLinkId) {
        this.warehouseLinkId = warehouseLinkId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getWarehouseLink() {
        return warehouseLink;
    }

    public void setWarehouseLink(String warehouseLink) {
        this.warehouseLink = warehouseLink;
    }
}
