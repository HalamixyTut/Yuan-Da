package hcux.eps.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.system.service.IBaseService;
import hcux.eps.dto.EpsCustomsDetail;
import hcux.sys.dto.SysJobRecord;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
/**
 * @author yexiang.ren@hand-china.com
 * @description 报关明细表
 */
public interface IEpsCustomsDetailService extends IBaseService<EpsCustomsDetail>, ProxySelf<IEpsCustomsDetailService> {
    /**
     * @param requestContext
     * @param dto
     * @param page
     * @param pageSize
     * @return
     */
    List<EpsCustomsDetail> selectLists(IRequest requestContext, EpsCustomsDetail dto, int page, int pageSize);

    /**
     * 报关明细头查询
     *
     * @param requestContext
     * @param dto
     * @param page
     * @param pageSize
     * @return
     */
    List<EpsCustomsDetail> selectErrorLists(IRequest requestContext, EpsCustomsDetail dto, int page, int pageSize);

    /**
     * 为门户提供查询的接口
     *
     * @param requestContext
     * @param dto
     * @param page
     * @param pageSize
     * @return
     */
    List<EpsCustomsDetail> selectPortalList(IRequest requestContext, EpsCustomsDetail dto, int page, int pageSize);

    /**
     * 根据条件查询总价总金额
     *
     * @param requestContext
     * @param dto
     * @param page
     * @param pageSize
     * @return
     */
    List<EpsCustomsDetail> selectTotalAmount(IRequest requestContext, EpsCustomsDetail dto, int page, int pageSize);

    /**
     * 查询最大的申报日期
     *
     * @param requestContext
     * @param dto
     * @return
     */
    EpsCustomsDetail selectMaxDeclareDate(IRequest requestContext, EpsCustomsDetail dto);

    /**
     * 微信端头查询逻辑
     *
     * @param requestContext
     * @param dto
     * @param page
     * @param pageSize
     * @return
     */
    List<EpsCustomsDetail> selectHeaderListForWechat(IRequest requestContext, EpsCustomsDetail dto, int page, int pageSize);

    /**
     * 微信端明细查询逻辑
     *
     * @param requestContext
     * @param dto
     * @param page
     * @param pageSize
     * @return
     */
    List<EpsCustomsDetail> selectDetailListForWechat(IRequest requestContext, EpsCustomsDetail dto, int page, int pageSize);

    /**
     * 门户Excel导出方法
     *
     * @param requestContext
     * @param dto
     * @param response
     */
    void exportExcelForPortal(IRequest requestContext, EpsCustomsDetail dto, HttpServletResponse response) throws Exception;

    /**
     * Excel导入
     *
     * @param request
     * @param requestContext
     * @param workbook
     */
    String importExcel(HttpServletRequest request, IRequest requestContext, Workbook workbook) throws Exception;

    /**
     * 自动导入job
     *
     * @param iRequest
     * @param startDate
     * @param record
     */
    String autoImportExcel(IRequest iRequest, Date startDate, SysJobRecord record) throws Exception;

    /**
     * excel根据条件导出数据
     *
     * @param iRequest
     * @param epsCustomsDetail
     * @return
     */
    void exportExcel(IRequest iRequest, EpsCustomsDetail epsCustomsDetail, HttpServletResponse response) throws Exception;

    /**
     * 海关数据查询Service
     *
     * @param requestContext
     * @param dto
     * @param page
     * @param pageSize
     * @return
     */
    ResponseData customsDataQuery(IRequest requestContext, EpsCustomsDetail dto, int page, int pageSize) throws IOException, ParseException;

    /**
     * 判断用户是否有下载权限
     * @param requestContext
     * @return
     */
    ResponseData exportHasAuthorized(IRequest requestContext);

    /**
     * 自定义导出
     * @param requestContext
     * @param epsCustomsDetail
     * @param response
     * @throws Exception
     */
    void exportExcelForSelf(IRequest requestContext, EpsCustomsDetail epsCustomsDetail, HttpServletResponse response) throws Exception;
}
