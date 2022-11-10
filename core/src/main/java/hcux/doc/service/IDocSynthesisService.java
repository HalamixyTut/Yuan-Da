package hcux.doc.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hcux.doc.dto.DocSynthesis;

import java.util.List;

public interface IDocSynthesisService extends IBaseService<DocSynthesis>, ProxySelf<IDocSynthesisService> {
    /**
     * 综合查询
     *
     * @param requestContext
     * @param dto
     * @param page
     * @param pageSize
     * @return
     */
    List<DocSynthesis> selectList(IRequest requestContext, DocSynthesis dto, int page, int pageSize);

    /**
     * 更新数据
     *
     * @param dto
     * @return
     */
    List<DocSynthesis> updateSynthesis(IRequest requestContext, List<DocSynthesis> dto);

    /**
     * 综合查询状态修改为准备做箱、准备报关
     * @param iRequest
     * @param docSynthesis
     * @return
     */
    int modifyStatus(IRequest iRequest, DocSynthesis docSynthesis);

    /**
     * 综合状态若是已开船之后的状态（包括已开船、运费已付、提单已传、已作废 状态），报关首页查询该项目号状态是 已完结
     *
     * @param requestCtx
     * @param customsId
     */
//    void modifyDocCustomsStatus(IRequest requestCtx, Long customsId);

    /**
     * 更改综合查询状态
     *
     * @param requestContext
     * @param docSynthesis
     */
    DocSynthesis modifyDocSynthesisStatus(IRequest requestContext, DocSynthesis docSynthesis);

    /**
     * 报关单状态从已审批到待提交，更改综合中状态为报关中的单据
     *
     * @param requestContext
     * @param customsId
     */
    void relateModifyStatus(IRequest requestContext, Long customsId);
}