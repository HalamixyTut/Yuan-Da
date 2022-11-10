package hcux.eps.job;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.impl.RequestHelper;
import com.hand.hap.job.AbstractJob;
import hcux.core.util.HcuxConstant;
import hcux.eps.dto.EpsReceiptDetail;
import hcux.eps.service.IEpsCustomsDetailService;
import hcux.mail.service.IMyMessageService;
import hcux.sys.dto.SysJobRecord;
import hcux.sys.mapper.SysJobRecordMapper;
import hcux.sys.util.SysConstant;
import org.apache.commons.lang3.time.DateUtils;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

public class AutoImportExcelJob extends AbstractJob {
    @Autowired
    private IEpsCustomsDetailService iEpsCustomsDetailService;
    @Autowired
    private SysJobRecordMapper sysJobRecordMapper;

    private Exception exception = null;
    private Logger logger = LoggerFactory.getLogger(AutoImportExcelJob.class);
    @Autowired
    private IMyMessageService myMessageService;
    @Override
    public void safeExecute(JobExecutionContext jobExecutionContext) throws Exception {
        IRequest iRequest = RequestHelper.getCurrentRequest(true);
        Instant startInstant = Instant.now();

        Date startDate = new Date();
        String importInfo = "";
        try {
            SysJobRecord record = new SysJobRecord(SysConstant.JOB_CODE.HCUX_EPS_CUSTOMS_AUTO_IMPORT);
            record = sysJobRecordMapper.selectOne(record);
            if (record == null) {
                record = new SysJobRecord();
                record.setJobCode(SysConstant.JOB_CODE.HCUX_EPS_CUSTOMS_AUTO_IMPORT);
                record.setPreviousFireDate(new Date());
                sysJobRecordMapper.insertSelective(record);
            } else {
                startDate = record.getPreviousFireDate();
            }

            importInfo = iEpsCustomsDetailService.autoImportExcel(iRequest, startDate, record);
        } catch (Exception e) {
            //获取类名
            String className=AutoImportExcelJob.class.getName();
            //获取错误信息
            String errorMessage=e.getMessage();
            myMessageService.sendErrorMessage(iRequest,className,errorMessage);

            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            exception = e;
//            throw e;
        }
        if (exception != null) {
            setExecutionSummary(exception.getClass().getName() + ":" + exception.getMessage());
        } else {
            Instant endInstant = Instant.now();
            long seconds = Duration.between(startInstant, endInstant).getSeconds();
            setExecutionSummary("运行成功！用时 " + seconds + " 秒！ " + importInfo);
        }
    }
}
