package hcux.util.excel;

public class ExcelConstant {
    /**
     * 每个sheet存储的记录数 100W
     */
    public static final Integer PER_SHEET_ROW_COUNT = 1000000;

    /**
     * 每次向EXCEL写入的记录数(查询每页数据大小) 10W
     */
    public static final Integer PER_WRITE_ROW_COUNT = 100000;


    /**
     * 每个sheet的写入次数 10
     */
    public static final Integer PER_SHEET_WRITE_COUNT = PER_SHEET_ROW_COUNT / PER_WRITE_ROW_COUNT;
}
