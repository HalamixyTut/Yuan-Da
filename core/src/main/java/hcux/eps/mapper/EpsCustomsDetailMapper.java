package hcux.eps.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hcux.eps.dto.EpsCustomsDetail;

import java.util.List;
/**
 * @author yexiang.ren@hand-china.com
 * @description 报关明细表
 */
public interface EpsCustomsDetailMapper extends Mapper<EpsCustomsDetail> {
    /**
     * 查询报关明细
     *
     * @param epsCustomsDetail
     * @return
     */
    List<EpsCustomsDetail> selectList(EpsCustomsDetail epsCustomsDetail);

    /**
     * 报关明细头查询
     *
     * @param epsCustomsDetail
     * @return
     */
    List<EpsCustomsDetail> selectErrorList(EpsCustomsDetail epsCustomsDetail);

    /**
     * 为门户提供接口
     *
     * @param epsCustomsDetail
     * @return
     */
    List<EpsCustomsDetail> selectPortalList(EpsCustomsDetail epsCustomsDetail);

    /**
     * 根据条件查询总价总金额
     *
     * @param epsCustomsDetail
     * @return
     */
    List<EpsCustomsDetail> selectTotalAmount(EpsCustomsDetail epsCustomsDetail);

    /**
     * 根据条件查询总价总金额
     *
     * @param epsCustomsDetail
     * @return
     */
    List<EpsCustomsDetail> selectTotalAmountForHap(EpsCustomsDetail epsCustomsDetail);

    /**
     * 查询出最大的申报日期
     *
     * @param epsCustomsDetail
     * @return
     */
    EpsCustomsDetail selectMaxDeclareDate(EpsCustomsDetail epsCustomsDetail);

    /**
     * 根据条件查询
     *
     * @param epsCustomsDetail
     * @return
     */
    List<EpsCustomsDetail> selectListByConditions(EpsCustomsDetail epsCustomsDetail);

    List<EpsCustomsDetail> selectHeaderListForWechat(EpsCustomsDetail dto);

    List<EpsCustomsDetail> selectDetailListForWechat(EpsCustomsDetail dto);

    List<EpsCustomsDetail> queryInvoiceNum(EpsCustomsDetail epsCustomsDetail);

    /**
     * 海关数据查询方法
     *
     * @param epsCustomsDetail
     * @return
     */
    List<EpsCustomsDetail> selectForCustomsData(EpsCustomsDetail epsCustomsDetail);

    /**
     * 查询条件下报关明细头总价币制汇总
     *
     * @param epsCustomsDetail
     * @return
     */
    List<EpsCustomsDetail> selectErrorTotalAmount(EpsCustomsDetail epsCustomsDetail);

    /**
     * 查询配置文件里拥有导出excel权限的用户userId
     * @return
     */
    List<EpsCustomsDetail> selectUserIds();

    /**
     * 根据条件查询出要导出的数据
     * @param epsCustomsDetail
     * @return
     */
    List<EpsCustomsDetail> selectSelfExportList(EpsCustomsDetail epsCustomsDetail);
}