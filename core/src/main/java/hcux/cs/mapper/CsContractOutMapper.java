package hcux.cs.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hcux.cs.dto.CsContractOut;

import java.util.List;

public interface CsContractOutMapper extends Mapper<CsContractOut>{
    /**
     * 从ebs系统查询数据
     * @param csContractOut
     * @return
     */
    List<CsContractOut> selectCsContractOutFromEbs(CsContractOut csContractOut);

    /**
     * 根据条件查询hap系统的数据
     * @param csContractOut
     * @return
     */
    CsContractOut selectCsContractOutFromHap(CsContractOut csContractOut);

    /**
     * 根据headerId删除HAP合同出库数据
     * @param csContractOut
     * @return
     */
    void deleteByHeaderId(CsContractOut csContractOut);
}