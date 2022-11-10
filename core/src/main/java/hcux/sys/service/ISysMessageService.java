package hcux.sys.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hcux.sys.dto.SysMessage;

import java.util.List;

/**
 * @author feng.liu01@hand-china.com
 * @description 系统消息表
 */
public interface ISysMessageService extends IBaseService<SysMessage>, ProxySelf<ISysMessageService> {

    void saveAndPublish(IRequest iRequest, SysMessage message);

    List<SysMessage> selectList(IRequest requestContext, SysMessage dto, int page, int pageSize );

    List<SysMessage> selectUserAll(IRequest requestContext, SysMessage dto, int page, int pageSize);
}