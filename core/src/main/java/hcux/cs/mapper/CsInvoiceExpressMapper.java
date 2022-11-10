package hcux.cs.mapper;

import com.hand.hap.account.dto.User;
import com.hand.hap.mybatis.common.Mapper;
import com.hand.hap.system.dto.CodeValue;
import hcux.cs.dto.CsInvoiceExpress;

import java.util.List;

/**
 * @author feng.liu01@hand-china.com
 * @description 发票快递单号
 */
public interface CsInvoiceExpressMapper extends Mapper<CsInvoiceExpress> {

    List<CsInvoiceExpress> selectData(CsInvoiceExpress dto);

    List<CodeValue> queryPlate();

    /**
     * 导入是获取客户数据
     *
     * @param dto
     * @return
     */
    List<User> queryCustoms(CsInvoiceExpress dto);
}