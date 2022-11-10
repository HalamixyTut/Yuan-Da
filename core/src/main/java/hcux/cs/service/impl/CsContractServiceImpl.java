package hcux.cs.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hcux.core.util.HcuxConstant;
import hcux.cs.dto.CsContract;
import hcux.cs.mapper.CsContractMapper;
import hcux.cs.service.ICsContractService;
import hcux.sys.mapper.SysPlateSectionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(rollbackFor = Exception.class)
public class CsContractServiceImpl extends BaseServiceImpl<CsContract> implements ICsContractService {
    @Autowired
    private CsContractMapper csContractMapper;
    @Autowired
    private SysPlateSectionMapper sysPlateSectionMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int epsCsContractToHap(IRequest iRequest, CsContract csContract) {
        List<CsContract> csContractList = csContractMapper.selectCsContractFromEbs(csContract);
        Optional<List<CsContract>> optionalBillList = Optional.ofNullable(csContractList);
        if (optionalBillList.isPresent()) {
            csContractList.forEach(x -> {
                CsContract csContract1 = csContractMapper.selectCsContractFromHap(x);
                Optional<CsContract> billOptional = Optional.ofNullable(csContract1);
                if (billOptional.isPresent()) {
                    x.setContractId(csContract1.getContractId());
                    x.set__status(DTOStatus.UPDATE);
                    x.setObjectVersionNumber(csContract1.getObjectVersionNumber());
                    x.setCreatedBy(csContract1.getCreatedBy());
                    x.setCreationDate(csContract1.getCreationDate());
                    self().updateByPrimaryKeySelective(iRequest, x);
                } else {
                    self().insertSelective(iRequest, x);
                }
            });
        }
        return csContractList == null ? 0 : csContractList.size();
    }

    @Override
    public List<CsContract> queryCsContract(IRequest iRequest, CsContract csContract, int page, int pageSize) {

        if (HcuxConstant.QUERY_TYPE.PORTAL.equals(csContract.getQueryType())) {
            List<String> sections = sysPlateSectionMapper.queryPlateSection(iRequest.getUserId());
            csContract.setSectionList(sections);
        }

        if (HcuxConstant.QUERY_TYPE.HAP.equals(csContract.getQueryType())) {
            List<String> sections = sysPlateSectionMapper.queryUserSection(iRequest.getUserId());
            csContract.setSectionList(sections);
        }

        PageHelper.startPage(page, pageSize);
        return csContractMapper.selectLists(csContract);
    }
}