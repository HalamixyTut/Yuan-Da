package hcux.sys.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.impl.RequestHelper;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hcux.sys.dto.SysWechatToken;
import hcux.sys.service.ISysWechatTokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author feng.liu01@hand-china.com
 * @description 微信token维护
 */
@Controller
public class SysWechatTokenController extends BaseController {

    @Autowired
    private ISysWechatTokenService service;
    @Autowired
    private TokenEndpoint tokenEndpoint;

    @RequestMapping(value = "/hcux/sys/wechat/token/query")
    @ResponseBody
    public ResponseData query(SysWechatToken dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/hcux/sys/wechat/token/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<SysWechatToken> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/hcux/sys/wechat/token/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<SysWechatToken> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     * 查询是否登录，登录了则返回token
     *
     * @param dto
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/api/public/hcux/sys/wechat/token/queryToken")
    @ResponseBody
    public ResponseData queryToken(SysWechatToken dto, HttpServletRequest request) throws IOException {
        IRequest requestContext = createRequestContext(request);
        return service.queryToken(requestContext, dto);
    }


    /**
     * 登录生成获取token
     *
     * @param principal
     * @param parameters
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/api/public/wechat/oauth/token")
    @ResponseBody
    public ResponseData bindToken(Principal principal,
                                  @RequestParam Map<String, String> parameters) throws Exception {
        //调用Spring oauth2的方法获取token
        ResponseEntity<OAuth2AccessToken> tokenEntity = tokenEndpoint.postAccessToken(principal, parameters);
        OAuth2AccessToken token = tokenEntity.getBody();

//        service.bindToken(token, parameters);
        SysWechatToken wechatToken = new SysWechatToken();

        String openid = parameters.get("openid");
        if (StringUtils.isBlank(openid)) {
            ResponseData rd = new ResponseData();
            rd.setSuccess(false);
            rd.setMessage("openid不存在");
            return rd;
        }
        wechatToken.setOpenid(openid);

        IRequest requestContext = RequestHelper.getCurrentRequest(true);
        // 根据openid查询是否存在记录
        List<SysWechatToken> list = service.select(requestContext, wechatToken, 0, 0);

        if (list.isEmpty()) {
            wechatToken.setUserName(parameters.get("username"));
            wechatToken.setAccessToken(token.getValue());
            wechatToken.setRefreshToken(token.getRefreshToken().getValue());
            wechatToken.setTokenUpdateDate(new Date());
            service.insertSelective(requestContext, wechatToken);
        } else {
            wechatToken = list.get(0);
            wechatToken.setUserName(parameters.get("username"));
            wechatToken.setAccessToken(token.getValue());
            wechatToken.setRefreshToken(token.getRefreshToken().getValue());
            wechatToken.setTokenUpdateDate(new Date());
            service.updateByPrimaryKeySelective(requestContext, wechatToken);
        }

        List<SysWechatToken> tokenList = new ArrayList<>();
        tokenList.add(wechatToken);
        return new ResponseData(tokenList);
    }
}
