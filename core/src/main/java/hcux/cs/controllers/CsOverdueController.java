package hcux.cs.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hcux.cs.dto.CsOverdue;
import hcux.cs.service.ICsOverdueService;
import hcux.sys.mapper.SysPlateSectionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CsOverdueController extends BaseController {

    @Autowired
    private ICsOverdueService service;
    @Autowired
    private SysPlateSectionMapper sysPlateSectionMapper;


    @RequestMapping(value = {"/hcux/cs/overdue/query", "/api/hcux/cs/overdue/query"})
    @ResponseBody
    public ResponseData query(CsOverdue dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);

        List<String> sections = sysPlateSectionMapper.queryPlateSection(requestContext.getUserId());
        dto.setSectionList(sections);

        List<CsOverdue> csOverdueList = service.selectListsFromHap(requestContext, dto, page, pageSize);
        return new ResponseData(csOverdueList);
    }

    @RequestMapping(value = {"/hcux/cs/overdue/querySum", "/api/hcux/cs/overdue/querySum"})
    @ResponseBody
    public ResponseData querySum(CsOverdue dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                 @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);

        List<String> sections = sysPlateSectionMapper.queryPlateSection(requestContext.getUserId());
        dto.setSectionList(sections);

        CsOverdue csOverdue = service.selectSumLockAmountFromHap(requestContext, dto);
        List<CsOverdue> list = new ArrayList<>();
        list.add(csOverdue);

        return new ResponseData(list);
    }

    //获取最新数据的查询
    @RequestMapping(value = {"/hcux/cs/overdue/latest/query", "/api/hcux/cs/overdue/latest/query"})
    @ResponseBody
    public ResponseData latestQuery(CsOverdue dto, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        dto.setAccountNumber(requestContext.getUserName());
        service.updateHapData(requestContext, dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/hcux/cs/overdue/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<CsOverdue> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/hcux/cs/overdue/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<CsOverdue> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = {"/hcux/cs/overdue/test", "/api/hcux/cs/overdue/test"})
    @ResponseBody
    public ResponseData excelUpload(@RequestParam MultipartFile file, HttpServletRequest request) throws Exception {
        IRequest requestCtx = createRequestContext(request);

        service.test(requestCtx, file);
        return new ResponseData();
    }
}