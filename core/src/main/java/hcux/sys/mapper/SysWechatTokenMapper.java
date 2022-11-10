package hcux.sys.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hcux.sys.dto.SysWechatToken;

import java.util.List;

/**
 * @author feng.liu01@hand-china.com
 * @description 微信token维护
 */
public interface SysWechatTokenMapper extends Mapper<SysWechatToken> {

    List<SysWechatToken> selectList(SysWechatToken selectDto);
}