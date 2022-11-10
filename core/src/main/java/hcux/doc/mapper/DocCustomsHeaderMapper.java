package hcux.doc.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hcux.doc.dto.DocCustomsHeader;

import java.util.List;
import java.util.Set;

/**
 * @author feng.liu01@hand-china.com
 * @description 报关单头表
 */
public interface DocCustomsHeaderMapper extends Mapper<DocCustomsHeader> {

    List<DocCustomsHeader> selectList(DocCustomsHeader dto);

    /**
     * 根据项目号集合查询出所有重复的数据
     *
     * @param invoiceNumberSet
     * @return
     */
    List<DocCustomsHeader> queryCustomsHeaderByInvoiceNumber(Set<String> invoiceNumberSet);

    /**
     * 查询最大海关改单序列号
     *
     * @param header
     * @return
     */
    Long queryMaxSn(DocCustomsHeader header);

    /**
     * 查询总计金额
     *
     * @param dto
     * @return
     */
    Double queryTotalAmount(DocCustomsHeader dto);

    /**
     * 根据托单ID查询状态不为已完结和已改单的数据
     *
     * @param docCustomsHeader
     * @return
     */
    List<DocCustomsHeader> selectByBookingId(DocCustomsHeader docCustomsHeader);

    /**
     * 查询报关单打印时需要复制的信息
     * @param docCustomsHeader
     * @return
     */
    DocCustomsHeader selectCopyInfo(DocCustomsHeader docCustomsHeader);

    /**
     * 按照币值汇总金额
     *
     * @param dto
     * @return
     */
    List<DocCustomsHeader> queryCurrencySystemTotal(DocCustomsHeader dto);
}