package hcux.eps.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hand.hap.account.dto.User;
import com.hand.hap.account.service.IUserService;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.system.service.ICodeService;
import com.hand.hap.system.service.IProfileService;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hcux.doc.dto.DocSynthesis;
import hcux.doc.mapper.DocSynthesisMapper;
import hcux.doc.service.IDocSynthesisService;
import hcux.doc.util.CommonUtil;
import hcux.eps.dto.EpsCustomsDetail;
import hcux.eps.mapper.EpsCustomsDetailMapper;
import hcux.eps.service.IEpsCustomsDetailService;
import hcux.eps.util.SpiderUtil;
import hcux.sys.dto.SysJobRecord;
import hcux.sys.mapper.SysJobRecordMapper;
import hcux.util.excel.PoiUtil;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.hand.hap.core.BaseConstants.DATE_FORMAT;
import static hcux.doc.util.DocConstant.EXTENSION_XLS;
import static hcux.doc.util.DocConstant.EXTENSION_XLSX;
import static hcux.eps.util.EpsConstant.*;

@Service
@Transactional(rollbackFor = Exception.class)
public class EpsCustomsDetailServiceImpl extends BaseServiceImpl<EpsCustomsDetail> implements IEpsCustomsDetailService {
    @Autowired
    private EpsCustomsDetailMapper epsCustomsDetailMapper;
    @Autowired
    private IEpsCustomsDetailService service;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private IProfileService profileService;
    @Autowired
    private ICodeService codeService;
    @Autowired
    private SysJobRecordMapper sysJobRecordMapper;
    @Autowired
    private IUserService userService;
    @Autowired
    private DocSynthesisMapper docSynthesisMapper;
    @Autowired
    private IDocSynthesisService docSynthesisService;

    @Override
    public List<EpsCustomsDetail> selectLists(IRequest requestContext, EpsCustomsDetail dto, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<EpsCustomsDetail> list = epsCustomsDetailMapper.selectList(dto);
        DecimalFormat decimalFormat = new DecimalFormat(".00");

        if (!list.isEmpty()) {
            List<EpsCustomsDetail> list1 = epsCustomsDetailMapper.selectTotalAmountForHap(dto);
            if (!list1.isEmpty()) {
                double totalAmount = (double) (Math.round(list1.get(0).getTotalAmount() * 100)) / 100;
                list.get(0).setTotalAmount(totalAmount);

                String stringTotalAmount = "";
                for (EpsCustomsDetail epsCustomsDetail : list1) {
                    stringTotalAmount += (epsCustomsDetail.getCurrencySystem() + " " + decimalFormat.format(epsCustomsDetail.getTotalAmount()) + "; ");
                }
                list.get(0).setStringTotalAmount(stringTotalAmount);
            }
        }
        return list;
    }

    @Override
    public List<EpsCustomsDetail> selectErrorLists(IRequest requestContext, EpsCustomsDetail dto, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<EpsCustomsDetail> list = epsCustomsDetailMapper.selectErrorList(dto);
        DecimalFormat decimalFormat = new DecimalFormat(".00");

        if (!list.isEmpty()) {
            List<EpsCustomsDetail> list1 = epsCustomsDetailMapper.selectErrorTotalAmount(dto);
            if (!list1.isEmpty()) {
                String stringErrorTotalAmount = "";
                for (EpsCustomsDetail epsCustomsDetail : list1) {
                    stringErrorTotalAmount += (epsCustomsDetail.getCurrencySystem() + " " + decimalFormat.format(epsCustomsDetail.getTotalAmount()) + "; ");
                }
                list.get(0).setStringErrorTotalAmount(stringErrorTotalAmount);
            }
        }

        return list;
    }

    @Override
    public List<EpsCustomsDetail> selectPortalList(IRequest requestContext, EpsCustomsDetail dto, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return epsCustomsDetailMapper.selectPortalList(dto);
    }

    @Override
    public List<EpsCustomsDetail> selectTotalAmount(IRequest requestContext, EpsCustomsDetail dto, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return epsCustomsDetailMapper.selectTotalAmount(dto);
    }

    @Override
    public EpsCustomsDetail selectMaxDeclareDate(IRequest requestContext, EpsCustomsDetail dto) {
        return epsCustomsDetailMapper.selectMaxDeclareDate(dto);
    }

