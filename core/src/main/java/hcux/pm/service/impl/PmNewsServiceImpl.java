package hcux.pm.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hcux.pm.mapper.PmNewsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hcux.pm.dto.PmNews;
import hcux.pm.service.IPmNewsService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class PmNewsServiceImpl extends BaseServiceImpl<PmNews> implements IPmNewsService{
    @Autowired
    private PmNewsMapper pmNewsMapper;
    @Override
    public List<PmNews> selectLists(IRequest requestContext, PmNews dto, int page, int pageSize) {
        PageHelper.startPage(page,pageSize);
        return pmNewsMapper.selectLists(dto);
    }

    @Override
    public PmNews selectNews(IRequest request, PmNews pmNews) {
        return pmNewsMapper.selectNews(pmNews);
    }
}