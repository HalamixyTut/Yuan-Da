package hcux.sys.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hcux.sys.dto.SysEntrustParty;

import java.util.List;

/**
 * @author feng.liu01@hand-china.com
 * @description 委托方用户
 */
public interface ISysEntrustPartyService extends IBaseService<SysEntrustParty>, ProxySelf<ISysEntrustPartyService> {

    List<SysEntrustParty> selectList(IRequest requestContext, SysEntrustParty dto, int page, int pageSize);

    SysEntrustParty selectTotalMinAgencyFee(IRequest request,SysEntrustParty dto);

    SysEntrustParty selectTotalWithoutUser(IRequest request,SysEntrustParty dto);

    List<SysEntrustParty> selectStatementFlag(IRequest request,SysEntrustParty dto);
}