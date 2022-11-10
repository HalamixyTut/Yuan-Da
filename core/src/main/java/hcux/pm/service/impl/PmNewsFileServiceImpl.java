package hcux.pm.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hcux.pm.mapper.PmNewsFileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hcux.pm.dto.PmNewsFile;
import hcux.pm.service.IPmNewsFileService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author haojie.zhang@hand-china.com
 * @description 门户新闻附件
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PmNewsFileServiceImpl extends BaseServiceImpl<PmNewsFile> implements IPmNewsFileService{
    @Autowired
    private PmNewsFileMapper mapper;

    @Override
    public List<PmNewsFile> selectLists(IRequest requestContext, PmNewsFile dto, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return mapper.selectLists(dto);
    }
}