package hcux.doc.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hcux.core.exception.HcuxException;
import hcux.core.util.AutoUpperUtil;
import hcux.doc.dto.DocCustomsLine;
import hcux.doc.service.IDocCustomsLineService;
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
 * @description 报关单行表
 */
@Controller
public class DocCustomsLineController extends BaseController {

    @Autowired
    private IDocCustomsLineService service;


    @RequestMapping(value = "/hcux/doc/customs/line/query")
    @ResponseBody
    public ResponseData query(DocCustomsLine dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectList(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/hcux/doc/customs/line/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<DocCustomsLine> dto, BindingResult result, HttpServletRequest request) throws HcuxException {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }

        // 自动转大写
        AutoUpperUtil.autoUpper(dto);

        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/hcux/doc/customs/line/copyRow")
    @ResponseBody
    public ResponseData copyRow(@RequestBody DocCustomsLine dto, HttpServletRequest request) {

        IRequest requestCtx = createRequestContext(request);
        service.copyRow(requestCtx, dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/hcux/doc/customs/line/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<DocCustomsLine> dto) {
        IRequest requestContext = createRequestContext(request);
        service.batchDeleteLine(requestContext, dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/hcux/doc/customs/line/queryall")
    @ResponseBody
    public ResponseData queryAll(DocCustomsLine dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                 @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryAll(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/hcux/doc/customs/line/copy")
    @ResponseBody
    public ResponseData lineCopy(@RequestBody List<DocCustomsLine> dto, HttpServletRequest request, @RequestParam(required = false) String customsLineId) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.copyDocCustomsLine(requestCtx, dto, customsLineId));
    }
}