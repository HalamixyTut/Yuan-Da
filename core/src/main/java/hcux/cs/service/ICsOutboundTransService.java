package hcux.cs.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hcux.cs.dto.CsOutboundTrans;

import java.util.List;

/**
 * @author haojie.zhang@hand-china.com
 * @description 出库物流详细
 */
public interface ICsOutboundTransService extends IBaseService<CsOutboundTrans>, ProxySelf<ICsOutboundTransService> {
    /**
     * 关联查询
     *
     * @param requestContext
     * @param dto
     * @param page
     * @param pageSize
     * @return
     */
    List<CsOutboundTrans> selectData(IRequest requestContext, CsOutboundTrans dto, int page, int pageSize);
}