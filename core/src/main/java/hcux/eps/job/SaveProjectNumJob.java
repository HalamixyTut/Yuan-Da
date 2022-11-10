package hcux.eps.job;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.impl.RequestHelper;
import com.hand.hap.job.AbstractJob;
import hcux.eps.service.IEpsProjectNumService;
import hcux.mail.service.IMyMessageService;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.Instant;

public class SaveProjectNumJob extends AbstractJob {
    @Autowired
    private IEpsProjectNumService iEpsProjectNumService;
    private Exception exception = null;
    private Logger logger = LoggerFactory.getLogger(SavePurchaseDetailJob.class);
    @Autowired
    private IMyMessageService myMessageService;
    @Override
    public void safeExecute(JobExecutionContext jobExecutionContext) throws Exception {
        IRequest iRequest = RequestHelper.getCurrentRequest(true);
        int count;
        Instant startInstant = Instant.now();

        try {
            count = iEpsProjectNumService.epsProjectNumToHap(iRequest);
        } catch (Exception e) {
            //获取类名
            String className=SaveProjectNumJob.class.getName();
            //获取错误信息
            String errorMessage=e.getMessage();
            myMessageService.sendErrorMessage(iRequest,className,errorMessage);
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
