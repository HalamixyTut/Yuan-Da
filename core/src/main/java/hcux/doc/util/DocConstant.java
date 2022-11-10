package hcux.doc.util;

public class DocConstant {
    private DocConstant() {
    }

    // 快码 托单-运输方式
    public static final String CODE_HCUX_DOC_TRANSPORT_WAY = "HCUX_DOC_TRANSPORT_WAY";

    // 快码 报关单-币制
    public static final String CODE_HCUX_DOC_CURRENCY_SYSTEM = "HCUX_DOC_CURRENCY_SYSTEM";

    // 快码 托单-运费条款
    public static final String CODE_HCUX_DOC_FREIGHT_CLAUSE = "HCUX_DOC_FREIGHT_CLAUSE";
    //excel版本
    public static final String EXTENSION_XLS = "xls";

    public static final String EXTENSION_XLSX = "xlsx";
    //是否快码的代码
    public static final String YES_NO_CODE = "SYS.YES_NO";
    //运输方式的代码
    public static final String HCUX_DOC_TRANSPORT_WAY = "HCUX_DOC_TRANSPORT_WAY";
    //监管方式的代码
    public static final String HCUX_DOC_SUPERVISION_MODE = "HCUX_DOC_SUPERVISION_MODE";
    //包装种类的代码
    public static final String HCUX_DOC_PACKAGE_TYPE = "HCUX_DOC_PACKAGE_TYPE";
    //成交方式代码
    public static final String HCUX_DOC_TRANSACTION_MODE = "HCUX_DOC_TRANSACTION_MODE";
    //征免方式代码
    public static final String HCUX_DOC_EXEMPTION_WAY = "HCUX_DOC_EXEMPTION_WAY";
    //件数单位代码
    public static final String HCUX_DOC_PACKAGE_NUMBER_UNIT = "HCUX_DOC_PACKAGE_NUMBER_UNIT";
    //征免性质代码
    public static final String HCUX_DOC_EXEMPTION_NATURE = "HCUX_DOC_EXEMPTION_NATURE";
    //日期格式
    public static final String DATE_FORMATE = "yyyy-MM-dd";

    // 托单 唛头图片 附件业务编码
    public static final String FILE_BOOKING_SHIPPING_MARK = "BOOKING_SHIPPING_MARK";
    //有重复数据
    public static final String HCUX_DOC_DUPLICATE_DATA = "hcux.doc.duplicate_data";
    //行的件数单位所填值不在值列表中,
    public static final String HCUX_DOC_UNIT_NOT_IN_LIST = "hcux.doc.unit_not_in_list";
    //第
    public static final String HCUX_DOC_THE_FIRST = "hcux.doc.the_first";
    //包装种类所填值不在值列表中,
    public static final String HCUX_DOC_PACKAGE_TYPE_NOT_IN_LIST = "hcux.doc.package_type_not_in_list";
    //成交方式所填值不在值列表中，
    public static final String HCUX_DOC_TRANSACTION_MODE_NOT_IN_LIST = "hcux.doc.transaction_mode_not_in_list";
    //运输方式所填值不在值列表中，
    public static final String HCUX_DOC_TRANSPORT_WAY_NOT_IN_LIST = "hcux.doc.transport_way_not_in_list";
    //项目号为空
    public static final String HCUX_DOC_PROJECT_NUMBER_IS_NULL = "hcux.doc.project_number_is_null";
    //报关图片上传的soruceType
    public static final String HCUX_DOC_CUSTOMS_HEADER_SOURCE_TYPE = "CUSTOMS_SHIPPING_MARK";
    //托单图片上传的soruceType
    public static final String HCUX_DOC_BOOKING_NOTE_SOURCE_TYPE = "BOOKING_SHIPPING_MARK";
    //唛头见发票
    public static final String HCUX_DOC_MARKS = "hcux.doc.marks";
    //危险货物UN号
    public static final String CODE_HCUX_DANGEROUS_GOODS_UN_NO = "HCUX_DANGEROUS_GOODS_UN_NO";
    //危险货物类别
    public static final String CODE_HCUX_DANGEROUS_GOODS_GENRES = "HCUX_DANGEROUS_GOODS_GENRES";
    //省市区
    public static final String CODE_HCUX_LM_ORDER_ADDRESS = "HCUX_LM.ORDER_ADDRESS";

    //报关状态
    public interface HCUX_DOC_STATUS {
        /**
         * 新建(未做)
         */
        String A = "0";
        /**
         * 准备
         */
        String B = "1"; // 2019-06-06 需求变更---去掉准备状态，综合状态为准备报关，报关状态为待提交
        /**
         * 待提交
         */
        String C = "2";
        /**
         * 审批中
         */
        String D = "3";

        /**
         * 已审批
         */
        String E = "4";

        /**
         * 已拒绝
         */
        String F = "5";

        /**
         * 已完结
         */
        String G = "6";

        /**
         * 已改单
         */
        String H = "7";
    }

    //操作类型
    public interface HCUX_DOC_RECORD_OPERATION {
        /**
         * 新建
         */
        String CREATE = "CREATE";
        /**
         * 提交
         */
        String SUMMIT = "SUMMIT";
        /**
         * 复核同意
         */
        String APPROVE_AGREE = "APPROVE_AGREE";
        /**
         * 复核拒绝
         */
        String APPROVE_REJECT = "APPROVE_REJECT";
        /**
         * 修改
         */
        String MODIFY = "MODIFY";
        /**
         * 海关改单
         */
        String CUSTOMS_MODIFY = "CUSTOMS_MODIFY";
    }

    //托单-件数单位
    public interface PACKAGE_NUMBER_UNIT_CODE {
        /**
         * 混装
         */
        String A8 = "8";
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

    //综合状态
    public interface HCUX_DOC_SYNTHESIS_STATUS {
        /**
         * 已订舱
         */
        String A = "0";
        /**
         * 已放舱
         */
        String B = "1";
        /**
         * 准备做箱
         */
        String C = "2";
        /**
         * 准备报关
         */
        String D = "3";

        /**
         * 报关中
         */
        String E = "4";

        /**
         * 已开船
         */
        String F = "5";

        /**
         * 运费已付
         */
        String G = "6";

        /**
         * 提单已传
         */
        String H = "7";

        /**
         * 已作废
         */
        String I = "8";

        /**
         * 已订舱/已放舱/准备做箱/准备报关/报关中
         */
        String J = "A";

        /**
         * 新建
         */
        String K = "B";
    }

}
