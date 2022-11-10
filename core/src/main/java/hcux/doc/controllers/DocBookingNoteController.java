package hcux.doc.controllers;

import com.hand.hap.attachment.FileInfo;
import com.hand.hap.attachment.UpConstants;
import com.hand.hap.attachment.Uploader;
import com.hand.hap.attachment.UploaderFactory;
import com.hand.hap.attachment.dto.AttachCategory;
import com.hand.hap.attachment.dto.Attachment;
import com.hand.hap.attachment.dto.SysFile;
import com.hand.hap.attachment.exception.AttachmentException;
import com.hand.hap.attachment.exception.FileReadIOException;
import com.hand.hap.attachment.exception.UniqueFileMutiException;
import com.hand.hap.attachment.service.IAttachCategoryService;
import com.hand.hap.attachment.service.ISysFileService;
import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.exception.TokenException;
import com.hand.hap.core.util.FormatUtil;
import com.hand.hap.core.util.UploadUtil;
import com.hand.hap.security.TokenUtils;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.system.service.ICodeService;
import com.itextpdf.text.DocumentException;
import hcux.core.exception.HcuxException;
import hcux.core.util.AutoUpperUtil;
import hcux.doc.dto.DocBookingNote;
import hcux.doc.dto.FileInputResponse;
import hcux.doc.service.IDocBookingNoteService;
import hcux.doc.util.CommonUtil;
import hcux.doc.util.DocConstant;
import hcux.doc.util.StringUtil;
import hcux.util.PdfUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author feng.liu01@hand-china.com
 * @description 托单数据
 */
