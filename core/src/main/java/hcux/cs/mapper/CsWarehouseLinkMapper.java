package hcux.cs.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hcux.cs.dto.CsWarehouseLink;

import java.util.List;

public interface CsWarehouseLinkMapper extends Mapper<CsWarehouseLink> {

    List<CsWarehouseLink> selectList(CsWarehouseLink csWarehouseLink);

}
