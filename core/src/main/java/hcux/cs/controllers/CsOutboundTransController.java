package hcux.cs.controllers;

import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import hcux.cs.dto.CsOutboundTrans;
import hcux.cs.service.ICsOutboundTransService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindingResult;

import java.util.List;

@Controller
public class CsOutboundTransController extends BaseController {

    @Autowired
    private ICsOutboundTransService service;


    @RequestMapping(value = {"/hcux/cs/outbound/trans/query", "/api/hcux/cs/outbound/trans/query"})
    @ResponseBody
    public ResponseData query(CsOutboundTrans dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectData(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/hcux/cs/outbound/trans/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<CsOutboundTrans> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/hcux/cs/outbound/trans/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<CsOutboundTrans> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
}