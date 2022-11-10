package hcux.lm.controllers;

import com.hand.hap.attachment.UpConstants;
import com.hand.hap.attachment.dto.SysFile;
import com.hand.hap.attachment.exception.AttachmentException;
import com.hand.hap.attachment.exception.FileReadIOException;
import com.hand.hap.attachment.service.ISysFileService;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.exception.TokenException;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hcux.doc.util.CommonUtil;
import hcux.lm.dto.LmTransportImg;
import hcux.lm.service.ILmTransportImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

@Controller
public class LmTransportImgController extends BaseController {

    @Autowired
    private ILmTransportImgService service;
    @Autowired
    private ISysFileService fileService;


    @RequestMapping(value = "/hcux/lm/transport/img/query")
    @ResponseBody
    public ResponseData query(LmTransportImg dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.selectLists(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/hcux/lm/transport/img/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<LmTransportImg> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/hcux/lm/transport/img/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<LmTransportImg> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/hcux/lm/transport/img/download/missing")
    @ResponseBody
    public ResponseData downloadMissing(HttpServletRequest request) throws Exception {
        IRequest iRequest = createRequestContext(request);
        service.downloadMissing(iRequest);

        return new ResponseData();
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
    @RequestMapping(value = "/hcux/lm/transport/img/download")
    @ResponseBody
    public void detailImg(HttpServletRequest request, HttpServletResponse response, @RequestParam Long fileId) throws Exception {
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
    @RequestMapping(value = "/hcux/lm/transport/img/delete")
    public ResponseData deleteFile(HttpServletRequest request, SysFile file) throws TokenException {
        IRequest requestContext = createRequestContext(request);
        fileService.deletefiles(requestContext, file);

        return new ResponseData(Arrays.asList(file));
    }
}