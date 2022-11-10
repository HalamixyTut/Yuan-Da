package hcux.mdm.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hcux.mdm.dto.MdmInventoryAddress;
import hcux.mdm.service.IMdmInventoryAddressService;
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
 * @author haojie.zhang@hand-china.com
 * @description 仓库地址维护
 */
@Controller
public class MdmInventoryAddressController extends BaseController {

    @Autowired
    private IMdmInventoryAddressService service;


    @RequestMapping(value = "/hcux/mdm/inventory/address/query")
    @ResponseBody
    public ResponseData query(MdmInventoryAddress dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectList(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/hcux/mdm/inventory/address/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<MdmInventoryAddress> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/hcux/mdm/inventory/address/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<MdmInventoryAddress> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
}