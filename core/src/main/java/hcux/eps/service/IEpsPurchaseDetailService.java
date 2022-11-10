package hcux.eps.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hcux.eps.dto.EpsPurchaseDetail;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author yexiang.ren@hand-china.com
 * @description 应收预付往来明细表
 */
public interface IEpsPurchaseDetailService extends IBaseService<EpsPurchaseDetail>, ProxySelf<IEpsPurchaseDetailService> {
    /**
     * ebs系统中的采购信息同步到hap中
     *
     * @param iRequest
     * @param epsPurchaseDetail
     */
    int ebsDataToHap(IRequest iRequest, EpsPurchaseDetail epsPurchaseDetail);

    /**
     * 通过条件查询应收预付往来明细表数据
     *
     * @param iRequest
     * @param epsPurchaseDetail
     * @param page
     * @param pageSize
     * @return
     */
    List<EpsPurchaseDetail> queryEpsPurchaseDetail(IRequest iRequest, EpsPurchaseDetail epsPurchaseDetail, int page, int pageSize);

    /**
     * 根据条件查询贷方金额总金额
     *
     * @param iRequest
     * @param epsPurchaseDetail
     * @param page
     * @param pageSize
     * @return
     */
    List<EpsPurchaseDetail> selectTotalAmount(IRequest iRequest, EpsPurchaseDetail epsPurchaseDetail, int page, int pageSize);

    void exportExcel(IRequest requestContext, EpsPurchaseDetail epsPurchaseDetail, HttpServletResponse response) throws Exception;
}