package hcux.pm.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hcux.pm.dto.PmNewsFile;

import java.util.List;

/**
 * @author haojie.zhang@hand-china.com
 * @description 门户新闻附件
 */
public interface IPmNewsFileService extends IBaseService<PmNewsFile>, ProxySelf<IPmNewsFileService>{
    /**
     * 查询新闻上传的附件
     *
     * @param requestContext
     * @param dto
     * @param page
     * @param pageSize
     * @return
     */
    List<PmNewsFile> selectLists(IRequest requestContext, PmNewsFile dto, int page, int pageSize);
}