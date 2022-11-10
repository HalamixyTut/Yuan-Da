package hcux.mdm.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hcux.mdm.dto.MdmCarNumber;
import hcux.mdm.service.IMdmCarNumberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class MdmCarNumberController extends BaseController {
    @Autowired
    private IMdmCarNumberService service;

    private static final Logger logger = LoggerFactory.getLogger(MdmCarNumberController.class);

    @RequestMapping(value = {"/hcux/mdm/car/number/query"})
    @ResponseBody
    public ResponseData query(MdmCarNumber dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectData(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = {"/hcux/mdm/car/number/exportExcel"})
    @ResponseBody
    public void readExportExcel(MdmCarNumber dto, HttpServletRequest request, HttpServletResponse response) throws Exception {
        IRequest requestContext = createRequestContext(request);
        service.exportExcel(requestContext, dto, response);
    }
}
