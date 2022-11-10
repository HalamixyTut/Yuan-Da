package hcux.eps.util;

import hcux.eps.dto.EpsCustomsDetail;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.hand.hap.core.BaseConstants.DATE_FORMAT;
import static hcux.doc.util.DocConstant.EXTENSION_XLS;
import static hcux.doc.util.DocConstant.EXTENSION_XLSX;
import static hcux.eps.util.EpsConstant.HCUX_EPS_ROW;
import static hcux.eps.util.EpsConstant.HCUX_EPS_THE_FIRST;

public class CommonUtil {
    /**
     * 用于判断BigDecimal类型的数据是否为空，为空的话，返回0，不为空返回本值
     *
     * @param number
     * @return
     */
    public static BigDecimal numberIsNull(BigDecimal number) {
        if (number == null) {
            return new BigDecimal(0);
        } else {
            return number;
        }
    }

    /**
     * 用于校验excel导入时某行数据是否为空
     *
     * @param row
     * @return
     */
    public static boolean rowIsNull(Row row) {
        boolean flag = true;
        for (int i = 0; i < 26; i++) {
            if (!hcux.doc.util.CommonUtil.objectIsNull(row.getCell(i))) {
                flag = false;
            }
        }
        return flag;
    }

    public static EpsCustomsDetail rowToCustomsDetail(Row row) throws ParseException {
        DecimalFormat decimalFormat = new DecimalFormat("###################.###########");

        Date declareDate = null;
        Date importExportDate = null;
        String projectNumber = null;
        if (!hcux.doc.util.CommonUtil.objectIsNull(row.getCell(3))) {
            if (row.getCell(3).getCellTypeEnum().equals(CellType.STRING)) {
                declareDate = new SimpleDateFormat(DATE_FORMAT).parse(row.getCell(3).getStringCellValue());
            } else {
                declareDate = row.getCell(3).getDateCellValue();
            }
        }
        if (!hcux.doc.util.CommonUtil.objectIsNull(row.getCell(4))) {
            if (row.getCell(4).getCellTypeEnum().equals(CellType.STRING)) {
                importExportDate = new SimpleDateFormat(DATE_FORMAT).parse(row.getCell(4).getStringCellValue());
            } else {
                importExportDate = row.getCell(4).getDateCellValue();
            }
        }
        if (!hcux.doc.util.CommonUtil.objectIsNull(row.getCell(11))) {
            if (row.getCell(11).getCellTypeEnum().equals(CellType.STRING)) {
                projectNumber = row.getCell(11).getStringCellValue();
            } else {
                projectNumber = decimalFormat.format(row.getCell(11).getNumericCellValue());
            }
        }

        EpsCustomsDetail epsCustomsDetail =  new EpsCustomsDetail() {{
            setCustomsNumber(hcux.doc.util.CommonUtil.objectIsNull(row.getCell(0)) == true ? null : row.getCell(0).toString());
            setImportExport(hcux.doc.util.CommonUtil.objectIsNull(row.getCell(1)) == true ? null : row.getCell(1).toString());
            setManualNumber(hcux.doc.util.CommonUtil.objectIsNull(row.getCell(2)) == true ? null : row.getCell(2).toString());
            setImportExportPort(hcux.doc.util.CommonUtil.objectIsNull(row.getCell(5)) == true ? null : row.getCell(5).toString());
            setDeclareUnit(hcux.doc.util.CommonUtil.objectIsNull(row.getCell(6)) == true ? null : row.getCell(6).toString());
            setContractNumber(hcux.doc.util.CommonUtil.objectIsNull(row.getCell(7)) == true ? null : row.getCell(7).toString().trim());
            setCountry(hcux.doc.util.CommonUtil.objectIsNull(row.getCell(8)) == true ? null : row.getCell(8).toString());
            setTransactionMode(hcux.doc.util.CommonUtil.objectIsNull(row.getCell(9)) == true ? null : row.getCell(9).toString());
            setDealWay(hcux.doc.util.CommonUtil.objectIsNull(row.getCell(10)) == true ? null : row.getCell(10).toString());
            setGoodsName(hcux.doc.util.CommonUtil.objectIsNull(row.getCell(12)) == true ? null : row.getCell(12).toString());
            setGoodsNumber(hcux.doc.util.CommonUtil.objectIsNull(row.getCell(13)) == true ? null : row.getCell(13).toString());
            setSpecificationType(hcux.doc.util.CommonUtil.objectIsNull(row.getCell(14)) == true ? null : row.getCell(14).toString());
            setAmount(hcux.doc.util.CommonUtil.objectIsNull(row.getCell(15)) == true ? null : Float.parseFloat(row.getCell(15).toString()));
            setUnit(hcux.doc.util.CommonUtil.objectIsNull(row.getCell(16)) == true ? null : row.getCell(16).toString());
            setFirstAmount(hcux.doc.util.CommonUtil.objectIsNull(row.getCell(17)) == true ? null : Float.parseFloat(row.getCell(17).toString()));
            setFirstUnit(hcux.doc.util.CommonUtil.objectIsNull(row.getCell(18)) == true ? null : row.getCell(18).toString());
            setSecondAmount(hcux.doc.util.CommonUtil.objectIsNull(row.getCell(19)) == true ? null : Float.parseFloat(row.getCell(19).toString()));
            setSecondUnit(hcux.doc.util.CommonUtil.objectIsNull(row.getCell(20)) == true ? null : row.getCell(20).toString());
            setCurrencySystem(hcux.doc.util.CommonUtil.objectIsNull(row.getCell(21)) == true ? null : row.getCell(21).toString());
            setUnitPrice(hcux.doc.util.CommonUtil.objectIsNull(row.getCell(22)) == true ? null : Double.parseDouble(row.getCell(22).toString()));
            setTotalPrice(hcux.doc.util.CommonUtil.objectIsNull(row.getCell(23)) == true ? null : Double.parseDouble(row.getCell(23).toString()));
            setBillNumber(hcux.doc.util.CommonUtil.objectIsNull(row.getCell(24)) == true ? null : row.getCell(24).toString().trim());
            setBoxNumber(hcux.doc.util.CommonUtil.objectIsNull(row.getCell(25)) == true ? null : row.getCell(25).toString());
            setVesselVoyage(hcux.doc.util.CommonUtil.objectIsNull(row.getCell(26)) == true ? null : row.getCell(26).toString());
            setIsExported("N");
        }};

        epsCustomsDetail.setDeclareDate(declareDate);
        epsCustomsDetail.setImportExportDate(importExportDate);
        epsCustomsDetail.setProjectNumber(projectNumber);
        return epsCustomsDetail;
    }

