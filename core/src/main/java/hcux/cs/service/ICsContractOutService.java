package hcux.cs.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hcux.cs.dto.CsContractOut;

public interface ICsContractOutService extends IBaseService<CsContractOut>, ProxySelf<ICsContractOutService> {
    /**
     * ebs系统的信息同步到hap系统
     *
     * @param iRequest
     * @param csContractOut
     */
    int epsCsContractOutToHap(IRequest iRequest, CsContractOut csContractOut);
}