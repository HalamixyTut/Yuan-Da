package hcux.lm.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.system.service.IBaseService;
import hcux.core.exception.HcuxException;
import hcux.lm.dto.LmOrderHeader;
import hcux.lm.dto.LmTransport;
import hcux.lm.dto.LmTransportImg;
import hcux.lm.dto.ZhTransport;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ILmTransportService extends IBaseService<LmTransport>, ProxySelf<ILmTransportService> {
    ResponseData queryFromZh(IRequest requestCtx, @RequestBody ZhTransport dto) throws Exception;

    /**
     * 查询运输单
     *
     * @param requestContext
     * @param dto
     * @param page
     * @param pageSize
     * @return
     */
    List<LmTransport> queryTransport(IRequest requestContext, LmTransport dto, int page, int pageSize);

    /**
     * 门户查询运输单
     *
     * @param requestContext
     * @param dto
     * @param page
     * @param pageSize
     * @return
     */
    List<LmTransport> queryForPortal(IRequest requestContext, LmTransport dto, int page, int pageSize);

    /**
     * 保存或更新中化传递的运输单
     *
     * @param iRequest
     * @param dto
     * @param orderHeader
     * @return
     */
    void saveAndUpdate(IRequest iRequest, ZhTransport dto, LmOrderHeader orderHeader) throws Exception;

    /**
     * 获取运输单下载后的attachmentId
     *
     * @param iRequest
     * @param transportImg
     * @return
     * @throws Exception
     */
    LmTransportImg saveTransImg (IRequest iRequest, LmTransportImg transportImg) throws Exception;

    void sendMessage(IRequest iRequest, ZhTransport zhTransport);
}