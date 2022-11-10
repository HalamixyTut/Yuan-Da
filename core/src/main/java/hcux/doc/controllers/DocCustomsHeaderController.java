package hcux.doc.controllers;

import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.itextpdf.text.DocumentException;
import hcux.core.exception.HcuxException;
import hcux.core.util.AutoUpperUtil;
import hcux.doc.dto.DocCustomsHeader;
import hcux.doc.dto.DocCustomsPrint;
import hcux.doc.dto.DocCustomsRecord;
import hcux.doc.dto.FileInputResponse;
import hcux.doc.service.IDocCustomsHeaderService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author feng.liu01@hand-china.com
 * @description 报关单头表
 */
@Controller
public class DocCustomsHeaderController extends BaseController {

    @Autowired
    private IDocCustomsHeaderService service;

    /**
     * 汇总页面查询用
     *
     * @param dto
     * @param request
     * @return
     */
    @RequestMapping(value = "/hcux/doc/customs/header/query")
    @ResponseBody
    public ResponseData query(@RequestBody DocCustomsHeader dto, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectList(requestContext, dto, dto.getPage(), dto.getPagesize()));
    }

    /**
     * 编辑详情页面查询用
     *
     * @param dto
     * @param request
     * @return
     */
    @RequestMapping(value = "/hcux/doc/customs/header/queryOne")
    @ResponseBody
    public ResponseData queryOne(DocCustomsHeader dto, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        DocCustomsHeader header = service.selectOne(requestContext, dto);
        List<DocCustomsHeader> list = new ArrayList<>();
        list.add(header);
        return new ResponseData(list);
    }

    @RequestMapping(value = "/hcux/doc/customs/header/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<DocCustomsHeader> dto, BindingResult result, HttpServletRequest request) throws HcuxException {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }

        // 自动转大写
        AutoUpperUtil.autoUpper(dto);

        IRequest requestCtx = createRequestContext(request);
        //发票号日期设置为当前日期
        dto.forEach(docCustomsHeader -> docCustomsHeader.setInvoiceDate(new Date()));
        return new ResponseData(service.batchUpdateHeaderAndLine(request, requestCtx, dto));
    }

    @RequestMapping(value = "/hcux/doc/customs/header/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<DocCustomsHeader> dto) throws HcuxException {
        IRequest requestCtx = createRequestContext(request);
        service.deleteHeaderAndLine(request, requestCtx, dto);
        return new ResponseData();
    }

    /**
     * 提交审批入口
     *
     * @param list
     * @param request
     * @return
     */
    @RequestMapping(value = "/hcux/doc/customs/header/submitApproval")
    @ResponseBody
    public ResponseData submitApproval(@RequestBody List<DocCustomsHeader> list, HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        return service.submitApproval(request, requestCtx, list);
    }

    /**
     * 审批入口
     *
     * @param dto
     * @param request
     * @return
     */
    @RequestMapping(value = "/hcux/doc/customs/header/approval")
    @ResponseBody
    public ResponseData approval(@RequestBody List<DocCustomsRecord> dto, HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        return service.approval(request, requestCtx, dto);
    }

    /**
     * 修改
     *
     * @param list
     * @param request
     * @return
     */
    @RequestMapping(value = "/hcux/doc/customs/header/modify")
    @ResponseBody
    public ResponseData modify(@RequestBody List<DocCustomsHeader> list, HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.modify(request, requestCtx, list));
    }

    /**
     * 海关改单
     *
     * @param list
     * @param request
     * @return
     */
    @RequestMapping(value = "/hcux/doc/customs/header/customsModify")
    @ResponseBody
    public ResponseData customsModify(@RequestBody List<DocCustomsHeader> list, HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.customsModify(request, requestCtx, list));
    }

    /**
     * pdf打印入口
     *
     * @param dto
     * @param request
     * @return
     */
    @RequestMapping(value = "/hcux/doc/customs/header/pdfPrint")
    @ResponseBody
    public ResponseData pdfPrint(DocCustomsPrint dto, HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException, HcuxException {
        IRequest requestCtx = createRequestContext(request);
        service.pdfPrint(requestCtx, dto, request, response);
        return new ResponseData();
    }

    /*@RequestMapping(value = "/hcux/doc/customs/header/excel/import", produces = "text/html;charset=utf-8")
    @ResponseBody
    public ResponseData excelUpload(@RequestParam MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        IRequest requestContext = createRequestContext(request);
        ResponseData responseData = new ResponseData();
        Workbook workbook = null;
        try {
            workbook = CommonUtil.fileType(file);//根据excel的不同版本创建不同的workbook
        } catch (Exception e) {
            responseData.setSuccess(false);
            responseData.setMessage(messageSource.getMessage(e.getMessage(), null, RequestContextUtils.getLocale(request)));
        }
        List<String> yesNoList = valueMeaningDtoMapper.selectValueMeaningByCode(YES_NO_CODE).stream().map(x -> x.getMeaning()).collect(Collectors.toList());
        List<String> transportWayList = valueMeaningDtoMapper.selectValueMeaningByCode(HCUX_DOC_TRANSPORT_WAY).stream().map(x -> x.getMeaning()).collect(Collectors.toList());
        List<String> supervisionModeList = valueMeaningDtoMapper.selectValueMeaningByCode(HCUX_DOC_SUPERVISION_MODE).stream().map(x -> x.getMeaning()).collect(Collectors.toList());
        List<String> packageTypeList = valueMeaningDtoMapper.selectValueMeaningByCode(HCUX_DOC_PACKAGE_TYPE).stream().map(x -> x.getMeaning()).collect(Collectors.toList());
        List<String> transactionModeList = valueMeaningDtoMapper.selectValueMeaningByCode(HCUX_DOC_TRANSACTION_MODE).stream().map(x -> x.getMeaning()).collect(Collectors.toList());
        List<String> exemptionWayList = valueMeaningDtoMapper.selectValueMeaningByCode(HCUX_DOC_EXEMPTION_WAY).stream().map(x -> x.getMeaning()).collect(Collectors.toList());
        List<String> packageNumberUnitList = valueMeaningDtoMapper.selectValueMeaningByCode(HCUX_DOC_PACKAGE_NUMBER_UNIT).stream().map(x -> x.getMeaning()).collect(Collectors.toList());
        List<String> exemptionNatureList = valueMeaningDtoMapper.selectValueMeaningByCode(HCUX_DOC_EXEMPTION_NATURE).stream().map(x -> x.getMeaning()).collect(Collectors.toList());
        Sheet sheet = workbook.getSheetAt(0);
        if (Objects.nonNull(sheet)) {
            List<Row> rowList = new ArrayList<>();//存放excel所有的行
            Set<String> projectNumList = new HashSet<String>();//用于存放所有的不同的项目号
            int firstRowNum = sheet.getFirstRowNum();//起始行
            int lastRowNum = sheet.getLastRowNum();//结束行
            Row row1 = sheet.getRow(firstRowNum + 2);
            String errorMessage = CommonUtil.checkExcelHeaderData(row1, firstRowNum + 3, messageSource, request,
                    yesNoList, transportWayList, supervisionModeList, packageTypeList, transactionModeList, exemptionNatureList);
            errorMessage += CommonUtil.checkExcelLineData(row1, firstRowNum + 3, messageSource, request, packageNumberUnitList, exemptionWayList);
            rowList.add(row1);
            projectNumList.add(row1.getCell(0).toString());
            for (int i = firstRowNum + 3; i <= lastRowNum; i++) {
                Row row = sheet.getRow(i);
                projectNumList.add(row.getCell(0).toString());
                rowList.add(row);
                if (row.getCell(0).toString().equals(sheet.getRow(i - 1).getCell(0))) {
                    errorMessage += CommonUtil.checkExcelLineData(row1, firstRowNum + 3, messageSource, request, packageNumberUnitList, exemptionWayList);
                } else {
                    errorMessage += CommonUtil.checkExcelHeaderData(row1, firstRowNum + 3, messageSource, request,
                            yesNoList, transportWayList, supervisionModeList, packageTypeList, transactionModeList, exemptionNatureList)
                            + CommonUtil.checkExcelLineData(row1, firstRowNum + 3, messageSource, request, packageNumberUnitList, exemptionWayList);
                }
            }
            if (errorMessage != null && !("").equals(errorMessage)) {
                responseData.setSuccess(false);
                responseData.setMessage(errorMessage);
            } else {
                List<DocCustomsHeader> queryHeaderList = service.queryDocCustomsHeaderBySet(requestContext, projectNumList);
                DocCustomsHeader docCustomsHeader1 = CommonUtil.rowToDocCustomsHeader(row1);
                docCustomsHeader1.setLineList(new ArrayList<DocCustomsLine>() {{
                    add(CommonUtil.rowToDocCustomsLine(row1));
                }});
                List<DocCustomsHeader> customsHeaderList = new ArrayList<DocCustomsHeader>() {{
                    add(docCustomsHeader1);
                }};
                if (rowList.size() > 0) {
                    for (int i = 1; i < rowList.size(); i++) {
                        if (rowList.get(i).getCell(i - 1).toString().equals(rowList.get(i).getCell(i).toString())) {
                            DocCustomsHeader docCustomsHeader = customsHeaderList.get(customsHeaderList.size() - 1);
                            List<DocCustomsLine> lineList = docCustomsHeader.getLineList();
                            lineList.add(CommonUtil.rowToDocCustomsLine(rowList.get(i)));
                            docCustomsHeader.setLineList(lineList);
                        } else {
                            DocCustomsHeader docCustomsHeader = CommonUtil.rowToDocCustomsHeader(rowList.get(i));
                            List<DocCustomsLine> lineList = docCustomsHeader.getLineList();
                            lineList.add(CommonUtil.rowToDocCustomsLine(rowList.get(i)));
                            docCustomsHeader.setLineList(lineList);
                            customsHeaderList.add(docCustomsHeader);
                        }
                    }
                }
                if (queryHeaderList.size() > 1) {

                } else if (queryHeaderList.size() == 1) {
                    responseData.setSuccess(false);
                    responseData.setMessage("");
                } else {
                    customsHeaderList.forEach(x -> {
                        x.set__status(DTOStatus.ADD);
                        x.getLineList().forEach(y -> y.set__status(DTOStatus.ADD));
                    });
                    service.batchUpdateHeaderAndLine(requestContext, customsHeaderList);
                }
            }
        }
        return null;
    }*/

    @RequestMapping(value = "/hcux/doc/customs/header/excel/import2")
    @ResponseBody
    public FileInputResponse excelUpload2(@RequestParam MultipartFile file, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        try {
            return service.excelUpload(request, requestContext, file);
        } catch (Exception e) {
            e.printStackTrace();
            FileInputResponse response = new FileInputResponse(false);
            response.setError(StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : e.getClass().getName());
            return response;
        }
    }

    @RequestMapping(value = "/hcux/doc/customs/header/excel/test")
    @ResponseBody
    public ResponseData test(HttpServletRequest request) throws Exception {
        IRequest requestContext = createRequestContext(request);
        DateUtils.parseDate("", BaseConstants.DATE_TIME_FORMAT, BaseConstants.DATE_FORMAT, "yyyy年MM月dd日", "MM月dd日");

        return new ResponseData();
    }
    /**
     * 报关单打印时需要复制的信息
     *
     * @param dto
     * @param request
     * @return
     */
    @RequestMapping(value = "/hcux/doc/customs/header/queryCopy")
    @ResponseBody
    public ResponseData queryCopy(DocCustomsHeader dto, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        List<DocCustomsHeader> docCustomsHeaderList=new ArrayList<>();
        docCustomsHeaderList.add(service.selectCopyInfo(requestContext,dto));
        return new ResponseData(docCustomsHeaderList);
    }

    @RequestMapping(value = "/hcux/doc/customs/header/exportExcel")
    @ResponseBody
    public void exportExcel(DocCustomsHeader dto, HttpServletRequest request, HttpServletResponse response) throws Exception {
        IRequest iRequest = createRequestContext(request);
        service.exportExcel(dto, iRequest, response);
    }
}