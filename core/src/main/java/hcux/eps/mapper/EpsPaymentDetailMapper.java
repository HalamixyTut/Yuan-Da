package hcux.eps.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hcux.eps.dto.EpsPaymentDetail;

import java.util.List;
/**
 * @author yexiang.ren@hand-china.com
 * @description 应付款表
 */
public interface EpsPaymentDetailMapper extends Mapper<EpsPaymentDetail> {
    /**
     * 从ebs系统的视图查询数据
     *
     * @param epsPaymentDetail
     * @return
     */
    List<EpsPaymentDetail> selectEpsPaymentDetailFromEbs(EpsPaymentDetail epsPaymentDetail);

    /**
     * 从hap系统查询单条数据
     *
     * @param epsPaymentDetail
     * @return
     */
    EpsPaymentDetail selectEpsPaymentDetailFromHap(EpsPaymentDetail epsPaymentDetail);

    /**
     * 根据条件查询数据
     *
     * @param epsPaymentDetail
     * @return
     */
    List<EpsPaymentDetail> queryEpsPaymentDetail(EpsPaymentDetail epsPaymentDetail);

    /**
     * 根据条件查询原币金额总金额
     *
     * @param epsPaymentDetail
     * @return
     */
    List<EpsPaymentDetail> selectTotalAmount(EpsPaymentDetail epsPaymentDetail);

    /**
     * 删除hap这边所有的付款信息
     */
    void deleteAll();

    /**
     * 同步全量数据
     *
     * @return
     */
    int insertAll();

    /**
     * 对账单计算时用于计算已付货款以及国内运费
     * @param epsPaymentDetail
     * @return
     */
    EpsPaymentDetail selectTotalLineAmount(EpsPaymentDetail epsPaymentDetail);

}