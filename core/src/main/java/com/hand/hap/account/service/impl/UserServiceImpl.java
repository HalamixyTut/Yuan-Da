package com.hand.hap.account.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import com.hand.hap.account.constants.UserConstants;
import com.hand.hap.account.dto.User;
import com.hand.hap.account.exception.UserException;
import com.hand.hap.account.mapper.UserMapper;
import com.hand.hap.account.mapper.UserRoleMapper;
import com.hand.hap.account.service.IRole;
import com.hand.hap.account.service.IRoleService;
import com.hand.hap.account.service.IUserService;
import com.hand.hap.cache.impl.ResourceItemAssignCache;
import com.hand.hap.cache.impl.RoleFunctionCache;
import com.hand.hap.cache.impl.UserCache;
import com.hand.hap.core.BaseConstants;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.annotation.StdWho;
import com.hand.hap.core.util.ValidateUtils;
import com.hand.hap.function.dto.Function;
import com.hand.hap.function.dto.MenuItem;
import com.hand.hap.function.dto.Resource;
import com.hand.hap.function.dto.ResourceItemAssign;
import com.hand.hap.function.mapper.ResourceItemAssignMapper;
import com.hand.hap.function.service.IFunctionService;
import com.hand.hap.function.service.IRoleFunctionService;
import com.hand.hap.function.service.IRoleResourceItemService;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.security.CustomUserDetails;
import com.hand.hap.security.IUserSecurityStrategy;
import com.hand.hap.security.PasswordManager;
import com.hand.hap.security.service.impl.UserSecurityStrategyManager;
import com.hand.hap.system.dto.DTOStatus;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * ?????????????????? - ?????????.
 *
 * @author njq.niu@hand-china.com
 * @author yuliao.chen@hand-china.com
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements IUserService {

    @Autowired
    private PasswordManager passwordManager;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    UserSecurityStrategyManager userSecurityStrategyManager;

    @Autowired
    @Qualifier("roleServiceImpl")
    private IRoleService roleService;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private IFunctionService functionService;

    @Autowired
    private IRoleFunctionService roleFunctionService;

    @Autowired
    private IRoleResourceItemService roleResourceItemService;

    @Autowired
    private ResourceItemAssignCache resourceItemAssignCache;

    @Autowired
    private ResourceItemAssignMapper resourceItemAssignMapper;

    @Autowired
    private RoleFunctionCache roleFunctionCache;

    @Autowired
    private UserCache userCache;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User insertSelective(IRequest request, @StdWho User record) {
        if (StringUtils.isEmpty(record.getPassword())) {
            record.setPassword(passwordManager.getDefaultPassword());
        }
        //????????????????????????
        record.setUserName(record.getUserName().toLowerCase());
        record.setPasswordEncrypted(passwordManager.encode(record.getPassword()));
        List<IUserSecurityStrategy> userSecurityStrategies = userSecurityStrategyManager.getUserSecurityStrategyList();
        for (IUserSecurityStrategy userSecurityStrategy : userSecurityStrategies) {
            record = userSecurityStrategy.beforeCreateUser(request, record);
        }
        record = super.insertSelective(request, record);
        //????????????????????????
        userMapper.updatePassword(record.getUserId(), record.getPasswordEncrypted());
        return record;
    }

    @Override
    public void validateEmail(String email) throws UserException {
        if (!ValidateUtils.validateEmail(email)) {
            throw new UserException(UserException.EMAIL_FORMAT, new Object[]{});
        }
    }

    @Override
    public void validatePhone(String phone) throws UserException {
        if (!ValidateUtils.validatePhone(phone)) {
            throw new UserException(UserException.PHONE_FORMAT, new Object[]{});
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public User login(User user) throws UserException {
        if (user == null || org.apache.commons.lang3.StringUtils.isAnyBlank(user.getUserName(), user.getPassword())) {
            throw new UserException(UserException.ERROR_USER_PASSWORD, UserException.ERROR_USER_PASSWORD, null);
        }
        User user1 = userMapper.selectByUserName(user.getUserName());
        if (user1 == null) {
            throw new UserException(UserException.ERROR_USER_PASSWORD, UserException.ERROR_USER_PASSWORD, null);
        }
        if (User.STATUS_LOCK.equals(user1.getStatus())) {
            throw new UserException(UserException.ERROR_USER_EXPIRED, UserException.ERROR_USER_EXPIRED, null);
        }
        if (user1.getStartActiveDate() != null && user1.getStartActiveDate().getTime() > System.currentTimeMillis()) {
            throw new UserException(UserException.ERROR_USER_EXPIRED, UserException.ERROR_USER_EXPIRED, null);
        }
        if (user1.getEndActiveDate() != null && user1.getEndActiveDate().getTime() < System.currentTimeMillis()) {
            throw new UserException(UserException.ERROR_USER_EXPIRED, UserException.ERROR_USER_EXPIRED, null);
        }
        if (!passwordManager.matches(user.getPassword(), user1.getPasswordEncrypted())) {
            throw new UserException(UserException.ERROR_USER_PASSWORD, UserException.ERROR_USER_PASSWORD, null);
        }
        return user1;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public User selectByUserName(String userName) {
        userName = userName.toLowerCase();
        User user = userCache.getValue(userName);
        if (null == user) {
            user = userMapper.selectByUserName(userName);
            if (null != user) {
                userCache.setValue(userName, user);
            }
        }
        return user;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<User> selectUserNameByEmployeeCode(String employeeCode) {
        return userMapper.selectUserNameByEmployeeCode(employeeCode);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User updateByPrimaryKeySelective(IRequest request, @StdWho User record) {
        int count = userMapper.updateBasic(record);
        checkOvn(count, record);
        userCache.remove(record.getUserName());
        return record;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePassword(Long userId, String userName, String password) {
        userMapper.updatePassword(userId, passwordManager.encode(password));
        userCache.remove(userName);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void validatePassword(IRequest request, String newPwd, String newPwdAgain) throws UserException {
        List<IUserSecurityStrategy> userSecurityStrategies = userSecurityStrategyManager.getUserSecurityStrategyList();
        for (IUserSecurityStrategy userSecurityStrategy : userSecurityStrategies) {
            userSecurityStrategy.validatePassword(newPwd, newPwdAgain);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateNotFirstLogin(Long userId, String status) {
        userMapper.updateFirstLogin(userId, status);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<User> selectUsers(IRequest request, User user, int pageNum, int pageSize) {
        if (user != null && StringUtil.isNotEmpty(user.getUserName())) {
            user.setUserName(user.getUserName().toLowerCase());
        }
        PageHelper.startPage(pageNum, pageSize);
//        Criteria criteria = new Criteria(user);
//        criteria.where(new WhereField(User.FIELD_USER_NAME, Comparison.LIKE), User.FIELD_USER_ID, User.FIELD_USER_TYPE,
//                new WhereField(User.FIELD_EMPLOYEE_CODE, Comparison.LIKE), User.FIELD_EMPLOYEE_NAME, User.FIELD_STATUS);
//        criteria.select(User.FIELD_USER_ID, User.FIELD_USER_NAME, User.FIELD_EMPLOYEE_ID,
//                User.FIELD_EMPLOYEE_NAME, User.FIELD_EMPLOYEE_CODE, User.FIELD_EMAIL, User.FIELD_PHONE,
//                User.FIELD_STATUS, User.FIELD_START_ACTIVE_DATE, User.FIELD_END_ACTIVE_DATE, User.FIELD_DESCRIPTION);
//        criteria.selectExtensionAttribute();
        return userMapper.selectUsersOption(user);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<IRole> selectUserAndRoles(IRequest request, User user, int pageNum, int pageSize) {
        return roleService.selectRolesByUser(request, user);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<MenuItem> queryFunction(IRequest requestContext, Long userId) {
        List<MenuItem> menus = functionService.selectAllMenus(requestContext);
        Set<Long> userFunctionIds = null;
        if (userId != null) {
            userFunctionIds = getUserFunctionIds(requestContext.getAllRoleId());
        }
        return updateMenu(menus, userFunctionIds);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<MenuItem> queryResourceItems(IRequest requestContext, Long userId, Long functionId) {
        Function function = new Function();
        function.setFunctionId(functionId);
        List<Resource> resources = roleResourceItemService.queryHtmlResources(requestContext, function);
        List<MenuItem> menus = updateResourceMenu(roleResourceItemService.createResources(resources));
        //??????????????????????????????????????? ????????????
        if (CollectionUtils.isEmpty(menus)) {
            return menus;
        }
        List<Long> allAssignElementIds = getAllAssignElementIds(userId, Arrays.asList(requestContext.getAllRoleId()));
        //???????????????????????????????????????????????????
        if (CollectionUtils.isEmpty(allAssignElementIds)) {
            return menus;
        }
        return updateMenuCheck(menus, allAssignElementIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<ResourceItemAssign> updateResourceItemAssign(IRequest requestContext,
                                                             List<ResourceItemAssign> resourceItemAssignList,
                                                             Long userId, Long functionId) {
        resourceItemAssignMapper.deleteByUserIdAndFunctionId(userId, functionId);
        if (CollectionUtils.isNotEmpty(resourceItemAssignList)) {
            for (ResourceItemAssign resourceItemAssign : resourceItemAssignList) {
                resourceItemAssign.setAssignType(ResourceItemAssign.ASSIGN_TYPE_USER);
                resourceItemAssign.setFunctionId(functionId);
                resourceItemAssignMapper.insertSelective(resourceItemAssign);
            }
        }
        resourceItemAssignCache.load(ResourceItemAssign.ASSIGN_TYPE_USER + BaseConstants.UNDER_LINE + userId);
        return resourceItemAssignList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteResourceItems(Long userId, Long functionId) {
        resourceItemAssignMapper.deleteByUserIdAndFunctionId(userId, functionId);
        resourceItemAssignCache.load(ResourceItemAssign.ASSIGN_TYPE_USER + BaseConstants.UNDER_LINE + userId);
    }

    @Override
    public void resetPassword(IRequest request, User user, String passwordAgain) throws UserException {
        String password = user.getPassword();
        validatePassword(request, password, passwordAgain);
        user = userMapper.selectByPrimaryKey(user);
        self().updatePassword(user.getUserId(), user.getUserName(), password);
        self().updateNotFirstLogin(user.getUserId(), "N");
    }

    @Override
    public void firstAndExpiredLoginUpdatePassword(IRequest request, String newPassword, String newPasswordAgain) throws UserException {
        Long accountId = request.getUserId();
        String userName = request.getUserName();
        validatePassword(request, newPassword, newPasswordAgain);
        self().updatePassword(accountId, userName, newPassword);
        self().updateNotFirstLogin(accountId, User.NOT_FIRST_LOGIN_STATUS);
    }

    @Override
    public void updateOwnerPassword(IRequest request, String oldPassword, String newPassword, String newPasswordAgain)
            throws UserException {
        Long accountId = request.getUserId();
        String userName = request.getUserName();

        validatePassword(request, newPassword, newPasswordAgain);

        if (StringUtils.isEmpty(oldPassword)) {
            throw new UserException(UserException.PASSWORD_NOT_EMPTY, null);
        }
        User tmp = new User();
        tmp.setUserId(accountId);
        User userInDB = selectByPrimaryKey(request, tmp);
        String pwd = userInDB.getPasswordEncrypted();
        if (!passwordManager.matches(oldPassword, pwd)) {
            throw new UserException(UserException.USER_PASSWORD_WRONG, null);
        }

        if (passwordManager.matches(newPassword, pwd)) {
            throw new UserException(UserException.USER_PASSWORD_SAME, null);
        }

        self().updatePassword(accountId, userName, newPassword);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<User> batchUpdate(IRequest request, List<User> list) {
        Criteria criteria = new Criteria();
        criteria.update(User.FIELD_EMPLOYEE_ID, User.FIELD_EMAIL, User.FIELD_PHONE,
                User.FIELD_STATUS, User.FIELD_END_ACTIVE_DATE, User.FIELD_START_ACTIVE_DATE,
                User.FIELD_DESCRIPTION,
                User.FIELD_USER_TYPE,
                User.FIELD_PLATE, User.FIELD_SIGN_FLAG, User.FIELD_ENTRUST_ID);
        criteria.updateExtensionAttribute();
        for (User user : list) {
            switch (user.get__status()) {
                case DTOStatus.ADD:
                    self().insertSelective(request, user);
                    break;
                case DTOStatus.UPDATE:
                    userCache.remove(user.getUserName());
                    self().updateByPrimaryKeyOptions(request, user, criteria);
                    break;
                default:
                    break;
            }
        }
        return list;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByPrimaryKey(User user) {
        int ret = super.deleteByPrimaryKey(user);
        userRoleMapper.deleteByUserId(user.getUserId());
        userCache.remove(user.getUserName());
        return ret;
    }

    @Override
    public User convertToUser(UserDetails userDetails) {
        if (userDetails instanceof CustomUserDetails) {
            CustomUserDetails customUserDetails = (CustomUserDetails) userDetails;
            User user = new User();
            user.setUserId(customUserDetails.getUserId());
            user.setUserName(customUserDetails.getUsername());
            user.setEmployeeId(customUserDetails.getEmployeeId());
            user.setEmployeeCode(customUserDetails.getEmployeeCode());
            Collection<? extends GrantedAuthority> authorities = customUserDetails.getAuthorities();
            List<String> roleCodes = new ArrayList<>();
            for (GrantedAuthority authority : authorities) {
                if (!authority.getAuthority().equals("ROLE_USER")) {
                    roleCodes.add(authority.getAuthority());
                }
            }
            user.setRoleCode(roleCodes);
            return user;
        }
        return selectByUserName(userDetails.getUsername());
    }

    /**
     * ????????????????????????ID.
     * 1??????????????? - ????????????????????????
     * 2??????????????? - ????????????????????????
     *
     * @param userId ??????ID
     * @return ???????????????????????????????????????ID (?????????)
     */
    @Override
    public List<Long> getAllAssignElementIds(Long userId, List<Long> roleIds) {
        List<Long> allAssignElementIds = new ArrayList<>();
        if (userId != null) {
            if (CollectionUtils.isEmpty(roleIds)) {
                return null;
            }
            //??????????????????????????????ID
            List<Long> roleAssignElementIds = getAllRoleAssignElementIds(roleIds);

            //??????????????????????????????ID
            ResourceItemAssign[] userAssigns = resourceItemAssignCache.getValue(ResourceItemAssign.ASSIGN_TYPE_USER + BaseConstants.UNDER_LINE + userId);
            if (ArrayUtils.isEmpty(userAssigns)) {
                //?????????????????????????????? ????????????????????????????????????
                if (CollectionUtils.isNotEmpty(roleAssignElementIds)) {
                    allAssignElementIds.addAll(roleAssignElementIds);
                }
                return allAssignElementIds;
            }
            for (ResourceItemAssign userAssign : userAssigns) {
                //??????????????????????????? ?????????????????????N????????????
                if (UserConstants.NO.equalsIgnoreCase(userAssign.getEnable())) {
                    allAssignElementIds.add(userAssign.getElementId());
                }
            }
            // ???????????????????????????  ?????????????????????????????????
            if (CollectionUtils.isNotEmpty(roleAssignElementIds)) {
                for (Long roleAssignElementId : roleAssignElementIds) {
                    boolean check = true;
                    for (ResourceItemAssign userAssign : userAssigns) {
                        if (roleAssignElementId.equals(userAssign.getElementId())) {
                            check = false;
                            break;
                        }
                    }
                    if (check) {
                        allAssignElementIds.add(roleAssignElementId);
                    }
                }
            }
        }
        return allAssignElementIds;
    }

    /**
     * ?????????????????????????????????.
     *
     * @param menus           ???????????????????????????
     * @param userFunctionIds ??????????????????ID
     * @return ?????????????????????????????????
     */
    private List<MenuItem> updateMenu(List<MenuItem> menus, Set<Long> userFunctionIds) {
        if (CollectionUtils.isEmpty(menus) || CollectionUtils.isEmpty(userFunctionIds)) {
            return new ArrayList<>();
        }
        Iterator<MenuItem> iterator = menus.iterator();
        while (iterator.hasNext()) {
            MenuItem menu = iterator.next();
            boolean hasChild = false;

            if (CollectionUtils.isNotEmpty(menu.getChildren())) {
                hasChild = CollectionUtils.isNotEmpty(updateMenu(menu.getChildren(), userFunctionIds));
            }

            boolean hasFunction = userFunctionIds.contains(menu.getId());

            if (!hasFunction && !hasChild) {
                iterator.remove();
            }
        }
        return menus;
    }

    /**
     * ????????????????????????????????????.
     *
     * @param allRoleIds ????????????
     * @return ??????ID
     */
    private Set<Long> getUserFunctionIds(Long[] allRoleIds) {
        if (ArrayUtils.isEmpty(allRoleIds)) {
            return null;
        }
        Set<Long> userFunctionIds = new HashSet<>();
        for (Long roleId : allRoleIds) {
            Long[] functionIds = roleFunctionService.getRoleFunctionById(roleId);
            if (ArrayUtils.isNotEmpty(functionIds)) {
                userFunctionIds.addAll(Arrays.asList(functionIds));
            }
        }
        return userFunctionIds;
    }

    /**
     * ????????????????????????.
     *
     * @param menus               ??????
     * @param allAssignElementIds ??????????????????ID
     * @return ??????????????????
     */
    private List<MenuItem> updateMenuCheck(List<MenuItem> menus, List<Long> allAssignElementIds) {
        if (CollectionUtils.isEmpty(menus) || CollectionUtils.isEmpty(allAssignElementIds)) {
            return menus;
        }
        for (MenuItem menuItem : menus) {
            if (CollectionUtils.isNotEmpty(menuItem.getChildren())) {
                updateMenuCheck(menuItem.getChildren(), allAssignElementIds);
            }
            if (allAssignElementIds.contains(menuItem.getId())) {
                menuItem.setIschecked(Boolean.FALSE);
            }
        }
        return menus;
    }

    /**
     * ??????????????????????????????ID????????????????????????ID???????????????.
     *
     * @param allRoleFunctionIds ?????????????????????ID??????
     * @param roleFunctionIds    ??????????????????????????????ID??????
     * @return ????????????ID??????????????????
     */
    private List<Long> getSameFunctionIds(Set<Long> allRoleFunctionIds, Set<Long> roleFunctionIds) {
        allRoleFunctionIds.retainAll(roleFunctionIds);
        return new ArrayList<>(allRoleFunctionIds);
    }

    /**
     * ??????????????????????????????ID.
     *
     * @param allFunctionElements ?????????????????????????????????ID??????
     * @param functionElementIds  ????????????????????????ID
     * @return ??????ID(??????????????????ID)
     */
    private List<Long> compareFunctionElements(List<Long> allFunctionElements, List<Long> functionElementIds) {
        //?????????????????????????????????????????? ????????????
        if (CollectionUtils.isEmpty(allFunctionElements) || CollectionUtils.isEmpty(functionElementIds)) {
            return new ArrayList<>();
        }
        // ?????? ???????????????????????????????????????
        allFunctionElements.retainAll(functionElementIds);
        return allFunctionElements;
    }

    /**
     * ???????????????????????????????????????????????? ??????Map<??????ID, ??????????????????ID??????>.
     *
     * @param assigns ????????????????????????????????????
     * @return ??????ID????????????????????????????????????
     */
    private Map<Long, List<Long>> getFunctionElements(ResourceItemAssign[] assigns) {
        Map<Long, List<Long>> function = new HashMap<>(16);
        if (ArrayUtils.isNotEmpty(assigns)) {
            for (ResourceItemAssign assign : assigns) {
                List<Long> functionElements = function.get(assign.getFunctionId());
                if (CollectionUtils.isEmpty(functionElements)) {
                    functionElements = new ArrayList<>();
                    function.put(assign.getFunctionId(), functionElements);
                }
                functionElements.add(assign.getElementId());
            }
        }
        return function;
    }

    /**
     * ??????????????????????????????????????????ID.
     *
     * @param roleIds ????????????Id??????
     * @return ??????????????????ID
     */
    private List<Long> getAllRoleAssignElementIds(List<Long> roleIds) {
        List<Long> allRoleAssignElementIds = new ArrayList<>();
        //key ??????ID value ?????????????????????ID
        Map<Long, List<Long>> allFunctionElements = new HashMap<>(16);
        boolean firstRole = true;
        Set<Long> allRoleFunctionIds = new HashSet<>();
        for (Long roleId : roleIds) {
            //?????????????????????????????????ID
            Long[] roleFunctionIdList = roleFunctionCache.getValue(roleId.toString());
            //????????????????????????????????????  ???????????????????????????????????????????????? ???????????????????????????
            if (ArrayUtils.isEmpty(roleFunctionIdList)) {
                continue;
            }
            Set<Long> roleFunctionIds = new HashSet<>();
            CollectionUtils.addAll(roleFunctionIds, roleFunctionIdList);

            //????????????????????????????????? ???????????????  ??????Map<??????Id,??????????????????Id??????>
            Map<Long, List<Long>> functionElements = getFunctionElements(resourceItemAssignCache.getValue(ResourceItemAssign.ASSIGN_TYPE_ROLE + BaseConstants.UNDER_LINE + roleId));
            if (firstRole) {
                firstRole = false;
                allRoleFunctionIds = roleFunctionIds;
                allFunctionElements = functionElements;
                continue;
            }
            // ???????????????ID???????????????????????????ID???????????????ID
            List<Long> sameFunctionIds = getSameFunctionIds(allRoleFunctionIds, roleFunctionIds);

            //??????????????????????????????????????????
            for (Long roleFunctionId : roleFunctionIds) {
                //?????????????????????????????????????????? ???????????? Map<??????, ??????????????????>
                if (!sameFunctionIds.contains(roleFunctionId)) {
                    allFunctionElements.put(roleFunctionId, functionElements.get(roleFunctionId));
                }
            }

            /*
             * ?????????????????????????????????  ????????????????????????????????????   ???????????? Map<??????Id, ????????????Id??????>???
             *
             * ?????????????????????
             * ????????????key:????????????1????????? F1???F2  ??????F1????????????????????? ??????Map?????????F1
             *             ??????2?????????F2  ??????1?????????2??????F2  ????????? ??????Map??????F1???F2????????????F2
             *
             * ???????????????key???????????????1????????? F1???F2 ???F1???F2???????????????????????? ??????Map??????F1???F2???
             *             ??????2?????????F2   ??????1?????????2??????F2  ????????? ??????Map??????F1???F2???F2????????????
             *
             */
            for (Long sameFunctionId : sameFunctionIds) {
                List<Long> elements = compareFunctionElements(allFunctionElements.get(sameFunctionId), functionElements.get(sameFunctionId));
                allFunctionElements.put(sameFunctionId, elements);
            }
            allRoleFunctionIds.addAll(roleFunctionIds);
        }
        allFunctionElements.forEach((k, v) -> {
            if (v != null) {
                allRoleAssignElementIds.addAll(v);
            }
        });
        return allRoleAssignElementIds;
    }

    /**
     * ????????????.
     *
     * @param menus HTML????????????
     * @return ????????????????????????HTML????????????
     */
    private List<MenuItem> updateResourceMenu(List<MenuItem> menus) {
        if (CollectionUtils.isEmpty(menus)) {
            return menus;
        }
        removeVariableMenu(menus);
        //????????????????????????????????? ?????????????????????????????????  ???????????????
        Iterator<MenuItem> iterator = menus.iterator();
        while (iterator.hasNext()) {
            MenuItem menu = iterator.next();
            if (CollectionUtils.isEmpty(menu.getChildren())) {
                iterator.remove();
            }
        }
        return menus;
    }

    /**
     * ???????????? ???????????????????????????.
     *
     * @param menus ??????
     */
    private void removeVariableMenu(List<MenuItem> menus) {
        Iterator<MenuItem> iterator = menus.iterator();
        while (iterator.hasNext()) {
            MenuItem menu = iterator.next();
            if (UserConstants.SERVER_VARIABLE.equalsIgnoreCase(menu.getText())) {
                iterator.remove();
                break;
            } else {
                if (CollectionUtils.isNotEmpty(menu.getChildren())) {
                    removeVariableMenu(menu.getChildren());
                }
            }
        }
    }

}
