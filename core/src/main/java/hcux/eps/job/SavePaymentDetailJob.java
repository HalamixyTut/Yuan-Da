package hcux.eps.job;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.impl.RequestHelper;
import com.hand.hap.job.AbstractJob;
import hcux.core.util.HcuxConstant;
import hcux.eps.dto.EpsPaymentDetail;
import hcux.eps.service.IEpsPaymentDetailService;
import hcux.mail.service.IMyMessageService;
import hcux.sys.dto.SysJobRecord;
import hcux.sys.mapper.SysJobRecordMapper;
import hcux.sys.util.SysConstant;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@DisallowConcurrentExecution
public class SavePaymentDetailJob extends AbstractJob {
    @Autowired
    private IEpsPaymentDetailService iEpsPaymentDetailService;
    private Exception exception = null;
    private Logger logger = LoggerFactory.getLogger(SavePurchaseDetailJob.class);
    @Autowired
    private IMyMessageService myMessageService;
    @Autowired
    private SysJobRecordMapper sysJobRecordMapper;

    @Override
    public void safeExecute(JobExecutionContext jobExecutionContext) throws Exception {
        IRequest iRequest = RequestHelper.getCurrentRequest(true);
        int count;
        Instant startInstant = Instant.now();

        Date startDate = new Date();
        try {

            /*SysJobRecord record = new SysJobRecord(SysConstant.JOB_CODE.HCUX_EPS_PAYMENT_DETAIL);
            record = sysJobRecordMapper.selectOne(record);
            if (record == null) {
                record = new SysJobRecord();
                record.setJobCode(SysConstant.JOB_CODE.HCUX_EPS_PAYMENT_DETAIL);
                record.setPreviousFireDate(new Date());
                sysJobRecordMapper.insertSelective(record);
            } else {
                startDate = record.getPreviousFireDate();
            }

            EpsPaymentDetail dto = new EpsPaymentDetail();
            startDate = DateUtils.addMinutes(startDate, HcuxConstant.HCUX_BACK_TIME);
            dto.setLastUpdateDate(startDate);

            count = iEpsPaymentDetailService.ebsPaymentDetailToHap(iRequest, dto);

            // 插入成功后再更新
            record.setPreviousFireDate(Date.from(startInstant));
            sysJobRecordMapper.updateByPrimaryKeySelective(record);*/
            count = iEpsPaymentDetailService.ebsPaymentDetailToHap(iRequest, new EpsPaymentDetail());

        } catch (Exception e) {
            //获取类名
            String className=SavePaymentDetailJob.class.getName();
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
            setExecutionSummary("运行成功！本次更新数据" + count + "条,开始于" + DateFormatUtils.format(startDate, BaseConstants.DATE_TIME_FORMAT) + "，用时 " + seconds + " 秒！");

        }
    }
}
