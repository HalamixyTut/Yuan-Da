package hcux.mdm.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.exception.BaseException;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hcux.core.util.HcuxConstant;
import hcux.mdm.dto.MdmBillingInfo;
import hcux.mdm.service.IMdmBillingInfoService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * @description 客户开票资料
 */
@Controller
public class MdmBillingInfoController extends BaseController {

    @Autowired
    private IMdmBillingInfoService service;

    private static final Logger logger = LoggerFactory.getLogger(MdmBillingInfoController.class);

    @RequestMapping(value = {"/hcux/mdm/billing/info/query"})
    @ResponseBody
    public ResponseData query(MdmBillingInfo dto, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        if (StringUtils.isBlank(dto.getCustomerName())) {
            return new ResponseData();
        }
        return new ResponseData(service.selectMdmBillingInfo(requestContext, dto, 0, 0));
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
    @RequestMapping(value = {"/api/hcux/mdm/billing/info/query"})
    @ResponseBody
    public ResponseData queryForPortal(MdmBillingInfo dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                       @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest iRequest = createRequestContext(request);
        dto.setQueryType(HcuxConstant.QUERY_TYPE.PORTAL);
        ResponseData rd = new ResponseData();
        List<MdmBillingInfo> list = service.selectMdmBillingInfo(iRequest, dto, 0, 0);
        rd.setRows(list);
        rd.setTotal((long) list.size());
        return rd;
    }

    @RequestMapping(value = {"/hcux/mdm/billing/info/submit"})
    @ResponseBody
    public ResponseData update(@RequestBody List<MdmBillingInfo> dto, BindingResult result, HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/hcux/mdm/billing/info/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<MdmBillingInfo> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = {"/api/hcux/mdm/billing/info/portal/submit"})
    @ResponseBody
    public ResponseData updatePortal(@RequestBody List<MdmBillingInfo> dto, BindingResult result, HttpServletRequest request) throws BaseException {
        IRequest requestCtx = createRequestContext(request);
        service.mdmBillingInfoBatchUpdate(requestCtx, dto);
        return new ResponseData();
    }
}