    @Override
    public List<EpsCustomsDetail> selectHeaderListForWechat(IRequest requestContext, EpsCustomsDetail dto, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return epsCustomsDetailMapper.selectHeaderListForWechat(dto);
    }

    @Override
    public List<EpsCustomsDetail> selectDetailListForWechat(IRequest requestContext, EpsCustomsDetail dto, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return epsCustomsDetailMapper.selectDetailListForWechat(dto);
    }

    @Override
    public void exportExcelForPortal(IRequest requestContext, EpsCustomsDetail epsCustomsDetail, HttpServletResponse response) throws Exception {

        // 总记录数
        PageHelper.startPage(1, 1);
        Page<EpsCustomsDetail> list = (Page<EpsCustomsDetail>) epsCustomsDetailMapper.selectPortalList(epsCustomsDetail);
        long totalRowCount = list.getTotal();

        // 导出EXCEL文件名称
        String fileName = "挂账查询" + DateFormatUtils.format(new Date(), "yyyyMMdd-HHmmss");

        // 标题
        String[] titles = {"发票号", "申报日期", "国别", "商品名称", "商品编号", "数量", "单位", "第一数量", "第一计量单位", "成交币制", "金额", "总价"};

        // 开始导入
        PoiUtil.exportExcelToWebsite(response, totalRowCount, fileName, titles, (eachSheet, startRowCount, endRowCount, currentPage, pageSize) -> {

            PageHelper.startPage(currentPage, pageSize);
            List<EpsCustomsDetail> detailList = epsCustomsDetailMapper.selectPortalList(epsCustomsDetail);

            if (!CollectionUtils.isEmpty(detailList)) {

                for (int i = startRowCount; i <= endRowCount; i++) {
                    SXSSFRow eachDataRow = eachSheet.createRow(i);
                    if ((i - startRowCount) < detailList.size()) {

                        EpsCustomsDetail dto = detailList.get(i - startRowCount);

                        eachDataRow.createCell(0).setCellValue(dto.getInvoiceNumber() == null ? "" : dto.getInvoiceNumber());
                        if (null != dto.getDeclareDate()) {
                            eachDataRow.createCell(1).setCellValue(DateFormatUtils.ISO_DATE_FORMAT.format(dto.getDeclareDate()));
                        }
                        eachDataRow.createCell(2).setCellValue(dto.getCountry() == null ? "" : dto.getCountry());
                        eachDataRow.createCell(3).setCellValue(dto.getGoodsName() == null ? "" : dto.getGoodsName());
                        eachDataRow.createCell(4).setCellValue(dto.getGoodsNumber() == null ? "" : dto.getGoodsNumber());
                        if (dto.getAmount() != null) {
                            eachDataRow.createCell(5).setCellValue(dto.getAmount());
                        }
                        eachDataRow.createCell(6).setCellValue(dto.getUnit());
                        if (dto.getFirstAmount() != null) {
                            eachDataRow.createCell(7).setCellValue(dto.getFirstAmount());
                        }
                        eachDataRow.createCell(8).setCellValue(dto.getFirstUnit());
                        eachDataRow.createCell(9).setCellValue(dto.getCurrencySystem());
                        if (dto.getTotalPrice() != null) {
                            eachDataRow.createCell(10).setCellValue(dto.getTotalPrice());
                        }
                        if (dto.getTotalValue() != null) {
                            eachDataRow.createCell(11).setCellValue(dto.getTotalValue());
                        }
                    }
                }
            }

        });
    }

