package hcux.sys.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hcux.sys.dto.SysPlateSection;

import java.util.List;

/**
 * @author feng.liu01@hand-china.com
 * @description 板块部门维护
 */
public interface ISysPlateSectionService extends IBaseService<SysPlateSection>, ProxySelf<ISysPlateSectionService> {

    List<SysPlateSection> selectList(IRequest requestContext, SysPlateSection dto, int page, int pageSize);
}