package hcux.eps.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hcux.eps.dto.EpsJournal;

import java.util.List;
/**
 * @author yexiang.ren@hand-china.com
 * @description 对账单对应的总账表
 */
public interface EpsJournalMapper extends Mapper<EpsJournal> {
    /**
     *
     * @param epsJournal
     * @return
     */
    List<EpsJournal> selectEpsJournal(EpsJournal epsJournal);
}
