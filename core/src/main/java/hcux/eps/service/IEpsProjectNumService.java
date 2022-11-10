package hcux.eps.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hcux.eps.dto.EpsProjectNum;

/**
 * @author haojie.zhang@hand-china.com
 * @description 白条发票号
 */
public interface IEpsProjectNumService extends IBaseService<EpsProjectNum>, ProxySelf<IEpsProjectNumService>{
    /**
     * 同步ebs白条发票号到hap
     *
     * @param iRequest
     */
    int epsProjectNumToHap(IRequest iRequest);
}