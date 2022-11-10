package hcux.cs.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hcux.cs.dto.CsContractOut;
import hcux.cs.mapper.CsContractOutMapper;
import hcux.cs.service.ICsContractOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class CsContractOutServiceImpl extends BaseServiceImpl<CsContractOut> implements ICsContractOutService {
    @Autowired
    private CsContractOutMapper csContractOutMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int epsCsContractOutToHap(IRequest iRequest, CsContractOut csContractOut) {
        List<CsContractOut> csContractOutList = csContractOutMapper.selectCsContractOutFromEbs(csContractOut);
        Set<Long> headerSet = new HashSet<>();
        int count = 0;
        if (csContractOutList == null || csContractOutList.isEmpty()) {
            return count;
        }

        csContractOutList.forEach(x -> headerSet.add(x.getHeaderId()));
        for (Long x : headerSet) {
            if (x != null) {
                CsContractOut contractOut = new CsContractOut();
                contractOut.setHeaderId(x);
                csContractOutMapper.deleteByHeaderId(contractOut);
                List<CsContractOut> csContractOuts = csContractOutMapper.selectCsContractOutFromEbs(contractOut);
                if (csContractOuts != null && !csContractOuts.isEmpty()) {
                    for (CsContractOut dto : csContractOuts) {
                        self().insertSelective(iRequest, dto);
                    }
                    count += csContractOuts.size();
                }
            }
        }

        return count;
    }
}