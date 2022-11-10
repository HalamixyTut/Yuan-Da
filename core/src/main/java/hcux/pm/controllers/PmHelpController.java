package hcux.pm.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.dto.ResponseData;
import hcux.pm.dto.PmHelp;
import hcux.pm.service.IPmHelpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author haojie.zhang@hand-china.com
 * @description 使用帮助
 */
@Controller
public class PmHelpController extends BaseController {

    @Autowired
    private IPmHelpService service;


    @RequestMapping(value = "/hcux/pm/help/query")
    @ResponseBody
    public ResponseData query(PmHelp dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectLists(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/hcux/pm/help/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<PmHelp> dto, BindingResult result, HttpServletRequest request) {
//        getValidator().validate(dto, result);
//        if (result.hasErrors()) {
//            ResponseData responseData = new ResponseData(false);
//            responseData.setMessage(getErrorMessage(result, request));
//            return responseData;
//        }
        IRequest requestCtx = createRequestContext(request);
        if (dto.get(0).getHelpId() != null) {
            dto.get(0).set__status(DTOStatus.UPDATE);
            dto.get(0).setLastUpdateDate(new Date());
            dto.get(0).setDeleteFlag("N");
            service.updateByPrimaryKey(requestCtx,dto.get(0));
            return new ResponseData(dto);
        } else {
            service.insertSelective(requestCtx,dto.get(0));
            return new ResponseData(dto);
        }
    }

    @RequestMapping(value = "/hcux/pm/help/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<PmHelp> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/hcux/pm/help/detail/query")
    @ResponseBody
    public ResponseData queryOne(PmHelp dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                  @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        List<PmHelp> pmHelps = new ArrayList<>();
        pmHelps.add(service.selectHelp(requestContext, dto));
        return new ResponseData(pmHelps);
    }

    @RequestMapping(value = "/hcux/pm/help/queryEffective")
    @ResponseBody
    public ResponseData queryEffective(PmHelp dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectEffective(requestContext,dto));
    }

    @RequestMapping(value = "/api/hcux/pm/help/detail/query")
    @ResponseBody
    public ResponseData queryPortal(PmHelp dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                 @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        List<PmHelp> pmHelps = new ArrayList<>();
        pmHelps.add(service.selectEffectiveForNH(requestContext, dto));
        return new ResponseData(pmHelps);
    }
}