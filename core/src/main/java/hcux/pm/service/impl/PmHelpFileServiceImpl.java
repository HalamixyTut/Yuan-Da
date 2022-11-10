package hcux.pm.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hcux.pm.dto.PmHelpFile;
import hcux.pm.mapper.PmHelpFileMapper;
import hcux.pm.service.IPmHelpFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author yuanqing.li@hand-china.com
 * @description 使用帮助附件
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PmHelpFileServiceImpl extends BaseServiceImpl<PmHelpFile> implements IPmHelpFileService {
    @Autowired
    private PmHelpFileMapper mapper;

    @Override
    public List<PmHelpFile> selectLists(IRequest requestContext, PmHelpFile dto, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return mapper.selectLists(dto);
    }
}