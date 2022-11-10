package hcux.lm.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.intergration.annotation.HapInbound;
import com.hand.hap.security.oauth.dto.Oauth2ClientDetails;
import com.hand.hap.security.oauth.service.IOauth2ClientDetailsService;
import com.hand.hap.system.controllers.BaseController;
import hcux.lm.dto.CommonResponse;
import hcux.lm.dto.LmOrderHeader;
import hcux.lm.dto.OrderRejectRequest;
import hcux.lm.dto.ZhTransport;
import hcux.lm.service.ILmOrderHeaderService;
import hcux.lm.service.ILmTransportService;
import hcux.sys.dto.SysMessage;
import hcux.sys.service.ISysMessageService;
import hcux.sys.util.SysConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * @author feng.liu01@hand-china.com
 * @description 提供给66云链平台的rest接口
 */
@RestController
public class LmApiController extends BaseController {

    @Autowired
    private ILmOrderHeaderService orderHeaderService;
    @Autowired
    private IOauth2ClientDetailsService oauth2ClientDetailsService;
    @Autowired
    private ILmTransportService transportService;
    @Autowired
    private ISysMessageService sysMessageService;

    @HapInbound(apiName = "委托单拒绝接口")
    @RequestMapping(value = "/api/public/lm/order/reject", method = RequestMethod.POST)
    public synchronized CommonResponse sentRequest(HttpServletRequest request, @RequestBody OrderRejectRequest dto) {
        try {
            IRequest iRequest = createRequestContext(request);

            if (!validate(request)) {
                return new CommonResponse("E", "client_id或client_secret错误，认证失败");
            }

            String entrustId = dto.getEntrustId();
            String event = dto.getEvent();
            String eventTime = dto.getEventTime();
            String eventReason = dto.getEventReason();
            if (entrustId == null || event == null) {
                return new CommonResponse("E", "委托单ID（entrustId）、执行事件（event）字段必传");
            }

            if (!"E005".equals(event)) {
                return new CommonResponse("E", "状态错误，应为拒绝状态");
            }

            LmOrderHeader orderHeader = new LmOrderHeader();
            orderHeader.setZhOrderId(entrustId);
            List<LmOrderHeader> headerList = orderHeaderService.select(iRequest, orderHeader, 0, 0);
            if (headerList.isEmpty()) {
                return new CommonResponse("E", "系统中未能找到委托单ID对应的委托单");
            }
            orderHeader = headerList.get(0);
            orderHeader.setStatus("2");
            String reason = (eventTime == null ? "" : eventTime) + (eventReason == null ? "" : eventReason);
            orderHeader.setRejectReason(reason);
            orderHeaderService.updateByPrimaryKeySelective(iRequest, orderHeader);

            String s = orderHeader.getOrderNumber() +
                    "该委托单被" +
                    orderHeader.getCarrier() +
                    "拒绝。";

            // 保存消息记录并推送消息
            SysMessage message = new SysMessage();
            message.setContent(s);
            message.setSourceKey(orderHeader.getOrderId());
            message.setSourceType(SysConstant.MESSAGE_SOURCE_TYPE.HCUX_LM_ORDER_HEADER);
            message.setOwnerId(orderHeader.getCreatedBy());
            sysMessageService.saveAndPublish(iRequest, message);

            return new CommonResponse("S");
        } catch (Exception e) {
            e.printStackTrace();
            return new CommonResponse("E", e.getMessage());
        }
    }


    @HapInbound(apiName = "运输单推送接口")
    @RequestMapping(value = "/api/public/lm/transport", method = RequestMethod.POST)
    public synchronized CommonResponse sentTransport(HttpServletRequest request, @RequestBody ZhTransport dto) {
        try {
            IRequest iRequest = createRequestContext(request);

            if (!validate(request)) {
                return new CommonResponse("E", "client_id或client_secret错误，认证失败");
            }

            LmOrderHeader orderHeader = new LmOrderHeader();
            orderHeader.setZhOrderId(dto.getEntrustId());
            List<LmOrderHeader> headerList = orderHeaderService.select(iRequest, orderHeader, 0, 0);
            if (headerList.isEmpty()) {
                return new CommonResponse("E", "系统中未能找到委托单ID对应的委托单");
            }
            orderHeader = headerList.get(0);

            transportService.saveAndUpdate(iRequest, dto, orderHeader);
            return new CommonResponse("S");
        } catch (Exception e) {
            e.printStackTrace();
            return new CommonResponse("E", e.getMessage());
        }
    }

    private boolean validate(HttpServletRequest request) {
        String clientId = request.getHeader("client_id");
        String clientSecret = request.getHeader("client_secret");

        Oauth2ClientDetails clientDetails = oauth2ClientDetailsService.selectByClientId(clientId);
        if (clientDetails == null || !clientDetails.getClientSecret().equals(clientSecret)) {
            return false;
        }
        return true;
    }
}
