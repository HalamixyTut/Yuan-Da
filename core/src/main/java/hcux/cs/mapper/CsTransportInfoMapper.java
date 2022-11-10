package hcux.cs.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hcux.cs.dto.CsTransportInfo;

import java.util.List;

public interface CsTransportInfoMapper extends Mapper<CsTransportInfo>{
    /**
     * 根据条件查询数据
     *
     * @param csTransportInfo
     * @return
     */
    List<CsTransportInfo> selectLists(CsTransportInfo csTransportInfo);

}