package hcux.cs.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hcux.cs.dto.CsWarehouseLink;
import hcux.cs.mapper.CsWarehouseLinkMapper;
import hcux.cs.service.ICsWarehouseLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class CsWarehouseLinkServiceImpl extends BaseServiceImpl<CsWarehouseLink> implements ICsWarehouseLinkService {

    @Autowired
    private CsWarehouseLinkMapper csWarehouseLinkMapper;

    @Override
    public List<CsWarehouseLink> queryCsWarehouseLink(IRequest request, CsWarehouseLink warehouseLink){
        return csWarehouseLinkMapper.selectList(warehouseLink);
    }

}