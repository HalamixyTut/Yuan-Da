package hcux.doc.util;

import com.hand.hap.attachment.dto.SysFile;
import com.hand.hap.core.BaseConstants;
import com.hand.hap.system.dto.DTOStatus;
import hcux.core.exception.HcuxException;
import hcux.doc.dto.DocBookingNote;
import hcux.doc.dto.DocCustomsHeader;
import hcux.doc.dto.DocCustomsLine;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.POIXMLDocumentPart;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTMarker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static hcux.doc.util.DocConstant.*;

public class CommonUtil {
    private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);

    /**
     * 判断excel文件的版本
     *
     * @param file
     * @return
     */
    public static Workbook fileType(MultipartFile file) throws Exception {
        Workbook workbook = null;
        Sheet sheet = null;
        if (!file.getOriginalFilename().endsWith(EXTENSION_XLS) && !file.getOriginalFilename().endsWith(EXTENSION_XLSX)) {
            throw new Exception("hcux.doc.file_formate_error");
        }
        if (file.getOriginalFilename().endsWith(EXTENSION_XLS)) {
            workbook = new HSSFWorkbook(file.getInputStream());
        } else if (file.getOriginalFilename().endsWith(EXTENSION_XLSX)) {
            workbook = new XSSFWorkbook(file.getInputStream());
        }
        return workbook;
    }

    /**
     * excel导入，把一行文件数据转换成DocCustomsHeader
     *
     * @param row
     * @return
     */
    public static DocCustomsHeader rowToDocCustomsHeader(Row row) {
        return new DocCustomsHeader() {{
            setInvoiceNumber(row.getCell(0) == null ? null : row.getCell(0).toString());
            setContractNumber(row.getCell(1) == null ? null : row.getCell(1).toString());
            setInvoiceDate(row.getCell(2) == null ? null : str2Date(row.getCell(2).toString(), null));
            setSailingDate(row.getCell(3) == null ? null : str2Date(row.getCell(3).toString(), null));
            setShipperName(row.getCell(4) == null ? null : row.getCell(4).toString());
            setShipperCode(row.getCell(5) == null ? null : row.getCell(5).toString());
            setExitPort(row.getCell(6) == null ? null : row.getCell(6).toString());
            setExitDate(row.getCell(7) == null ? null : str2Date(row.getCell(7).toString(), null));
            setRecordNumber(row.getCell(8) == null ? null : row.getCell(8).toString());
            setRecordDate(row.getCell(9) == null ? null : str2Date(row.getCell(9).toString(), null));
            setConsignee(row.getCell(10) == null ? null : row.getCell(10).toString());
            setTransportWay(row.getCell(11) == null ? null : row.getCell(11).toString());
            setVesselVoyage(row.getCell(12) == null ? null : row.getCell(12).toString());
            setDeliveryNumber(row.getCell(13) == null ? null : row.getCell(13).toString());
            setProductionUnit(row.getCell(14) == null ? null : row.getCell(14).toString());
            setProductionUnitCode(row.getCell(15) == null ? null : row.getCell(15).toString());
            setSupervisionMode(row.getCell(16) == null ? null : row.getCell(16).toString());
            setExemptionNature(row.getCell(17) == null ? null : row.getCell(17).toString());
            setLicenseKey(row.getCell(18) == null ? null : row.getCell(18).toString());
            setTradingCountry(row.getCell(19) == null ? null : row.getCell(19).toString());
            setArrivalCountry(row.getCell(20) == null ? null : row.getCell(20).toString());
            setDischargePort(row.getCell(21) == null ? null : row.getCell(21).toString());
            setDestinationPort(row.getCell(22) == null ? null : row.getCell(22).toString());
            setDeparturePort(row.getCell(23) == null ? null : row.getCell(23).toString());
            setPackageType(row.getCell(24) == null ? null : row.getCell(24).toString());
            setTransactionMode(row.getCell(25) == null ? null : row.getCell(25).toString());
            setFreight(row.getCell(26) == null ? null : Double.parseDouble(row.getCell(26).toString()));
            setPremium(row.getCell(27) == null ? null : Double.parseDouble(row.getCell(27).toString()));
            setIncidental(row.getCell(28) == null ? null : Double.parseDouble(row.getCell(28).toString()));
            setAttachedDocument(row.getCell(29) == null ? null : row.getCell(29).toString());
            setShippingMark(row.getCell(30) == null ? null : row.getCell(30).toString());
            setSpecialRelation(row.getCell(31) == null ? null : row.getCell(31).toString());
            setPriceImpact(row.getCell(32) == null ? null : row.getCell(32).toString());
            setPaymentRoyalties(row.getCell(33) == null ? null : row.getCell(33).toString());
        }};
    }

    /**
     * excel导入，把一行文件数据转换成DocCustomsLine
     *
     * @param row
     * @return
     */
    public static DocCustomsLine rowToDocCustomsLine(Row row) {
        return new DocCustomsLine() {{
            setGoodsNumber(row.getCell(34) == null ? null : row.getCell(34).toString());
            setProductNameCn(row.getCell(35) == null ? null : row.getCell(35).toString());
            setProductNameEn(row.getCell(36) == null ? null : row.getCell(36).toString());
            setCustomsAmountOne(row.getCell(37) == null ? null : Double.parseDouble(row.getCell(37).toString()));
            setCustomsUnitOne(row.getCell(38) == null ? null : row.getCell(38).toString());
            setCustomsAmountTwo(row.getCell(39) == null ? null : Double.parseDouble(row.getCell(39).toString()));
            setCustomsUnitTwo(row.getCell(40) == null ? null : row.getCell(40).toString());
            setCustomsAmountThree(row.getCell(41) == null ? null : Double.parseDouble(row.getCell(41).toString()));
            setCustomsUnitThree(row.getCell(42) == null ? null : row.getCell(42).toString());
            setPackageNumber(row.getCell(43) == null ? null : Double.parseDouble(row.getCell(43).toString()));
            setPackageNumberUnit(row.getCell(44) == null ? null : row.getCell(44).toString());
            setValuationAmount(row.getCell(45) == null ? null : Double.parseDouble(row.getCell(45).toString()));
            setUnitPrice(row.getCell(46) == null ? null : Double.parseDouble(row.getCell(46).toString()));
            setTotalPrice(row.getCell(47) == null ? null : Double.parseDouble(row.getCell(47).toString()));
//            setCurrencySystem(row.getCell(48) == null ? null : row.getCell(48).toString());
            setGrossWeight(row.getCell(49) == null ? null : Double.parseDouble(row.getCell(49).toString()));
            setNetWeight(row.getCell(50) == null ? null : Double.parseDouble(row.getCell(50).toString()));
            setVolume(row.getCell(51) == null ? null : Double.parseDouble(row.getCell(51).toString()));
            setOriginCountry(row.getCell(52) == null ? null : row.getCell(52).toString());
            setDestinationCountry(row.getCell(53) == null ? null : row.getCell(53).toString());
            setOriginPlace(row.getCell(54) == null ? null : row.getCell(54).toString());
            setExemptionWay(row.getCell(55) == null ? null : row.getCell(55).toString());
            setDeclarationElement(row.getCell(56) == null ? null : row.getCell(56).toString());
        }};
    }

    /**
     * 校验日期格式是否有错
     *
     * @param dateString
     * @return
     */
    public static String checkDateFormate(String dateString, int cellNumber, MessageSource messageSource, HttpServletRequest request) {
        String errorReason = "";
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMATE);
        boolean dateflag = true;
        try {
            Date date = format.parse(dateString);
        } catch (Exception e) {
            dateflag = false;
        } finally {
            if (!dateflag) {
                errorReason = errorField(cellNumber, messageSource, request);
            }
        }
        return errorReason;
    }

    public static String errorField(int cellNumber, MessageSource messageSource, HttpServletRequest request) {
        String errorMessage = "";
        switch (cellNumber) {
            case 2:
                errorMessage = "发票日期格式错误，";
                break;
            case 3:
                errorMessage = "船期格式错误，";
                break;
            case 7:
                errorMessage = "出口日期格式错误，";
                break;
            case 9:
                errorMessage = "申报期格式错误，";
                break;
            default:
                errorMessage = "";
                break;
        }
        return errorMessage;
    }

    /**
     * 校验头数据
     *
     * @param row
     * @param rowNumber
     * @param messageSource
     * @param request
     * @param yesNoList
     * @param transportWayList
     * @param supervisionModeList
     * @param packageTypeList
     * @param transactionModeList
     * @param exemptionNatureList
     * @return
     */
    public static String checkExcelHeaderData(Row row, int rowNumber, MessageSource messageSource, HttpServletRequest request,
                                              List<String> yesNoList, List<String> transportWayList, List<String> supervisionModeList,
                                              List<String> packageTypeList, List<String> transactionModeList,
                                              List<String> exemptionNatureList) {
        String errorReason = "";
        //所有日期格式校验
        List<Integer> dateNumberList = new ArrayList<Integer>() {{
            add(2);
            add(3);
            add(7);
            add(9);
        }};
        for (int i : dateNumberList) {
            if (Objects.nonNull(row.getCell(i))) {
                String dateError = checkDateFormate(row.getCell(i).toString(), i, messageSource, request);
                if (dateError != null && !("".equals(dateError))) {
                    errorReason += dateError;
                }
            }
        }
        //运输方式校验
        if (Objects.nonNull(row.getCell(11))) {
            if (!transportWayList.contains(row.getCell(11).toString())) {
                errorReason += "运输方式字段所填值不在值列表中，";
            }
        }
        //监管方式校验
        if (Objects.nonNull(row.getCell(16))) {
            if (!supervisionModeList.contains(row.getCell(16).toString())) {
                errorReason += "监管方式字段所填值不在值列表中，";
            }
        }
        //征免性质校验
        if (Objects.nonNull(row.getCell(17))) {
            if (!exemptionNatureList.contains(row.getCell(17).toString())) {
                errorReason += "征免性质字段所填值不在值列表中，";
            }
        }
        //包装种类
        if (Objects.nonNull(row.getCell(24))) {
            if (!packageTypeList.contains(row.getCell(24).toString())) {
                errorReason += "包装种类字段所填值不在值列表中，";
            }
        }
        //成交方式
        if (Objects.nonNull(row.getCell(25))) {
            if (!transactionModeList.contains(row.getCell(25).toString())) {
                errorReason += "成交方式字段所填值不在值列表中，";
            }
        }
        //特殊关系确认
        if (Objects.nonNull(row.getCell(31))) {
            if (!yesNoList.contains(row.getCell(31).toString())) {
                errorReason += "特殊关系字段所填值不在值列表中，";
            }
        }
        //价格影响确认
        if (Objects.nonNull(row.getCell(32))) {
            if (!yesNoList.contains(row.getCell(32).toString())) {
                errorReason += "价格影响字段所填值不在值列表中，";
            }
        }
        //支付特许权使用费确认
        if (Objects.nonNull(row.getCell(33))) {
            if (!yesNoList.contains(row.getCell(33).toString())) {
                errorReason += "支付特许权使用费确认字段所填值不在值列表中，";
            }
        }
        if (!("").equals(errorReason) && errorReason != null) {
            errorReason = "EXCEL行" + rowNumber + "项目号" + row.getCell(0).toString() + "的" + errorReason;
        }
        return errorReason;
    }

    /**
     * 校验行数据
     *
     * @param row
     * @param rowNumber
     * @param messageSource
     * @param request
     * @param packageNumberUnitList
     * @param exemptionWayList
     * @return
     */
    public static String checkExcelLineData(Row row, int rowNumber, MessageSource messageSource, HttpServletRequest request,
                                            List<String> packageNumberUnitList, List<String> exemptionWayList) {
        String errorMessage = "";
        //件数单位
        if (Objects.nonNull(row.getCell(44))) {
            if (!packageNumberUnitList.contains(row.getCell(44).toString())) {
                errorMessage += "件数单位字段所填值不在值列表中，";
            }
        }
        //征免
        if (Objects.nonNull(row.getCell(55))) {
            if (!packageNumberUnitList.contains(row.getCell(55).toString())) {
                errorMessage += "征免字段所填值不在值列表中，";
            }
        }
        if (!("").equals(errorMessage) && errorMessage != null) {
            errorMessage = "EXCEL行" + rowNumber + "项目号" + row.getCell(0).toString() + "的" + errorMessage;
        }
        return errorMessage;
    }

    /**
     * String类型数据转换成date类型
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
     * excel上传时sheet页转换成头信息
     *
     * @param sheet
     * @return
     */
    public static DocCustomsHeader sheetToHeader(Sheet sheet, MessageSource messageSource, HttpServletRequest request) throws HcuxException {
        DocCustomsHeader header = new DocCustomsHeader();
        header.setInvoiceNumber(sheet.getRow(3).getCell(1).toString());
        header.setConsignee(objectIsNull(sheet.getRow(4).getCell(1)) ? null : sheet.getRow(4).getCell(1).getStringCellValue());
        header.setDeparturePort(objectIsNull(sheet.getRow(5).getCell(1)) ? null : sheet.getRow(5).getCell(1).getStringCellValue());
        header.setDischargePort(objectIsNull(sheet.getRow(6).getCell(1)) ? null : sheet.getRow(6).getCell(1).getStringCellValue());
        header.setArrivalCountry(objectIsNull(sheet.getRow(7).getCell(1)) ? null : sheet.getRow(7).getCell(1).getStringCellValue());
        header.setTradingCountry(objectIsNull(sheet.getRow(8).getCell(1)) ? null : sheet.getRow(8).getCell(1).getStringCellValue());

        if (StringUtils.isBlank(header.getTradingCountry())) {
            header.setTradingCountry(header.getArrivalCountry());
        }

        header.setTransportWay(objectIsNull(sheet.getRow(9).getCell(1)) ? null : sheet.getRow(9).getCell(1).getStringCellValue());
        header.setCurrencySystem(objectIsNull(sheet.getRow(10).getCell(1)) ? null : sheet.getRow(10).getCell(1).getStringCellValue());
        header.setTransactionMode(objectIsNull(sheet.getRow(11).getCell(1)) ? null : sheet.getRow(11).getCell(1).getStringCellValue());
        try {
            header.setFreight(objectIsNull(sheet.getRow(12).getCell(1)) ? null : sheet.getRow(12).getCell(1).getNumericCellValue());
        } catch (IllegalStateException e) {
            throw new HcuxException("导入失败，海运费需为数字，请检查格式！");
        }
        try {
            header.setPremium(objectIsNull(sheet.getRow(13).getCell(1)) ? null : sheet.getRow(13).getCell(1).getNumericCellValue());
        } catch (IllegalStateException e) {
            throw new HcuxException("导入失败，保险费/DDP税费需为数字，请检查格式！");
        }
        header.setShippingMark(objectIsNull(sheet.getRow(14).getCell(1)) ? null : sheet.getRow(14).getCell(1).getStringCellValue());

        header.set__status(DTOStatus.ADD);
        return header;
    }

    /**
     * excel上传时sheet页转换成拖单信息
     *
     * @param sheet
     * @return
     */
    public static DocBookingNote sheetToNote(Sheet sheet) throws HcuxException {
        DocBookingNote note = new DocBookingNote();
        note.setInvoiceNumber(sheet.getRow(3).getCell(1).toString());

        //收货单位首行(先取值，后面有值则覆盖)
        note.setConsignee(objectIsNull(sheet.getRow(4).getCell(1)) ? null : sheet.getRow(4).getCell(1).toString());

        note.setShipmentPort(objectIsNull(sheet.getRow(5).getCell(1)) ? null : sheet.getRow(5).getCell(1).toString());
        note.setDestinationPort(objectIsNull(sheet.getRow(6).getCell(1)) ? null : sheet.getRow(6).getCell(1).toString());

        note.setTransportWay(objectIsNull(sheet.getRow(9).getCell(1)) ? null : sheet.getRow(9).getCell(1).toString());
        note.setShippingMark(objectIsNull(sheet.getRow(14).getCell(1)) ? null : sheet.getRow(14).getCell(1).toString());

        Cell cell17 = sheet.getRow(17).getCell(1);
        if (cell17.getCellTypeEnum().compareTo(CellType.NUMERIC) == 0) {
            note.setGoodsDate(cell17.getDateCellValue());
        } else if (cell17.getCellTypeEnum().compareTo(CellType.STRING) == 0) {
            try {
                note.setGoodsDate(DateUtils.parseDate(cell17.getStringCellValue(), BaseConstants.DATE_FORMAT));
            } catch (ParseException e) {
                throw new HcuxException("船期或货好日格式出错，日期格式需为Excel标准日期框或yyyy-MM-dd格式!");
            }
        }

        Cell cell18 = sheet.getRow(18).getCell(1);
        if (cell18.getCellTypeEnum().compareTo(CellType.NUMERIC) == 0) {
            note.setSailingDate(cell18.getDateCellValue());
        } else if (cell18.getCellTypeEnum().compareTo(CellType.STRING) == 0) {
            try {
                note.setSailingDate(DateUtils.parseDate(cell18.getStringCellValue(), BaseConstants.DATE_FORMAT));
            } catch (ParseException e) {
                throw new HcuxException("船期或货好日格式出错，日期格式需为Excel标准日期框或yyyy-MM-dd格式!");
            }
        }

        note.setBoxType(objectIsNull(sheet.getRow(19).getCell(1)) ? null : sheet.getRow(19).getCell(1).getStringCellValue());
        note.setCabinet(objectIsNull(sheet.getRow(20).getCell(1)) ? null : sheet.getRow(20).getCell(1).getStringCellValue());
        note.setCabinetAddress(objectIsNull(sheet.getRow(21).getCell(1)) ? null : sheet.getRow(21).getCell(1).getStringCellValue());
        note.setCustomerOrderNum(objectIsNull(sheet.getRow(22).getCell(1)) ? null : sheet.getRow(22).getCell(1).getStringCellValue());
        note.setBillCopies(objectIsNull(sheet.getRow(23).getCell(1)) ? null : sheet.getRow(23).getCell(1).getStringCellValue());
        note.setRemark(objectIsNull(sheet.getRow(24).getCell(1)) ? null : sheet.getRow(24).getCell(1).getStringCellValue());

        note.setProductName(objectIsNull(sheet.getRow(25).getCell(1)) ? null : sheet.getRow(25).getCell(1).getStringCellValue());

        //托运单位
        note.setShipper(objectIsNull(sheet.getRow(26).getCell(1)) ? null : sheet.getRow(26).getCell(1).getStringCellValue());
        note.setShipperAddress(objectIsNull(sheet.getRow(27).getCell(1)) ? null : sheet.getRow(27).getCell(1).getStringCellValue());
        note.setShipperMobile(objectIsNull(sheet.getRow(28).getCell(1)) ? null : sheet.getRow(28).getCell(1).getStringCellValue());

        //收货人
        note.setConsignee(objectIsNull(sheet.getRow(29).getCell(1)) ? note.getConsignee() : sheet.getRow(29).getCell(1).getStringCellValue());
        note.setConsigneeAddress(objectIsNull(sheet.getRow(30).getCell(1)) ? null : sheet.getRow(30).getCell(1).getStringCellValue());
        note.setConsigneeMobile(objectIsNull(sheet.getRow(31).getCell(1)) ? null : sheet.getRow(31).getCell(1).getStringCellValue());

        //通知单位
        note.setNotificationUnit(objectIsNull(sheet.getRow(32).getCell(1)) ? null : sheet.getRow(32).getCell(1).getStringCellValue());
        note.setNotificationAddress(objectIsNull(sheet.getRow(33).getCell(1)) ? null : sheet.getRow(33).getCell(1).getStringCellValue());
        note.setNotificationMobile(objectIsNull(sheet.getRow(34).getCell(1)) ? null : sheet.getRow(34).getCell(1).getStringCellValue());

        // 收货单位第三行，如果为空，取值下面通知单位代码和电话
        if (StringUtils.isBlank(note.getConsigneeMobile())) {
            note.setConsigneeMobile(note.getNotificationMobile());
        }

        //通知单位代码和电话，如果为空，取上面收货人代码和电话
        if (StringUtils.isBlank(note.getNotificationMobile())) {
            note.setNotificationMobile(note.getConsigneeMobile());
        }

        // 货代
        note.setCargoAgentUnit(objectIsNull(sheet.getRow(35).getCell(1)) ? null : sheet.getRow(35).getCell(1).getStringCellValue());
        note.setCargoAgent(objectIsNull(sheet.getRow(36).getCell(1)) ? null : sheet.getRow(36).getCell(1).getStringCellValue());

        Cell cell37 = sheet.getRow(37).getCell(1);
        if (cell37 != null) {
            cell37.setCellType(CellType.STRING);
            note.setCargoAgentMobile(cell37.getStringCellValue());
        }

        note.setCargoAgentEmail(objectIsNull(sheet.getRow(38).getCell(1)) ? null : sheet.getRow(38).getCell(1).getStringCellValue());

        //报关行
        Cell cell39 = sheet.getRow(39).getCell(1);
        Cell cell40 = sheet.getRow(40).getCell(1);
        if (!objectIsNull(cell39) || !objectIsNull(cell40)) {
            if (cell40 != null) {
                cell40.setCellType(CellType.STRING);
            }
            note.setDeclareCustomLine((cell39 == null ? "" : (cell39.getStringCellValue() + ",")) + (cell40 == null ? "" : cell40.getStringCellValue()));
        }

//        Cell cell2 = sheet.getRow(24).getCell(1);
//        Cell cell3 = sheet.getRow(25).getCell(1);
//        Cell cell4 = sheet.getRow(26).getCell(1);
//        if (!objectIsNull(cell2) || !objectIsNull(cell3) || !objectIsNull(cell4)) {
//            note.setStowagePlan((cell2 == null ? "" : (cell2.toString() + "\n")) + (cell3 == null ? "" : (cell3.toString() + "\n")) + (cell4 == null ? "" : (cell4.toString())));
//        }
        note.set__status(DTOStatus.ADD);
        return note;
    }

    /**
     * @param sheet
     * @return
     */
    public static List<DocCustomsLine> sheetToLineList(Sheet sheet) throws HcuxException {
        List<DocCustomsLine> list = new ArrayList<>();
        int firstRowNum = sheet.getFirstRowNum() + 44;//起始行
        int lastRowNum = sheet.getLastRowNum();//结束行
        for (int i = firstRowNum; i <= lastRowNum; i++) {
            Row row = sheet.getRow(i);
            if (!rowIsNull(row)) {
                list.add(excelRowToLine(row));
            }
        }
        return list;
    }

    /**
     * excel导入时每行数据转换成行信息
     *
     * @param row
     * @return
     */
    public static DocCustomsLine excelRowToLine(Row row) throws HcuxException {
        DocCustomsLine line = new DocCustomsLine();
        Cell cell0 = row.getCell(0);
        if (cell0 != null) {
            cell0.setCellType(CellType.STRING);
            line.setGoodsNumber(cell0.getStringCellValue());
        }
        line.setProductNameCn(objectIsNull(row.getCell(1)) ? null : row.getCell(1).getStringCellValue());
        line.setProductNameEn(objectIsNull(row.getCell(2)) ? null : row.getCell(2).getStringCellValue());

        try {
            line.setPackageNumber(objectIsNull(row.getCell(3)) ? null : round(row.getCell(3).getNumericCellValue(), 0));
        } catch (IllegalStateException e) {
            throw new HcuxException("导入失败，件数需为数字，请检查格式！");
        }

        // 当明细行的件数、毛重、体积都为空，需要 件数单位 强制为↑混装
        if (objectIsNull(row.getCell(3)) && objectIsNull(row.getCell(10)) && objectIsNull(row.getCell(12))) {
            line.setPackageNumberUnit(objectIsNull(row.getCell(4)) ? null : "↑混装");
        } else {
            line.setPackageNumberUnit(objectIsNull(row.getCell(4)) ? null : row.getCell(4).getStringCellValue());
        }

        try {
            line.setCustomsAmountOne(objectIsNull(row.getCell(5)) ? null : round(row.getCell(5).getNumericCellValue(), 2));
        } catch (IllegalStateException e) {
            throw new HcuxException("导入失败，报关数量需为数字，请检查格式！");
        }

        line.setCustomsUnitOne(objectIsNull(row.getCell(6)) ? null : row.getCell(6).getStringCellValue());

        try {
            Cell cell7 = row.getCell(7);
            if (!objectIsNull(cell7)) {
                cell7.setCellType(CellType.STRING);
                line.setCustomsAmountThree(round(Double.parseDouble(cell7.getStringCellValue()), 2));
            }
        } catch (IllegalStateException | NumberFormatException e) {
            throw new HcuxException("导入失败，辅助数量需为数字，请检查格式！");
        }

        line.setCustomsUnitThree(objectIsNull(row.getCell(8)) ? null : row.getCell(8).getStringCellValue());

        try {
            line.setTotalPrice(objectIsNull(row.getCell(9)) ? null : round(row.getCell(9).getNumericCellValue(), 2));
        } catch (IllegalStateException e) {
            throw new HcuxException("导入失败，金额需为数字，请检查格式！");
        }
        try {
            line.setGrossWeight(objectIsNull(row.getCell(10)) ? null : round(row.getCell(10).getNumericCellValue(), 2));
        } catch (IllegalStateException e) {
            throw new HcuxException("导入失败，毛重需为数字，请检查格式！");
        }
        try {
            line.setNetWeight(objectIsNull(row.getCell(11)) ? null : round(row.getCell(11).getNumericCellValue(), 2));
        } catch (IllegalStateException e) {
            throw new HcuxException("导入失败，净重需为数字，请检查格式！");
        }
        try {
            line.setVolume(objectIsNull(row.getCell(12)) ? null : round(row.getCell(12).getNumericCellValue(), 3));
        } catch (IllegalStateException e) {
            throw new HcuxException("导入失败，体积需为数字，请检查格式！");
        }

        line.setOriginPlace(objectIsNull(row.getCell(13)) ? null : row.getCell(13).getStringCellValue());
        line.setDeclarationElement(objectIsNull(row.getCell(14)) ? null : row.getCell(14).getStringCellValue());

        // 计算单价
        if (line.getTotalPrice() != null && line.getCustomsAmountOne() != null) {
            BigDecimal totalPrice = new BigDecimal(line.getTotalPrice());
            BigDecimal customsAmountOne = new BigDecimal(line.getCustomsAmountOne());
            line.setUnitPrice(totalPrice.divide(customsAmountOne, 4, BigDecimal.ROUND_HALF_UP).doubleValue());
        }

        line.set__status(DTOStatus.ADD);
        return line;
    }

    /**
     * 当数据库存在重复数据时，用excel当中非空字段数据覆盖数据库中的子段
     *
     * @param excelHeader
     * @param hapHeader
     * @return
     */
    public static DocCustomsHeader dataCover(DocCustomsHeader excelHeader, DocCustomsHeader hapHeader) {

        beanToBean(excelHeader, hapHeader);
        hapHeader.set__status(DTOStatus.UPDATE);
        return hapHeader;
    }

    /**
     * 用于判断一个对象是否为空
     *
     * @param object
     * @return
     */
    public static boolean objectIsNull(Object object) {
        if (object == null || StringUtils.isBlank(object.toString())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 用于判断一行数据是否为空
     *
     * @param row
     * @return
     */
    public static boolean rowIsNull(Row row) {
        if (row == null){
            return true;
        } else if (objectIsNull(row.getCell(0)) && objectIsNull(row.getCell(1)) && objectIsNull(row.getCell(2)) && objectIsNull(row.getCell(3)) && objectIsNull(row.getCell(4)) &&
                objectIsNull(row.getCell(5)) && objectIsNull(row.getCell(6)) && objectIsNull(row.getCell(7)) && objectIsNull(row.getCell(8)) && objectIsNull(row.getCell(9)) &&
                objectIsNull(row.getCell(10)) && objectIsNull(row.getCell(11)) && objectIsNull(row.getCell(12)) && objectIsNull(row.getCell(13)) && objectIsNull(row.getCell(14)) &&
                objectIsNull(row.getCell(15)) && objectIsNull(row.getCell(16)) && objectIsNull(row.getCell(17)) && objectIsNull(row.getCell(18)) && objectIsNull(row.getCell(19))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取Excel2003图片
     *
     * @param sheetNum 当前sheet编号
     * @param sheet    当前sheet对象
     * @param workbook 工作簿对象
     * @return Map key:图片单元格索引（0_1_1）String，value:图片流PictureData
     * @throws IOException
     */
    public static Map<String, PictureData> getSheetPictrues03(int sheetNum,
                                                              HSSFSheet sheet, HSSFWorkbook workbook) {

        Map<String, PictureData> sheetIndexPicMap = new HashMap<String, PictureData>();
        List<HSSFPictureData> pictures = workbook.getAllPictures();
        if (pictures.size() != 0) {
            for (HSSFShape shape : sheet.getDrawingPatriarch().getChildren()) {
                HSSFClientAnchor anchor = (HSSFClientAnchor) shape.getAnchor();
                if (shape instanceof HSSFPicture) {
                    HSSFPicture pic = (HSSFPicture) shape;
                    int pictureIndex = pic.getPictureIndex() - 1;
                    HSSFPictureData picData = pictures.get(pictureIndex);
                    String picIndex = String.valueOf(sheetNum) + "_"
                            + String.valueOf(anchor.getRow1()) + "_"
                            + String.valueOf(anchor.getCol1());
                    sheetIndexPicMap.put(picIndex, picData);
                }
            }
            return sheetIndexPicMap;
        } else {
            return null;
        }
    }

    /**
     * 获取Excel2007图片
     *
     * @param sheetNum 当前sheet编号
     * @param sheet    当前sheet对象
     * @param workbook 工作簿对象
     * @return Map key:图片单元格索引（0_1_1）String，value:图片流PictureData
     */
    public static Map<String, PictureData> getSheetPictrues07(int sheetNum,
                                                              XSSFSheet sheet, XSSFWorkbook workbook) {
        Map<String, PictureData> sheetIndexPicMap = new HashMap<String, PictureData>();

        for (POIXMLDocumentPart dr : sheet.getRelations()) {
            if (dr instanceof XSSFDrawing) {
                XSSFDrawing drawing = (XSSFDrawing) dr;
                List<XSSFShape> shapes = drawing.getShapes();
                for (XSSFShape shape : shapes) {
                    XSSFPicture pic = (XSSFPicture) shape;
                    XSSFClientAnchor anchor = pic.getPreferredSize();
                    CTMarker ctMarker = anchor.getFrom();
                    String picIndex = String.valueOf(sheetNum) + "_"
                            + ctMarker.getRow() + "_" + ctMarker.getCol();
                    sheetIndexPicMap.put(picIndex, pic.getPictureData());
                }
            }
        }

        return sheetIndexPicMap;
    }

    public static void printImg(Map<String, PictureData> pictureDataMap, String path, SysFile sysFile) {
        File file = new File(path);
        try (FileOutputStream out = new FileOutputStream(file)) {
            pictureDataMap.forEach((s, pictureData) -> {
                // 获取图片索引
                byte[] data = pictureData.getData();
                try {
                    out.write(data);
                } catch (IOException e) {
                    logger.error("写文件出错", e);
                }

                sysFile.setFilePath(path);
                sysFile.setFileSize(file.length());
                sysFile.setFileName(s + "." + pictureData.suggestFileExtension());
                sysFile.setFileType(pictureData.getMimeType());
                sysFile.setUploadDate(new Date());
            });
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

    }

    /**
     * 根据不同excel版本创建不同的PictureData
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static Map<String, PictureData> getExcelPictureData(MultipartFile file) throws Exception {
        Workbook workbook = null;
        Sheet sheet = null;
        Map<String, PictureData> sheetIndexPicMap = new HashMap<>();
        if (file.getOriginalFilename().endsWith(EXTENSION_XLS)) {
            workbook = new HSSFWorkbook(file.getInputStream());
            sheet = workbook.getSheetAt(0);
            sheetIndexPicMap = getSheetPictrues03(0, (HSSFSheet) sheet, (HSSFWorkbook) workbook);
        } else if (file.getOriginalFilename().endsWith(EXTENSION_XLSX)) {
            workbook = new XSSFWorkbook(file.getInputStream());
            sheet = workbook.getSheetAt(0);
            sheetIndexPicMap = getSheetPictrues07(0, (XSSFSheet) sheet, (XSSFWorkbook) workbook);
        }
        return sheetIndexPicMap;
    }

    public static void bookDataCover(DocBookingNote excelDocBookingNote, DocBookingNote hapDocBookingNote) {
        beanToBean(excelDocBookingNote, hapDocBookingNote);
        hapDocBookingNote.set__status(DTOStatus.UPDATE);
    }

    /**
     * 将excelBean的值set给hapBean
     *
     * @param excelBean
     * @param hapBean
     * @param <T>
     */
    private static <T> void beanToBean(T excelBean, T hapBean) {
        Map<String, Object> excelMap = Map2BeanUtil.beanToMap(excelBean);
        Map<String, Object> hapMap = Map2BeanUtil.beanToMap(hapBean);
        excelMap.forEach((s, o) -> {
            if (o != null) {
                hapMap.put(s, o);
            }
        });
        Map2BeanUtil.mapToBean(hapMap, hapBean);
    }

    /**
     * 保留小数位函数
     *
     * @param value
     * @param scale
     * @return
     */
    private static double round(double value, int scale) {
        return new BigDecimal(value).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 将文件对象的流写入Responsne对象.
     *
     * @param response HttpServletResponse
     * @param file     File
     * @throws FileNotFoundException 找不到文件异常
     * @throws IOException           IO异常
     */
    public static void writeFileToResp(HttpServletResponse response, File file) throws Exception {
        byte[] buf = new byte[1024];
        try (InputStream inStream = new FileInputStream(file);
             ServletOutputStream outputStream = response.getOutputStream()) {
            int readLength;
            while (((readLength = inStream.read(buf)) != -1)) {
                outputStream.write(buf, 0, readLength);
            }
            outputStream.flush();
        }
    }
}
