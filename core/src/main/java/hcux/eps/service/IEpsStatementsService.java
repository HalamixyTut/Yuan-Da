package hcux.eps.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.system.service.IBaseService;
import hcux.eps.dto.EpsStatements;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
/**
 * @author yexiang.ren@hand-china.com
 * @description 对账单表
 */
public interface IEpsStatementsService extends IBaseService<EpsStatements>, ProxySelf<IEpsStatementsService> {
    /**
     * 查询对账单信息
     *
     * @param iRequest
     * @param epsStatements
     * @param page
     * @param pageSize
     * @return
     */
    List<EpsStatements> queryEpsStatements(IRequest iRequest, EpsStatements epsStatements, int page, int pageSize);

    /**
     * 根据项目号查询出口调整项是否存在
     *
     * @param requestContext
     * @param epsStatement
     * @return
     */
    EpsStatements queryStatementByProjectNum(IRequest requestContext, EpsStatements epsStatement);

//    /**
//     * 更新出口调整项
//     *
//     * @param requestContext
//     * @param epsStatements
//     * @return
//     */
//    List<EpsStatements> batchUpdate(IRequest requestContext, @StdWho List<EpsStatements> epsStatements);

    /**
     * ebs数据同步到hap数据库
     * @param iRequest
     */
    void epsStatementsToHap(IRequest iRequest,EpsStatements epsStatement);

    /**
     * excel导出所有数据
     *
     * @param iRequest
     * @param epsStatements
     * @return
     */
    void exportExcel(IRequest iRequest, EpsStatements epsStatements, HttpServletResponse response) throws Exception;

    void exportExcelOuter(IRequest iRequest, EpsStatements epsStatements, HttpServletResponse response) throws Exception;

    /**
     * 查询对账单信息
     *
     * @param iRequest
     * @param epsStatements
     * @param page
     * @param pageSize
     * @return
     */
    List<EpsStatements> queryEpsStatementsOnly(IRequest iRequest, EpsStatements epsStatements, int page, int pageSize);

    void readExportExcel(IRequest iRequest, EpsStatements epsStatements, HttpServletResponse response) throws Exception;
    /**
     * 门户excel导出所有数据
     *
     * @param iRequest
     * @param epsStatements
     * @return
     */
    void portalExportExcel(IRequest iRequest, EpsStatements epsStatements, HttpServletResponse response) throws Exception;
}
