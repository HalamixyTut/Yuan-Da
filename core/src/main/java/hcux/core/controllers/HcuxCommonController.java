package hcux.core.controllers;

import com.hand.hap.account.dto.User;
import com.hand.hap.account.service.IUserService;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.CodeValue;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.system.service.ICodeService;
import hcux.sys.dto.SysEntrustParty;
import hcux.sys.mapper.SysEntrustPartyMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 参考框架的获取code方法，提供 /api 接口
 */
@RestController
public class HcuxCommonController extends BaseController {

    @Autowired
    private ICodeService codeService;
    @Autowired
    private IUserService userService;
    @Autowired
    private SysEntrustPartyMapper sysEntrustPartyMapper;
    /**
     * 获取通用数据.
     *
     * @param code    快码code
     * @param request HttpServletRequest
     * @return json值
     */
    @RequestMapping(value = "/api/common/code/{code}")
    @ResponseBody
    public ResponseData getCommonData(@PathVariable String code,
                                      HttpServletRequest request) {
        IRequest iRequest = createRequestContext(request);
        List<CodeValue> enabledCodeValues = codeService.getCodeValuesByCode(iRequest, code);
        return new ResponseData(enabledCodeValues);
    }

    @RequestMapping(value = "/api/common/user/query")
    @ResponseBody
    public ResponseData queryUsers(HttpServletRequest request) {
        IRequest iRequest = createRequestContext(request);
        User user = new User();
        user.setUserId(iRequest.getUserId());
        List<User> list = userService.selectUsers(iRequest, user, 0, 0);
        for (User dto : list) {
            if ("OUTER".equals(dto.getUserType()) && StringUtils.isBlank(dto.getEmployeeName())) {
                dto.setEmployeeName(dto.getDescription());
                //获取外部人员对应的是否对账字段
                SysEntrustParty sysEntrustParty=new SysEntrustParty(){{setUserId(dto.getUserId());}};
                List<SysEntrustParty> entrustPartyList=sysEntrustPartyMapper.selectSerialNumberOuter(sysEntrustParty);
                if(entrustPartyList!=null&&!entrustPartyList.isEmpty()){
                    dto.setStatementFlag(entrustPartyList.get(0).getStatementFlag());
                }
            }
        }
        return new ResponseData(list);
    }
}
