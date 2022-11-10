package hcux.mdm.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.core.exception.BaseException;
import com.hand.hap.system.service.IBaseService;
import hcux.mdm.dto.MdmBillingInfo;

import java.util.List;

/**
 * @author feng.liu01@hand-china.com
 * @description 客户开票资料
 */
public interface IMdmBillingInfoService extends IBaseService<MdmBillingInfo>, ProxySelf<IMdmBillingInfoService> {
    /**
     * 从ebs系统以及hap系统中查询数据
     * @author yexiang.ren@hand-china.com
     * @date 2019/03/06
     * @param iRequest
     * @param mdmBillingInfo
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<MdmBillingInfo> selectMdmBillingInfo(IRequest iRequest, MdmBillingInfo mdmBillingInfo, int pageNum, int pageSize);

    /**
     * 更新后的数据保存到hap系统数据库
     * @author yexiang.ren@hand-china.com
     * @date 2019/03/06
     * @param iRequest
     * @param mdmBillingInfoList
     * @return
     */
    void mdmBillingInfoBatchUpdate(IRequest iRequest, List<MdmBillingInfo> mdmBillingInfoList)throws BaseException;
}