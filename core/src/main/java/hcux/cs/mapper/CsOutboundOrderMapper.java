package hcux.cs.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hcux.cs.dto.CsOutboundOrder;

import java.util.List;

public interface CsOutboundOrderMapper extends Mapper<CsOutboundOrder>{
    /**
     * 从HAP系统查询数据, 关联查询出库物流信息
     * @param csOutboundOrder
     * @return
     */
    List<CsOutboundOrder> selectData(CsOutboundOrder csOutboundOrder);
}