    /**
     * str转换为date类型
     *
     * @param date
     * @param formater
     * @return
     */
    public static Date str2Date(String date, String formater) {
        if (date == null) {
            return null;
        }
        if (formater == null) {
            formater = "yyyy-MM-dd";
        }
        Date date1 = null;
        SimpleDateFormat sdf = new SimpleDateFormat(formater);
        try {
            date1 = sdf.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date1;
    }

    /**
     * 校验合同号的规则
     *
     * @param contractNumber
     * @param rowNumber
     * @return
     */
    public static String checkContractNumber(String contractNumber, int rowNumber, MessageSource messageSource, HttpServletRequest request) {
        Pattern p = Pattern.compile("^[PA][BD]1250[1-9A-Z][A-Z]{2}\\d{5}[A-Z]$");
        Matcher m = p.matcher(contractNumber);

        if (request != null) {
            if (!m.find()) {
                return messageSource.getMessage(HCUX_EPS_THE_FIRST, null, RequestContextUtils.getLocale(request)) + rowNumber + messageSource.getMessage(HCUX_EPS_ROW, null, RequestContextUtils.getLocale(request));
            } else {
                return "";
            }
        } else {
            if (!m.find()) {
                return "Non-conformity";
            } else {
                return "";
            }
        }
    }

    /**
     * 判断是英文
     *
     * @param charaString
     * @return
     */
    public static boolean isEnglish(String charaString) {

        return charaString.matches("^[a-zA-Z]*");

    }

    /**
     * excel导出方法
     *
     * @param request
     * @param response
     * @param list
     * @param fileName
     * @param title
     */
    public static void createExcel(HttpServletRequest request, HttpServletResponse response,
                                   List<EpsCustomsDetail> list, String fileName, List<String> title) {
        try {
            // 创建Excel的工作书册 Workbook,对应到一个excel文档
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFCellStyle style = workbook.createCellStyle();
            XSSFDataFormat format = workbook.createDataFormat();
            XSSFCellStyle styleDate = workbook.createCellStyle();
            styleDate.setDataFormat(format.getFormat("yyyy/MM/dd"));
            // 生成一个字体
            XSSFFont font = workbook.createFont();
            // 字体增粗
            //font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            font.setFontHeightInPoints((short) 11);
            style.setFont(font);
            // 创建Excel的工作sheet,对应到一个excel文档的tab
            XSSFSheet sheet = workbook.createSheet("sheet1");
            // 创建Excel的sheet的一行 (表头)
            XSSFRow row = sheet.createRow(0);
            // 表头内容填充
            for (int i = 0; i < title.size(); i++) {
                // 设置excel每列宽度
                sheet.setColumnWidth(i, 5000);
                XSSFCell cell = row.createCell(i);
                cell.setCellValue(title.get(i));
                cell.setCellStyle(style);
            }
            // 创建内容行
            XSSFCellStyle cellStyle = workbook.createCellStyle();
            // cellStyle.setWrapText(true);// 自动换行
            // cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
            for (int j = 0; j < list.size(); j++) {
                XSSFRow contentRow = sheet.createRow(j + 1);
                for (int k = 0; k < title.size(); k++) {
                    XSSFCell cell = contentRow.createCell(k);
                    EpsCustomsDetail detail = list.get(j);
                    switch (k) {
                        case 0:
                            if (detail.getCustomsNumber() != null && !("").equals(detail.getCustomsNumber())) {// 报关单号
                                cell.setCellValue(detail.getCustomsNumber());
                                cell.setCellStyle(cellStyle);
                            }
                            break;
                        case 1:
                            if (detail.getImportExport() != null && !("").equals(detail.getImportExport())) {//进出口
                                cell.setCellValue(detail.getImportExport());
                                cell.setCellStyle(cellStyle);
                            }
                            break;
                        case 2:
                            if (detail.getManualNumber() != null && !("").equals(detail.getManualNumber())) {//手册号
                                cell.setCellValue(detail.getManualNumber());
                                cell.setCellStyle(cellStyle);
                            }
                            break;
                        case 3:
                            if (detail.getDeclareDate() != null && !("").equals(detail.getDeclareDate())) {//申报日期
                                cell.setCellValue(detail.getDeclareDate());
                                cell.setCellStyle(styleDate);
                            }
                            break;
                        case 4:
                            if (detail.getImportExportDate() != null && !("").equals(detail.getImportExportDate())) {//进出口日期
                                cell.setCellValue(detail.getImportExportDate());
                                cell.setCellStyle(styleDate);
                            }
                            break;
                        case 5:
                            if (detail.getImportExportPort() != null && !("").equals(detail.getImportExportPort())) {//进出口口岸
                                cell.setCellValue(detail.getImportExportPort());
                                cell.setCellStyle(cellStyle);
                            }
                            break;
                        case 6:
                            if (detail.getDeclareUnit() != null && !("").equals(detail.getDeclareUnit())) {//申报单位
                                cell.setCellValue(detail.getDeclareUnit());
                                cell.setCellStyle(cellStyle);
                            }
                            break;
                        case 7:
                            if (detail.getContractNumber() != null && !("").equals(detail.getContractNumber())) {//合同号
                                cell.setCellValue(detail.getContractNumber());
                                cell.setCellStyle(cellStyle);
                            }
                            break;
                        case 8:
                            if (detail.getInvoiceNumber() != null && !("").equals(detail.getInvoiceNumber())) {//发票号
                                cell.setCellValue(detail.getInvoiceNumber());
                                cell.setCellStyle(cellStyle);
                            }
                            break;
                        case 9:
                            if (detail.getCountry() != null && !("").equals(detail.getCountry())) {//国别
                                cell.setCellValue(detail.getCountry());
                                cell.setCellStyle(cellStyle);
                            }
                            break;
                        case 10:
                            if (detail.getTransactionMode() != null && !("").equals(detail.getTransactionMode())) {//贸易方式
                                cell.setCellValue(detail.getTransactionMode());
                                cell.setCellStyle(cellStyle);
                            }
                            break;
                        case 11:
                            if (detail.getDealWay() != null && !("").equals(detail.getDealWay())) {//成交方式
                                cell.setCellValue(detail.getDealWay());
                                cell.setCellStyle(cellStyle);
                            }
                            break;
                        case 12:
                            if (detail.getProjectNumber() != null && !("").equals(detail.getProjectNumber())) {//项号
                                cell.setCellValue(detail.getProjectNumber());
                                cell.setCellStyle(cellStyle);
                            }
                            break;
                        case 13:
                            if (detail.getGoodsName() != null && !("").equals(detail.getGoodsName())) {//商品名称
                                cell.setCellValue(detail.getGoodsName());
                                cell.setCellStyle(cellStyle);
                            }
                            break;
                        case 14:
                            if (detail.getGoodsNumber() != null && !("").equals(detail.getGoodsNumber())) {//商品编号
                                cell.setCellValue(detail.getGoodsNumber());
                                cell.setCellStyle(cellStyle);
                            }
                            break;
                        case 15:
                            if (detail.getSpecificationType() != null && !("").equals(detail.getSpecificationType())) {//规格型号
                                cell.setCellValue(detail.getSpecificationType());
                                cell.setCellStyle(cellStyle);
                            }
                            break;
                        case 16:
                            if (detail.getAmount() != null && !("").equals(detail.getAmount())) {//数量
                                cell.setCellValue(detail.getAmount());
                                cell.setCellStyle(cellStyle);
                            }
                            break;
                        case 17:
                            if (detail.getUnit() != null && !("").equals(detail.getUnit())) {//单位
                                cell.setCellValue(detail.getUnit());
                                cell.setCellStyle(cellStyle);
                            }
                            break;
                        case 18:
                            if (detail.getFirstAmount() != null && !("").equals(detail.getFirstAmount())) {//第一数量
                                cell.setCellValue(detail.getFirstAmount());
                                cell.setCellStyle(cellStyle);
                            }
                            break;
                        case 19:
                            if (detail.getFirstUnit() != null && !("").equals(detail.getFirstUnit())) {//第一计量单位
                                cell.setCellValue(detail.getFirstUnit());
                                cell.setCellStyle(cellStyle);
                            }
                            break;
                        case 20:
                            if (detail.getSecondAmount() != null && !("").equals(detail.getSecondAmount())) {//第二数量
                                cell.setCellValue(detail.getSecondAmount());
                                cell.setCellStyle(cellStyle);
                            }
                            break;
                        case 21:
                            if (detail.getSecondUnit() != null && !("").equals(detail.getSecondUnit())) {//第二计量单位
                                cell.setCellValue(detail.getSecondUnit());
                                cell.setCellStyle(cellStyle);
                            }
                            break;
                        case 22:
                            if (detail.getCurrencySystem() != null && !("").equals(detail.getCurrencySystem())) {//成交币制
                                cell.setCellValue(detail.getCurrencySystem());
                                cell.setCellStyle(cellStyle);
                            }
                            break;
                        case 23:
                            if (detail.getUnitPrice() != null && !("").equals(detail.getUnitPrice())) {//单价
                                cell.setCellValue(detail.getUnitPrice());
                                cell.setCellStyle(cellStyle);
                            }
                            break;
                        case 24:
                            if (detail.getTotalPrice() != null && !("").equals(detail.getTotalPrice())) {//总价
                                cell.setCellValue(detail.getTotalPrice());
                                cell.setCellStyle(cellStyle);
                            }
                            break;
                        case 25:
                            if (detail.getBillNumber() != null && !("").equals(detail.getBillNumber())) {//提单号
                                cell.setCellValue(detail.getBillNumber());
                                cell.setCellStyle(cellStyle);
                            }
                            break;
                        case 26:
                            if (detail.getBoxNumber() != null && !("").equals(detail.getBoxNumber())) {//集装箱号
                                cell.setCellValue(detail.getBoxNumber());
                                cell.setCellStyle(cellStyle);
                            }
                            break;
                        default:
                            break;
                    }
                }
            }

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            try {
                workbook.write(os);
            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] content = os.toByteArray();
            InputStream is = new ByteArrayInputStream(content);
            // 处理浏览器差异导致的下载文件名称乱码的问题
            String userAgent = request.getHeader("USER-AGENT");
            String enocodeType = "";
            if (StringUtils.contains(userAgent, "Mozilla")) {// google,火狐浏览器
                enocodeType = "iso8859-1";
            } else {
                enocodeType = "utf8";
            }

            response.setContentType("application/x-download");
            fileName = response.encodeURL(new String(fileName.getBytes(), enocodeType)); // 保存的文件名,必须和页面编码一致,否则乱码
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");
            response.setContentLength(os.size());
            ServletOutputStream out = response.getOutputStream();
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;
            try {
                bis = new BufferedInputStream(is);
                bos = new BufferedOutputStream(out);
                byte[] buff = new byte[2048];
                int bytesRead;
                while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                    bos.write(buff, 0, bytesRead);
                }
                bos.flush();
            } catch (final IOException e) {
                throw e;
            } finally {
                if (bis != null) {
                    bis.close();
                }
                if (bos != null) {
                    bos.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 得到excel导入的所有提单号
     *
     * @param sheet
     * @return
     */
    public static HashSet<String> sheet2billNumberList(Sheet sheet) {
        HashSet<String> billNumberList = new HashSet<String>();
        int firstRowNum = sheet.getFirstRowNum();
        //获取sheet中最后一行行号
        int lastRowNum = sheet.getLastRowNum();
        int i = firstRowNum + 1;
        for (; i <= lastRowNum; i++) {
            if (sheet.getRow(i).getCell(24) != null && !"".equals(sheet.getRow(i).getCell(24).toString())) {
                billNumberList.add(sheet.getRow(i).getCell(24).toString().trim());
            }
        }
        return billNumberList;
    }

    public static HashSet<String> sheet2customsNumberList(Sheet sheet) {
        HashSet<String> customsNumberList = new HashSet<String>();
        int firstRowNum = sheet.getFirstRowNum();
        //获取sheet中最后一行行号
        int lastRowNum = sheet.getLastRowNum();
        int i = firstRowNum + 1;
        for (; i <= lastRowNum; i++) {
            if (sheet.getRow(i).getCell(0) != null && !"".equals(sheet.getRow(i).getCell(0).toString())) {
                customsNumberList.add(sheet.getRow(i).getCell(0).toString().trim());
            }
        }
        return customsNumberList;
    }

    public static HashSet<String> sheet2ContractNumberList(Sheet sheet) {
        HashSet<String> customsNumberList = new HashSet<String>();
        int firstRowNum = sheet.getFirstRowNum();
        //获取sheet中最后一行行号
        int lastRowNum = sheet.getLastRowNum();
        int i = firstRowNum + 1;
        for (; i <= lastRowNum; i++) {
            if (sheet.getRow(i).getCell(7) != null && !"".equals(sheet.getRow(i).getCell(7).toString())) {
                customsNumberList.add(sheet.getRow(i).getCell(7).toString().trim());
            }
        }
        return customsNumberList;
    }

    /**
     * 获取windows共享文件
     *
     * @param remoteUrl
     * @param username
     * @param username
     * @return
     */
    public static Workbook getShareFile(String remoteUrl, String username, String password) throws Exception {
        Workbook workbook = null;
        InputStream in = null;

        try {
            // authentication
            NtlmPasswordAuthentication ntPassAuth = new NtlmPasswordAuthentication(null, username, password);
            // access cifs share
            String url = null;
            DateFormat dateFormat=new SimpleDateFormat(DATE_FORMAT);
            Calendar now =Calendar.getInstance();
            now.add(Calendar.DATE, -1);
            String yesterdayDate=dateFormat.format(now.getTime());
            String fileName = yesterdayDate + "." + EXTENSION_XLSX;
            url = remoteUrl + fileName;
            SmbFile smbFile = new SmbFile(url, ntPassAuth);

            if (smbFile.exists()) {
                in = new BufferedInputStream(new SmbFileInputStream(smbFile));
                workbook = new XSSFWorkbook(in);
            } else {
                fileName = yesterdayDate + "." + EXTENSION_XLS;
                url = remoteUrl + fileName;
                smbFile = new SmbFile(url, ntPassAuth);

                if (smbFile.exists()) {
                    in = new BufferedInputStream(new SmbFileInputStream(smbFile));
                    workbook = new HSSFWorkbook(in);
                } else {
                    throw new Exception("共享文件不存在");
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (in != null) {
                in.close();
            }
        }

        return workbook;
    }
}
