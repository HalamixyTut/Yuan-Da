package hcux.cs.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hcux.cs.dto.CsOutboundTransFull;

import java.util.List;

public interface ICsOutboundTransFullService extends IBaseService<CsOutboundTransFull>, ProxySelf<ICsOutboundTransFullService> {
    /**
     * 关联查询
     *
     * @param requestContext
     * @param dto
     * @param page
     * @param pageSize
     * @return
     */
    List<CsOutboundTransFull> selectData(IRequest requestContext, CsOutboundTransFull dto, int page, int pageSize);

    /**
     * 微信出库查询接口
     * @param requestContext
     * @param dto
     * @param page
     * @param pageSize
     * @return
     */
    List<CsOutboundTransFull> selectTransFullForWechat(IRequest requestContext, CsOutboundTransFull dto, int page, int pageSize);
}