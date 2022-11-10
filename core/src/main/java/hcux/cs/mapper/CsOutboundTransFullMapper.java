package hcux.cs.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hcux.cs.dto.CsOutboundOrderFull;
import hcux.cs.dto.CsOutboundTransFull;
import hcux.lm.dto.LmTransport;

import java.util.List;

public interface CsOutboundTransFullMapper extends Mapper<CsOutboundTransFull>{
    /**
     * 从HAP系统查询数据, 关联查询出库物流详细信息
     * @param csOutboundTransFull
     * @return
     */
    List<CsOutboundTransFull> selectData(CsOutboundTransFull csOutboundTransFull);

    /**
     * 根据需要更新的order表数据从ebs系统查询出库单trans表数据
     *
     * @param csOutboundOrderFull
     * @return
     */
    List<CsOutboundTransFull> selectCsOutboundTransFullFromEbs(CsOutboundOrderFull csOutboundOrderFull);

    /**
     * 从hap查询出库单trans表数据
     *
     * @param csOutboundTransFull
     * @return
     */
    CsOutboundTransFull selectCsOutboundTransFullFromHap(CsOutboundTransFull csOutboundTransFull);

    /**
     * 根据出库编号和运输单号查询物流连接
     *
     * @param csOutboundOrderFull
     * @return
     */
    LmTransport selectLmTransport(CsOutboundTransFull csOutboundOrderFull);


    /**
     * 微信出库查询接口
     * @param dto
     * @return
     */
    List<CsOutboundTransFull> selectTransFullForWechat(CsOutboundTransFull dto);
}