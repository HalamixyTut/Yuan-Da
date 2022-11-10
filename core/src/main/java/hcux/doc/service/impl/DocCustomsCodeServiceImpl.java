package hcux.doc.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import hcux.doc.dto.DocCustomsCode;
import hcux.doc.service.IDocCustomsCodeService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class DocCustomsCodeServiceImpl extends BaseServiceImpl<DocCustomsCode> implements IDocCustomsCodeService{

}