package hcux.lm.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hcux.lm.dto.LmOrderLine;

import java.util.List;

public interface ILmOrderLineService extends IBaseService<LmOrderLine>, ProxySelf<ILmOrderLineService> {
    /**
     * 查询
     *
     * @param requestContext
     * @param dto
     * @param page
     * @param pageSize
     * @return
     */
    List<LmOrderLine> selectList(IRequest requestContext, LmOrderLine dto, int page, int pageSize);
}