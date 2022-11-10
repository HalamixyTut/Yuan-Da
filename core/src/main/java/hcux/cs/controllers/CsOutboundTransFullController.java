package hcux.cs.controllers;

import hcux.core.util.HcuxConstant;
import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import hcux.cs.dto.CsOutboundTransFull;
import hcux.cs.service.ICsOutboundTransFullService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindingResult;

import java.util.List;

@Controller
public class CsOutboundTransFullController extends BaseController {

    @Autowired
    private ICsOutboundTransFullService service;


    @RequestMapping(value = {"/hcux/cs/outbound/trans/full/query", "/api/hcux/cs/outbound/trans/full/query"})
    @ResponseBody
    public ResponseData query(CsOutboundTransFull dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectData(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/hcux/cs/outbound/trans/full/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<CsOutboundTransFull> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/hcux/cs/outbound/trans/full/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<CsOutboundTransFull> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     * 微信出库查询接口
     * @param dto
     * @param page
     * @param pageSize
     * @param request
     * @return
     */
    @RequestMapping(value = {"/api/hcux/cs/outbound/trans/full/wechat/query"})
    @ResponseBody
    public ResponseData wechatTransFullquery(CsOutboundTransFull dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize,HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        dto.setQueryType(HcuxConstant.QUERY_TYPE.PORTAL);
        return new ResponseData(service.selectTransFullForWechat(requestContext, dto, page, pageSize));
    }
}