    @Override
    public String importExcel(HttpServletRequest request, IRequest requestContext, Workbook workbook) throws Exception {
        int numberOfSheets = workbook.getNumberOfSheets();
         String customsNumberError = "";//1.报关单号相同，未导入
        // String customsNumberError1 = "";//2.报关单号相同，申报日期晚于系统申报日期
        String billNumberError = "";//1.提单号相同，申报日期相同
        String billNumberError1 = "";//2.提单号相同，申报日期晚于系统申报日期
        String contractNumberMultiply = "";//合同号相同
        String contractNumberError = "";//合同号不符规则
        HashMap<String, Boolean> customsNumberIsExists = new HashMap<>();
        HashSet<String> customsNumberList = new HashSet<>();
        HashMap<String, Boolean> billNumberIsExists = new HashMap<>();
        HashSet<String> billNumberList = new HashSet<>();
        HashSet<String> contractNumberList = new HashSet<>();
        HashMap<String, Boolean> contractNumberIsExists = new HashMap<>();
        if (numberOfSheets > 0) {
            Sheet sheet = workbook.getSheetAt(0);
            if (Objects.nonNull(sheet)) {
                customsNumberList = hcux.eps.util.CommonUtil.sheet2customsNumberList(sheet);
                billNumberList = hcux.eps.util.CommonUtil.sheet2billNumberList(sheet);
                contractNumberList = hcux.eps.util.CommonUtil.sheet2ContractNumberList(sheet);
            }
        }
        if (customsNumberList != null && !customsNumberList.isEmpty()) {
            customsNumberList.forEach(x -> {
                List<EpsCustomsDetail> epsCustomsDetailList = epsCustomsDetailMapper.selectListByConditions(new EpsCustomsDetail() {{
                    setCustomsNumber(x);
                }});
                if (epsCustomsDetailList != null && !epsCustomsDetailList.isEmpty()) {
                    customsNumberIsExists.put(x, true);
                } else {
                    customsNumberIsExists.put(x, false);
                }
            });
        }
        if (billNumberList != null && !billNumberList.isEmpty()) {
            billNumberList.forEach(x -> {
                List<EpsCustomsDetail> epsCustomsDetailList = epsCustomsDetailMapper.selectListByConditions(new EpsCustomsDetail() {{
                    setBillNumber(x);
                }});
                if (epsCustomsDetailList != null && !epsCustomsDetailList.isEmpty()) {
                    billNumberIsExists.put(x, true);
                } else {
                    billNumberIsExists.put(x, false);
                }
            });
        }
        if (contractNumberList != null && !contractNumberList.isEmpty()) {
            contractNumberList.forEach(x -> {
                List<EpsCustomsDetail> epsCustomsDetailList = epsCustomsDetailMapper.selectListByConditions(new EpsCustomsDetail() {{
                    setContractNumber(x);
                }});
                if (epsCustomsDetailList != null && !epsCustomsDetailList.isEmpty()) {
                    contractNumberIsExists.put(x, true);
                } else {
                    contractNumberIsExists.put(x, false);
                }
            });
        }
        if (numberOfSheets > 0) {
            Sheet sheet = workbook.getSheetAt(0);
            if (Objects.nonNull(sheet)) {
                int firstRowNum = sheet.getFirstRowNum();
                //获取sheet中最后一行行号
                int lastRowNum = sheet.getLastRowNum();
                int i = firstRowNum + 1;

                List<EpsCustomsDetail> insertList = new ArrayList<>();

                for (; i <= lastRowNum; i++) {
                    Row row = sheet.getRow(i);
                    if (!hcux.eps.util.CommonUtil.rowIsNull(row)) {
                        EpsCustomsDetail epsCustomsDetail = hcux.eps.util.CommonUtil.rowToCustomsDetail(row);
                        //报关单号不为空 且 报关单号不相同
                        if (!CommonUtil.objectIsNull(epsCustomsDetail.getCustomsNumber()) && !customsNumberIsExists.get(epsCustomsDetail.getCustomsNumber())) {
                            //提单号不为空 且 提单号相同
                            if (!CommonUtil.objectIsNull(epsCustomsDetail.getBillNumber()) && billNumberIsExists.get(epsCustomsDetail.getBillNumber())) {
                                if (epsCustomsDetail.getDeclareDate() != null) {
                                    EpsCustomsDetail epsCustomsDetail1 = service.selectMaxDeclareDate(requestContext, epsCustomsDetail);
                                    if (epsCustomsDetail1.getMaxDeclareDate() != null) {
                                        if (epsCustomsDetail.getDeclareDate().after(epsCustomsDetail1.getMaxDeclareDate())) {
                                            if (request != null) {
                                                billNumberError1 += messageSource.getMessage(HCUX_EPS_THE_FIRST, null, RequestContextUtils.getLocale(request)) + (i + 1) + messageSource.getMessage(HCUX_EPS_ROW, null, RequestContextUtils.getLocale(request));
                                            }
                                            List<EpsCustomsDetail> epsCustomsDetailLists = epsCustomsDetailMapper.select(new EpsCustomsDetail() {{
                                                setBillNumber(epsCustomsDetail.getBillNumber());
                                            }});
                                            epsCustomsDetailLists.forEach(k -> {
                                                k.setModifyFlag("Y");
                                                k.set__status(DTOStatus.UPDATE);
                                            });
                                            //原数据【是否改单】标记为Y
                                            service.batchUpdate(requestContext, epsCustomsDetailLists);

                                            String contractNumberMultiplyi = "";
                                            String contractNumberErrori = "";
                                            //验证合同号
                                            if (!CommonUtil.objectIsNull(epsCustomsDetail.getContractNumber())) {
                                                contractNumberErrori = hcux.eps.util.CommonUtil.checkContractNumber(epsCustomsDetail.getContractNumber(), i + 1, messageSource, request);

                                                //合同号符合规则
                                                if ("".equals(contractNumberErrori)) {
                                                    EpsCustomsDetail epsCustomsDetail2 = new EpsCustomsDetail();
                                                    epsCustomsDetail2.setModifyFlag("N");
                                                    epsCustomsDetail2.setInvoiceNumber(epsCustomsDetail.getContractNumber());
                                                    List<EpsCustomsDetail> listInvNum = epsCustomsDetailMapper.queryInvoiceNum(epsCustomsDetail2);
                                                    //合同号正常
                                                    if (listInvNum.isEmpty()) {
                                                        epsCustomsDetail.setStatus("1");
                                                        epsCustomsDetail.setInvoiceNumber(epsCustomsDetail.getContractNumber());
                                                        if (epsCustomsDetail.getVesselVoyage() != null) {
                                                            DocSynthesis docSynthesis = new DocSynthesis();
                                                            docSynthesis.setInvoiceNumber(epsCustomsDetail.getContractNumber());
                                                            List<DocSynthesis> synthesisList = docSynthesisMapper.queryDocSynthesis(docSynthesis);
                                                            if (!synthesisList.isEmpty()) {
                                                                docSynthesis = synthesisList.get(0);
                                                                docSynthesis.setVesselVoyage(epsCustomsDetail.getVesselVoyage());
                                                                docSynthesisService.updateByPrimaryKeySelective(requestContext, docSynthesis);
                                                            }
                                                        }
                                                    } else {//合同号重复
                                                        epsCustomsDetail.setStatus("2");
                                                        if (request != null) {
                                                            contractNumberMultiplyi = messageSource.getMessage(HCUX_EPS_THE_FIRST, null, RequestContextUtils.getLocale(request)) + (i + 1) + messageSource.getMessage(HCUX_EPS_ROW, null, RequestContextUtils.getLocale(request));
                                                            contractNumberMultiply += contractNumberMultiplyi;
                                                        }
                                                    }
                                                } else {//合同号不符合规则
                                                    epsCustomsDetail.setStatus("0");
                                                    if (request != null) {
                                                        contractNumberError += contractNumberErrori;
                                                    }
                                                }
                                            } else {
                                                //合同号不符合规则
                                                epsCustomsDetail.setStatus("0");
                                                if (request != null) {
                                                    contractNumberError += contractNumberErrori;
                                                }
                                            }

                                            //新插入的数据【是否改单】标记为N并保存
                                            epsCustomsDetail.setModifyFlag("N");
                                            insertList.add(epsCustomsDetail);
                                        } else {
                                            if (request != null) {
                                                billNumberError += messageSource.getMessage(HCUX_EPS_THE_FIRST, null, RequestContextUtils.getLocale(request)) + (i + 1) + messageSource.getMessage(HCUX_EPS_ROW, null, RequestContextUtils.getLocale(request));
                                            }
                                        }
                                    }
                                }
                            } else { // 提单号为空 或 提单号不同
                                String contractNumberMultiplyi = "";
                                String contractNumberErrori = "";
                                //验证合同号
                                if (!CommonUtil.objectIsNull(epsCustomsDetail.getContractNumber())) {
                                    contractNumberErrori = hcux.eps.util.CommonUtil.checkContractNumber(epsCustomsDetail.getContractNumber(), i + 1, messageSource, request);

                                    //合同号符合规则
                                    if ("".equals(contractNumberErrori)) {
                                        EpsCustomsDetail epsCustomsDetail2 = new EpsCustomsDetail();
                                        epsCustomsDetail2.setModifyFlag("N");
                                        epsCustomsDetail2.setInvoiceNumber(epsCustomsDetail.getContractNumber());
                                        List<EpsCustomsDetail> listInvNum = epsCustomsDetailMapper.queryInvoiceNum(epsCustomsDetail2);
                                        //合同号正常
                                        if (listInvNum.isEmpty()) {
                                            epsCustomsDetail.setStatus("1");
                                            epsCustomsDetail.setInvoiceNumber(epsCustomsDetail.getContractNumber());
                                            if (epsCustomsDetail.getVesselVoyage() != null) {
                                                DocSynthesis docSynthesis = new DocSynthesis();
                                                docSynthesis.setInvoiceNumber(epsCustomsDetail.getContractNumber());
                                                List<DocSynthesis> synthesisList = docSynthesisMapper.queryDocSynthesis(docSynthesis);
                                                if (!synthesisList.isEmpty()) {
                                                    docSynthesis = synthesisList.get(0);
                                                    docSynthesis.setVesselVoyage(epsCustomsDetail.getVesselVoyage());
                                                    docSynthesisService.updateByPrimaryKeySelective(requestContext, docSynthesis);
                                                }
                                            }
                                        } else {//合同号重复
                                            epsCustomsDetail.setStatus("2");
                                            if (request != null) {
                                                contractNumberMultiplyi = messageSource.getMessage(HCUX_EPS_THE_FIRST, null, RequestContextUtils.getLocale(request)) + (i + 1) + messageSource.getMessage(HCUX_EPS_ROW, null, RequestContextUtils.getLocale(request));
                                                contractNumberMultiply += contractNumberMultiplyi;
                                            }
                                        }
                                    } else {//合同号不符合规则
                                        epsCustomsDetail.setStatus("0");
                                        if (request != null) {
                                            contractNumberError += contractNumberErrori;
                                        }
                                    }
                                } else {
                                    //合同号不符合规则
                                    epsCustomsDetail.setStatus("0");
                                    if (request != null) {
                                        contractNumberError += contractNumberErrori;
                                    }
                                }

                                //新插入的数据【是否改单】标记为N并保存
                                epsCustomsDetail.setModifyFlag("N");
                                epsCustomsDetail.setIsExported("N");
                                insertList.add(epsCustomsDetail);
                            }
                        } else {
                            if (request != null){
                                customsNumberError += messageSource.getMessage(HCUX_EPS_THE_FIRST, null, RequestContextUtils.getLocale(request)) + (i + 1) + messageSource.getMessage(HCUX_EPS_ROW, null, RequestContextUtils.getLocale(request));
                            }
                        }
                    }
                }

                for (EpsCustomsDetail epsCustomsDetail : insertList) {
                    service.insertSelective(requestContext, epsCustomsDetail);
                }
            }
        }
        String errorMessage = "";
        if (!CommonUtil.objectIsNull(customsNumberError) && !("").equals(customsNumberError) && request != null){
            errorMessage += (messageSource.getMessage(HCUX_EPS_SAME_AS_CUSTOMS_NUMBER, null, RequestContextUtils.getLocale(request)) + customsNumberError) + "<br />";
        }
        if (!CommonUtil.objectIsNull(billNumberError) && !("").equals(billNumberError) && request != null) {
            errorMessage += (messageSource.getMessage(HCUX_EPS_SAME_AS_CUSTOMS_DECLARATION, null, RequestContextUtils.getLocale(request)) + billNumberError) + "<br />";
        }
        if (!CommonUtil.objectIsNull(billNumberError1) && !("").equals(billNumberError1) && request != null) {
            errorMessage += (messageSource.getMessage(HCUX_EPS_SAME_AS_CUSTOMS_DECLARATION1, null, RequestContextUtils.getLocale(request)) + billNumberError1) + "<br />";
        }
        if (!CommonUtil.objectIsNull(contractNumberMultiply) && !("").equals(contractNumberMultiply) && request != null) {
            errorMessage += messageSource.getMessage(HCUX_EPS_SAME_AS_CONTRACT_NUMBER, null, RequestContextUtils.getLocale(request)) + contractNumberMultiply + "<br />";
        }
        if (!CommonUtil.objectIsNull(contractNumberError) && !("").equals(contractNumberError) && request != null) {
            errorMessage += messageSource.getMessage(HCUX_EPS_CONTRACT_NUMBER_NOT_IN_RULE, null, RequestContextUtils.getLocale(request)) + contractNumberError;
        }

        return errorMessage;
    }

