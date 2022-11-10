package hcux.mdm.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.core.exception.BaseException;
import com.hand.hap.system.service.IBaseService;
import hcux.mdm.dto.MdmCustomer;

import java.util.List;

/**
 * @author feng.liu01@hand-china.com
 * @description 客户数据
 */
public interface IMdmCustomerService extends IBaseService<MdmCustomer>, ProxySelf<IMdmCustomerService> {

    List<MdmCustomer> batchUpdateCustomer(IRequest requestCtx, @StdWho List<MdmCustomer> list) throws BaseException;

    int deleteData(IRequest requestCtx, @StdWho List<MdmCustomer> list);

    List<MdmCustomer> selectList(IRequest requestContext, MdmCustomer dto, int page, int pageSize);
}