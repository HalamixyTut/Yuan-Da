package hcux.lm.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.system.service.IBaseService;
import hcux.core.exception.HcuxException;
import hcux.lm.dto.LmOrderHeader;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ILmOrderHeaderService extends IBaseService<LmOrderHeader>, ProxySelf<ILmOrderHeaderService> {
    /**
     * 根据条件查询委托单
     *
     *  iRequest
     * @param lmOrderHeader
     * @param page
     * @param pageSize
     * @return
     */
    List<LmOrderHeader> selectLists(IRequest iRequest, LmOrderHeader lmOrderHeader, int page, int pageSize);

    ResponseData submitToZh(IRequest requestCtx, @RequestBody LmOrderHeader dto) throws HcuxException;

    /**
     * 头行一起保存
     *
     * @param requestCtx
     * @param headerList
     * @return
     */
    List<LmOrderHeader> batchUpdateHeaderAndLine(IRequest requestCtx, List<LmOrderHeader> headerList);

    /**
     * 查询单个，处理编辑页面汇总行逻辑
     *
     * @param requestContext
     * @param dto
     * @return
     */
    LmOrderHeader selectOne(IRequest requestContext, LmOrderHeader dto);
}