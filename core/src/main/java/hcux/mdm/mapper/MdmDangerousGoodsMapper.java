package hcux.mdm.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hcux.mdm.dto.MdmDangerousGoods;

import java.util.List;

public interface MdmDangerousGoodsMapper extends Mapper<MdmDangerousGoods> {
    List<MdmDangerousGoods> selectList(MdmDangerousGoods dto);

    List<MdmDangerousGoods> selectEnabled(MdmDangerousGoods dto);

    List<MdmDangerousGoods> selectEnabledByGoodsId(MdmDangerousGoods dto);

    List<MdmDangerousGoods> selectEbsItemNum(MdmDangerousGoods dto);
}
