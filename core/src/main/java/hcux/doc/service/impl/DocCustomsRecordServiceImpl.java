package hcux.doc.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hcux.doc.dto.DocCustomsHeader;
import hcux.doc.dto.DocCustomsRecord;
import hcux.doc.mapper.DocCustomsRecordMapper;
import hcux.doc.service.IDocCustomsRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author feng.liu01@hand-china.com
 * @description 报关单操作记录
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DocCustomsRecordServiceImpl extends BaseServiceImpl<DocCustomsRecord> implements IDocCustomsRecordService {

    @Autowired
    private DocCustomsRecordMapper mapper;
    @Override
    public List<DocCustomsRecord> selectList(IRequest requestContext, DocCustomsRecord dto, int page, int pageSize) {
        PageHelper.startPage(page,pageSize);
        return mapper.selectList(dto);
    }

    @Override
    public DocCustomsRecord selectMaxTime(DocCustomsHeader header){
        return mapper.selectMaxTime(header);
    }
}