package hcux.doc.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hcux.doc.mapper.DocSynthesisFileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hcux.doc.dto.DocSynthesisFile;
import hcux.doc.service.IDocSynthesisFileService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class DocSynthesisFileServiceImpl extends BaseServiceImpl<DocSynthesisFile> implements IDocSynthesisFileService {
    @Autowired
    private DocSynthesisFileMapper docSynthesisFileMapper;

    @Override
    public List<DocSynthesisFile> selectLists(IRequest requestContext, DocSynthesisFile dto, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return docSynthesisFileMapper.selectLists(dto);
    }
}