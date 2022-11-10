package hcux.cs.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hcux.cs.dto.CsOverdue;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ICsOverdueService extends IBaseService<CsOverdue>, ProxySelf<ICsOverdueService> {

    int ebsDataInsertIntoHap(IRequest request);

    List<CsOverdue> selectListsFromHap(IRequest request, CsOverdue csOverdue, int page, int pageSize);

    List<CsOverdue> selectListsFromEbs(IRequest request, CsOverdue csOverdue);

    CsOverdue selectSumLockAmountFromHap(IRequest request, CsOverdue csOverdue);

    void test(IRequest iRequest, MultipartFile file);

    void updateHapData(IRequest request, CsOverdue csOverdue);
}
