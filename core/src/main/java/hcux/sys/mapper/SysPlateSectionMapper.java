package hcux.sys.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hcux.sys.dto.SysPlateSection;

import java.util.List;

/**
 * @author feng.liu01@hand-china.com
 * @description 板块部门维护
 */
public interface SysPlateSectionMapper extends Mapper<SysPlateSection> {

    /**
     * 查询某个用户下的 板块 下的部门
     *
     * @param userId
     * @return
     */
    List<String> queryPlateSection(Long userId);

    /**
     * 查询某个用户下的 部门分配 中 的部门
     *
     * @param userId
     * @return
     */
    List<String> queryUserSection(Long userId);

    List<SysPlateSection> selectList(SysPlateSection dto);
}