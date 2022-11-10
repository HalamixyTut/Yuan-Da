package hcux.mdm.exception;

import com.hand.hap.core.exception.BaseException;

/**
 * @author yexiang.ren@hand-china.com
 * @version 1.0
 * @name MdmBillingInfoToEbsException
 * @description 自定义客户开票资料保存到ebs系统异常类
 * @date 2019/03/06
 */
public class MdmBillingInfoToEbsException extends BaseException {
    /**
     * 异常类名称，用于区分异常类
     */
    public static final String EXCEPTION_CODE = "hcux.mdm.MdmBillingInfoToEbsException";

    public MdmBillingInfoToEbsException(String code, String descriptionKey, Object[] parameters) {
        super(code, descriptionKey, parameters);
    }

    public MdmBillingInfoToEbsException(String message, Object[] parameters) {
        super(EXCEPTION_CODE, message, parameters);
    }
}
