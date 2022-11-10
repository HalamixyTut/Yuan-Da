package hcux.pm.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hcux.pm.dto.PmNews;

import java.util.List;

public interface PmNewsMapper extends Mapper<PmNews>{
    /**
     * 查询不包含新闻内容的列表
     * @param pmNews
     * @return
     */
    List<PmNews> selectLists(PmNews pmNews);

    /**
     * 根据新闻的id查询新闻的主要内容
     * @param pmNews
     * @return
     */
    PmNews selectNews(PmNews pmNews);

}