package hcux.lm.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hcux.core.exception.HcuxException;
import hcux.lm.dto.LmOrderHeader;
import hcux.lm.service.ILmOrderHeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LmOrderHeaderController extends BaseController {

    @Autowired
    private ILmOrderHeaderService service;

    @RequestMapping(value = "/hcux/lm/order/header/query")
    @ResponseBody
    public ResponseData query(LmOrderHeader dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectLists(requestContext, dto, page, pageSize));
    }

    /**
     * 编辑详情页面时查询
     *
     * @param dto
     * @param request
     * @return
     */
    @RequestMapping(value = "/hcux/lm/order/header/queryOne")
    @ResponseBody
    public ResponseData queryOne(LmOrderHeader dto, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        LmOrderHeader header = service.selectOne(requestContext, dto);
        List<LmOrderHeader> list = new ArrayList<>();
        list.add(header);
        return new ResponseData(list);
    }

    @RequestMapping(value = "/hcux/lm/order/header/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<LmOrderHeader> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdateHeaderAndLine(requestCtx, dto));
    }

    @RequestMapping(value = "/hcux/lm/order/header/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<LmOrderHeader> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }


    /**
     * 提交到中化66快车平台
     *
     * @param request
     * @param dto
     * @return
     */
    @RequestMapping(value = "/hcux/lm/order/header/submitToZh")
    @ResponseBody
    public ResponseData submitToZh(HttpServletRequest request, @RequestBody LmOrderHeader dto) throws HcuxException {
        IRequest requestCtx = createRequestContext(request);
        return service.submitToZh(requestCtx, dto);
    }
}