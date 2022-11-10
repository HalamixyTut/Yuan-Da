package hcux.mdm.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.system.service.IBaseService;
import hcux.mdm.dto.MdmDangerousGoods;

import java.util.List;

public interface IMdmDangerousGoodsService extends IBaseService<MdmDangerousGoods>, ProxySelf<IMdmDangerousGoodsService> {

    List<MdmDangerousGoods> selectList(IRequest requestContext, MdmDangerousGoods dto, int page, int pageSize);

    List<MdmDangerousGoods> selectEnabled(IRequest requestContext, MdmDangerousGoods dto, int page, int pageSize);

    ResponseData saveAndUpdate(IRequest requestContext, List<MdmDangerousGoods> dto);

}
