package hcux.doc.job;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.impl.RequestHelper;
import com.hand.hap.job.AbstractJob;
import hcux.doc.dto.DocCustomsHeader;
import hcux.doc.dto.DocSynthesis;
import hcux.doc.service.IDocCustomsHeaderService;
import hcux.doc.service.IDocSynthesisService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.Instant;

/**
 * job---报关单修改状态为准备 综合查询状态修改为准备做箱、准备报关
 */
@DisallowConcurrentExecution
public class DocCustomsChangeStatusJob extends AbstractJob {
    @Autowired
    private IDocSynthesisService docSynthesisService;
    @Autowired
    private IDocCustomsHeaderService docCustomsHeaderService;
    private Exception exception = null;
    private Logger logger = LoggerFactory.getLogger(DocCustomsChangeStatusJob.class);

    @Override
    public void safeExecute(JobExecutionContext jobExecutionContext) throws Exception {
        IRequest iRequest = RequestHelper.getCurrentRequest(true);
        int count = 0;
        int countSynthesis = 0;
        Instant startInstant = Instant.now();

        try {
            count = docCustomsHeaderService.modifyStatus(iRequest, new DocCustomsHeader());

            countSynthesis = docSynthesisService.modifyStatus(iRequest, new DocSynthesis());
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
            setExecutionSummary("运行成功！本次更新报关数据" + count + "条,更新综合数据" + countSynthesis + "条,用时 " + seconds + " 秒！");
        }
    }
}
