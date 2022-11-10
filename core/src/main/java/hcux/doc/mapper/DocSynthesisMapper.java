package hcux.doc.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hcux.doc.dto.DocSynthesis;

import java.util.List;

public interface DocSynthesisMapper extends Mapper<DocSynthesis> {

    List<DocSynthesis> selectLists(DocSynthesis dto);

    List<DocSynthesis> queryStatus();

    /**
     * 通过发票号查询综合信息
     *
     * @param docSynthesis
     */
    List<DocSynthesis> queryDocSynthesis(DocSynthesis docSynthesis);
}