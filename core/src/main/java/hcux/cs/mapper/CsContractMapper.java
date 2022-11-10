package hcux.cs.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hcux.cs.dto.CsContract;
import hcux.cs.dto.CsTransportInfo;

import java.util.List;

public interface CsContractMapper extends Mapper<CsContract> {
    /**
     * 从ebs系统查询合同信息
     *
     * @param csContract
     * @return
     */
    List<CsContract> selectCsContractFromEbs(CsContract csContract);

    /**
     * 从hap系统查询合同信息
     *
     * @param csContract
     * @return
     */
    CsContract selectCsContractFromHap(CsContract csContract);

    /**
     * 根据条件查询数据
     *
     * @param csContract
     * @return
     */
    List<CsContract> selectLists(CsContract csContract);

    /**
     * 根据合同编号查询出物料编码
     *
     * @param csTransportInfo
     * @return
     */
    List<CsContract> selectListByCustPoNumber(CsTransportInfo csTransportInfo);

}