    @Override
    public String autoImportExcel(IRequest iRequest, Date date, SysJobRecord record) throws Exception {
        String remoteUrl = profileService.getProfileValue(iRequest, "HCUX_EPS_AUTO_IMPORT_URL");
        String username = profileService.getProfileValue(iRequest, "HCUX_EPS_AUTO_IMPORT_USERNAME");
        String password = profileService.getProfileValue(iRequest, "HCUX_EPS_AUTO_IMPORT_PASSWORD");

        String existInfo = "导入日期包括: ";
        String notExistInfo = "未导入日期包括: ";

        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Calendar today = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.setTime(date);

        while (sdf.parse(sdf.format(startDate.getTime())).compareTo(sdf.parse(sdf.format(today.getTime()))) < 0) {
            Workbook workbook = null;
            InputStream in = null;
            String fileDae = sdf.format(startDate.getTime());
            try {
                // authentication
                NtlmPasswordAuthentication ntPassAuth = new NtlmPasswordAuthentication(null, username, password);
                String url = null;
                String fileName = fileDae + "." + EXTENSION_XLSX;
                url = remoteUrl + fileName;
                SmbFile smbFile = new SmbFile(url, ntPassAuth);

                if (smbFile.exists()) {
                    in = new BufferedInputStream(new SmbFileInputStream(smbFile));
                    workbook = new XSSFWorkbook(in);
                    importExcel(null, iRequest, workbook);
                    // 插入成功后再更新
                    record.setPreviousFireDate(today.getTime());
                    sysJobRecordMapper.updateByPrimaryKeySelective(record);
                    existInfo += fileDae + ",";
                } else {
                    fileName = fileDae + "." + EXTENSION_XLS;
                    url = remoteUrl + fileName;
                    smbFile = new SmbFile(url, ntPassAuth);

                    if (smbFile.exists()) {
                        in = new BufferedInputStream(new SmbFileInputStream(smbFile));
                        workbook = new HSSFWorkbook(in);
                        importExcel(null, iRequest, workbook);
                        // 插入成功后再更新
                        record.setPreviousFireDate(today.getTime());
                        sysJobRecordMapper.updateByPrimaryKeySelective(record);
                        existInfo += fileDae;
                    } else {
                        notExistInfo += fileDae + ",";
                    }
                }
            } catch (Exception e) {
                throw e;
            } finally {
                if (in != null) {
                    in.close();
                }
            }
            startDate.add(Calendar.DATE, 1);
        }

        String returnInfo = "";
        if (!existInfo.equals("导入日期包括: ")) {
            returnInfo += existInfo + "\n";
        } else {
            returnInfo += "本次未导入数据。" + "\n";
        }
        if (!notExistInfo.equals("未导入日期包括: ")) {
            returnInfo += notExistInfo;
        }

        return returnInfo;
    }

