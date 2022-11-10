package hcux.cs.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hcux.core.util.HcuxConstant;
import hcux.cs.dto.CsInvoiceExpress;
import hcux.cs.service.ICsInvoiceExpressService;
import hcux.doc.util.CommonUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author feng.liu01@hand-china.com
 * @description 发票快递单号
 */
@Controller
public class CsInvoiceExpressController extends BaseController {

    @Autowired
    private ICsInvoiceExpressService service;
    @Autowired
    private MessageSource messageSource;


    @RequestMapping(value = {"/hcux/cs/invoice/express/query"})
    @ResponseBody
    public ResponseData query(CsInvoiceExpress dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        dto.setQueryType(HcuxConstant.QUERY_TYPE.HAP);
        return new ResponseData(service.selectData(requestContext, dto, page, pageSize));
    }

    /**
     * 门户查询，权限不同
     *
     * @param dto
     * @param page
     * @param pageSize
     * @param request
     * @return
     */
    @RequestMapping(value = {"/api/hcux/cs/invoice/express/query"})
    @ResponseBody
    public ResponseData queryForPortal(CsInvoiceExpress dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                       @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        dto.setQueryType(HcuxConstant.QUERY_TYPE.PORTAL);
        return new ResponseData(service.selectData(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/hcux/cs/invoice/express/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<CsInvoiceExpress> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/hcux/cs/invoice/express/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<CsInvoiceExpress> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     * 查询用户可选择的板块
     *
     * @param request
     * @return
     */
    @RequestMapping(value = {"/hcux/cs/invoice/express/queryPlate", "/api/hcux/cs/invoice/express/queryPlate"})
    @ResponseBody
    public ResponseData queryPlate(HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryPlate(requestContext));
    }

    /**
     * 发票快递单号导入
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/hcux/cs/invoice/express/excel/import")
    @ResponseBody
    public ResponseData importExcel(@RequestParam MultipartFile file, HttpServletRequest request) throws Exception {
        IRequest requestContext = createRequestContext(request);

        ResponseData responseData = new ResponseData();
        Workbook workbook = null;
        try {
            workbook = CommonUtil.fileType(file);//根据excel的不同版本创建不同的workbook
        } catch (Exception e) {
            responseData.setSuccess(false);
            responseData.setMessage(messageSource.getMessage(e.getMessage(), null, RequestContextUtils.getLocale(request)));
        }

        String errorMessage = service.importExcel(request, requestContext, workbook);
        if (!CommonUtil.objectIsNull(errorMessage)) {
            responseData.setMessage(errorMessage);
        }

        return responseData;
    }
}