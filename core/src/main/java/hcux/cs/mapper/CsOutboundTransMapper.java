package hcux.cs.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hcux.cs.dto.CsOutboundTrans;

import java.util.List;

public interface CsOutboundTransMapper extends Mapper<CsOutboundTrans>{
    /**
     * 从HAP系统查询数据, 关联查询出库物流详细信息
     * @param csOutboundTrans
     * @return
     */
    List<CsOutboundTrans> selectData(CsOutboundTrans csOutboundTrans);
}