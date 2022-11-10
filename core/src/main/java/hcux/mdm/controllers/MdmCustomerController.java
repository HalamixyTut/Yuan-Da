package hcux.mdm.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hcux.core.util.AutoUpperUtil;
import hcux.mdm.dto.MdmCustomer;
import hcux.mdm.service.IMdmCustomerService;
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
 * @description 客户数据
 */
@Controller
public class MdmCustomerController extends BaseController {

    @Autowired
    private IMdmCustomerService service;


    @RequestMapping(value = "/hcux/mdm/customer/query")
    @ResponseBody
    public ResponseData query(MdmCustomer dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectList(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/hcux/mdm/customer/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<MdmCustomer> list, BindingResult result, HttpServletRequest request) throws Exception {
        getValidator().validate(list, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }

        // 自动转大写
        AutoUpperUtil.autoUpper(list);

        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdateCustomer(requestCtx, list));
    }

    @RequestMapping(value = "/hcux/mdm/customer/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<MdmCustomer> list) {
//        service.batchDelete(list);
        IRequest requestCtx = createRequestContext(request);
        service.deleteData(requestCtx, list);
        return new ResponseData();
    }
}