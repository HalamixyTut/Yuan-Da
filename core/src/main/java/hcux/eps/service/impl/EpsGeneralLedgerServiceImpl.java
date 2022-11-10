package hcux.eps.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hcux.eps.dto.EpsGeneralLedger;
import hcux.eps.mapper.EpsGeneralLedgerMapper;
import hcux.eps.service.IEpsGeneralLedgerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class EpsGeneralLedgerServiceImpl extends BaseServiceImpl<EpsGeneralLedger> implements IEpsGeneralLedgerService {
    @Autowired
    private EpsGeneralLedgerMapper epsGeneralLedgerMapper;
    @Override
    public int epsGeneralLedgerToHap(IRequest iRequest) {
        epsGeneralLedgerMapper.delete(new EpsGeneralLedger());
        return epsGeneralLedgerMapper.insertAll();
    }
}
