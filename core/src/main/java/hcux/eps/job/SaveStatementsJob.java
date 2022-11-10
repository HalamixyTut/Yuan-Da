package hcux.eps.job;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.impl.RequestHelper;
import com.hand.hap.job.AbstractJob;
import hcux.eps.dto.EpsStatements;
import hcux.eps.service.IEpsStatementsService;
import hcux.mail.service.IMyMessageService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@DisallowConcurrentExecution
public class SaveStatementsJob extends AbstractJob {
    @Autowired
    private IEpsStatementsService iEpsStatementsService;
    private Exception exception = null;
    private Logger logger = LoggerFactory.getLogger(SaveStatementsJob.class);
    @Autowired
    private IMyMessageService myMessageService;
    @Override
    public void safeExecute(JobExecutionContext jobExecutionContext) throws Exception {
        IRequest iRequest = RequestHelper.getCurrentRequest(true);
        Instant startInstant = Instant.now();
        Date startDate = new Date();
        try {
         iEpsStatementsService.epsStatementsToHap(iRequest,new EpsStatements());
        } catch (Exception e) {
            //获取类名
            String className=SaveStatementsJob.class.getName();
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
            Date endDate = new Date();
            setExecutionSummary("运行成功！开始于" + DateFormatUtils.format(startDate, BaseConstants.DATE_TIME_FORMAT) + "，用时 " + seconds + " 秒！结束于"+ DateFormatUtils.format(endDate, BaseConstants.DATE_TIME_FORMAT));
        }
    }
}
