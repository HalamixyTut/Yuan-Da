package hcux.eps.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hcux.eps.dto.EpsPurchaseDetail;

import java.util.List;
/**
 * @author yexiang.ren@hand-china.com
 * @description 应收预付往来明细表
 */
public interface EpsPurchaseDetailMapper extends Mapper<EpsPurchaseDetail> {
    /**
     * 查询ebs系统视图的数据
     *
     * @param epsPurchaseDetail
     * @return
     */
    List<EpsPurchaseDetail> selectEpsPurchaseDetailFromEbs(EpsPurchaseDetail epsPurchaseDetail);

    /**
     * 通过条件查询hap系统的应收预付往来明细表数据
     *
     * @param epsPurchaseDetail
     * @return
     */
    EpsPurchaseDetail selectEpsPurchaseDetailFromHap(EpsPurchaseDetail epsPurchaseDetail);

    /**
     * 通过条件查询应收预付往来明细表数据
     *
     * @param epsPurchaseDetail
     * @return
     */
    List<EpsPurchaseDetail> queryEpsPurchaseDetail(EpsPurchaseDetail epsPurchaseDetail);

    /**
     * 根据条件查询贷方金额总金额
     *
     * @param epsPurchaseDetail
     * @return
     */
    List<EpsPurchaseDetail> selectTotalAmount(EpsPurchaseDetail epsPurchaseDetail);

    /**
     * 根据项目号查询折合总和
     *
     * @param epsPurchaseDetail
     * @return
     */
    EpsPurchaseDetail querySumCrBaseAmount(EpsPurchaseDetail epsPurchaseDetail);
}