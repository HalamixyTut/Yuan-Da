package hcux.eps.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hcux.eps.dto.EpsBill;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
/**
 * @author yexiang.ren@hand-china.com
 * @description 挂账数据表
 */
public interface IEpsBillService extends IBaseService<EpsBill>, ProxySelf<IEpsBillService> {
    /**
     * ebs系统中的外汇账单数据同步到hap系统
     *
     * @param iRequest
     */
    int epsBillToHap(IRequest iRequest);

    /**
     * 通过条件查询信息
     *
     * @param iRequest
     * @param epsBill
     * @return
     */
    List<EpsBill> queryEpsBill(IRequest iRequest, EpsBill epsBill, int page, int pageSize);

    /**
     * excel导出所有数据
     *
     * @param iRequest
     * @param epsBill
     * @return
     */
    void exportExcel(IRequest iRequest, EpsBill epsBill, HttpServletResponse response) throws Exception;

    /**
     * 根据条件查询原币金额总金额
     *
     * @param requestContext
     * @param dto
     * @param page
     * @param pageSize
     * @return
     */
    List<EpsBill> selectTotalAmount(IRequest requestContext, EpsBill dto, int page, int pageSize);
}