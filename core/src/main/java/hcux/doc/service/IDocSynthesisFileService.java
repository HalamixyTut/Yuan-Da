package hcux.doc.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hcux.doc.dto.DocSynthesisFile;

import java.util.List;

public interface IDocSynthesisFileService extends IBaseService<DocSynthesisFile>, ProxySelf<IDocSynthesisFileService> {
    List<DocSynthesisFile> selectLists(IRequest requestContext, DocSynthesisFile dto, int page, int pageSize);
}