package hcux.lm.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hcux.lm.dto.LmTransport;

import java.util.List;

public interface LmTransportMapper extends Mapper<LmTransport> {
    /**
     * 查询运输单
     *
     * @param dto
     * @return
     */
    List<LmTransport> queryTransport(LmTransport dto);

    LmTransport selectByTrancode(LmTransport dto);
    /**
     * 门户查询运输单
     *
     * @param dto
     * @return
     */
    List<LmTransport> queryForPortal(LmTransport dto);
}