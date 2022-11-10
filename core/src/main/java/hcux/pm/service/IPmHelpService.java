package hcux.pm.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hcux.pm.dto.PmHelp;

import java.util.List;

/**
 * @author haojie.zhang@hand-china.com
 * @description 使用帮助
 */
public interface IPmHelpService extends IBaseService<PmHelp>, ProxySelf<IPmHelpService>{
    /**
     * 查询使用帮助
     *
     * @param iRequest
     * @param dto
     * @param page
     * @param pageSize
     * @return
     */
    List<PmHelp> selectLists(IRequest iRequest, PmHelp dto, int page, int pageSize);

    PmHelp selectHelp(IRequest iRequest, PmHelp dto);

    List<PmHelp> selectEffective(IRequest iRequest, PmHelp dto);

    PmHelp selectEffectiveForNH(IRequest iRequest, PmHelp dto);
}