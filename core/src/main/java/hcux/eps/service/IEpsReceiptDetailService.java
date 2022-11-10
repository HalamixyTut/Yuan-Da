package hcux.eps.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hcux.eps.dto.EpsReceiptDetail;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
/**
 * @author yexiang.ren@hand-china.com
 * @description 收款明细表
 */
public interface IEpsReceiptDetailService extends IBaseService<EpsReceiptDetail>, ProxySelf<IEpsReceiptDetailService> {
    /**
     * ebs系统中的收款明细数据同步到hap系统
     *
     * @param iRequest
     * @param epsReceiptDetail
     */
    int epsReceiptDetailToHap(IRequest iRequest, EpsReceiptDetail epsReceiptDetail);

    /**
     * 根据条件查询收款明心信息
     *
     * @param iRequest
     * @param epsReceiptDetail
     * @param page
     * @param pageSize
     * @return
     */
    List<EpsReceiptDetail> queryEpsReceiptDetail(IRequest iRequest, EpsReceiptDetail epsReceiptDetail, int page, int pageSize);

    /**
     * 根据条件查询贷方金额总金额
     *
     * @param iRequest
     * @param epsReceiptDetail
     * @param page
     * @param pageSize
     * @return
     */
    List<EpsReceiptDetail> selectTotalAmount(IRequest iRequest, EpsReceiptDetail epsReceiptDetail, int page, int pageSize);

    void exportExcel(IRequest requestContext, EpsReceiptDetail epsReceiptDetail, HttpServletResponse response) throws Exception;
}