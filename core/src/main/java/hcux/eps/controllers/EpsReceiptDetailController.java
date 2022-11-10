package hcux.eps.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hcux.core.util.HcuxConstant;
import hcux.eps.dto.EpsReceiptDetail;
import hcux.eps.service.IEpsReceiptDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class EpsReceiptDetailController extends BaseController {

    @Autowired
    private IEpsReceiptDetailService service;


    @RequestMapping(value = {"/hcux/eps/receipt/detail/query"})
    @ResponseBody
    public ResponseData query(EpsReceiptDetail dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryEpsReceiptDetail(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = {"/api/hcux/eps/receipt/detail/query"})
    @ResponseBody
    public ResponseData queryForPortal(EpsReceiptDetail dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                       @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        dto.setQueryType(HcuxConstant.QUERY_TYPE.PORTAL);
        return new ResponseData(service.queryEpsReceiptDetail(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = {"/hcux/eps/receipt/detail/exportExcel"})
    @ResponseBody
    public void exportExcel(EpsReceiptDetail dto, HttpServletRequest request, HttpServletResponse response) throws Exception {
        IRequest requestContext = createRequestContext(request);
        service.exportExcel(requestContext, dto, response);
    }

    @RequestMapping(value = {"/api/hcux/eps/receipt/detail/exportExcel"})
    @ResponseBody
    public void exportExcelForPortal(EpsReceiptDetail dto, HttpServletRequest request, HttpServletResponse response) throws Exception {
        IRequest requestContext = createRequestContext(request);
        dto.setQueryType(HcuxConstant.QUERY_TYPE.PORTAL);
        service.exportExcel(requestContext, dto, response);
    }

    @RequestMapping(value = {"/api/hcux/eps/receipt/detail/query/total"})
    @ResponseBody
    public ResponseData queryTotal(EpsReceiptDetail dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                   @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        dto.setQueryType(HcuxConstant.QUERY_TYPE.PORTAL);
        return new ResponseData(service.selectTotalAmount(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/hcux/eps/receipt/detail/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<EpsReceiptDetail> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/hcux/eps/receipt/detail/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<EpsReceiptDetail> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/hcux/eps/receipt/detail/test")
    @ResponseBody
    public ResponseData test(HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        service.epsReceiptDetailToHap(requestCtx, new EpsReceiptDetail());
        return new ResponseData();
    }
}