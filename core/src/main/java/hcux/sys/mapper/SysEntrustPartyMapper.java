package hcux.sys.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hcux.sys.dto.SysEntrustParty;

import java.util.List;

/**
 * @author feng.liu01@hand-china.com
 * @description 委托方用户
 */
public interface SysEntrustPartyMapper extends Mapper<SysEntrustParty> {

    List<SysEntrustParty> selectList(SysEntrustParty dto);

    /**
     * 查找用户下的所有系列号
     * @param dto
     * @return
     */
    List<SysEntrustParty> selectSerialNumber(SysEntrustParty dto);

    /**
     * 查询期初余额的总值
     * @param dto
     * @return
     */
    SysEntrustParty selectTotalMinAgencyFee(SysEntrustParty dto);

    /**
     * 不带用户名查询系列号
     * @param dto
     * @return
     */
    List<SysEntrustParty> selectSerialNumberWithoutUser(SysEntrustParty dto);

    /**
     * 不带用户名的查询期初余额总值
     * @param dto
     * @return
     */
    SysEntrustParty selectTotalWithOutUser(SysEntrustParty dto);

    /**
     * 选择系列号的买断价保留小数位
     * @param dto
     * @return
     */
    SysEntrustParty selectPriceDecimal (SysEntrustParty dto);

    /**
     * 通过userId查询员工
     * @param dto
     * @return
     */
    SysEntrustParty selectEmployee (SysEntrustParty dto);

    /**
     * 外部人员查询期初余额总值
     * @param dto
     * @return
     */
    SysEntrustParty selectTotalMinAgencyFeeOuter (SysEntrustParty dto);

    /**
     * 门户外部人员登录时对应的系列号
     * @param dto
     * @return
     */
    List<SysEntrustParty> selectSerialNumberOuter (SysEntrustParty dto);
}