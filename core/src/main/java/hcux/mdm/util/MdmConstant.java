package hcux.mdm.util;

public class MdmConstant {
    private MdmConstant() {
    }

    // 编码规则-客户数据-收货人编码
    public static final String HCUX_MDM_CUSTOMER_CODE = "HCUX_MDM_CUSTOMER_CODE";
    //把状态变成审批
    public static final String HCUX_MDM_BILLING_INFO_STATUS = "Y";

    //包执行返回的状态
    public static final String PACKAGE_EXECUTE_EXCEPTION_E="E";
    //把状态变成待审批
    public static final String HCUX_MDM_BILLING_INFO_STATUS_N = "N";
    //运输状态
    public static final String HCUX_LM_TRANSPORT_STATUS="HCUX_LM_TRANSPORT_STATUS";
    //车辆属性
    public static final String HCUX_MDM_CAR_ATTRIBUTE ="HCUX_MDM_CAR_ATTRIBUTE";
}
