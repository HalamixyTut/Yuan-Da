package hcux.sys.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.system.service.IBaseService;
import hcux.sys.dto.SysWechatToken;

import java.io.IOException;

/**
 * @author feng.liu01@hand-china.com
 * @description 微信token维护
 */
public interface ISysWechatTokenService extends IBaseService<SysWechatToken>, ProxySelf<ISysWechatTokenService> {

    ResponseData queryToken(IRequest requestContext, SysWechatToken dto) throws IOException;
}