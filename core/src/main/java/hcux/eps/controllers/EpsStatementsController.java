package hcux.eps.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.dto.ResponseData;
import hcux.core.util.HcuxConstant;
import hcux.eps.dto.EpsStatements;
import hcux.eps.service.IEpsStatementsService;
import hcux.eps.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;

@Controller
public class EpsStatementsController extends BaseController {
    @Autowired
    private IEpsStatementsService iEpsStatementsService;
    @RequestMapping(value = {"/hcux/eps/statements/query"})
    @ResponseBody
    public ResponseData query(EpsStatements dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(iEpsStatementsService.queryEpsStatements(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = {"/api/hcux/eps/statements/query"})
    @ResponseBody
    public ResponseData queryForPortal(EpsStatements dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                       @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        dto.setQueryType(HcuxConstant.QUERY_TYPE.PORTAL);
        return new ResponseData(iEpsStatementsService.queryEpsStatements(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/hcux/eps/statements/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<EpsStatements> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        dto.forEach(x -> {
                x.set__status(DTOStatus.UPDATE);
                if(x.getBalance1()!=null){
                    x.setBalance((x.getBalance1().subtract(CommonUtil.numberIsNull(x.getExportAdjustment()))).setScale(2, BigDecimal.ROUND_HALF_UP));
                }
                iEpsStatementsService.updateByPrimaryKey(requestCtx,x);
        });

        return new ResponseData(dto);
    }

    @RequestMapping(value = "/hcux/eps/statements/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<EpsStatements> dto) {
        iEpsStatementsService.batchDelete(dto);
        return new ResponseData();
    }
    @RequestMapping(value = {"/hcux/eps/statements/exportExcel"})
    @ResponseBody
    public void exportExcel(EpsStatements dto, HttpServletRequest request, HttpServletResponse response) throws Exception {
        IRequest requestContext = createRequestContext(request);
        iEpsStatementsService.exportExcel(requestContext, dto, response);
    }
    @RequestMapping(value = {"/hcux/eps/statements/read/exportExcel"})
    @ResponseBody
    public void readExportExcel(EpsStatements dto, HttpServletRequest request, HttpServletResponse response) throws Exception {
        IRequest requestContext = createRequestContext(request);
        iEpsStatementsService.readExportExcel(requestContext, dto, response);
    }
//    @RequestMapping(value = {"/hcux/eps/statements/test"})
//    @ResponseBody
//    public ResponseData test(EpsStatements dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
//                             @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
//        Instant startInstant = Instant.now();
//
//        IRequest requestContext = createRequestContext(request);
////        dto.setProjectNum("AD12503CP");
//        List<EpsStatements> epsStatements = iEpsStatementsService.queryEpsStatements(requestContext, dto, 0, 0);
//        Instant endInstant = Instant.now();
//        long seconds = Duration.between(startInstant, endInstant).getSeconds();
//        System.out.println(seconds);
//        return new ResponseData();
//    }
@RequestMapping(value = {"/hcux/eps/statements/only/query"})
@ResponseBody
public ResponseData onlyQuery(EpsStatements dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                          @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
    IRequest requestContext = createRequestContext(request);
    return new ResponseData(iEpsStatementsService.queryEpsStatementsOnly(requestContext, dto, page, pageSize));
}
    @RequestMapping(value = {"/hcux/eps/statements/test"})
    @ResponseBody
    public ResponseData dataTest(EpsStatements dto, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        iEpsStatementsService.epsStatementsToHap(requestContext,dto);
        return new ResponseData();
    }
    @RequestMapping(value = {"/api/hcux/eps/statements/exportExcel"})
    @ResponseBody
    public void portalExportExcel(EpsStatements dto, HttpServletRequest request, HttpServletResponse response) throws Exception {
        IRequest requestContext = createRequestContext(request);
        iEpsStatementsService.exportExcelOuter(requestContext, dto, response);
    }
}
