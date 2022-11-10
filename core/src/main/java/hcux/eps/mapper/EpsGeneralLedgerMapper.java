package hcux.eps.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hcux.eps.dto.EpsGeneralLedger;
/**
 * @author yexiang.ren@hand-china.com
 * @description 总账表
 */
public interface EpsGeneralLedgerMapper extends Mapper<EpsGeneralLedger> {
    /**
     * ebs系统中的总账信息同步到hap系统
     * @return
     */
    int insertAll();
}
