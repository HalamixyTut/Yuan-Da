package hcux.mdm.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hcux.mdm.dto.MdmCarNumber;

import java.util.List;

public interface MdmCarNumberMapper extends Mapper<MdmCarNumber> {
    /**
     * 根据条件查询车牌编号
     * @param mdmCarNumber
     * @return
     */
    List<MdmCarNumber> selectList(MdmCarNumber mdmCarNumber);

    /**
     * 根据条件查询车辆总数
     * @param mdmCarNumber
     * @return
     */
    List<MdmCarNumber> totalCarNum(MdmCarNumber mdmCarNumber);
}
