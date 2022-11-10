package hcux.pm.controllers;

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
import com.hand.hap.attachment.service.IAttachmentService;
import com.hand.hap.attachment.service.ISysFileService;
import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.util.FormatUtil;
import com.hand.hap.core.util.UploadUtil;
import com.hand.hap.security.TokenUtils;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hcux.doc.controllers.DocSynthesisFileController;
import hcux.doc.dto.FileInputResponse;
import hcux.doc.util.CommonUtil;
import hcux.pm.dto.PmHelpFile;
import hcux.pm.service.IPmHelpFileService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author yuanqing.li@hand-china.com
 * @description 使用帮助附件
 */
@Controller
public class PmHelpFileController extends BaseController {

    @Autowired
    private IPmHelpFileService service;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private ISysFileService fileService;
    @Autowired
    private IAttachCategoryService categoryService;
    @Autowired
    private IAttachmentService attachmentService;
    private static final Logger logger = LoggerFactory.getLogger(DocSynthesisFileController.class);

    @RequestMapping(value = {"/hcux/pm/help/file/query", "/api/hcux/pm/help/file/query"})
    @ResponseBody
    public ResponseData query(PmHelpFile dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectLists(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/hcux/pm/help/file/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<PmHelpFile> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/hcux/pm/help/file/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<PmHelpFile> dto) {
        IRequest requestContext = createRequestContext(request);
        Long fileId = dto.get(0).getFileId();

        if (fileId != null) {
            SysFile sysFile = fileService.selectByPrimaryKey(requestContext, fileId);
            if (sysFile.getAttachmentId() != null) {
                Attachment attach = new Attachment();
                attach.setAttachmentId(sysFile.getAttachmentId());
                // 确定attachmentId一定存在，否则会清空attachment表
                if (attach.getAttachmentId() != null) {
                    attachmentService.deleteAttachment(requestContext, attach);
                }
            } else {
                fileService.deletefiles(requestContext, sysFile);
            }
        }
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/hcux/pm/help/file/save")
    @ResponseBody
    public ResponseData saveData(@RequestBody List<PmHelpFile> dto, BindingResult result, HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        List<PmHelpFile> pmHelpFiles = new ArrayList<>();
        if (dto.get(0).getHelpFileId() == null) {
            PmHelpFile pmHelpFile = service.insertSelective(requestCtx, dto.get(0));
            pmHelpFiles.add(pmHelpFile);
        } else {
            pmHelpFiles.addAll(dto);
        }
        return new ResponseData(pmHelpFiles);
    }

    @RequestMapping(value = "/hcux/pm/help/file/upload", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    @ResponseBody
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

        List<SysFile> sysFileList = new ArrayList<>();
        for (FileInfo fileInfo : fileInfoList) {
            try {
                SysFile sysFile = UploadUtil.genSysFile(fileInfo, requestContext.getUserId(), requestContext.getUserId());
                // 分类如果是唯一类型
                if (BaseConstants.YES.equals(category.getIsUnique())) {
                    sysFile = fileService.updateOrInsertFile(requestContext, attach, sysFile);
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
     * 下载单个文件.
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param fileId   文件id
     * @throws FileReadIOException 文件读取IO异常
     */
    @RequestMapping(value = {"/hcux/pm/help/file/download", "/api/hcux/pm/help/file/download"})
    public void downloadFile(HttpServletRequest request, HttpServletResponse response, @RequestParam Long fileId) throws Exception {
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
     * 下载多个文件.
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param sysFiles   文件list
     * @throws FileReadIOException 文件读取IO异常
     */
    @RequestMapping(value = {"/hcux/pm/help/file/downloadZip", "/api/hcux/pm/help/file/downloadZip"})
    public void downloadFilesZip(HttpServletRequest request, HttpServletResponse response, @RequestBody List<SysFile> sysFiles) throws Exception {
        IRequest requestContext = createRequestContext(request);
        //设置zip压缩文件的存放目录
        AttachCategory category = categoryService.selectAttachByCode(requestContext, "HCUX_TEPM_DIR");
        String dir = category.getCategoryPath();
        if (dir == null) {
            dir = "/u01/file/temp/";
            File directoryFile=new File(dir);
            directoryFile.mkdirs();
        }

        //设置最终输出zip文件的目录+文件名
        SimpleDateFormat formatter  = new SimpleDateFormat(BaseConstants.DATE_TIME_FORMAT);
        String zipFileName = formatter.format(new Date())+".zip";
        String zipPath = dir + zipFileName;

        ZipOutputStream zipOutputStream = null;
        FileInputStream zipSource = null;
        BufferedInputStream bufferStream = null;
        File zipFile = new File(zipPath);

        try {
            //构造最终压缩包的输出流
            zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFile));
            for (SysFile dto : sysFiles) {
                Long fileId = dto.getFileId();
                if (fileId != null) {
                    SysFile sysFile = fileService.selectByPrimaryKey(requestContext, fileId);
                    File file = new File(sysFile.getFilePath());
                    if (file.exists()) {
                        //将需要压缩的文件格式化为输入流
                        zipSource = new FileInputStream(file);
                        /*压缩条目不是具体独立的文件，而是压缩包文件列表中的列表项，称为条目，就像索引一样这里的name就是文件名
                        文件名和之前的重复就会导致文件被覆盖*/
                        //在压缩目录中文件的名字
                        ZipEntry zipEntry = new ZipEntry(sysFile.getFileName());
                        //定位该压缩条目位置，开始写入文件到压缩包中
                        zipOutputStream.putNextEntry(zipEntry);

                        bufferStream = new BufferedInputStream(zipSource, 1024);
                        int read = 0;
                        byte[] buff = new byte[1024];
                        while((read = bufferStream.read(buff, 0, 1024)) != -1) {
                            zipOutputStream.write(buff, 0, read);
                        }
                    }
                }
            }
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("download files zip", e);
            }
        } finally {
            //关闭流
            try {
                if (null != bufferStream) {
                    bufferStream.close();
                }
                if (null != zipOutputStream){
                    zipOutputStream.flush();
                    zipOutputStream.close();
                }
                if (null != zipSource) {
                    zipSource.close();
                }
            } catch (IOException e) {
                if (logger.isErrorEnabled()) {
                    logger.error("close IOException", e);
                }
            }
        }

        //判断系统压缩文件是否存在：true-把该压缩文件通过流输出给客户端后删除该压缩文件  false-未处理
        if(zipFile.exists()){
            response.addHeader("Content-Disposition", "attachment;filename=\"" + URLEncoder.encode(zipFileName, ENC) + "\"");
            response.setContentType("application/zip;charset=" + ENC);
            response.setHeader("Accept-Ranges", "bytes");
            int fileLength = (int) zipFile.length();
            response.setContentLength(fileLength);
            if (fileLength > 0) {
                CommonUtil.writeFileToResp(response, zipFile);
            }
            zipFile.delete();
        }
    }
}