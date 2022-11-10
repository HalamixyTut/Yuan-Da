package hcux.eps.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hcux.eps.dto.EpsBill;

import java.util.List;
/**
 * @author yexiang.ren@hand-china.com
 * @description 挂账表
 */
public interface EpsBillMapper extends Mapper<EpsBill> {
    /**
     * 从ebs系统的视图查询数据
     *
     * @param epsBill
     * @return
     */
    List<EpsBill> selectEpsBillFromEbs(EpsBill epsBill);

    /**
     * 从hap系统查询数据
     *
     * @param epsBill
     * @return
     */
    EpsBill selectEpsBillFromHap(EpsBill epsBill);

    /**
     * 通过条件查询信息
     *
     * @param epsBill
     * @return
     */
    List<EpsBill> queryEpsBill(EpsBill epsBill);

    /**
     * 插入不包含白条发票号的数据
     *
     * @return
     */
    int insertAll();

    List<EpsBill> selectTotalAmount(EpsBill dto);

    /**
     * 科目项是应收外汇账款的总金额汇总
     * @param dto
     * @return
     */
    EpsBill selectTotalRemainAmount(EpsBill dto);

    /**
     * 预付账款取值
     *
     * @return
     */
    int insertPayment();

    /**
     * 预收外汇账款
     *
     * @return
     */
    int insertReceiptForeign();

    /**
     * 预收账款
     *
     * @return
     */
    int insertReceipt();
}