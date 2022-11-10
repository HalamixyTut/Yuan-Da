package hcux.eps.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hcux.eps.dto.EpsCashFlow;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface IEpsCashFlowService extends IBaseService<EpsCashFlow>, ProxySelf<IEpsCashFlowService>{
    /**
     * 查询ebs数据同步到hap系统
     * @param iRequest
     * @return
     */
    int ebsDataToHap(IRequest iRequest);

    /**
     * 根据条件查询数据
     * @param iRequest
     * @param epsCashFlow
     * @return
     */
    List<EpsCashFlow> selectList(IRequest iRequest,EpsCashFlow epsCashFlow, int page, int pageSize);

    /**
     * 根据查询条件导出到excel
     * @param iRequest
     * @param epsCashFlow
     * @param response
     * @throws Exception
     */
    void exportExcel(IRequest iRequest, EpsCashFlow epsCashFlow, HttpServletResponse response) throws Exception;

    /**
     * 门户根据查询条件导出到excel
     * @param iRequest
     * @param epsCashFlow
     * @param response
     * @throws Exception
     */
    void exportExcelForPortal(IRequest iRequest, EpsCashFlow epsCashFlow, HttpServletResponse response) throws Exception;

}