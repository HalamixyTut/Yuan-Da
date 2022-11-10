package hcux.sys.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.account.dto.User;
import com.hand.hap.account.service.IUserService;
import com.hand.hap.core.IRequest;
import com.hand.hap.message.IMessagePublisher;
import com.hand.hap.message.websocket.BadgeManager;
import com.hand.hap.message.websocket.CommandMessage;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hcux.sys.dto.SysMessage;
import hcux.sys.mapper.SysMessageMapper;
import hcux.sys.service.ISysMessageService;
import hcux.sys.util.SysConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author feng.liu01@hand-china.com
 * @description 系统消息表
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysMessageServiceImpl extends BaseServiceImpl<SysMessage> implements ISysMessageService {

    @Autowired
    private SysMessageMapper mapper;
    @Autowired
    IMessagePublisher iMessagePublisher;
    @Autowired
    private IUserService userService;

    @Override
    public void saveAndPublish(IRequest iRequest, SysMessage message) {
        self().insertSelective(iRequest, message);

        User user = new User();
        user.setUserId(message.getOwnerId());
        user = userService.selectByPrimaryKey(iRequest, user);

        CommandMessage commandMessage = new CommandMessage();
        commandMessage.setUserName(user.getUserName());
        commandMessage.setAction(SysConstant.HCUX_SYS_MESSAGE);
        Map<String, Object> map = new HashMap<>();
        map.put("message", message.getContent());
        commandMessage.setParameter(map);
        iMessagePublisher.publish(BadgeManager.CHANNEL_BADGE, commandMessage);
    }

    @Override
    public List<SysMessage> selectList(IRequest requestContext, SysMessage dto, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return mapper.selectList(dto);
    }

    @Override
    public List<SysMessage> selectUserAll(IRequest requestContext, SysMessage dto, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return mapper.selectUserAll(dto);
    }
}