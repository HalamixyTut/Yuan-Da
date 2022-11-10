package hcux.eps.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hcux.eps.dto.EpsReceiptDetail;

import java.util.List;
/**
 * @author yexiang.ren@hand-china.com
 * @description 收款明细表
 */
public interface EpsReceiptDetailMapper extends Mapper<EpsReceiptDetail> {
    /**
     * 从ebs系统的试图查询数据
     *
     * @param epsReceiptDetail
     * @return
     */
    List<EpsReceiptDetail> selectEpsReceiptDetailFromEbs(EpsReceiptDetail epsReceiptDetail);

    /**
     * 从hap系统查询单条数据
     *
     * @param epsReceiptDetail
     * @return
     */
    EpsReceiptDetail selectEpsReceiptDetailFromHap(EpsReceiptDetail epsReceiptDetail);

    /**
     * 根据条件查询收款明心信息
     *
     * @param epsReceiptDetail
     * @return
     */
    List<EpsReceiptDetail> queryEpsReceiptDetail(EpsReceiptDetail epsReceiptDetail);

    /**
     * 根据条件查询贷方金额总金额
     *
     * @param epsReceiptDetail
     * @return
     */
    List<EpsReceiptDetail> selectTotalAmount(EpsReceiptDetail epsReceiptDetail);

    /**
     *
     * @param epsReceiptDetail
     * @return
     */
    EpsReceiptDetail selectTotalBaseAmount1(EpsReceiptDetail epsReceiptDetail);

    /**
     * 对账单中计算收汇金额
     * @param epsReceiptDetail
     * @return
     */
    EpsReceiptDetail selectTotalCrAmount(EpsReceiptDetail epsReceiptDetail);

    /**
     * 对账单中计算收汇金额
     * @param epsReceiptDetail
     * @return
     */
    EpsReceiptDetail selectTotalReturnAmount(EpsReceiptDetail epsReceiptDetail);
    /**
     * 对账单中计算人民币
     * @param epsReceiptDetail
     * @return
     */
    EpsReceiptDetail selectTotalCrAmountCny(EpsReceiptDetail epsReceiptDetail);

    /**
     * 对账单中计算人民币
     * @param epsReceiptDetail
     * @return
     */
    EpsReceiptDetail selectTotalReturnAmountCny(EpsReceiptDetail epsReceiptDetail);
    /**
     * 对账单中计算等值金额
     * @param epsReceiptDetail
     * @return
     */
    EpsReceiptDetail selectTotalReturnAmountEqu(EpsReceiptDetail epsReceiptDetail);

    /**
     *
     * @param epsReceiptDetail
     * @return
     */
    EpsReceiptDetail selectTotalCrBaseAmount1(EpsReceiptDetail epsReceiptDetail);
}