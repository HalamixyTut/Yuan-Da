package hcux.cs.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import com.itextpdf.text.DocumentException;
import hcux.core.exception.HcuxException;
import hcux.cs.dto.CsOutboundOrderFull;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author haojie.zhang@hand-china.com
 * @description 出库物流
 */
public interface ICsOutboundOrderFullService extends IBaseService<CsOutboundOrderFull>, ProxySelf<ICsOutboundOrderFullService> {
    /**
     * ebs出库单全量信息同步hap
     *
     * @param iRequest
     * @param csOutboundOrderFull
     */
    int ebsCsOutboundFullToHap(IRequest iRequest, CsOutboundOrderFull csOutboundOrderFull);

    /**
     * 关联查询
     *
     * @param requestContext
     * @param dto
     * @param page
     * @param pageSize
     * @return
     */
    List<CsOutboundOrderFull> selectData(IRequest requestContext, CsOutboundOrderFull dto, int page, int pageSize);

    /**
     * pdf打印逻辑处理方法
     *
     * @param requestCtx
     * @param invoiceIds
     * @param request
     * @param response
     */
    void pdfPrint(IRequest requestCtx, String invoiceIds, HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException, HcuxException;

    void sendMessage(IRequest iRequest,CsOutboundOrderFull csOutboundOrderFull);
}