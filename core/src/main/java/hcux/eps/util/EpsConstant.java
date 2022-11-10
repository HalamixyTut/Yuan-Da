package hcux.eps.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EpsConstant {
    //定时器中前半个小时
    public static final long HCUX_EPS_BACK_TIME = 30 * 60 * 1000;
    //agent_type值为‘FIXED CNY AMOUNT’
    public static final String FIXED_CNY_AMOUNT = "FIXED CNY AMOUNT";
    //agent_type值为"FIXED AMOUNT"
    public static final String FIXED_INVOICE_PROPORTION = "FIXED INVOICE PROPORTION";
    //agent_type值为"FIXED PROPORTION"
    public static final String FIXED_PROPORTION = "FIXED PROPORTION";
    //agent_type值为"EXCHANGE RATE"
    public static final String EXCHANGE_RATE = "EXCHANGE RATE";
    //1.报关单号相同，未导入，包括：
    public static final String HCUX_EPS_SAME_AS_CUSTOMS_DECLARATION = "hcux.eps.same_as_customs_declaration";
    //2.合同号相同，已导入，包括：
    public static final String HCUX_EPS_SAME_AS_CONTRACT_NUMBER = "hcux.eps_same_as_contract_number";
    //3.合同号不符规则，已导入，包括：
    public static final String HCUX_EPS_CONTRACT_NUMBER_NOT_IN_RULE = "hcux.eps_contract_number_not_in_rule";
    //行，
    public static final String HCUX_EPS_ROW = "hcux.eps_row";
    //5.报关单号相同，未导入，包括：
    public static final String HCUX_EPS_SAME_AS_CUSTOMS_NUMBER = "hcux.eps_same_as_customs_number";
    /**
     * 模板下载时得到请求头的信息
     */
    public static final String USER_ANENT = "USER-AGENT";
    /**
     * IE浏览器
     */
    public static final String MSIE = "MSIE";
    public static final String CONTENT_DISPOSITION = "Content-Disposition";
    public static final String ATTACHMENT_FILE_NAME = "attachment;filename=";
    /**
     * 火狐浏览器
     */
    public static final String MOZILLA = "MOZILLA";
    public static final List<String> TITLE = new ArrayList<String>(Arrays.asList(
            "报关单号", "进/出口", "手册号",
            "申报日期", "进出口日期", "进出口口岸",
            "申报单位", "合同号", "发票号", "国别", "贸易方式", "成交方式",
            "项号", "商品名称", "商品编号",
            "规格型号", "数量", "单位",
            "第一数量", "第一计量单位", "第二数量",
            "第二计量单位", "成交币制", "单价",
            "总价", "提单号", "集装箱号"));
    public static final String CODE_UTF_8 = "UTF8";
    public static final String CODE_ISO8859_1 = "ISO8859-1";
    //第
    public static final String HCUX_EPS_THE_FIRST = "hcux.eps_the_first";
    public static final String HCUX_EPS_SAME_AS_CUSTOMS_DECLARATION1 = "hcux.eps.same_as_customs_declaration1";
    //报关明细头合同状态
    public static final String HCUX_EPS_CUSTOMS_DETAIL_STATUS = "HCUX_EPS_CUSTOMS_DETAIL_STATUS";
    //报关明细是否导入
    public static final String HCUX_EPS_CUSTOMS_DETAIL_IS_EXPORTED_Y = "Y";
    public static final String HCUX_EPS_CUSTOMS_DETAIL_IS_EXPORTED_N = "N";
}
