package hcux.lm.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hcux.lm.mapper.LmOrderLineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hcux.lm.dto.LmOrderLine;
import hcux.lm.service.ILmOrderLineService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class LmOrderLineServiceImpl extends BaseServiceImpl<LmOrderLine> implements ILmOrderLineService {
    @Autowired
    private LmOrderLineMapper mapper;

    @Override
    public List<LmOrderLine> selectList(IRequest requestContext, LmOrderLine dto, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return mapper.selectList(dto);
    }
}