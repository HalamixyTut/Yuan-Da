package hcux.mdm.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hcux.mdm.dto.MdmCustomerAddress;

import java.util.List;

/**
 * @author feng.liu01@hand-china.com
 * @description 客户收件人地址
 */
public interface IMdmCustomerAddressService extends IBaseService<MdmCustomerAddress>, ProxySelf<IMdmCustomerAddressService> {

    /**
     * 关联查询
     *
     * @param requestContext
     * @param dto
     * @param page
     * @param pageSize
     * @return
     */
    List<MdmCustomerAddress> selectData(IRequest requestContext, MdmCustomerAddress dto, int page, int pageSize);
}