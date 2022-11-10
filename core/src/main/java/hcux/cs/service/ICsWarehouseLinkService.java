package hcux.cs.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hcux.cs.dto.CsWarehouseLink;

import java.util.List;

public interface ICsWarehouseLinkService extends IBaseService<CsWarehouseLink>, ProxySelf<CsWarehouseLink> {

    List<CsWarehouseLink> queryCsWarehouseLink(IRequest request, CsWarehouseLink warehouseLink);

}
