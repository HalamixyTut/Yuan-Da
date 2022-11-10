package hcux.doc.service;

import com.hand.hap.attachment.dto.Attachment;
import com.hand.hap.attachment.dto.SysFile;
import com.hand.hap.attachment.exception.UniqueFileMutiException;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.system.service.IBaseService;
import hcux.doc.dto.DocBookingNote;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author feng.liu01@hand-china.com
 * @description 托单数据
 */
public interface IDocBookingNoteService extends IBaseService<DocBookingNote>, ProxySelf<IDocBookingNoteService> {

    /**
     * 对于分类唯一的来调用.
     *
     * @param requestContext IRequest
     * @param attach         Attachment对象
     * @param file           SysFile对象
     * @return SysFile 返回更新或者插入的SysFile对象
     * @throws UniqueFileMutiException 此分类下有多个附件 异常
     */
    SysFile updateOrInsertFile(IRequest requestContext, @StdWho Attachment attach, @StdWho SysFile file)
            throws UniqueFileMutiException;

    /**
     * 关联查询
     *
     * @param requestContext
     * @param dto
     * @param page
     * @param pageSize
     * @return
     */
    List<DocBookingNote> selectList(IRequest requestContext, DocBookingNote dto, int page, int pageSize);

    ResponseData updateDocBookingNote(HttpServletRequest request, IRequest iRequest, @StdWho List<DocBookingNote> list);

    /**
     * 托单导出
     *
     * @param dto
     * @param iRequest
     * @param response
     * @return
     */
    void exportExcel(DocBookingNote dto, IRequest iRequest, HttpServletResponse response) throws Exception;
}