package hcux.doc.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hcux.doc.dto.DocSynthesisFile;

import java.util.List;

public interface DocSynthesisFileMapper extends Mapper<DocSynthesisFile> {
    List<DocSynthesisFile> selectLists(DocSynthesisFile docSynthesisFile);
}