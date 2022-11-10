package hcux.mdm.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hcux.mdm.dto.MdmProduct;
import hcux.mdm.service.IMdmProductService;
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
public class MdmProductController extends BaseController {

    @Autowired
    private IMdmProductService service;


    @RequestMapping(value = {"/hcux/mdm/product/query", "/api/hcux/mdm/product/query"})
    @ResponseBody
    public ResponseData query(MdmProduct dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/hcux/mdm/product/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<MdmProduct> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/hcux/mdm/product/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<MdmProduct> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
}