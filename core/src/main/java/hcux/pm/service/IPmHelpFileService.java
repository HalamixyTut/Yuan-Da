package hcux.pm.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hcux.pm.dto.PmHelpFile;

import java.util.List;

/**
 * @author yuanqing.li@hand-china.com
 * @description 使用帮助附件
 */
public interface IPmHelpFileService extends IBaseService<PmHelpFile>, ProxySelf<IPmHelpFileService>{
    /**
     * 查询新闻上传的附件
     *
     * @param requestContext
     * @param dto
     * @param page
     * @param pageSize
     * @return
     */
    List<PmHelpFile> selectLists(IRequest requestContext, PmHelpFile dto, int page, int pageSize);
}