package hcux.cs.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hcux.cs.dto.CsCredit;
import hcux.cs.service.ICsCreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CsCreditController extends BaseController {
    @Autowired
    private ICsCreditService service;

    @RequestMapping(value = {"/hcux/cs/credit/query", "/api/hcux/cs/credit/query"})
    @ResponseBody
    public ResponseData query(CsCredit dto, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        dto.setHeaderName(requestContext.getUserName());
        return new ResponseData(service.selectData(requestContext, dto));
    }
}
