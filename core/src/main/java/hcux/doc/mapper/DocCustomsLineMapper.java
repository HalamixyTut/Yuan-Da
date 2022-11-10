package hcux.doc.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hcux.doc.dto.DocCustomsHeader;
import hcux.doc.dto.DocCustomsLine;

import java.util.List;

/**
 * @author feng.liu01@hand-china.com
 * @description 报关单行表
 */
public interface DocCustomsLineMapper extends Mapper<DocCustomsLine> {

    DocCustomsLine querySum(Long customsId);

    List<DocCustomsLine> queryPackageNumberUnit(Long customsId);

    List<DocCustomsLine> selectList(DocCustomsLine dto);

    List<DocCustomsLine> queryList(DocCustomsLine docCustomsLine);

    /**
     * 根据条件查出头和行
     *
     * @param dto
     * @return
     */
    List<DocCustomsLine> queryHeaderAndLine(DocCustomsHeader dto);
}