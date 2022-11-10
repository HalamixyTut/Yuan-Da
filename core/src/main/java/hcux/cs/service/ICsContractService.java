package hcux.cs.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hcux.cs.dto.CsContract;

import java.util.List;

public interface ICsContractService extends IBaseService<CsContract>, ProxySelf<ICsContractService> {
    /**
     * ebs系统的合同数据同步到hap系统
     *
     * @param iRequest
     * @param csContract
     */
    int epsCsContractToHap(IRequest iRequest, CsContract csContract);

    /**
     * 根据条件查询合同数据
     *
     * @param iRequest
     * @param csContract
     * @param page
     * @param pageSize
     * @return
     */
    List<CsContract> queryCsContract(IRequest iRequest, CsContract csContract, int page, int pageSize);
}