package hcux.doc.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.system.service.IBaseService;
import com.itextpdf.text.DocumentException;
import hcux.core.exception.HcuxException;
import hcux.doc.dto.DocCustomsHeader;
import hcux.doc.dto.DocCustomsPrint;
import hcux.doc.dto.DocCustomsRecord;
import hcux.doc.dto.FileInputResponse;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * @author feng.liu01@hand-china.com
 * @description 报关单头表
 */
public interface IDocCustomsHeaderService extends IBaseService<DocCustomsHeader>, ProxySelf<IDocCustomsHeaderService> {

    /**
     * 关联查询，查询list
     *
     * @param requestContext
     * @param dto
     * @param page
     * @param pageSize
     * @return
     */
    List<DocCustomsHeader> selectList(IRequest requestContext, DocCustomsHeader dto, int page, int pageSize);

    /**
     * 查询单个，处理编辑页面汇总行逻辑，所以独立出来
     *
     * @param requestContext
     * @param dto
     * @return
     */
    DocCustomsHeader selectOne(IRequest requestContext, DocCustomsHeader dto);

    /**
     * 头行一起保存
     *
     * @param requestCtx
     * @param headerList
     * @return
     */
    List<DocCustomsHeader> batchUpdateHeaderAndLine(HttpServletRequest request, IRequest requestCtx, List<DocCustomsHeader> headerList);

    /**
     * 通过项目号集合查询出项目号在集合中的数据
     *
     * @param request
     * @param invoiceNumberSet
     * @return
     */
    List<DocCustomsHeader> queryDocCustomsHeaderBySet(IRequest request, Set<String> invoiceNumberSet);

    /**
     * 级联删除头和行
     *
     * @param list
     */
    void deleteHeaderAndLine(HttpServletRequest request, IRequest requestCtx, List<DocCustomsHeader> list) throws HcuxException;

    /**
     * 提交审批方法
     *
     * @param requestCtx
     * @param list
     * @return
     */
    ResponseData submitApproval(HttpServletRequest request, IRequest requestCtx, List<DocCustomsHeader> list);

    /**
     * 审批同意/拒绝执行逻辑方法
     *
     * @param request
     * @param requestCtx
     * @param dto
     * @return
     */
    ResponseData approval(HttpServletRequest request, IRequest requestCtx, List<DocCustomsRecord> dto);

    /**
     * 修改逻辑处理方法
     *
     * @param request
     * @param requestCtx
     * @param list
     * @return
     */
    List<DocCustomsHeader> modify(HttpServletRequest request, IRequest requestCtx, List<DocCustomsHeader> list);

    /**
     * 海关改单
     *
     * @param request
     * @param requestCtx
     * @param list
     * @return
     */
    List<DocCustomsHeader> customsModify(HttpServletRequest request, IRequest requestCtx, List<DocCustomsHeader> list);

    /**
     * pdf打印逻辑处理方法
     *
     * @param requestCtx
     * @param dto
     * @param request
     * @param response
     */
    void pdfPrint(IRequest requestCtx, DocCustomsPrint dto, HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException, HcuxException;

    /**
     * Excel导入
     *
     * @param request
     * @param requestContext
     * @param file
     * @return
     * @throws Exception
     */
    FileInputResponse excelUpload(HttpServletRequest request, IRequest requestContext, MultipartFile file) throws Exception;

    /**
     * 更新其他包装种类字段
     *
     * @param requestCtx
     * @param header
     */
    void updateOtherPackageType(IRequest requestCtx, DocCustomsHeader header);

    /**
     * 报关单定时任务修改状态为准备或者待提交
     *
     * @param iRequest
     * @param docCustomsHeader
     */
    int modifyStatus(IRequest iRequest, DocCustomsHeader docCustomsHeader);

    /**
     * 查询报关单打印时需要复制的信息
     *
     * @param iRequest
     * @param docCustomsHeader
     * @return
     */
    DocCustomsHeader selectCopyInfo(IRequest iRequest, DocCustomsHeader docCustomsHeader);

    /**
     * 综合状态为准备报关，报关状态为待提交
     *
     * @param iRequest
     * @param docCustomsHeader
     */
    void syncChangeStatus(IRequest iRequest, DocCustomsHeader docCustomsHeader);

    /**
     * 综合状态为准备报关之前，报关状态为新建
     *
     * @param iRequest
     * @param docCustomsHeader
     */
    void syncModifyStatus(IRequest iRequest, DocCustomsHeader docCustomsHeader);

    /**
     * 报关单根据查询条件导出头和行
     *
     * @param dto
     * @param iRequest
     * @param response
     */
    void exportExcel(DocCustomsHeader dto, IRequest iRequest, HttpServletResponse response) throws Exception;
}