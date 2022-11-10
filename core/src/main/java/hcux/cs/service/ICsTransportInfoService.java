package hcux.cs.service;

import com.hand.hap.code.rule.exception.CodeRuleException;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.system.service.IBaseService;
import hcux.cs.dto.CsContract;
import hcux.cs.dto.CsTransportInfo;

import java.util.List;

public interface ICsTransportInfoService extends IBaseService<CsTransportInfo>, ProxySelf<ICsTransportInfoService>{
    /**
     * 根据条件查询数据
     * @param iRequest
     * @param csTransportInfo
     * @param page
     * @param pageSize
     * @return
     */
    List<CsTransportInfo> selectLists(IRequest iRequest,CsTransportInfo csTransportInfo, int page, int pageSize);

    /**
     * 根据条件查询出物料编码数据
     * @param csTransportInfo
     * @return
     */
    List<CsContract> selectListByCustPoNumber(CsTransportInfo csTransportInfo);

    List<CsTransportInfo> batchUpdateTransport(IRequest requestCtx, @StdWho List<CsTransportInfo> dto) throws CodeRuleException;
}