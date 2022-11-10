package hcux.sys.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hcux.core.util.HcuxConstant;
import hcux.sys.dto.SysMessage;
import hcux.sys.service.ISysMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author feng.liu01@hand-china.com
 * @description 系统消息表
 */
@Controller
public class SysMessageController extends BaseController {

    @Autowired
    private ISysMessageService service;


    @RequestMapping(value = "/hcux/sys/message/query")
    @ResponseBody
    public ResponseData query(SysMessage dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/hcux/sys/message/queryByUser")
    @ResponseBody
    public ResponseData queryByUser(SysMessage dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        dto.setOwnerId(requestContext.getUserId());
        dto.setReadFlag(HcuxConstant.YES_NO.N);
        return new ResponseData(service.selectList(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/hcux/sys/message/queryUserAll")
    @ResponseBody
    public ResponseData queryUserAll(SysMessage dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        dto.setOwnerId(requestContext.getUserId());
        return new ResponseData(service.selectUserAll(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/hcux/sys/message/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<SysMessage> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/hcux/sys/message/updateReadFlag")
    @ResponseBody
    public ResponseData updateReadFlag(@RequestParam Long messageId, HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        SysMessage dto = new SysMessage();
        dto.setMessageId(messageId);
        dto = service.selectByPrimaryKey(requestCtx, dto);
        dto.setReadFlag(HcuxConstant.YES_NO.Y);
        service.updateByPrimaryKeySelective(requestCtx, dto);
        List<SysMessage> list = new ArrayList<>();
        list.add(dto);
        return new ResponseData(list);
    }

    @RequestMapping(value = "/hcux/sys/message/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<SysMessage> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
}