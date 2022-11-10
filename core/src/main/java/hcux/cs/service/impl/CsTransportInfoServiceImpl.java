package hcux.cs.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.account.dto.User;
import com.hand.hap.account.service.IUserService;
import com.hand.hap.code.rule.exception.CodeRuleException;
import com.hand.hap.code.rule.service.ISysCodeRuleProcessService;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.hr.dto.Employee;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hcux.cs.dto.CsContract;
import hcux.cs.mapper.CsContractMapper;
import hcux.cs.mapper.CsTransportInfoMapper;
import hcux.cs.util.CsConstant;
import hcux.sys.dto.SysMessage;
import hcux.sys.service.ISysMessageService;
import hcux.sys.service.ISysUserSectionService;
import hcux.sys.util.SysConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hcux.cs.dto.CsTransportInfo;
import hcux.cs.service.ICsTransportInfoService;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional(rollbackFor = Exception.class)
public class CsTransportInfoServiceImpl extends BaseServiceImpl<CsTransportInfo> implements ICsTransportInfoService {
    @Autowired
    private CsTransportInfoMapper csTransportInfoMapper;
    @Autowired
    private CsContractMapper csContractMapper;
    @Autowired
    private ISysCodeRuleProcessService codeRuleProcessService;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private ISysMessageService sysMessageService;

    @Override
    public List<CsTransportInfo> selectLists(IRequest iRequest, CsTransportInfo csTransportInfo, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return csTransportInfoMapper.selectLists(csTransportInfo);
    }

    @Override
    public List<CsContract> selectListByCustPoNumber(CsTransportInfo csTransportInfo) {
        return csContractMapper.selectListByCustPoNumber(csTransportInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<CsTransportInfo> batchUpdateTransport(IRequest requestCtx, @StdWho List<CsTransportInfo> dto) throws CodeRuleException {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

        for (CsTransportInfo csTransportInfo : dto) {
            if (csTransportInfo.getTransportId() != null) {
                csTransportInfo.setLastUpdateDate(new Date());
                self().updateByPrimaryKey(requestCtx, csTransportInfo);
            } else {
                String transportCode = "ZT" + format.format(new Date()) + codeRuleProcessService.getRuleCode(CsConstant.HCUX_CS_CONTRACT_TRANSPORT_S);
                csTransportInfo.setTransportCode(transportCode);
                self().insertSelective(requestCtx, csTransportInfo);

                CsContract csContract = new CsContract();
                csContract.setCustPoNumber(csTransportInfo.getCustPoNumber());
                List<CsContract> list = csContractMapper.select(csContract);
                CsTransportInfo transportInfo = new CsTransportInfo();
                transportInfo.setTransportId(csTransportInfo.getTransportId());
                List<CsTransportInfo> transportInfoList = csTransportInfoMapper.select(transportInfo);
                if (!list.isEmpty() && !transportInfoList.isEmpty()) {
                    CsContract contract = list.get(0);
                    CsTransportInfo csTransportInfo1 = transportInfoList.get(0);
                    String createPerson = contract.getCreatePerson();
                    String pattern = "[\u4e00-\u9fa5]+";

                    Pattern reg = Pattern.compile(pattern);
                    Matcher match = reg.matcher(createPerson);
                    if (match.find()) {
                        String employeeName = match.group();

                        User user = new User();
                        user.setEmployeeName(employeeName);
                        List<User>  userList = iUserService.selectUsers(requestCtx, user, 0, 0);
                        if (!userList.isEmpty()) {
                            String s = contract.getCustPoNumber() + " " +
                                    contract.getCustomerName() +
                            " 该客户已经录入自提信息，数量"+csTransportInfo1.getAmount()+contract.getSoUomDesc()+"，请尽早做出库单！";

                            // 保存消息记录并推送消息
                            SysMessage message = new SysMessage();
                            message.setContent(s);
                            message.setSourceKey(csTransportInfo.getTransportId());
                            message.setSourceType(SysConstant.MESSAGE_SOURCE_TYPE.HCUX_CS_CONTRACT_TRANS);
                            message.setOwnerId(userList.get(0).getUserId());
                            sysMessageService.saveAndPublish(requestCtx, message);
                        }
                    }
                }
            }
        }

        return dto;
    }
}