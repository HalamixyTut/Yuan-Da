package hcux.eps.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hcux.eps.dto.EpsCashFlow;

import java.util.List;

public interface EpsCashFlowMapper extends Mapper<EpsCashFlow> {
    /**
     * 还款日期为null的查询,也就是还款字段为空
     *
     * @return
     */
    List<EpsCashFlow> selectWithoutReturnDate();

    /**
     * 还款日期不为null的查询,也就是还款字段需要计算
     *
     * @return
     */
    List<EpsCashFlow> selectWithReturnDate();

    /**
     * 根据条件查询数据
     *
     * @param epsCashFlow
     * @return
     */
    List<EpsCashFlow> selectList(EpsCashFlow epsCashFlow);

    /**
     * 根据查询条件计算退款金额合计
     *
     * @param epsCashFlow
     * @return
     */
    List<EpsCashFlow> selectTotalReturnAmount(EpsCashFlow epsCashFlow);

    /**
     * 根据查询条件，查询收款编号与系列号确定唯一性在系统中只有一条数据的收款金额的各种币制的合计，
     *
     * @param epsCashFlow
     * @return
     */
    List<EpsCashFlow> selectTotalAmountOne(EpsCashFlow epsCashFlow);

    /**
     * 根据查询条件，查询收款编号与系列号确定唯一性在系统中有多条数据的收款金额的各种币制的合计，
     *
     * @param epsCashFlow
     * @return
     */
    List<EpsCashFlow> selectTotalAmountMore(EpsCashFlow epsCashFlow);

    /**
     * 根据查询条件，查询收款编号与系列号确定唯一性在系统中只有一条数据的收款本位币金额的合计，
     *
     * @param epsCashFlow
     * @return
     */
    EpsCashFlow selectTotalNatureAmountOne(EpsCashFlow epsCashFlow);

    /**
     * 根据查询条件，查询收款编号与系列号确定唯一性在系统中有多条数据的收款本位币金额的合计，
     *
     * @param epsCashFlow
     * @return
     */
    EpsCashFlow selectTotalNatureAmountMore(EpsCashFlow epsCashFlow);

    /**
     * 查询出所有的币种
     * @return
     */
    List<EpsCashFlow> selectCurrencyCode();

    /**
     * 删掉重复数据
     */
    void deleteRepeat();

    /**
     * 查询重复数据的条数
     * @return
     */
    List<EpsCashFlow> selectRepeatCount();

    /**
     * 根据系列号以及
     * @param epsCashFlow
     * @return
     */
    List<EpsCashFlow> selectWithReceiptSerialNumber(EpsCashFlow epsCashFlow);
}