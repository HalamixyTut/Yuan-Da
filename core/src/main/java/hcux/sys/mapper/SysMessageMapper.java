package hcux.sys.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hcux.sys.dto.SysMessage;

import java.util.List;

/**
 * @author feng.liu01@hand-china.com
 * @description 系统消息表
 */
public interface SysMessageMapper extends Mapper<SysMessage> {

    List<SysMessage> selectList(SysMessage dto);

    List<SysMessage> selectUserAll(SysMessage dto);
}