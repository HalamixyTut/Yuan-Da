package hcux.mdm.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hcux.mdm.dto.MdmInventoryAddress;

import java.util.List;

/**
 * @author haojie.zhang@hand-china.com
 * @description 仓库地址维护
 */
public interface IMdmInventoryAddressService extends IBaseService<MdmInventoryAddress>, ProxySelf<IMdmInventoryAddressService>{
    /**
     * 根据条件查询仓库地址数据
     *
     * @param requestContext
     * @param dto
     * @param page
     * @param pageSize
     * @return
     */
    List<MdmInventoryAddress> selectList(IRequest requestContext, MdmInventoryAddress dto, int page, int pageSize);
}