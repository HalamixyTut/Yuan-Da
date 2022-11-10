package hcux.cs.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hcux.cs.dto.CsCredit;

import java.util.List;

public interface CsCreditMapper extends Mapper<CsCredit> {
    List<CsCredit> selectCsCreditFromEbs(CsCredit csCredit);
}
