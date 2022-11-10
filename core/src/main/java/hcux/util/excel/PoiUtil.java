package hcux.util.excel;


import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;


public class PoiUtil {

    private final static Logger logger = LoggerFactory.getLogger(PoiUtil.class);

    /**
     * 初始化EXCEL(sheet个数和标题)
     *
     * @param totalRowCount 总记录数
     * @param titles        标题集合
     * @return XSSFWorkbook对象
     */
    public static SXSSFWorkbook initExcel(long totalRowCount, String[] titles) {

        // 在内存当中保持 100 行 , 超过的数据放到硬盘中在内存当中保持 100 行 , 超过的数据放到硬盘中
        SXSSFWorkbook wb = new SXSSFWorkbook(100);

        long sheetCount = ((totalRowCount % ExcelConstant.PER_SHEET_ROW_COUNT == 0) ?
                (totalRowCount / ExcelConstant.PER_SHEET_ROW_COUNT) : (totalRowCount / ExcelConstant.PER_SHEET_ROW_COUNT + 1));

        //定义样式
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFillBackgroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);

        // 根据总记录数创建sheet并分配标题
        for (long i = 0; i < sheetCount; i++) {
            SXSSFSheet sheet = wb.createSheet("sheet" + (i + 1));
            SXSSFRow headRow = sheet.createRow(0);

            for (int j = 0; j < titles.length; j++) {

//                sheet.setColumnWidth(j, 20 * 256);

                SXSSFCell headRowCell = headRow.createCell(j);
                headRowCell.setCellValue(titles[j]);
                headRowCell.setCellStyle(cellStyle);
            }
        }

        return wb;
    }


    /**
     * 下载EXCEL到本地指定的文件夹
     *
     * @param wb         EXCEL对象SXSSFWorkbook
     * @param exportPath 导出路径
     */
    public static void downLoadExcelToLocalPath(SXSSFWorkbook wb, String exportPath) {
        FileOutputStream fops = null;
        try {
            fops = new FileOutputStream(exportPath);
            wb.write(fops);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != wb) {
                try {
                    wb.dispose();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (null != fops) {
                try {
                    fops.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 下载EXCEL到浏览器
     *
     * @param wb       EXCEL对象XSSFWorkbook
     * @param response
     * @param fileName 文件名称
     * @throws IOException
     */
    public static void downLoadExcelToWebsite(SXSSFWorkbook wb, HttpServletResponse response, String fileName) throws IOException {

        response.setHeader("Content-disposition", "attachment; filename="
                + new String((fileName + ".xlsx").getBytes(StandardCharsets.UTF_8), "ISO8859-1"));//设置下载的文件名

        response.setHeader("Set-Cookie", "fileDownload=true; path=/");
        try (OutputStream outputStream = response.getOutputStream()) {
            wb.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != wb) {
                try {
                    wb.dispose();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 导出Excel到本地指定路径
     *
     * @param totalRowCount           总记录数
     * @param titles                  标题
     * @param exportPath              导出路径
     * @param writeExcelDataDelegated 向EXCEL写数据/处理格式的委托类 自行实现
     * @throws Exception
     */
    public static void exportExcelToLocalPath(Integer totalRowCount, String[] titles, String exportPath, WriteExcelDataDelegated writeExcelDataDelegated) throws Exception {

        logger.info("开始导出：" + DateFormatUtils.ISO_DATETIME_FORMAT.format(new Date()));

        // 初始化EXCEL
        SXSSFWorkbook wb = PoiUtil.initExcel(totalRowCount, titles);

        // 调用委托类分批写数据
        int sheetCount = wb.getNumberOfSheets();
        for (int i = 0; i < sheetCount; i++) {
            SXSSFSheet eachSheet = wb.getSheetAt(i);

            for (int j = 1; j <= ExcelConstant.PER_SHEET_WRITE_COUNT; j++) {

                int currentPage = i * ExcelConstant.PER_SHEET_WRITE_COUNT + j;
                int pageSize = ExcelConstant.PER_WRITE_ROW_COUNT;
                int startRowCount = (j - 1) * ExcelConstant.PER_WRITE_ROW_COUNT + 1;
                int endRowCount = startRowCount + pageSize - 1;


                writeExcelDataDelegated.writeExcelData(eachSheet, startRowCount, endRowCount, currentPage, pageSize);

            }
        }


        // 下载EXCEL
        PoiUtil.downLoadExcelToLocalPath(wb, exportPath);

        logger.info("导出完成：" + DateFormatUtils.ISO_DATETIME_FORMAT.format(new Date()));
    }


    /**
     * 导出Excel到浏览器
     *
     * @param response
     * @param totalRowCount           总记录数
     * @param fileName                文件名称
     * @param titles                  标题
     * @param writeExcelDataDelegated 向EXCEL写数据/处理格式的委托类 自行实现
     * @throws Exception
     */
    public static void exportExcelToWebsite(HttpServletResponse response, long totalRowCount, String fileName, String[] titles, WriteExcelDataDelegated writeExcelDataDelegated) throws Exception {

        logger.info("开始导出：" + DateFormatUtils.ISO_DATETIME_FORMAT.format(new Date()));

        // 初始化EXCEL
        SXSSFWorkbook wb = PoiUtil.initExcel(totalRowCount, titles);


        // 调用委托类分批写数据
        int sheetCount = wb.getNumberOfSheets();
        for (int i = 0; i < sheetCount; i++) {
            SXSSFSheet eachSheet = wb.getSheetAt(i);

            int total = totalRowCount < ExcelConstant.PER_SHEET_ROW_COUNT ? (int) totalRowCount : ExcelConstant.PER_SHEET_WRITE_COUNT;
            int count = total / ExcelConstant.PER_WRITE_ROW_COUNT + 1;

            for (int j = 1; j <= count; j++) {

                int currentPage = i * ExcelConstant.PER_SHEET_WRITE_COUNT + j;
                int pageSize = ExcelConstant.PER_WRITE_ROW_COUNT;
                int startRowCount = (j - 1) * ExcelConstant.PER_WRITE_ROW_COUNT + 1;
                int end = total % pageSize == 0 ? pageSize : total % pageSize;
                int endRowCount = j == count ? startRowCount + end - 1 : startRowCount + pageSize - 1;

                writeExcelDataDelegated.writeExcelData(eachSheet, startRowCount, endRowCount, currentPage, pageSize);

            }

            // 设置自动列宽
            eachSheet.trackAllColumnsForAutoSizing();
            for (int j = 0; j < titles.length; j++) {
                eachSheet.autoSizeColumn(j, true);
            }
        }


        // 下载EXCEL
        PoiUtil.downLoadExcelToWebsite(wb, response, fileName);

        logger.info("导出完成：" + DateFormatUtils.ISO_DATETIME_FORMAT.format(new Date()));
    }


}