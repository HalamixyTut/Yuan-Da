package hcux.cs.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hcux.cs.mapper.CsOutboundTransFullMapper;
import hcux.lm.dto.LmTransport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hcux.cs.dto.CsOutboundTransFull;
import hcux.cs.service.ICsOutboundTransFullService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class CsOutboundTransFullServiceImpl extends BaseServiceImpl<CsOutboundTransFull> implements ICsOutboundTransFullService {
    @Autowired
    private CsOutboundTransFullMapper mapper;

    @Override
    public List<CsOutboundTransFull> selectData(IRequest requestContext, CsOutboundTransFull dto, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<CsOutboundTransFull> list = mapper.selectData(dto);
        for(CsOutboundTransFull csOutboundTransFull : list) {
            LmTransport lmTransport = mapper.selectLmTransport(csOutboundTransFull);
            if (lmTransport != null) {
                csOutboundTransFull.setLink(lmTransport.getLink());
                csOutboundTransFull.setZhTransportId(lmTransport.getZhTransportId().toString());
            }
        }
        return list;
    }

    @Override
    public List<CsOutboundTransFull> selectTransFullForWechat(IRequest requestContext, CsOutboundTransFull dto, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);

        return mapper.selectTransFullForWechat(dto);
    }
}