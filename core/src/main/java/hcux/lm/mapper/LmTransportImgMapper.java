package hcux.lm.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hcux.lm.dto.LmTransportImg;

import java.util.List;

public interface LmTransportImgMapper extends Mapper<LmTransportImg>{
    /**
    * 查询一个运输单下的图片数量
    *
    * @param img
    */
    Long queryImgCount(LmTransportImg img);

    /**
     * 查询attachmentId为空的数据
     *
     * @param
     */
    List<LmTransportImg> queryListByAttachmentIsNull();

    /**
     * 根据条件查询运输单回执图片
     *
     * @param dto
     * @return
     */
    List<LmTransportImg> selectLists(LmTransportImg dto);
}