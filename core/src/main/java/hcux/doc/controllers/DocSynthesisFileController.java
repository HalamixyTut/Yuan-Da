package hcux.doc.controllers;

import com.hand.hap.attachment.FileInfo;
import com.hand.hap.attachment.UpConstants;
import com.hand.hap.attachment.Uploader;
import com.hand.hap.attachment.UploaderFactory;
import com.hand.hap.attachment.dto.AttachCategory;
import com.hand.hap.attachment.dto.Attachment;
import com.hand.hap.attachment.dto.SysFile;
import com.hand.hap.attachment.exception.AttachmentException;
import com.hand.hap.attachment.exception.UniqueFileMutiException;
import com.hand.hap.attachment.service.IAttachCategoryService;
import com.hand.hap.attachment.service.ISysFileService;
import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.util.FormatUtil;
import com.hand.hap.core.util.UploadUtil;
import com.hand.hap.security.TokenUtils;
import com.hand.hap.system.dto.DTOStatus;
import hcux.doc.dto.DocBookingNote;
import hcux.doc.dto.DocSynthesis;
import hcux.doc.dto.FileInputResponse;
import hcux.doc.service.IDocBookingNoteService;
import hcux.doc.service.IDocSynthesisService;
import hcux.doc.util.CommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import hcux.doc.dto.DocSynthesisFile;
import hcux.doc.service.IDocSynthesisFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindingResult;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
public class DocSynthesisFileController extends BaseController {

    @Autowired
    private IDocSynthesisFileService service;

    @Autowired
    private MessageSource messageSource;
    @Autowired
    private IAttachCategoryService categoryService;
    @Autowired
    private ISysFileService fileService;
    @Autowired
    private IDocSynthesisService docSynthesisService;
    private static final Logger logger = LoggerFactory.getLogger(DocSynthesisFileController.class);

    @RequestMapping(value = "/hcux/doc/synthesis/file/query")
    @ResponseBody
    public ResponseData query(DocSynthesisFile dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectLists(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/hcux/doc/synthesis/file/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<DocSynthesisFile> dto, BindingResult result, HttpServletRequest request) {
        /*getValidator().validate(dto, result);
        if (result.hasErrors()) {
        ResponseData responseData = new ResponseData(false);
        responseData.setMessage(getErrorMessage(result, request));
        return responseData;
        }*/
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/hcux/doc/synthesis/file/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<DocSynthesisFile> dto) {
        IRequest requestContext = createRequestContext(request);
        Long fileId = dto.get(0).getFileId();
        if (fileId != null) {
            fileService.deletefiles(requestContext, new SysFile() {{
                setFileId(fileId);
            }});
        }
        service.batchDelete(dto);

        //更新综合报关状态
        DocSynthesis docSynthesis = new DocSynthesis();
        docSynthesis.setSynthesisId(dto.get(0).getSynthesisId());
        docSynthesis = docSynthesisService.selectByPrimaryKey(requestContext, docSynthesis);
        docSynthesisService.modifyDocSynthesisStatus(requestContext,docSynthesis);
        docSynthesisService.updateByPrimaryKeySelective(requestContext,docSynthesis);
        return new ResponseData();
    }

    @RequestMapping(value = "/hcux/doc/synthesis/file/upload", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
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
        DocSynthesisFile synthesisFile=service.selectByPrimaryKey(requestContext,new DocSynthesisFile(){{setSynthesisFileId(Long.parseLong(sourceKey));}});
        DocSynthesis docSynthesis=docSynthesisService.selectByPrimaryKey(requestContext,new DocSynthesis(){{setSynthesisId(synthesisFile.getSynthesisId());}});
        if(("0").equals(synthesisFile.getFileType()) && !CommonUtil.objectIsNull(docSynthesis.getStatus())&&!("8").equals(docSynthesis.getStatus())) {
            docSynthesis.setStatus("7");//文件上传，综合表的状态设置为：提单已传
            docSynthesis.set__status(DTOStatus.UPDATE);
            docSynthesisService.updateByPrimaryKeySelective(requestContext,docSynthesis);
        }
        return response;
    }

    @RequestMapping(value = "/hcux/doc/synthesis/file/save")
    @ResponseBody
    public ResponseData save(@RequestBody List<DocSynthesisFile> dto, HttpServletRequest request) {
        IRequest requestCtx = createRequestContext(request);
        List<DocSynthesisFile> docSynthesisFiles = new ArrayList<>();
        if (dto.get(0).getSynthesisFileId() == null) {
            DocSynthesisFile docSynthesisFile = service.insertSelective(requestCtx, dto.get(0));
            docSynthesisFiles.add(docSynthesisFile);
        } else {
            docSynthesisFiles.addAll(dto);
        }
        return new ResponseData(docSynthesisFiles);
    }
}