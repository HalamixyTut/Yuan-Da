package hcux.mdm.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hcux.mdm.mapper.MdmInventoryAddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hcux.mdm.dto.MdmInventoryAddress;
import hcux.mdm.service.IMdmInventoryAddressService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author haojie.zhang@hand-china.com
 * @description 仓库地址维护
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MdmInventoryAddressServiceImpl extends BaseServiceImpl<MdmInventoryAddress> implements IMdmInventoryAddressService{

    @Autowired
    private MdmInventoryAddressMapper mapper;

    @Override
    public List<MdmInventoryAddress> selectList(IRequest requestContext, MdmInventoryAddress dto, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return mapper.selectList(dto);
    }
}