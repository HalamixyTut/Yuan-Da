package hcux.eps.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import hcux.eps.dto.EpsCode;
import hcux.eps.service.IEpsCodeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class EpsCodeServiceImpl extends BaseServiceImpl<EpsCode> implements IEpsCodeService{
}