    @Override
    public void exportExcel(IRequest iRequest, EpsCustomsDetail epsCustomsDetail, HttpServletResponse response) throws Exception {
        // 总记录数
        PageHelper.startPage(1, 1);
        Page<EpsCustomsDetail> list = (Page<EpsCustomsDetail>) epsCustomsDetailMapper.selectErrorList(epsCustomsDetail);
        long totalRowCount = list.getTotal();

        // 导出EXCEL文件名称
        String fileName = "报关明细头" + DateFormatUtils.format(new Date(), "yyyyMMdd-HHmmss");
        // 标题
        String[] titles = {"状态", "报关单号", "合同号", "发票号", "申报日期", "进出口日期", "总价", "成交币制", "进/出口", "商品名称", "进出口口岸", "申报单位", "国别", "贸易方式", "交易方式"};

        // 开始导入
        PoiUtil.exportExcelToWebsite(response, totalRowCount, fileName, titles, (eachSheet, startRowCount, endRowCount, currentPage, pageSize) -> {
            PageHelper.startPage(currentPage, pageSize);
            List<EpsCustomsDetail> detailList = epsCustomsDetailMapper.selectErrorList(epsCustomsDetail);

            if (!CollectionUtils.isEmpty(detailList)) {
                for (int i = startRowCount; i <= endRowCount; i++) {
                    SXSSFRow eachDataRow = eachSheet.createRow(i);

                    if ((i - startRowCount) < detailList.size()) {
                        EpsCustomsDetail dto = detailList.get(i - startRowCount);

                        if (null != dto.getStatus()) {
                            String status = codeService.getCodeMeaningByValue(iRequest, HCUX_EPS_CUSTOMS_DETAIL_STATUS, dto.getStatus());
                            eachDataRow.createCell(0).setCellValue(status);
                        }

                        eachDataRow.createCell(1).setCellValue(dto.getCustomsNumber() == null ? "" : dto.getCustomsNumber());
                        eachDataRow.createCell(2).setCellValue(dto.getContractNumber() == null ? "" : dto.getContractNumber());
                        eachDataRow.createCell(3).setCellValue(dto.getInvoiceNumber() == null ? "" : dto.getInvoiceNumber());

                        if (null != dto.getDeclareDate()) {
                            eachDataRow.createCell(4).setCellValue(DateFormatUtils.ISO_DATE_FORMAT.format(dto.getDeclareDate()));
                        }

                        if (null != dto.getImportExportDate()) {
                            eachDataRow.createCell(5).setCellValue(DateFormatUtils.ISO_DATE_FORMAT.format(dto.getImportExportDate()));
                        }

                        if (null != dto.getTotalValue()) {
                            eachDataRow.createCell(6).setCellValue(dto.getTotalValue());
                        }
                        eachDataRow.createCell(7).setCellValue(dto.getCurrencySystem() == null ? "" : dto.getCurrencySystem());
                        eachDataRow.createCell(8).setCellValue(dto.getImportExport() == null ? "" : dto.getImportExport());
                        eachDataRow.createCell(9).setCellValue(dto.getGoodsName() == null ? "" : dto.getGoodsName());
                        eachDataRow.createCell(10).setCellValue(dto.getImportExportPort() == null ? "" : dto.getImportExportPort());
                        eachDataRow.createCell(11).setCellValue(dto.getDeclareUnit() == null ? "" : dto.getDeclareUnit());
                        eachDataRow.createCell(12).setCellValue(dto.getCountry() == null ? "" : dto.getCountry());
                        eachDataRow.createCell(13).setCellValue(dto.getTransactionMode() == null ? "" : dto.getTransactionMode());
                        eachDataRow.createCell(14).setCellValue(dto.getDealWay() == null ? "" : dto.getDealWay());

                    }
                }
            }
        });
    }

