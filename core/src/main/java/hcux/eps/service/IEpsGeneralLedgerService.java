package hcux.eps.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hcux.eps.dto.EpsGeneralLedger;
/**
 * @author yexiang.ren@hand-china.com
 * @description 总账表
 */
public interface IEpsGeneralLedgerService extends IBaseService<EpsGeneralLedger>, ProxySelf<IEpsGeneralLedgerService> {
    /**
     * ebs系统的总账单数据同步到hap系统
     * @param iRequest
     * @return
     */
    int epsGeneralLedgerToHap(IRequest iRequest);
}
