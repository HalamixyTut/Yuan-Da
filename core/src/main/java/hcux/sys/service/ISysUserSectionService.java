package hcux.sys.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hcux.sys.dto.SysUserSection;

import java.util.List;

/**
 * @author feng.liu01@hand-china.com
 * @description 用户部门
 */
public interface ISysUserSectionService extends IBaseService<SysUserSection>, ProxySelf<ISysUserSectionService> {

    List<SysUserSection> selectList(IRequest requestContext, SysUserSection dto, int page, int pageSize);
}