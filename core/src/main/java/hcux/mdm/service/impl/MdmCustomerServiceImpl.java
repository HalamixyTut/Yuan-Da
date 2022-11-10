package hcux.mdm.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.code.rule.service.ISysCodeRuleProcessService;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.core.exception.BaseException;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hcux.mdm.dto.MdmCustomer;
import hcux.mdm.mapper.MdmCustomerMapper;
import hcux.mdm.service.IMdmCustomerService;
import hcux.mdm.util.MdmConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author feng.liu01@hand-china.com
 * @description 客户数据
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MdmCustomerServiceImpl extends BaseServiceImpl<MdmCustomer> implements IMdmCustomerService {
    @Autowired
    private MdmCustomerMapper mapper;
    @Autowired
    private ISysCodeRuleProcessService codeRuleProcessService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<MdmCustomer> batchUpdateCustomer(IRequest requestCtx, @StdWho List<MdmCustomer> list) throws BaseException {
        for (MdmCustomer dto : list) {
            switch (dto.get__status()) {
                case DTOStatus.ADD:
                    dto.setCustomerCode(codeRuleProcessService.getRuleCode(MdmConstant.HCUX_MDM_CUSTOMER_CODE));
                    self().insertSelective(requestCtx, dto);
                    break;
                case DTOStatus.UPDATE:
                    dto.setLastUpdateDate(new Date());
                    self().updateByPrimaryKey(requestCtx, dto);
                    break;
                default:
                    break;
            }
        }
        return list;
    }

    @Override
    public int deleteData(IRequest requestCtx, @StdWho List<MdmCustomer> list) {
        int c = 0;
        for (MdmCustomer dto : list) {
            dto.setDeleteFlag("Y");
            self().updateByPrimaryKeySelective(requestCtx, dto);
            c++;
        }
        return c;
    }

    @Override
    public List<MdmCustomer> selectList(IRequest requestContext, MdmCustomer dto, int page, int pageSize){
        PageHelper.startPage(page, pageSize);
        return mapper.selectList(dto);
    }
}