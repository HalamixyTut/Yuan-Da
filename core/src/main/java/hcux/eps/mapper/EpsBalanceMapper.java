package hcux.eps.mapper;

import com.hand.hap.account.dto.User;
import com.hand.hap.job.dto.JobDetailDto;
import com.hand.hap.mybatis.common.Mapper;
import hcux.eps.dto.EpsBalance;

import java.util.List;
/**
 * @author yexiang.ren@hand-china.com
 * @description 对账单的余额表
 */
public interface EpsBalanceMapper extends Mapper<EpsBalance> {
    /**
     * 查询ebs视图的数据
     * @param epsBalance
     * @return
     */
    List<EpsBalance> selectEpsBalanceFromEbs(EpsBalance epsBalance);

    /**
     * 查询admin角色下用户的所有邮件
     * @return
     */
    List<User> selectEmails();

    /**
     * 通过类名查找定时任务的名称
     * @param dto
     * @return
     */
    JobDetailDto selectJobName(JobDetailDto dto);
}
