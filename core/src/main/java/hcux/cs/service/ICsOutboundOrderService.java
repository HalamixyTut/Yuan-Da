package hcux.cs.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hcux.cs.dto.CsOutboundOrder;

import java.util.List;

/**
 * @author haojie.zhang@hand-china.com
 * @description 出库物流
 */
public interface ICsOutboundOrderService extends IBaseService<CsOutboundOrder>, ProxySelf<ICsOutboundOrderService> {
    /**
     * 关联查询
     *
     * @param requestContext
     * @param dto
     * @param page
     * @param pageSize
     * @return
     */
    List<CsOutboundOrder> selectData(IRequest requestContext, CsOutboundOrder dto, int page, int pageSize);
}