package hcux.lm.controllers;

import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import hcux.lm.dto.LmOrderLine;
import hcux.lm.service.ILmOrderLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindingResult;

import java.util.List;

@Controller
public class LmOrderLineController extends BaseController {

    @Autowired
    private ILmOrderLineService service;


    @RequestMapping(value = "/hcux/lm/order/line/query")
    @ResponseBody
    public ResponseData query(LmOrderLine dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectList(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/hcux/lm/order/line/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<LmOrderLine> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/hcux/lm/order/line/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<LmOrderLine> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
}