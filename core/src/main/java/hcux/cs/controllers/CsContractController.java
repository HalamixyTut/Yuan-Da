package hcux.cs.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hcux.core.util.HcuxConstant;
import hcux.cs.dto.CsContract;
import hcux.cs.service.ICsContractService;
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
public class CsContractController extends BaseController {

    @Autowired
    private ICsContractService service;


    /**
     * 中台页面查询
     *
     * @param dto
     * @param page
     * @param pageSize
     * @param request
     * @return
     */
    @RequestMapping(value = {"/hcux/cs/contract/query"})
    @ResponseBody
    public ResponseData query(CsContract dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        dto.setQueryType(HcuxConstant.QUERY_TYPE.HAP);
        return new ResponseData(service.queryCsContract(requestContext, dto, page, pageSize));
    }

    /**
     * 门户查询，权限不同
     *
     * @param dto
     * @param page
     * @param pageSize
     * @param request
     * @return
     */
    @RequestMapping(value = {"/api/hcux/cs/contract/query"})
    @ResponseBody
    public ResponseData queryForPortal(CsContract dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                       @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        dto.setQueryType(HcuxConstant.QUERY_TYPE.PORTAL);
        return new ResponseData(service.queryCsContract(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/hcux/cs/contract/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<CsContract> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/hcux/cs/contract/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<CsContract> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
       /* @RequestMapping(value = "/hcux/cs/contract/test")
        @ResponseBody
        public ResponseData test(HttpServletRequest request){
            IRequest requestCtx = createRequestContext(request);
            //service.epsCsContractToHap(requestCtx,new CsContract());
            service.test(requestCtx);
            return new ResponseData();
        }*/
}