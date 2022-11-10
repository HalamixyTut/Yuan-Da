package hcux.lm.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hcux.lm.dto.LmTransportImg;

import java.util.List;

public interface ILmTransportImgService extends IBaseService<LmTransportImg>, ProxySelf<ILmTransportImgService>{
    int deleteImg(IRequest iRequest, LmTransportImg dto);

    /**
     * 下载表中attachmentId为空的运输回执图片
     *
     * @param iRequest
     */
    void downloadMissing(IRequest iRequest) throws Exception;

    /**
     * 根据条件查询运输单回执图片
     *
     * @param iRequest
     * @param dto
     * @param page
     * @param pageSize
     */
    List<LmTransportImg> selectLists(IRequest iRequest, LmTransportImg dto, int page, int pageSize);
}