package hcux.cs.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hcux.cs.dto.CsWarehouseLink;
import hcux.cs.service.ICsWarehouseLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CsWarehouseLinkController extends BaseController {

    @Autowired
    private ICsWarehouseLinkService service;

    /**
     * 中台页面查询
     *
     * @param dto
     * @param request
     * @return
     */
    @RequestMapping(value = {"/hcux/cs/warehouseLink/query"})
    @ResponseBody
    public ResponseData query(CsWarehouseLink dto, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.queryCsWarehouseLink(requestContext, dto));
    }

}
