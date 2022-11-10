package hcux.cs.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hcux.cs.dto.CsOutboundItemFull;
import hcux.cs.dto.CsOutboundOrderFull;

import java.util.List;

public interface CsOutboundItemFullMapper extends Mapper<CsOutboundItemFull>{
    /**
     * 从hap查询出库单item表数据
     *
     * @param csOutboundItemFull
     * @return
     */
    CsOutboundItemFull selectCsOutboundItemFullFromHap(CsOutboundItemFull csOutboundItemFull);

    /**
     * 根据需要更新的order表数据从ebs系统查询出库单item表数据
     *
     * @param csOutboundOrderFull
     * @return
     */
    List<CsOutboundItemFull> selectCsOutboundItemFullFromEbs(CsOutboundOrderFull csOutboundOrderFull);
}