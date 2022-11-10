package hcux.eps.controllers;

import hcux.core.util.HcuxConstant;
import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import hcux.eps.dto.EpsCashFlow;
import hcux.eps.service.IEpsCashFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindingResult;

import java.util.List;

@Controller
public class EpsCashFlowController extends BaseController {

    @Autowired
    private IEpsCashFlowService service;


    @RequestMapping(value = "/hcux/eps/cash/flow/query")
    @ResponseBody
    public ResponseData query(EpsCashFlow dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
      //  service.ebsDataToHap(requestContext);
        return new ResponseData(service.selectList(requestContext, dto, page, pageSize));
    }

    /**
     * 门户查询接口
     *
     * @param dto
     * @param request
     * @param page
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/api/hcux/eps/cash/flow/portal/query")
    @ResponseBody
    public ResponseData queryForPortal(EpsCashFlow dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                       @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        dto.setQueryType(HcuxConstant.QUERY_TYPE.PORTAL);
        return new ResponseData(service.selectList(requestContext, dto, page, pageSize));
    }

    /**
     * 微信查询接口
     *
     * @param dto
     * @param request
     * @param page
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/api/hcux/eps/cash/flow/wechat/query")
    @ResponseBody
    public ResponseData queryForWechat(EpsCashFlow dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                       @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        dto.setQueryType(HcuxConstant.QUERY_TYPE.WECHAT);
        return new ResponseData(service.selectList(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/hcux/eps/cash/flow/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<EpsCashFlow> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/hcux/eps/cash/flow/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<EpsCashFlow> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/hcux/eps/cash/flow/exportExcel")
    @ResponseBody
    public void exportExcel(EpsCashFlow dto, HttpServletRequest request, HttpServletResponse response) throws Exception{
        IRequest iRequest = createRequestContext(request);
        service.exportExcel(iRequest,dto,response);
    }

    @RequestMapping(value = "/api/hcux/eps/cash/flow/exportExcel")
    @ResponseBody
    public void exportExcelForPortal(EpsCashFlow dto, HttpServletRequest request, HttpServletResponse response) throws Exception{
        IRequest iRequest = createRequestContext(request);
        dto.setQueryType(HcuxConstant.QUERY_TYPE.PORTAL);
        service.exportExcelForPortal(iRequest,dto,response);
    }
}