@Controller
public class DocBookingNoteController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(DocBookingNoteController.class);

    @Autowired
    private IDocBookingNoteService service;
    @Autowired
    private ICodeService codeService;
    @Autowired
    private IAttachCategoryService categoryService;
    @Autowired
    private ISysFileService fileService;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private ISysFileService sysFileService;


    @RequestMapping(value = "/hcux/doc/booking/note/query")
    @ResponseBody
    public ResponseData query(DocBookingNote dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectList(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/hcux/doc/booking/note/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<DocBookingNote> dto, BindingResult result, HttpServletRequest request) throws Exception {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }

        // 自动转大写
        AutoUpperUtil.autoUpper(dto);

        IRequest requestCtx = createRequestContext(request);
        return service.updateDocBookingNote(request, requestCtx, dto);
    }

    @RequestMapping(value = "/hcux/doc/booking/note/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<DocBookingNote> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    /**
     * PDF打印预览界面
     *
     * @param dto
     * @param request
     * @param response
     * @throws IOException
     * @throws DocumentException
     */
    @RequestMapping(value = "/hcux/doc/booking/note/pdfPrint")
    @ResponseBody
    public void pdfPrintByFlying(DocBookingNote dto, HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException, HcuxException {
        IRequest requestContext = createRequestContext(request);

        Map<String, Object> map = new HashMap<>();
        dto = service.selectList(requestContext, dto, 0, 0).get(0);

        if (dto.getTransportWay() != null) {
            String transportWay = codeService.getCodeMeaningByValue(requestContext, DocConstant.CODE_HCUX_DOC_TRANSPORT_WAY, dto.getTransportWay());
            dto.setTransportWay(transportWay);
        }
        if (dto.getFreightClause() != null) {
            String freightClause = codeService.getCodeMeaningByValue(requestContext, DocConstant.CODE_HCUX_DOC_FREIGHT_CLAUSE, dto.getFreightClause());
            dto.setFreightClause(freightClause);
        }

        if (dto.getPackageNumberUnit() != null) {
            String packageNumberUnit = codeService.getCodeMeaningByValue(requestContext, DocConstant.HCUX_DOC_PACKAGE_NUMBER_UNIT, dto.getPackageNumberUnit());
            dto.setPackageNumberUnit(packageNumberUnit);
        }
        if (dto.getRemark() == null) {
            dto.setRemark("");
        }
        if (dto.getCustomerOrderNum() == null) {
            dto.setCustomerOrderNum("");
        }
        if (dto.getRemark() != null) {
            dto.setRemark(dto.getRemark() + " " + dto.getCustomerOrderNum());
        } else {
            dto.setRemark(dto.getCustomerOrderNum());
        }
        // 处理特殊符号
        StringUtil.escapeBean(dto);

        if (dto.getFileId() != null) {
            String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
            String url = basePath + "/api/public/hcux/doc/booking/note/download?fileId=" + dto.getFileId();
            dto.setDownloadUrl(url);
        }

        if (dto.getShippingMark() != null) {
            dto.setShippingMark(dto.getShippingMark().replace("\n", "<br/>"));
        }

        if (dto.getDischargePort() ==null || ("").equals(dto.getDischargePort())) {
            dto.setDischargePort(dto.getDestinationPort());
        }

        map.put("booking", dto);

        String realPath = request.getSession().getServletContext().getRealPath("/");
        map.put("realPath", realPath);

        String customsName = dto.getConsignee(); // 收货单位
        if (customsName != null) {
            customsName = customsName.split(" +")[0]; // 截取第一个单词
            if (customsName.equals("TO")) {
                String notificationUnit = dto.getNotificationUnit(); // 通知单位
                if (notificationUnit != null) {
                    customsName = "_" + notificationUnit.split(" +")[0]; // 截取第一个单词
                } else {
                    customsName = "";
                }
            } else {
                customsName = "_" + customsName;
            }
        } else {
            customsName = "";
        }
        String packageNumber = "";
        Double number = dto.getPackageNumber();
        String unit = dto.getPackageNumberUnit();
        if (number != null) {
            packageNumber = "_" + number.intValue();
            if (unit != null) {
                packageNumber += unit;
            }
        }

        String fileName = dto.getInvoiceNumber() + "_托单" + customsName + packageNumber + ".pdf";
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline;filename=" + URLEncoder.encode(fileName, ENC));

        String content = PdfUtil.freeMarkerRender(map, "booking_note");
        PdfUtil.parseHtmlToPdfByFlying(map, response.getOutputStream(), content);
    }

    /**
     * 附件上传提交页面.
     *
     * @param request     HttpServletRequest
     * @param locale      当前语言
     * @param contextPath 上下文路径
     * @return 上传结果信息
     * @throws Exception 上传异常
     */
    @RequestMapping(value = "/hcux/doc/booking/note/upload", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    public FileInputResponse upload(HttpServletRequest request, Locale locale, String contextPath) throws Exception {
        Uploader uploader = UploaderFactory.getMutiUploader();
        uploader.init(request);

        String status = uploader.getStatus();

        FileInputResponse response = new FileInputResponse();
        response.setError("");
        response.setAppend(false);
        if (UpConstants.NOT_FILE_ERROR.equals(status)) {
            response.setError(messageSource.getMessage(UpConstants.ERROR_UPLOAD_NOT_FILE_FORM, null, locale));
        }
        if (UpConstants.ALL_SIZE_MAX_ERROR.equals(status)) {
            response.setError(messageSource.getMessage(UpConstants.ERROR_UPLOAD_TOTAL_SIZE_LIMIT_EXCEEDED, null, locale));
        }
        if (UpConstants.LIMIT_UPLOAD_NUM_ERROR.equals(status)) {
            response.setError(messageSource.getMessage(UpConstants.ERROR_UPLOAD_TOTAL_NUM_LIMIT_EXCEEDED, null, locale));
        }
        if (UpConstants.UPLOAD_ERROR.equals(status)) {
            response.setError(messageSource.getMessage(UpConstants.ERROR_UPLOAD_UNKNOWN, null, locale));
        }

        String sourceType = uploader.getParams(UpConstants.ATTACHMENT_SOURCE_TYPE);
        String sourceKey = uploader.getParams(UpConstants.ATTACHMENT_SOURCE_KEY);
        String attachmentId = request.getParameter(DocBookingNote.FIELD_ATTACHMENT_ID);

//        response.setMessage(messageSource.getMessage("hap.upload_success", null, locale));


        if (StringUtils.isBlank(sourceType)) {
            throw new AttachmentException(UpConstants.ERROR_UPLOAD_SOURCE_TYPE_EMPTY, UpConstants.ERROR_UPLOAD_SOURCE_TYPE_EMPTY, new Object[0]);
        }
        IRequest requestContext = createRequestContext(request);

        AttachCategory category = categoryService.selectAttachByCode(requestContext, sourceType);
        if (category == null) {
            throw new AttachmentException(UpConstants.ERROR_UPLOAD_SOURCE_TYPE_FOLDER_NOT_FOUND, UpConstants.ERROR_UPLOAD_SOURCE_TYPE_FOLDER_NOT_FOUND, new String[]{sourceType});
        }

        // 设置上传参数
        UploadUtil.initUploaderParams(uploader, category);

        List<FileInfo> fileInfoList = uploader.upload();
        //上传过后 重新获取状态
        status = uploader.getStatus();
        if (UpConstants.FILE_PROCESS_ERROR.equals(status)) {
            throw new AttachmentException(UpConstants.ERROR_UPLOAD_FILE_PROCESS, UpConstants.ERROR_UPLOAD_FILE_PROCESS, new Object[0]);
        }

        Attachment attach = UploadUtil.genAttachment(category, sourceKey, requestContext.getUserId(), requestContext.getUserId());
        if (StringUtils.isNotBlank(attachmentId)) {
            attach.setAttachmentId(Long.parseLong(attachmentId));
        }
        List<SysFile> sysFileList = new ArrayList<>();
        for (FileInfo fileInfo : fileInfoList) {
            try {
                SysFile sysFile = UploadUtil.genSysFile(fileInfo, requestContext.getUserId(), requestContext.getUserId());
                // 分类如果是唯一类型
                if (BaseConstants.YES.equals(category.getIsUnique())) {
                    sysFile = service.updateOrInsertFile(requestContext, attach, sysFile);
                } else {
                    fileService.insertFileAndAttach(requestContext, attach, sysFile);
                }
                sysFile.setFilePath(null);
                sysFile.setFileSizeDesc(FormatUtil.formatFileSize(sysFile.getFileSize()));
                TokenUtils.generateAndSetToken(TokenUtils.getSecurityKey(request.getSession(false)), sysFile);
                sysFileList.add(sysFile);

                response.setFileName(sysFile.getFileName());
                response.setFileId(sysFile.getFileId());
                response.setAttachmentId(attach.getAttachmentId());
//
//                List<String> initialPreview = response.getInitialPreview();
//                String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
//                String url = basePath + "/hcux/doc/booking/note/download?fileId=" + sysFile.getFileId();
//                initialPreview.add(url);
//
//                List<InitialPreviewConfig> initialPreviewConfigList = response.getInitialPreviewConfig();
//                InitialPreviewConfig initialPreviewConfig = new InitialPreviewConfig();
//                initialPreviewConfig.setDownloadUrl(url);
//                initialPreviewConfig.setCaption(sysFile.getFileName());
//                initialPreviewConfig.setUrl(basePath + "/hcux/doc/booking/note/delete?fileId=" + sysFile.getFileId());
//                initialPreviewConfig.setKey("1");
//                initialPreviewConfig.setSize(sysFile.getFileSize() + "");
//                initialPreviewConfigList.add(initialPreviewConfig);
//
//                List<InitialPreviewConfig> initialPreviewThumbTags = response.getInitialPreviewThumbTags();
//                initialPreviewThumbTags.add(new InitialPreviewConfig());

            } catch (UniqueFileMutiException ex) {
                response.setError(messageSource.getMessage("hap.mesg_unique", null, locale));
                break;
            } catch (Exception e) {
                if (logger.isErrorEnabled()) {
                    logger.error("database error", e);
                }
                File file = fileInfo.getFile();
                if (file.exists()) {
                    file.delete();
                }
                response.setError(e.getMessage());
                break;
            }
        }
        return response;
    }

    /**
     * 文件下载默认编码.
     */
    private static final String ENC = "UTF-8";

    /**
     * 具体查看某个附件.
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param fileId   文件id
     * @throws FileReadIOException 文件读取IO异常
     */
    @RequestMapping(value = {"/hcux/doc/booking/note/download", "/api/public/hcux/doc/booking/note/download"})
    public void detail(HttpServletRequest request, HttpServletResponse response, @RequestParam Long fileId) throws Exception {
        IRequest requestContext = createRequestContext(request);
        SysFile sysFile = fileService.selectByPrimaryKey(requestContext, fileId);
        File file = new File(sysFile.getFilePath());
        if (file.exists()) {
            response.addHeader("Content-Disposition", "attachment;filename=\"" + URLEncoder.encode(sysFile.getFileName(), ENC) + "\"");
            response.setContentType(sysFile.getFileType() + ";charset=" + ENC);
            response.setHeader("Accept-Ranges", "bytes");
            int fileLength = (int) file.length();
            response.setContentLength(fileLength);
            if (fileLength > 0) {
                CommonUtil.writeFileToResp(response, file);
            }
        } else {
            throw new AttachmentException(UpConstants.ERROR_DOWNLOAD_FILE_ERROR, UpConstants.ERROR_DOWNLOAD_FILE_ERROR, new Object[0]);
        }
    }

    /**
     * 删除一个文件
     *
     * @param request
     * @param file
     * @return
     * @throws TokenException
     */
    @RequestMapping(value = "/hcux/doc/booking/note/delete")
    public ResponseData deleteFile(HttpServletRequest request, SysFile file) throws TokenException {
        IRequest requestContext = createRequestContext(request);
        sysFileService.deletefiles(requestContext, file);

        return new ResponseData(Arrays.asList(file));

    }

    @RequestMapping(value = "/hcux/doc/booking/note/exportExcel")
    @ResponseBody
    public void exportExcel(DocBookingNote dto, HttpServletRequest request, HttpServletResponse response) throws Exception{
        IRequest iRequest = createRequestContext(request);
        service.exportExcel(dto, iRequest,response);
    }
}