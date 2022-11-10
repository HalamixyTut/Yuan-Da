package hcux.sys.util;

public class SysConstant {

    public static final String HCUX_SYS_MESSAGE = "HCUX_SYS_MESSAGE";

    /**
     * 查询类型值集
     */
    public interface PLATE {
        /**
         * 能化
         */
        String NH = "NH";

        /**
         * 新景
         */
        String XJ = "XJ";

    }

    /**
     * 系统消息对应业务类型
     */
    public interface MESSAGE_SOURCE_TYPE {
        /**
         * 委托单下运输单
         */
        String HCUX_LM_TRANSPORT = "HCUX_LM_TRANSPORT";

        /**
         * 委托单
         */
        String HCUX_LM_ORDER_HEADER = "HCUX_LM_ORDER_HEADER";

        /**
         * 合同执行(自提)
         */
        String HCUX_CS_CONTRACT_TRANS = "HCUX_CS_CONTRACT_TRANS";

        /**
         * 确认收货
         */
        String HCUX_CS_CONFIRM_RECEIPT = "HCUX_CS_CONFIRM_RECEIPT";

        /**
         * 运输单变更
         */
        String HCUX_LM_TRANSPORT_CHANGE = "HCUX_LM_TRANSPORT_CHANGE";
    }

    /**
     * 系统消息对应业务类型
     */
    public interface JOB_CODE {
        /**
         * 付款
         */
        String HCUX_EPS_PAYMENT_DETAIL = "HCUX_EPS_PAYMENT_DETAIL";

        /**
         * 采购
         */
        String HCUX_EPS_PURCHASE_DETAIL = "HCUX_EPS_PURCHASE_DETAIL";

        /**
         * 收款
         */
        String HCUX_EPS_RECEIPT_DETAIL = "HCUX_EPS_RECEIPT_DETAIL";

        /**
         * 合同
         */
        String HCUX_CS_CONTRACT = "HCUX_CS_CONTRACT";

        /**
         * 合同出库
         */
        String HCUX_CS_CONTRACT_OUT = "HCUX_CS_CONTRACT_OUT";

        /**
         * 开票
         */
        String HCUX_CS_CONTRACT_VAT = "HCUX_CS_CONTRACT_VAT";

        /**
         * 出库单
         */
        String HCUX_CS_OUTBOUND_ORDER_FULL = "HCUX_CS_OUTBOUND_ORDER_FULL";

        /**
         * 自动导入
         */
        String HCUX_EPS_CUSTOMS_AUTO_IMPORT = "HCUX_EPS_CUSTOMS_AUTO_IMPORT";
    }
}
