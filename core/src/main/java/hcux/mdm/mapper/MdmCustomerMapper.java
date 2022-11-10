package hcux.mdm.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hcux.mdm.dto.MdmCustomer;

import java.util.List;

/**
 * @author feng.liu01@hand-china.com
 * @description 客户数据
 */
public interface MdmCustomerMapper extends Mapper<MdmCustomer> {
    List<MdmCustomer> selectList(MdmCustomer dto);
}