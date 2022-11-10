package hcux.cs.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hcux.cs.mapper.CsOutboundOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hcux.cs.dto.CsOutboundOrder;
import hcux.cs.service.ICsOutboundOrderService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author haojie.zhang@hand-china.com
 * @description 出库物流
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CsOutboundOrderServiceImpl extends BaseServiceImpl<CsOutboundOrder> implements ICsOutboundOrderService {
    @Autowired
    private CsOutboundOrderMapper mapper;

    @Override
    public List<CsOutboundOrder> selectData(IRequest requestContext, CsOutboundOrder dto, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return mapper.selectData(dto);
    }
}