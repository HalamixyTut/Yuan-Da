package hcux.cs.util;

public class CsConstant {
    private CsConstant() {
    }

    // 编码规则-合同执行情况-运输单号序列
    public static final String HCUX_CS_CONTRACT_TRANSPORT_S = "HCUX_CS_CONTRACT_TRANSPORT_S";
    // 系统管理-板块
    public static final String HCUX_SYS_PLATE = "HCUX_SYS_PLATE";
    // 快递公司
    public static final String HCUX_CS_EXPRESS_COMPANY = "HCUX_CS_EXPRESS_COMPANY";
    //板块不存在，未导入，包括：
    public static final String HCUX_CS_IMVOICE_PLATE_NOT_EXIST="hcux.cs.invoice.plate_not_exist";
    //客户名称不存在，未导入，包括：
    public static final String HCUX_CS_INVOICE_CUSTOM_NAME_NOT_EXIST = "hcux.cs.invoice.custom_name_not_exist";
    //客户名称对应多个账户，未导入，包括：
    public static final String HCUX_CS_INVOICE_MULTIPLE_CUSTOM_NAME = "hcux.cs.invoice.multiple_custom_name";
    //导入成功，共
    public static final String HCUX_EXCEL_IMPORT_SUCCESS = "hcux.excel.import.success";
    // 。请联系管理员！
    public static final String HCUX_CONTACT_ADMINISTRATOR = "hcux.contact_administrator";
}
