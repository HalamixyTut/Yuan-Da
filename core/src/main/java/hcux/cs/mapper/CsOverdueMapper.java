package hcux.cs.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hcux.cs.dto.CsOverdue;

import java.util.List;

public interface CsOverdueMapper extends Mapper<CsOverdue> {
    /**
     * 从ebs系统查询逾期信息
     *
     * @param csOverdue
     * @return
     */
    List<CsOverdue> selectCsOverdueFromEbs(CsOverdue csOverdue);

    /**
     * 删除hap这边所有的逾期信息
     */
    void deleteCsOverdue();

    /**
     * 查询出ebs系统那边数据的总条数
     *
     * @return
     */
    CsOverdue selectCountCsOverdueFromEbs();

    /**
     * 根据查询条件查询Hap中的数据
     *
     * @param csOverdue
     * @return
     */
    List<CsOverdue> selectListsFromHap(CsOverdue csOverdue);

    /**
     * 根据条件查询Ebs系统中的数据
     *
     * @param csOverdue
     * @return
     */
    List<CsOverdue> selectListsFromEbs(CsOverdue csOverdue);

    /**
     * 根据条件从Hap查询出额度占用总额
     *
     * @param csOverdue
     * @return
     */
    CsOverdue selectSumLockAmountFromHap(CsOverdue csOverdue);

    /**
     * 根据invoiceId查询hap里面是否有相同数据
     *
     * @param csOverdue
     * @return
     */
    CsOverdue selectByInvoiceId(CsOverdue csOverdue);

    /**
     * 同步全量数据
     *
     * @return
     */
    int insertAll();
}