package hcux.lm.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hcux.lm.mapper.LmTransportImgMapper;
import hcux.lm.service.ILmTransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hcux.lm.dto.LmTransportImg;
import hcux.lm.service.ILmTransportImgService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class LmTransportImgServiceImpl extends BaseServiceImpl<LmTransportImg> implements ILmTransportImgService{
    @Autowired
    private LmTransportImgMapper mapper;
    @Autowired
    private ILmTransportService lmTransportService;

    @Override
    public int deleteImg(IRequest iRequest, LmTransportImg dto) {
        return mapper.delete(dto);
    }

    @Override
    public void downloadMissing(IRequest iRequest) throws Exception {
        List<LmTransportImg> list = mapper.queryListByAttachmentIsNull();

        if (!list.isEmpty()) {
            for (LmTransportImg img : list) {
                if (img.getImgUrl() != null) {
                    img = lmTransportService.saveTransImg(iRequest, img);
                    self().updateByPrimaryKeySelective(iRequest, img);
                }
            }
        }
    }

    @Override
    public List<LmTransportImg> selectLists(IRequest iRequest, LmTransportImg dto, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return mapper.selectLists(dto);
    }
}