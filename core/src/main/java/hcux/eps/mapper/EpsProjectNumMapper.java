package hcux.eps.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hcux.eps.dto.EpsProjectNum;

public interface EpsProjectNumMapper extends Mapper<EpsProjectNum>{
    /**
     * 同步ebs白条发票号到hap
     *
     */
    int insertAll();
}