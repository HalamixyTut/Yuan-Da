package hcux.sys.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hcux.sys.dto.SysUserSection;

import java.util.List;

/**
 * @author feng.liu01@hand-china.com
 * @description 用户部门
 */
public interface SysUserSectionMapper extends Mapper<SysUserSection> {

    List<SysUserSection> selectList(SysUserSection dto);
}