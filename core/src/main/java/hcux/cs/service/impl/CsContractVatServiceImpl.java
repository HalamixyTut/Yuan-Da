package hcux.cs.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hcux.cs.dto.CsContractVat;
import hcux.cs.mapper.CsContractVatMapper;
import hcux.cs.service.ICsContractVatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(rollbackFor = Exception.class)
public class CsContractVatServiceImpl extends BaseServiceImpl<CsContractVat> implements ICsContractVatService {
    @Autowired
    private CsContractVatMapper csContractVatMapper;

    @Override
    public int epsCsContractVatToHap(IRequest iRequest, CsContractVat csContractVat) {
        List<CsContractVat> csContractVatList = csContractVatMapper.selectCsContractVatFromEbs(csContractVat);
        Optional<List<CsContractVat>> optionalBillList = Optional.ofNullable(csContractVatList);
        if (optionalBillList.isPresent()) {
            csContractVatList.forEach(x -> {
                CsContractVat csContractVat1 = csContractVatMapper.selectCsContractVatFromHap(x);
                Optional<CsContractVat> billOptional = Optional.ofNullable(csContractVat1);
                if (billOptional.isPresent()) {
                    x.setContractVatId(csContractVat1.getContractVatId());
                    x.set__status(DTOStatus.UPDATE);
                    x.setObjectVersionNumber(csContractVat1.getObjectVersionNumber());
                    x.setCreatedBy(csContractVat1.getCreatedBy());
                    x.setCreationDate(csContractVat1.getCreationDate());
                    self().updateByPrimaryKeySelective(iRequest, x);
                } else {
                    self().insertSelective(iRequest, x);
                }
            });
        }
        return csContractVatList == null ? 0 : csContractVatList.size();

    }
}