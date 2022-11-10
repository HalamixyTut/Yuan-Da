package hcux.mdm.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.exception.BaseException;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hcux.mdm.dto.MdmBillingInfo;
import hcux.mdm.exception.MdmBillingInfoToEbsException;
import hcux.mdm.mapper.MdmBillingInfoMapper;
import hcux.mdm.service.IMdmBillingInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static hcux.mdm.util.MdmConstant.PACKAGE_EXECUTE_EXCEPTION_E;

/**
 * @author feng.liu01@hand-china.com
 * @description 客户开票资料
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MdmBillingInfoServiceImpl extends BaseServiceImpl<MdmBillingInfo> implements IMdmBillingInfoService {
    @Autowired
    private MdmBillingInfoMapper mdmBillingInfoMapper;

    @Override
    public List<MdmBillingInfo> selectMdmBillingInfo(IRequest iRequest, MdmBillingInfo mdmBillingInfo, int pageNum, int pageSize) {
        return mdmBillingInfoMapper.selectMdmBillingInfo(mdmBillingInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void mdmBillingInfoBatchUpdate(IRequest iRequest, List<MdmBillingInfo> mdmBillingInfoList) throws BaseException {

        if (mdmBillingInfoList != null && !mdmBillingInfoList.isEmpty()) {

            mdmBillingInfoList.forEach(dto -> {
                mdmBillingInfoMapper.updateMdmBillingInfoToEbs(dto);

                dto.set__status(DTOStatus.ADD);
                dto.setAttribute1(dto.getReturnStatus());
                dto.setAttribute2(dto.getMsgCount() == null ? null : dto.getMsgCount() + "");
                dto.setAttribute3(dto.getMsgData());
            });

            // 保存数据到hap，留个记录
            self().batchUpdate(iRequest, mdmBillingInfoList);

            String message = "";
            for (MdmBillingInfo mdmBillingInfo : mdmBillingInfoList) {
                if (PACKAGE_EXECUTE_EXCEPTION_E.equals(mdmBillingInfo.getReturnStatus())) {
                    message += mdmBillingInfo.getMsgData();
                }
            }

            if (StringUtils.isNotBlank(message))
                throw new MdmBillingInfoToEbsException(message, null);

//            List<String> returnStatus = mdmBillingInfoList.stream().map(MdmBillingInfo::getReturnStatus).collect(Collectors.toList());
//            if (returnStatus.contains(PACKAGE_EXECUTE_EXCEPTION_E)) {
//                String errData = mdmBillingInfoList.stream().filter(x -> (PACKAGE_EXECUTE_EXCEPTION_E).equals(x.getStatus())).map(MdmBillingInfo::getMsgData).collect(Collectors.toList()).get(0);
//                throw new MdmBillingInfoToEbsException(errData, null);
//            }
        }
    }

}