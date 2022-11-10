package hcux.mdm.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.dto.ResponseData;
import hcux.core.util.HcuxConstant;
import hcux.mdm.dto.MdmCustomerAddress;
import hcux.mdm.service.IMdmCustomerAddressService;
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
 * @description 客户收件人地址
 */
@Controller
public class MdmCustomerAddressController extends BaseController {

    @Autowired
    private IMdmCustomerAddressService service;


    @RequestMapping(value = {"/hcux/mdm/customer/address/query"})
    @ResponseBody
    public ResponseData query(MdmCustomerAddress dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        dto.setQueryType(HcuxConstant.QUERY_TYPE.HAP);
        return new ResponseData(service.selectData(requestContext, dto, page, pageSize));
    }

    /**
     * 门户查询，权限不同
     *
     * @param dto
     * @param page
     * @param pageSize
     * @param request
     * @return
     */
    @RequestMapping(value = {"/api/hcux/mdm/customer/address/query"})
    @ResponseBody
    public ResponseData queryForPortal(MdmCustomerAddress dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                       @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        dto.setQueryType(HcuxConstant.QUERY_TYPE.PORTAL);
        return new ResponseData(service.selectData(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = {"/hcux/mdm/customer/address/submit", "/api/hcux/mdm/customer/address/submit"})
    @ResponseBody
    public ResponseData update(@RequestBody List<MdmCustomerAddress> dto, BindingResult result, HttpServletRequest request) {
       /* getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }*/
        IRequest requestCtx = createRequestContext(request);
        dto.forEach(x->{
            if(x.getAddressId()!=null){
        x.set__status(DTOStatus.UPDATE);
        }else{
                x.set__status(DTOStatus.ADD);
            }
        });
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = {"/hcux/mdm/customer/address/remove", "/api/hcux/mdm/customer/address/remove"})
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<MdmCustomerAddress> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     * 门户更新接口
     *
     * @param dto
     * @param result
     * @param request
     * @return
     */
    @RequestMapping(value = {"/hcux/mdm/customer/address/portal/submit", "/api/hcux/mdm/customer/address/portal/submit"})
    @ResponseBody
    public ResponseData updatePortal(@RequestBody List<MdmCustomerAddress> dto, BindingResult result, HttpServletRequest request) {
        dto.forEach(x -> {
            if (x.getAddressId() != null) {
                x.set__status(DTOStatus.UPDATE);
            } else {
                x.set__status(DTOStatus.ADD);
            }
        });
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }
}