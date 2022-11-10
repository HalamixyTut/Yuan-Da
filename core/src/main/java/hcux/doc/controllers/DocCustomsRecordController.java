package hcux.doc.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hcux.doc.dto.DocCustomsRecord;
import hcux.doc.service.IDocCustomsRecordService;
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
 * @description 报关单操作记录
 */
@Controller
public class DocCustomsRecordController extends BaseController {

    @Autowired
    private IDocCustomsRecordService service;


    @RequestMapping(value = "/hcux/doc/customs/record/query")
    @ResponseBody
    public ResponseData query(DocCustomsRecord dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectList(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/hcux/doc/customs/record/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<DocCustomsRecord> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/hcux/doc/customs/record/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<DocCustomsRecord> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
}