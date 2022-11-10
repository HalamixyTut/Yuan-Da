package hcux.sys.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hcux.sys.dto.SysJobRecord;
import hcux.sys.service.ISysJobRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author feng.liu01@hand-china.com
 * @description 增量JOB运行记录表
 */
@Controller
public class SysJobRecordController extends BaseController {

    @Autowired
    private ISysJobRecordService service;


    @RequestMapping(value = "/hcux/sys/job/record/query")
    @ResponseBody
    public ResponseData query(SysJobRecord dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/hcux/sys/job/record/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<SysJobRecord> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/hcux/sys/job/record/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<SysJobRecord> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
}