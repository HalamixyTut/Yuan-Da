package hcux.sys.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.system.service.IProfileService;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hcux.sys.dto.SysWechatToken;
import hcux.sys.dto.WechatTokenResponse;
import hcux.sys.mapper.SysWechatTokenMapper;
import hcux.sys.service.ISysWechatTokenService;
import hcux.sys.util.SysConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author feng.liu01@hand-china.com
 * @description 微信token维护
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysWechatTokenServiceImpl extends BaseServiceImpl<SysWechatToken> implements ISysWechatTokenService {

    @Autowired
    private SysWechatTokenMapper mapper;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private IProfileService profileService;

    @Override
    public ResponseData queryToken(IRequest requestContext, SysWechatToken dto) throws IOException {
        ResponseData rd = new ResponseData();
        if (StringUtils.isBlank(dto.getCode())) {
            rd.setSuccess(false);
            rd.setCode("code_not_exist");
            rd.setMessage("请传入code");
            return rd;
        }
        String appid, secret;
        if (SysConstant.PLATE.NH.equals(dto.getPlate())) {
            appid = profileService.getProfileValue(requestContext, "HCUX_WECHAT_OPENID_NH_APPID");
            secret = profileService.getProfileValue(requestContext, "HCUX_WECHAT_OPENID_NH_SECRET");
        } else if (SysConstant.PLATE.XJ.equals(dto.getPlate())) {
            appid = profileService.getProfileValue(requestContext, "HCUX_WECHAT_OPENID_XJ_APPID");
            secret = profileService.getProfileValue(requestContext, "HCUX_WECHAT_OPENID_XJ_SECRET");
        } else {
            rd.setSuccess(false);
            rd.setCode("plate_not_exist");
            rd.setMessage("请传入板块字段plate");
            return rd;
        }


        // 根据code获取openid
        RestTemplate template = new RestTemplate();
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appid +
                "&secret=" + secret +
                "&grant_type=authorization_code" +
                "&code=" + dto.getCode();
        String s = template.getForObject(url, String.class);
//        WechatTokenResponse tokenResponse = template.getForObject(url, WechatTokenResponse.class);
        WechatTokenResponse tokenResponse = objectMapper.readValue(s, WechatTokenResponse.class);
        if (StringUtils.isNotBlank(tokenResponse.getErrcode())) {
            rd.setSuccess(false);
            rd.setCode(tokenResponse.getErrcode());
            rd.setMessage(tokenResponse.getErrmsg());
            return rd;
        }

        // 根据openid 查询是否存在token
        SysWechatToken selectDto = new SysWechatToken();
        selectDto.setOpenid(tokenResponse.getOpenid());
        List<SysWechatToken> list = mapper.selectList(selectDto);
        if (list.isEmpty()) {
            rd.setSuccess(true);
            rd.setCode("not_login");
            list.add(selectDto);
            rd.setRows(list);
            return rd;
        }
        processTokenStatus(list);
        rd.setRows(list);
        return rd;
    }

    private void processTokenStatus(List<SysWechatToken> tokenList) {
        for (SysWechatToken token : tokenList) {
            if ("N".equalsIgnoreCase(token.getRevokeFlag())) {
                token.setTokenStatus("invalid");
            } else if (token.getTokenExpiresTime().before(new Date())) {
                token.setTokenStatus("invalid");
            } else {
                token.setTokenStatus("valid");
            }
        }
    }
}