package hcux.cs.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hcux.cs.dto.CsContractVat;

public interface ICsContractVatService extends IBaseService<CsContractVat>, ProxySelf<ICsContractVatService> {
    /**
     * ebs的销售开票明细数据同步到hap系统
     *
     * @param iRequest
     * @param csContractVat
     */
    int epsCsContractVatToHap(IRequest iRequest, CsContractVat csContractVat);
}