package hcux.cs.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.dto.ResponseData;
import hcux.cs.dto.CsTransportInfo;
import hcux.cs.service.ICsTransportInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CsTransportInfoController extends BaseController {

    @Autowired
    private ICsTransportInfoService service;


    @RequestMapping(value = {"/hcux/cs/transport/info/query", "/api/hcux/cs/transport/info/query"})
    @ResponseBody
    public ResponseData query(CsTransportInfo dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectLists(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = {"/hcux/cs/transport/info/submit", "/api/hcux/cs/transport/info/submit"})
    @ResponseBody
    public ResponseData update(@RequestBody List<CsTransportInfo> dto, BindingResult result, HttpServletRequest request) throws Exception {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdateTransport(requestCtx, dto));
    }

    @RequestMapping(value = {"/hcux/cs/transport/info/remove", "/api/hcux/cs/transport/info/remove"})
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<CsTransportInfo> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = {"/hcux/cs/transport/info/item/code/query", "/api/hcux/cs/transport/info/item/code/query"})
    @ResponseBody
    public ResponseData itemCodeQuery(CsTransportInfo dto, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectListByCustPoNumber(dto));
    }
}