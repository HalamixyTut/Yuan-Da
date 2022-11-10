package hcux.sys.service;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hcux.sys.dto.SysJobRecord;

/**
 * @author feng.liu01@hand-china.com
 * @description 增量JOB运行记录表
 */
public interface ISysJobRecordService extends IBaseService<SysJobRecord>, ProxySelf<ISysJobRecordService> {

}