package hcux.core.exception;

import com.hand.hap.core.exception.BaseException;

/**
 * @version 1.0
 * @description HCUX异常
 * @update feng.liu01@hand-china.com
 */
public class HcuxException extends BaseException {

    private static final long serialVersionUID = 8504784325083890453L;

    private static final String CODE = "HCUX";

    public HcuxException(String descriptionKey, Object[] parameters) {
        super(CODE, descriptionKey, parameters);
    }

    public HcuxException(String descriptionKey) {
        super(CODE, descriptionKey, null);
    }
}
