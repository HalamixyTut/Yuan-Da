package hcux.eps.job;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.impl.RequestHelper;
import com.hand.hap.job.AbstractJob;
import hcux.core.exception.HcuxException;
import hcux.eps.service.IEpsBillService;
import hcux.mail.service.IMyMessageService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.Instant;

@DisallowConcurrentExecution
public class SaveEpsBillJob extends AbstractJob {
    @Autowired
    private IEpsBillService iEbsBillService;
    private Exception exception = null;
    private Logger logger = LoggerFactory.getLogger(SavePurchaseDetailJob.class);
    @Autowired
    private IMyMessageService myMessageService;
    @Override
    public void safeExecute(JobExecutionContext jobExecutionContext) throws Exception {
        IRequest iRequest = RequestHelper.getCurrentRequest(true);
        int count = 0;
        Instant startInstant = Instant.now();

        ThreadLocal<Integer> index = new ThreadLocal<Integer>();
        index.set(0);
        while (index.get() < 3) {
            try {
                count = iEbsBillService.epsBillToHap(iRequest);
                index.set(3);
                break;
            } catch (Exception e) {
                exception = e;
                if (logger.isErrorEnabled()) {
                    logger.error(exception.getMessage(), exception);
                }
                if (index.get() == 2) {
                    String className = SaveEpsBillJob.class.getName();
                    //获取错误信息
                    String errorMessage = exception.getMessage();
                    myMessageService.sendErrorMessage(iRequest,className,errorMessage);
                    if (exception.getMessage().length() <= 200) {
                        throw e;
                    } else {
                        throw new HcuxException("定时任务出错，请查看日志输出");
                    }
                }
            }
            index.set(index.get() + 1);
            Thread.sleep(60*1000);
        }

        Instant endInstant = Instant.now();
        long seconds = Duration.between(startInstant, endInstant).getSeconds();
        setExecutionSummary("运行成功！本次更新数据" + count + "条,用时 " + seconds + " 秒！");
    }
}