    @Override
    public ResponseData customsDataQuery(IRequest requestContext, EpsCustomsDetail dto, int page, int pageSize) throws IOException, ParseException {
        ResponseData rd = new ResponseData();
        Long userId = requestContext.getUserId();

        if (userId == null) {
            rd.setSuccess(false);
            rd.setMessage("未登录，查询失败！");
            return rd;
        }

        String contractNumber = dto.getContractNumber();
        if (StringUtils.isBlank(contractNumber)) {
            rd.setSuccess(false);
            rd.setMessage("合同号为空，查询失败！");
            return rd;
        }
        contractNumber = contractNumber.toUpperCase();
        dto.setContractNumber(contractNumber);

        User user = new User();
        user.setUserId(userId);
        List<User> userList = userService.selectUsers(requestContext, user, 0, 0);
        user = userList.get(0);
        if ("OUTER".equals(user.getUserType())) {
            String serialNumber = user.getSerialNumber();
            if (StringUtils.isBlank(serialNumber)) {
                rd.setSuccess(false);
                rd.setMessage("该用户未维护系列号，查询失败！");
                return rd;
            }

            if (!contractNumber.substring(1).startsWith(serialNumber)) {
                rd.setSuccess(false);
                rd.setMessage("无权限查询此系列号，查询失败！");
                return rd;
            }
        }

//        PageHelper.startPage(page, pageSize);
        // 查询本地表数据
        List<EpsCustomsDetail> list = epsCustomsDetailMapper.selectForCustomsData(dto);
        if (!list.isEmpty()) {
            rd.setRows(list);
            return rd;
        }

        List<EpsCustomsDetail> detailList = new ArrayList<>();
        SpiderUtil spiderUtil = new SpiderUtil();

        // 爬取报关
        spiderUtil.customs(detailList, contractNumber);
        if (!detailList.isEmpty()) {
            rd.setRows(detailList);
            return rd;
        }

        // 爬取异地报关
        spiderUtil.externalCustoms(detailList, contractNumber);
        rd.setRows(detailList);
        return rd;
    }

