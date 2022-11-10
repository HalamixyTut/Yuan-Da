package hcux.sys.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hcux.sys.dto.SysPlateSection;
import hcux.sys.mapper.SysPlateSectionMapper;
import hcux.sys.service.ISysPlateSectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author feng.liu01@hand-china.com
 * @description 板块部门维护
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysPlateSectionServiceImpl extends BaseServiceImpl<SysPlateSection> implements ISysPlateSectionService {

    @Autowired
    private SysPlateSectionMapper mapper;

    @Override
    public List<SysPlateSection> selectList(IRequest requestContext, SysPlateSection dto, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return mapper.selectList(dto);
    }
}