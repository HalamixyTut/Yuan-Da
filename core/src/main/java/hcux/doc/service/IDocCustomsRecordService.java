package hcux.doc.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hcux.doc.dto.DocCustomsHeader;
import hcux.doc.dto.DocCustomsRecord;

import java.util.Date;
import java.util.List;

/**
 * @author feng.liu01@hand-china.com
 * @description 报关单操作记录
 */
public interface IDocCustomsRecordService extends IBaseService<DocCustomsRecord>, ProxySelf<IDocCustomsRecordService> {

    /**
     * 关联查询
     *
     * @param requestContext
     * @param dto
     * @param page
     * @param pageSize
     * @return
     */
    List<DocCustomsRecord> selectList(IRequest requestContext, DocCustomsRecord dto, int page, int pageSize);

    /**
     * 根据报关单头表ID查询最大的操作时间
     */
    DocCustomsRecord selectMaxTime(DocCustomsHeader header);
}