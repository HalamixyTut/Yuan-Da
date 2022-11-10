package hcux.cs.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hcux.cs.dto.CsOutboundItemFull;
import hcux.cs.dto.CsOutboundOrderFull;
import hcux.cs.dto.CsOutboundTransFull;

import java.util.List;

public interface CsOutboundOrderFullMapper extends Mapper<CsOutboundOrderFull>{
    /**
     * 从ebs系统查询出库单
     *
     * @param csOutboundOrderFull
     * @return
     */
    List<CsOutboundOrderFull> selectCsOutboundFullFromEbs(CsOutboundOrderFull csOutboundOrderFull);

    /**
     * 从hap查询出库单
     *
     * @param csOutboundOrderFull
     * @return
     */
    CsOutboundOrderFull selectCsOutboundFullFromHap(CsOutboundOrderFull csOutboundOrderFull);

    /**
     * 从HAP系统查询数据, 关联查询出库物流信息
     * @param csOutboundOrderFull
     * @return
     */
    List<CsOutboundOrderFull> selectData(CsOutboundOrderFull csOutboundOrderFull);

    /**
     * 查询打印信息
     * @param csOutboundOrderFull
     * @return
     */
    List<CsOutboundOrderFull> selectPrintData(CsOutboundOrderFull csOutboundOrderFull);
}