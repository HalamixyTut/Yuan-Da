package hcux.doc.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hcux.core.exception.HcuxException;
import hcux.core.util.AutoUpperUtil;
import hcux.doc.dto.DocSynthesis;
import hcux.doc.service.IDocSynthesisService;
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
public class DocSynthesisController extends BaseController {

    @Autowired
    private IDocSynthesisService service;


    @RequestMapping(value = "/hcux/doc/synthesis/query")
    @ResponseBody
    public ResponseData query(@RequestBody DocSynthesis dto, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectList(requestContext, dto, dto.getPage(), dto.getPagesize()));
    }

    @RequestMapping(value = "/hcux/doc/synthesis/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<DocSynthesis> dto, BindingResult result, HttpServletRequest request) throws HcuxException {
       /* getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }*/
        // 自动转大写
        AutoUpperUtil.autoUpper(dto);

        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.updateSynthesis(requestCtx, dto));
    }

    @RequestMapping(value = "/hcux/doc/synthesis/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<DocSynthesis> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
}