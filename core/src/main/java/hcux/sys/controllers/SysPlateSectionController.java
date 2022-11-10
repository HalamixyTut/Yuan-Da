package hcux.sys.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.WhereField;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hcux.sys.dto.SysPlateSection;
import hcux.sys.service.ISysPlateSectionService;
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
 * @description 板块部门维护
 */
@Controller
public class SysPlateSectionController extends BaseController {

    @Autowired
    private ISysPlateSectionService service;


    @RequestMapping(value = "/hcux/sys/plate/section/query")
    @ResponseBody
    public ResponseData query(SysPlateSection dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectList(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/hcux/sys/plate/section/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<SysPlateSection> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/hcux/sys/plate/section/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<SysPlateSection> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
}