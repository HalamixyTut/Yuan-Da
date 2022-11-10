package hcux.mdm.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hcux.mdm.dto.MdmDangerousGoods;
import hcux.mdm.mapper.MdmDangerousGoodsMapper;
import hcux.mdm.service.IMdmDangerousGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class MdmDangerousGoodsServiceImpl extends BaseServiceImpl<MdmDangerousGoods> implements IMdmDangerousGoodsService {

    @Autowired
    private MdmDangerousGoodsMapper mapper;

    @Autowired
    private IMdmDangerousGoodsService service;

    @Override
    public List<MdmDangerousGoods> selectList(IRequest requestContext, MdmDangerousGoods dto, int page, int pageSize){
        PageHelper.startPage(page, pageSize);
        return mapper.selectList(dto);
    }

    @Override
    public List<MdmDangerousGoods> selectEnabled(IRequest requestContext, MdmDangerousGoods dto, int page, int pageSize){
        PageHelper.startPage(page, pageSize);
        return mapper.selectEnabled(dto);
    }

    @Override
    public ResponseData saveAndUpdate(IRequest requestContext, List<MdmDangerousGoods> dto){
        int dtoSize = dto.size();
        ArrayList<MdmDangerousGoods> list1= new ArrayList<>();
        ArrayList<MdmDangerousGoods> list2 = new ArrayList<>();
        for (MdmDangerousGoods m : dto){
            List<MdmDangerousGoods> mdmDangerousGoods = mapper.selectEnabledByGoodsId(m);
            if (mdmDangerousGoods.size() == 0){
                list1.add(m);
            } else {
                list2.add(m);
            }
        }
        if (dtoSize == list1.size()){
            return new ResponseData(service.batchUpdate(requestContext, dto));
        } else {
            ResponseData responseData = new ResponseData();
            responseData.setSuccess(false);
            responseData.setMessage("物料"+list2.get(0).getItemNum()+"已维护有效危险货物数据，请检查！");
            return responseData;
        }
    }
}
