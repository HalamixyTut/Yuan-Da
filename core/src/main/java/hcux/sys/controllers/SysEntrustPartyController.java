package hcux.sys.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hcux.sys.dto.SysEntrustParty;
import hcux.sys.service.ISysEntrustPartyService;
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

/**
 * @author feng.liu01@hand-china.com
 * @description 委托方用户
 */
@Controller
public class SysEntrustPartyController extends BaseController {

    @Autowired
    private ISysEntrustPartyService service;


    @RequestMapping(value = "/hcux/sys/entrust/party/query")
    @ResponseBody
    public ResponseData query(SysEntrustParty dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectList(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/hcux/sys/entrust/party/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<SysEntrustParty> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/hcux/sys/entrust/party/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<SysEntrustParty> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
    @RequestMapping(value = {"/hcux/sys/entrust/party/total/query","/api/hcux/sys/entrust/party/total/query"})
    @ResponseBody
    public ResponseData queryTotalMinAgencyFee(SysEntrustParty dto,  HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        List<SysEntrustParty> sysEntrustPartyList=new ArrayList<>();
        dto.setUserId(requestContext.getUserId());
        sysEntrustPartyList.add(service.selectTotalMinAgencyFee(requestContext,dto));
        return new ResponseData(sysEntrustPartyList);
    }
    @RequestMapping(value = "/hcux/sys/entrust/party/total/without/user/query")
    @ResponseBody
    public ResponseData queryTotalWithoutUser(SysEntrustParty dto,  HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        List<SysEntrustParty> sysEntrustPartyList=new ArrayList<>();
        sysEntrustPartyList.add(service.selectTotalWithoutUser(requestContext,dto));
        return new ResponseData(sysEntrustPartyList);
    }
    @RequestMapping(value = "/api/hcux/sys/entrust/party/statement/flag/query")
    @ResponseBody
    public ResponseData querystatementFlag(SysEntrustParty dto,  HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectStatementFlag(requestContext,dto));
    }
}