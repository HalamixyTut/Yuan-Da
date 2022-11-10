package hcux.sys.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import hcux.sys.dto.SysJobRecord;
import hcux.sys.service.ISysJobRecordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author feng.liu01@hand-china.com
 * @description 增量JOB运行记录表
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysJobRecordServiceImpl extends BaseServiceImpl<SysJobRecord> implements ISysJobRecordService {

}