package hcux.cs.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.dto.ResponseData;
import com.itextpdf.text.DocumentException;
import hcux.core.exception.HcuxException;
import hcux.core.util.HcuxConstant;
import hcux.cs.dto.CsOutboundOrderFull;
import hcux.cs.service.ICsOutboundOrderFullService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class CsOutboundOrderFullController extends BaseController {

    @Autowired
    private ICsOutboundOrderFullService service;


    @RequestMapping(value = {"/hcux/cs/outbound/order/full/query"})
    @ResponseBody
    public ResponseData query(CsOutboundOrderFull dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        dto.setQueryType(HcuxConstant.QUERY_TYPE.HAP);
        return new ResponseData(service.selectData(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = {"/api/hcux/cs/outbound/order/full/query"})
    @ResponseBody
    public ResponseData queryForPortal(CsOutboundOrderFull dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                       @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        dto.setQueryType(HcuxConstant.QUERY_TYPE.PORTAL);
        return new ResponseData(service.selectData(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = {"/hcux/cs/outbound/order/full/submit", "/api/hcux/cs/outbound/order/full/submit"})
    @ResponseBody
    public ResponseData update(@RequestBody List<CsOutboundOrderFull> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        dto.forEach(x -> {
            if (x.getOrderAllId() != null) {
                x.set__status(DTOStatus.UPDATE);
                service.sendMessage(requestCtx,x);
            }
        });

        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/hcux/cs/outbound/order/full/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<CsOutboundOrderFull> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     * 出库物流打印客户确认收货的数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/hcux/cs/outbound/order/full/pdfPrint")
    @ResponseBody
    public ResponseData pdfPrint(HttpServletRequest request, HttpServletResponse response) throws IOException, HcuxException, DocumentException {
        IRequest requestCtx = createRequestContext(request);
        String invoiceIds = request.getParameter("invoiceIds");
        service.pdfPrint(requestCtx, invoiceIds, request, response);
        return new ResponseData();
    }
}