package hcux.lm.controllers;

import hcux.core.exception.HcuxException;
import hcux.lm.dto.ZhTransport;
import org.apache.wss4j.dom.handler.RequestData;
import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import hcux.lm.dto.LmTransport;
import hcux.lm.service.ILmTransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindingResult;

import java.util.List;

@Controller
public class LmTransportController extends BaseController {

    @Autowired
    private ILmTransportService service;


    @RequestMapping(value = "/hcux/lm/transport/query")
    @ResponseBody
    public ResponseData query(LmTransport dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryTransport(requestContext, dto, page, pageSize));
    }

    /**
     * 门户查询运输单接口
     *
     * @param request
     * @param dto
     * @return
     */
    @RequestMapping(value = "/api/hcux/lm/transport/query")
    @ResponseBody
    public ResponseData queryForPortal(LmTransport dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                       @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);

        return new ResponseData(service.queryForPortal(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/hcux/lm/transport/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<LmTransport> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/hcux/lm/transport/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<LmTransport> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     * 运单查询接口-补偿机制
     *
     * @param request
     * @param dto
     * @return
     */
    @RequestMapping(value = "/hcux/lm/order/header/queryFromZh")
    @ResponseBody
    public ResponseData queryFromZh (HttpServletRequest request, @RequestBody ZhTransport dto) throws Exception {
        IRequest requestCtx = createRequestContext(request);
        return service.queryFromZh(requestCtx, dto);
    }
}