package hcux.mdm.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hcux.mdm.dto.MdmCustomerAddress;

import java.util.List;

/**
 * @author feng.liu01@hand-china.com
 * @description 客户收件人地址
 */
public interface MdmCustomerAddressMapper extends Mapper<MdmCustomerAddress> {

    List<MdmCustomerAddress> selectData(MdmCustomerAddress dto);
}