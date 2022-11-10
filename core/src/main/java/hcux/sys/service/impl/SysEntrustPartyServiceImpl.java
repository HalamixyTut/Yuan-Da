package hcux.sys.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hcux.sys.dto.SysEntrustParty;
import hcux.sys.mapper.SysEntrustPartyMapper;
import hcux.sys.service.ISysEntrustPartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author feng.liu01@hand-china.com
 * @description 委托方用户
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysEntrustPartyServiceImpl extends BaseServiceImpl<SysEntrustParty> implements ISysEntrustPartyService {

    @Autowired
    private SysEntrustPartyMapper mapper;

    @Override
    public List<SysEntrustParty> selectList(IRequest requestContext, SysEntrustParty dto, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return mapper.selectList(dto);
    }

    @Override
    public SysEntrustParty selectTotalMinAgencyFee(IRequest request, SysEntrustParty dto) {
        SysEntrustParty sysEntrustParty=mapper.selectEmployee(dto);
        if(sysEntrustParty!=null) {
            return mapper.selectTotalMinAgencyFee(dto);
        }else{
            return mapper.selectTotalMinAgencyFeeOuter(dto);
        }
    }

    @Override
    public SysEntrustParty selectTotalWithoutUser(IRequest request, SysEntrustParty dto) {
        return mapper.selectTotalWithOutUser(dto);
    }

    @Override
    public List<SysEntrustParty> selectStatementFlag(IRequest request, SysEntrustParty dto) {
        return mapper.selectSerialNumberOuter(dto);
    }
}