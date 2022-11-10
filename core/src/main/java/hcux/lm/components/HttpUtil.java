package hcux.lm.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.impl.RequestHelper;
import com.hand.hap.intergration.beans.HapInvokeInfo;
import com.hand.hap.intergration.dto.HapInterfaceOutbound;
import com.hand.hap.intergration.service.IHapInterfaceOutboundService;
import com.hand.hap.system.service.IProfileService;
import hcux.core.exception.HcuxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Date;

@Component
public class HttpUtil {
    private final RestTemplate template = new RestTemplate();
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private IHapInterfaceOutboundService outboundService;
    @Autowired
    private IProfileService profileService;

    public <T> T post(Object dto, String url, String interfaceName, Class<T> clazz) throws IOException, HcuxException {
        IRequest requestCtx = RequestHelper.getCurrentRequest(true);
        long startTime = System.currentTimeMillis();

        HttpHeaders headers = new HttpHeaders();
//      headers.add("X-Auth-Token", UUID.randomUUID().toString());

        String strategyId = profileService.getProfileValue(requestCtx, "HCUX_LM_ORDER_ZH_STRATEGY_ID");
        String apikey = profileService.getProfileValue(requestCtx, "HCUX_LM_ORDER_ZH_APIKEY");
        String authorizationType = profileService.getProfileValue(requestCtx, "HCUX_LM_ORDER_ZH_AUTHORIZATION_TYPE");

        headers.add("Strategy-Id", strategyId);
        headers.add("Apikey", apikey);
        headers.add("Authorization-Type", authorizationType);
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        String requestJson = objectMapper.writeValueAsString(dto);
        HttpEntity<Object> requestEntity = new HttpEntity<>(dto, headers);
        ResponseEntity<String> entity = null;
        String responseBody = null;

        // 插入调用记录
        HapInterfaceOutbound outbound = new HapInterfaceOutbound();

        try {
            //调用接口
            entity = template.postForEntity(url, requestEntity, String.class);
            responseBody = entity.getBody();
            outbound.setResponseCode(entity.getStatusCode().toString());
            outbound.setResponseContent(responseBody);
            outbound.setRequestStatus(HapInvokeInfo.REQUEST_SUCESS);

        } catch (Exception e) {
            outbound.setRequestStatus(HapInvokeInfo.REQUEST_FAILURE);
            outbound.setStackTrace(e.getMessage());
            throw new HcuxException("接口调用失败");
        } finally {
            outbound.setInterfaceUrl(url);
            outbound.setInterfaceName(interfaceName);
            outbound.setRequestTime(new Date(startTime));
            outbound.setRequestParameter(requestJson);
            outbound.setResponseTime(System.currentTimeMillis() - startTime);
            outboundService.outboundInvoke(outbound);
        }

        outbound.setInterfaceUrl(url);
        outbound.setInterfaceName(interfaceName);
        outbound.setRequestTime(new Date(startTime));
        outbound.setRequestParameter(requestJson);
        outbound.setResponseTime(System.currentTimeMillis() - startTime);
        outboundService.outboundInvoke(outbound);

        return objectMapper.readValue(responseBody, clazz);
    }
}
