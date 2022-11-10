package hcux.cs.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hcux.cs.dto.CsCredit;
import hcux.cs.mapper.CsCreditMapper;
import hcux.cs.service.ICsCreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class CsCreditServiceImpl  extends BaseServiceImpl<CsCredit> implements ICsCreditService {
    @Autowired
    private CsCreditMapper csCreditMapper;
    @Override
    public List<CsCredit> selectData(IRequest requestContext, CsCredit dto) {
        return csCreditMapper.selectCsCreditFromEbs(dto);
    }
}
