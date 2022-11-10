package hcux.cs.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import hcux.cs.dto.CsOutboundItemFull;
import hcux.cs.service.ICsOutboundItemFullService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class CsOutboundItemFullServiceImpl extends BaseServiceImpl<CsOutboundItemFull> implements ICsOutboundItemFullService {

}