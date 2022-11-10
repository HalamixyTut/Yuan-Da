package hcux.mdm.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hcux.mdm.dto.MdmCarNumber;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface IMdmCarNumberService extends IBaseService<MdmCarNumber>, ProxySelf<IMdmCarNumberService> {
    /**
     * 根据条件查询车牌号
     * @param requestContext
     * @param dto
     * @param page
     * @param pageSize
     * @return
     */
    List<MdmCarNumber> selectData(IRequest requestContext, MdmCarNumber dto, int page, int pageSize);

    /**
     * excel导出
     * @param iRequest
     * @param mdmCarNumber
     * @param response
     * @throws Exception
     */
    void exportExcel(IRequest iRequest, MdmCarNumber mdmCarNumber, HttpServletResponse response) throws Exception;
}
