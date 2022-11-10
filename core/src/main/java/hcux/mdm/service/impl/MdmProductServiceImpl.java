package hcux.mdm.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import hcux.mdm.dto.MdmProduct;
import hcux.mdm.service.IMdmProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class MdmProductServiceImpl extends BaseServiceImpl<MdmProduct> implements IMdmProductService {

}