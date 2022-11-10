package hcux.lm.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hcux.lm.dto.LmOrderLine;

import java.util.List;

public interface LmOrderLineMapper extends Mapper<LmOrderLine> {
    List<LmOrderLine> selectList(LmOrderLine dto);
}