package hcux.cs.controllers;

import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import hcux.cs.dto.CsContractVat;
import hcux.cs.service.ICsContractVatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindingResult;

import java.util.List;

@Controller
public class CsContractVatController extends BaseController {

    @Autowired
    private ICsContractVatService service;


    @RequestMapping(value = "/hcux/cs/contract/vat/query")
    @ResponseBody
    public ResponseData query(CsContractVat dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/hcux/cs/contract/vat/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<CsContractVat> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/hcux/cs/contract/vat/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<CsContractVat> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/hcux/cs/contract/vat/test")
    @ResponseBody
    public ResponseData test(HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        service.epsCsContractVatToHap(requestCtx, new CsContractVat());
        return new ResponseData();
    }
}