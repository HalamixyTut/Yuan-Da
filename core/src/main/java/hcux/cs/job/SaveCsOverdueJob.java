package hcux.cs.job;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.impl.RequestHelper;
import com.hand.hap.job.AbstractJob;
import hcux.cs.service.ICsOverdueService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.Instant;

@DisallowConcurrentExecution
public class SaveCsOverdueJob extends AbstractJob {
    @Autowired
    private ICsOverdueService iCsOverdueService;
    private Exception exception = null;
    private Logger logger = LoggerFactory.getLogger(SaveCsContractJob.class);

    @Override
    public void safeExecute(JobExecutionContext jobExecutionContext) throws Exception {
        IRequest iRequest = RequestHelper.getCurrentRequest(true);
        int count;
        Instant startInstant = Instant.now();

        try {
            count = iCsOverdueService.ebsDataInsertIntoHap(iRequest);
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            exception = e;
            throw e;
        }
        if (exception != null) {
            setExecutionSummary(exception.getClass().getName() + ":" + exception.getMessage());
        } else {
            Instant endInstant = Instant.now();
            long seconds = Duration.between(startInstant, endInstant).getSeconds();
            setExecutionSummary("运行成功！本次更新数据" + count + "条,用时 " + seconds + " 秒！");
        }
    }
}
