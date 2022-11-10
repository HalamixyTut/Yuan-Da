package hcux.eps.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hcux.eps.mapper.EpsProjectNumMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hcux.eps.dto.EpsProjectNum;
import hcux.eps.service.IEpsProjectNumService;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author haojie.zhang@hand-china.com
 * @description 白条发票号
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class EpsProjectNumServiceImpl extends BaseServiceImpl<EpsProjectNum> implements IEpsProjectNumService{
    @Autowired
    private EpsProjectNumMapper mapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int epsProjectNumToHap(IRequest iRequest) {
        mapper.delete(new EpsProjectNum());
        return mapper.insertAll();
    }
}