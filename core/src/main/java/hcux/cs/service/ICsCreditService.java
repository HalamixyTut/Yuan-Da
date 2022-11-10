package hcux.cs.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hcux.cs.dto.CsCredit;

import java.util.List;

public interface ICsCreditService extends IBaseService<CsCredit>, ProxySelf<ICsCreditService> {
    List<CsCredit> selectData(IRequest requestContext, CsCredit dto);
}
