package hcux.cs.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.dto.CodeValue;
import com.hand.hap.system.service.IBaseService;
import hcux.cs.dto.CsInvoiceExpress;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author feng.liu01@hand-china.com
 * @description 发票快递单号
 */
public interface ICsInvoiceExpressService extends IBaseService<CsInvoiceExpress>, ProxySelf<ICsInvoiceExpressService> {

    /**
     * 关联查询
     *
     * @param requestContext
     * @param dto
     * @param page
     * @param pageSize
     * @return
     */
    List<CsInvoiceExpress> selectData(IRequest requestContext, CsInvoiceExpress dto, int page, int pageSize);

    /**
     * 查询用户可选择的板块
     *
     * @param requestContext
     * @return
     */
    List<CodeValue> queryPlate(IRequest requestContext);

    /**
     * Excel导入
     *
     * @param request
     * @param requestContext
     * @param workbook
     */
    String importExcel(HttpServletRequest request, IRequest requestContext, Workbook workbook) throws Exception;
}