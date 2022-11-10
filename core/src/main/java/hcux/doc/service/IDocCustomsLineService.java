package hcux.doc.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hcux.doc.dto.DocCustomsLine;

import java.util.List;

/**
 * @author feng.liu01@hand-china.com
 * @description 报关单行表
 */
public interface IDocCustomsLineService extends IBaseService<DocCustomsLine>, ProxySelf<IDocCustomsLineService> {

    /**
     * 查询
     *
     * @param requestContext
     * @param dto
     * @param page
     * @param pageSize
     * @return
     */
    List<DocCustomsLine> selectList(IRequest requestContext, DocCustomsLine dto, int page, int pageSize);

    /**
     * 复制数据
     *
     * @param requestCtx
     * @param dto
     * @return
     */
    List<DocCustomsLine> copyRow(IRequest requestCtx, DocCustomsLine dto);

    /**
     * 根据条件查询行数据
     *
     * @param requestContext
     * @param dto
     * @param page
     * @param pageSize
     * @return
     */
    List<DocCustomsLine> queryAll(IRequest requestContext, DocCustomsLine dto, int page, int pageSize);

    /**
     * 复制行数据
     * @param requestCtx
     * @param dto
     * @return
     */
    List<DocCustomsLine> copyDocCustomsLine(IRequest requestCtx, List<DocCustomsLine> dto, String customsLineId);

    int batchDeleteLine(IRequest requestCtx,List<DocCustomsLine> list);
}