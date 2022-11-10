package hcux.core.util;

public class HcuxConstant {

    public static final int HCUX_BACK_TIME = -30;

    /**
     * 查询类型值集
     */
    public interface QUERY_TYPE {
        /**
         * HAP
         */
        String HAP = "HAP";

        /**
         * 门户
         */
        String PORTAL = "PORTAL";

        /**
         * 微信
         */
        String WECHAT = "WECHAT";
    }

    //是否YN
    public interface YES_NO {
        /**
         * 是
         */
        String Y = "Y";

        /**
         * 否
         */
        String N = "N";
    }
}
