package hcux.cs.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hcux.cs.dto.CsContractVat;

import java.util.List;

public interface CsContractVatMapper extends Mapper<CsContractVat> {
    /**
     * 从ebs系统查询合同信息
     *
     * @param csContractVat
     * @return
     */
    List<CsContractVat> selectCsContractVatFromEbs(CsContractVat csContractVat);

    /**
     * 从hap系统查询合同信息
     *
     * @param csContractVat
     * @return
     */
    CsContractVat selectCsContractVatFromHap(CsContractVat csContractVat);
}