package hcux.mdm.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hcux.mdm.dto.MdmCustomerAddress;
import hcux.mdm.mapper.MdmCustomerAddressMapper;
import hcux.mdm.service.IMdmCustomerAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author feng.liu01@hand-china.com
 * @description 客户收件人地址
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MdmCustomerAddressServiceImpl extends BaseServiceImpl<MdmCustomerAddress> implements IMdmCustomerAddressService {

    @Autowired
    private MdmCustomerAddressMapper mapper;

    @Override
    public List<MdmCustomerAddress> selectData(IRequest requestContext, MdmCustomerAddress dto, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return mapper.selectData(dto);
    }
}