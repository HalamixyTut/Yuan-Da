package hcux.pm.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hcux.pm.dto.PmNewsFile;

import java.util.List;

/**
 * @author haojie.zhang@hand-china.com
 * @description 门户新闻附件
 */
public interface PmNewsFileMapper extends Mapper<PmNewsFile>{
    /**
     * 查询新闻上传的附件
     *
     * @param dto
     * @return
     */
    List<PmNewsFile> selectLists(PmNewsFile dto);
}