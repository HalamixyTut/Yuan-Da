package hcux.pm.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hcux.pm.mapper.PmHelpMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hcux.pm.dto.PmHelp;
import hcux.pm.service.IPmHelpService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author haojie.zhang@hand-china.com
 * @description 使用帮助
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PmHelpServiceImpl extends BaseServiceImpl<PmHelp> implements IPmHelpService{
    @Autowired
    private PmHelpMapper mapper;

    @Override
    public List<PmHelp> selectLists(IRequest iRequest, PmHelp dto, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return mapper.selectLists(dto);
    }

    @Override
    public PmHelp selectHelp(IRequest iRequest, PmHelp dto){
        return mapper.selectHelp(dto);
    }

    @Override
    public List<PmHelp> selectEffective(IRequest iRequest, PmHelp dto){
        return mapper.selectEffective(dto);
    }

    @Override
    public PmHelp selectEffectiveForNH(IRequest iRequest, PmHelp dto){
        return mapper.selectEffectiveForNH(dto);
    }
}