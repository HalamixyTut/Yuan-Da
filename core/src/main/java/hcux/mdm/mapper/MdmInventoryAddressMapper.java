package hcux.mdm.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hcux.mdm.dto.MdmInventoryAddress;

import java.util.List;

/**
 * @author haojie.zhang@hand-china.com
 * @description 仓库地址维护
 */
public interface MdmInventoryAddressMapper extends Mapper<MdmInventoryAddress>{

    /**
     * 根据条件查询仓库地址数据
     *
     * @param dto
     * @return
     */
    List<MdmInventoryAddress> selectList(MdmInventoryAddress dto);
}