package hcux.doc.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hcux.doc.dto.DocBookingNote;

import java.util.List;

/**
 * @author feng.liu01@hand-china.com
 * @description 托单数据
 */
public interface DocBookingNoteMapper extends Mapper<DocBookingNote> {

    List<DocBookingNote> selectList(DocBookingNote dto);

    /**
     * 根据项目号查询托单
     * @param dto
     * @return
     */
    DocBookingNote selectDocBookingNote(DocBookingNote dto);
}