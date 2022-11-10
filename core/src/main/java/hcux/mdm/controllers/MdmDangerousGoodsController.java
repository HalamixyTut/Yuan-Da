package hcux.mdm.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hcux.mdm.dto.MdmDangerousGoods;
import hcux.mdm.service.IMdmDangerousGoodsService;
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
public class MdmDangerousGoodsController extends BaseController {

    @Autowired
    private IMdmDangerousGoodsService service;

    @RequestMapping(value = "/hcux/mdm/dangerousgoods/query")
    @ResponseBody
    public ResponseData query(MdmDangerousGoods dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectList(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/hcux/mdm/dangerousgoods/queryEnabled")
    @ResponseBody
    public ResponseData queryEnabled(MdmDangerousGoods dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectEnabled(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/hcux/mdm/dangerousgoods/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<MdmDangerousGoods> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return service.saveAndUpdate(requestCtx, dto);
    }

    @RequestMapping(value = "/hcux/mdm/dangerousgoods/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<MdmDangerousGoods> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

}
