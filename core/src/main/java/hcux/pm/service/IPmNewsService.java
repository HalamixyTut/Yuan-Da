package hcux.pm.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hcux.pm.dto.PmNews;

import java.util.List;

public interface IPmNewsService extends IBaseService<PmNews>, ProxySelf<IPmNewsService>{

    List<PmNews> selectLists(IRequest requestContext, PmNews dto, int page, int pageSize);
    PmNews selectNews(IRequest request,PmNews pmNews);
}