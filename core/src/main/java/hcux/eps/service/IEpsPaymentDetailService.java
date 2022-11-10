package hcux.eps.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hcux.eps.dto.EpsPaymentDetail;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
/**
 * @author yexiang.ren@hand-china.com
 * @description 应付款表
 */
public interface IEpsPaymentDetailService extends IBaseService<EpsPaymentDetail>, ProxySelf<IEpsPaymentDetailService> {
    /**
     * ebs系统中的付款信息同步到hap系统中
     *
     * @param iRequest
     * @param epsPaymentDetail
     */
    int ebsPaymentDetailToHap(IRequest iRequest, EpsPaymentDetail epsPaymentDetail);

    /**
     * 根据条件查询数据
     *
     * @param requestContext
     * @param dto
     * @param page
     * @param pageSize
     * @return
     */
    List<EpsPaymentDetail> selectData(IRequest requestContext, EpsPaymentDetail dto, int page, int pageSize);

    /**
     * 根据条件查询原币金额总金额
     *
     * @param requestContext
     * @param dto
     * @param page
     * @param pageSize
     * @return
     */
    List<EpsPaymentDetail> selectTotalAmount(IRequest requestContext, EpsPaymentDetail dto, int page, int pageSize);

    void exportExcel(IRequest requestContext, EpsPaymentDetail epsPaymentDetail, HttpServletResponse response) throws Exception;
}