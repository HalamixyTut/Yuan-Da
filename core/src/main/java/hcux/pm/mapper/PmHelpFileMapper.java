package hcux.pm.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hcux.pm.dto.PmHelpFile;

import java.util.List;

/**
 * @author yuanqing.li@hand-china.com
 * @description 使用帮助附件
 */
public interface PmHelpFileMapper extends Mapper<PmHelpFile>{
    /**
     * 查询新闻上传的附件
     *
     * @param dto
     * @return
     */
    List<PmHelpFile> selectLists(PmHelpFile dto);
}