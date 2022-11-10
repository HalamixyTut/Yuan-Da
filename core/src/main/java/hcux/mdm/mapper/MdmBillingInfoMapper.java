package hcux.mdm.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hcux.mdm.dto.MdmBillingInfo;

import java.util.List;

/**
 * @author feng.liu01@hand-china.com
 * @description 客户开票资料
 */
public interface MdmBillingInfoMapper extends Mapper<MdmBillingInfo> {
    /**
     * 根据条件查询ebs以及hap里面的开票信息
     *
     * @param mdmBillingInfo
     * @return java.util.List<hcux.mdm.dto.MdmBillingInfo>
     * @author yexiang.ren@hand-china.com
     * @date 2019/03/05
     */
    List<MdmBillingInfo> selectMdmBillingInfo(MdmBillingInfo mdmBillingInfo);

    /**
     * 把hap系统上已经审批的数据存储到ebs系统中
     *
     * @param mdmBillingInfo
     * @author yexiang.ren@hand-china.com
     * @date 2019/03/05
     */
    void updateMdmBillingInfoToEbs(MdmBillingInfo mdmBillingInfo);
}