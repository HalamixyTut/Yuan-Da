package hcux.sys.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hcux.sys.dto.SysUserSection;
import hcux.sys.mapper.SysUserSectionMapper;
import hcux.sys.service.ISysUserSectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author feng.liu01@hand-china.com
 * @description 用户部门
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysUserSectionServiceImpl extends BaseServiceImpl<SysUserSection> implements ISysUserSectionService {

    @Autowired
    private SysUserSectionMapper mapper;

    @Override
    public List<SysUserSection> selectList(IRequest requestContext, SysUserSection dto, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return mapper.selectList(dto);
    }
}