package hcux.pm.mapper;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Mapper;
import hcux.pm.dto.PmHelp;

import java.util.List;

/**
 * @author haojie.zhang@hand-china.com
 * @description 使用帮助
 */
public interface PmHelpMapper extends Mapper<PmHelp>{
    /**
     * 查询不包含内容的使用帮助
     *
     * @param dto
     * @return
     */
    List<PmHelp> selectLists(PmHelp dto);

    PmHelp selectHelp(PmHelp dto);

    List<PmHelp> selectEffective(PmHelp dto);

    PmHelp selectEffectiveForNH(PmHelp dto);
}