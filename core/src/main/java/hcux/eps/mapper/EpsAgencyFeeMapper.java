package hcux.eps.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hcux.eps.dto.EpsAgencyFee;

import java.util.List;
/**
 * @author yexiang.ren@hand-china.com
 * @description 对账单的代理费
 */
public interface EpsAgencyFeeMapper extends Mapper<EpsAgencyFee> {
    /**
     * 根据条件查询换汇比
     *
     * @param epsAgencyFee
     * @return
     */
    List<EpsAgencyFee> queryEpsAgencyFee(EpsAgencyFee epsAgencyFee);

    /**
     * 查询发票号的行总金额
     * @param epsAgencyFee
     * @return
     */
    List<EpsAgencyFee> queryTotalExtendedPrice(EpsAgencyFee epsAgencyFee);

    /**
     * 查询各档金额
     * @param epsAgencyFee
     * @return
     */
    List<EpsAgencyFee> queryInvoiceAmount(EpsAgencyFee epsAgencyFee);
}
