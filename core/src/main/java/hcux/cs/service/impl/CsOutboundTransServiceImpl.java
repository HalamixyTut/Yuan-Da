package hcux.cs.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hcux.cs.mapper.CsOutboundTransMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hcux.cs.dto.CsOutboundTrans;
import hcux.cs.service.ICsOutboundTransService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author haojie.zhang@hand-china.com
 * @description 出库物流详细
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CsOutboundTransServiceImpl extends BaseServiceImpl<CsOutboundTrans> implements ICsOutboundTransService {
    @Autowired
    private CsOutboundTransMapper mapper;

    @Override
    public List<CsOutboundTrans> selectData(IRequest requestContext, CsOutboundTrans dto, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return mapper.selectData(dto);
    }
}