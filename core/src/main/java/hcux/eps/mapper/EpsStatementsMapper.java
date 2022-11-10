package hcux.eps.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hcux.eps.dto.EpsStatements;

import java.util.List;
/**
 * @author yexiang.ren@hand-china.com
 * @description 对账单表
 */
public interface EpsStatementsMapper extends Mapper<EpsStatements> {
    /**
     * 查询对账数据
     *
     * @param epsStatements
     * @return
     */
    List<EpsStatements> selectEpsStatements(EpsStatements epsStatements);

    /**
     * 根据项目号计算退税额
     *
     * @param epsStatements
     * @return
     */
    EpsStatements queryTaxRefunds(EpsStatements epsStatements);

    /**
     * 根据项目号查询出口调整项是否存在
     *
     * @param epsStatement
     * @return
     */
    EpsStatements queryStatementByProjectNum(EpsStatements epsStatement);

    /**
     * 从ebs查询
     * @return
     */
    List<EpsStatements> selectEpsStatementsFromEbs();

    /**
     * 从ebs查询增量数据
     * @return
     */
    List<EpsStatements> selectEpsStatementsAddFromEbs();

    /**
     * 从hap数据库查询前天晚上计算的数据
     * @return
     */
    List<EpsStatements> selectEpsStatementsFromHap(EpsStatements epsStatement);

    /**
     * 查询总计余额
     * @param epsStatements
     * @return
     */
    EpsStatements selectTotalBalance(EpsStatements epsStatements);

    /**
     * 查询业务员对应的对账单
     * @param epsStatements
     * @return
     */
    List<EpsStatements> selectEditableEpsStatements(EpsStatements epsStatements);

    /**
     * 查询可编辑的对账单的总计余额
     * @param epsStatements
     * @return
     */
    EpsStatements selectEditableTotalBalance(EpsStatements epsStatements);

    /**
     * 通过项目号计算增量对账单的各个值
     * @param epsStatements
     * @return
     */
    EpsStatements selectCalculateStatementsFromEbs(EpsStatements epsStatements);
    /**
     * 通过项目号计算全量对账单的各个值
     * @param epsStatements
     * @return
     */
    EpsStatements selectCalculateStatementsAddFromEbs(EpsStatements epsStatements);

    /**
     * 通过项目号查询某条对账数据
     * @param epsStatements
     * @return
     */
    EpsStatements selectStatementsByProjectNum(EpsStatements epsStatements);

    /**
     * 通过项目号删除对账单
     * @param epsStatements
     */
    void deleteByProject(EpsStatements epsStatements);
}
