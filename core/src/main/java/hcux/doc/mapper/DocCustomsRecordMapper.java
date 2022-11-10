package hcux.doc.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hcux.doc.dto.DocCustomsHeader;
import hcux.doc.dto.DocCustomsRecord;

import java.util.Date;
import java.util.List;

/**
 * @author feng.liu01@hand-china.com
 * @description 报关单操作记录
 */
public interface DocCustomsRecordMapper extends Mapper<DocCustomsRecord> {

    List<DocCustomsRecord> selectList(DocCustomsRecord dto);

    /**
     * 根据报关单头表ID查询最大的操作时间
     */
    DocCustomsRecord selectMaxTime(DocCustomsHeader header);
}