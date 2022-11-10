package hcux.lm.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hcux.lm.dto.LmOrderHeader;

import java.util.List;

public interface LmOrderHeaderMapper extends Mapper<LmOrderHeader> {
    /**
     * 根据条件查询委托单
     *
     * @param lmOrderHeader
     * @return
     */
    List<LmOrderHeader> selectLists(LmOrderHeader lmOrderHeader);

    /**
     * 查询一条数据的头和行
     *
     * @param dto
     * @return
     */
    List<LmOrderHeader> selectHeaderAndLine(LmOrderHeader dto);

    /**
     * 获取第一个合同号
     *
     * @param dto
     * @return
     */
    LmOrderHeader queryMaxOrderNumber(LmOrderHeader dto);

    LmOrderHeader selectById(LmOrderHeader dto);
}