    @Override
    public ResponseData exportHasAuthorized(IRequest requestContext) {
        ResponseData responseData=new ResponseData();
        Long id=requestContext.getUserId();
        List<Long> userIdList=epsCustomsDetailMapper.selectUserIds().stream().map(EpsCustomsDetail::getUserId).collect(Collectors.toList());;
        if(userIdList.contains(id)){
            return responseData;
        }else{
            responseData.setSuccess(false);
            return responseData;
        }

    }

    @Override
    public void exportExcelForSelf(IRequest requestContext, EpsCustomsDetail epsCustomsDetail, HttpServletResponse response) throws Exception {
        // 总记录数
        PageHelper.startPage(1, 1);
        Page<EpsCustomsDetail> list = (Page<EpsCustomsDetail>) epsCustomsDetailMapper.selectSelfExportList(epsCustomsDetail);
        long totalRowCount = list.getTotal();

        // 导出EXCEL文件名称
        String fileName = "报关明细头自定义导出" + DateFormatUtils.format(new Date(), "yyyyMMdd-HHmmss");
        // 标题
        String[] titles = {"项目号", "报关口岸", "领用日期", "币种",  "总价", "报关单号"};

        // 开始导入
        PoiUtil.exportExcelToWebsite(response, totalRowCount, fileName, titles, (eachSheet, startRowCount, endRowCount, currentPage, pageSize) -> {
            PageHelper.startPage(currentPage, pageSize);
            List<EpsCustomsDetail> detailList = epsCustomsDetailMapper.selectSelfExportList(epsCustomsDetail);

            if (!CollectionUtils.isEmpty(detailList)) {
                for (int i = startRowCount; i <= endRowCount; i++) {
                    SXSSFRow eachDataRow = eachSheet.createRow(i);

                    if ((i - startRowCount) < detailList.size()) {
                        EpsCustomsDetail dto = detailList.get(i - startRowCount);
                        eachDataRow.createCell(0).setCellValue(dto.getInvoiceNumber() == null ? "" : dto.getInvoiceNumber());
                        eachDataRow.createCell(1).setCellValue(dto.getImportExportPort() == null ? "" : dto.getImportExportPort());
                        if (null != dto.getDeclareDate()) {
                            eachDataRow.createCell(2).setCellValue(DateFormatUtils.ISO_DATE_FORMAT.format(dto.getDeclareDate()));
                        }
                        eachDataRow.createCell(3).setCellValue(dto.getCurrencySystem() == null ? "" : dto.getCurrencySystem());
                        if (dto.getTotalValue() != null) {
                            eachDataRow.createCell(4).setCellValue(dto.getTotalValue());
                        }
                        eachDataRow.createCell(5).setCellValue(dto.getCustomsNumber() == null ? "" : dto.getCustomsNumber());
                       List<EpsCustomsDetail> epsCustomsDetailList=epsCustomsDetailMapper.selectListByConditions(new EpsCustomsDetail(){{
                           setCustomsNumber(dto.getCustomsNumber());
                       }});
                        epsCustomsDetailList.forEach(epsCustomsDetail1 -> {
                            if(epsCustomsDetail1.getIsExported()!=null&&!("").equals(epsCustomsDetail1.getIsExported())){
                                if(epsCustomsDetail1.getIsExported().equals(HCUX_EPS_CUSTOMS_DETAIL_IS_EXPORTED_N)){
                                    epsCustomsDetail1.setIsExported(HCUX_EPS_CUSTOMS_DETAIL_IS_EXPORTED_Y);
                                    self().updateByPrimaryKey(requestContext,epsCustomsDetail1);}
                            }else{
                                epsCustomsDetail1.setIsExported(HCUX_EPS_CUSTOMS_DETAIL_IS_EXPORTED_Y);
                                self().updateByPrimaryKey(requestContext,epsCustomsDetail1);
                        }
                        });
                    }
                }
            }
        });
    }
}
//ceshi