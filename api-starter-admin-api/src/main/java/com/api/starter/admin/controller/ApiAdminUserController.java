package com.api.starter.admin.controller;

import com.api.starter.admin.controller.bean.request.AdminUserAddRequest;
import com.api.starter.admin.controller.bean.request.AdminUserUpdateRequest;
import com.api.starter.admin.controller.bean.request.AdminUserListRequest;
import com.api.starter.dao.model.ApiAdminResourceModel;
import com.api.starter.dao.model.ApiAdminUserModel;
import com.api.starter.service.interfaces.ApiAdminResourceService;
import com.api.starter.service.interfaces.ApiAdminUserService;
import com.api.starter.util.enums.ApiAdminResourceType;
import com.api.starter.util.utils.CalendarUtil;
import com.api.starter.util.utils.JwtUtils;
import com.api.starter.util.utils.ResponseEntity;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户接口
 */
@RestController
@RequestMapping("user")
public class ApiAdminUserController {

    @Autowired
    private ApiAdminUserService apiAdminUserService;

    @Autowired
    private ApiAdminResourceService apiAdminResourceService;

    /**
     * 后台用户列表
     * @param request {@link AdminUserListRequest}
     * @return {@link ResponseEntity}
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ResponseEntity list(AdminUserListRequest request) {
        ApiAdminUserModel entry = new ApiAdminUserModel();
        BeanUtils.copyProperties(request,entry);
        PageHelper.startPage(request.getCurrent(), request.getPageSize());
        List<ApiAdminUserModel> list = apiAdminUserService.userList(entry,CalendarUtil.str2date(request.getStartDateStr()),CalendarUtil.str2date(request.getEndDateStr()));
        return ResponseEntity.PAGE_SUCCESS(list);
    }


    /**
     * 更新后台用户信息
     * @param request {@link AdminUserUpdateRequest}
     * @return {@link ResponseEntity}
     */
    @RequestMapping(value = "updateAdminUser", method = RequestMethod.POST)
    public ResponseEntity updateAdminUser(AdminUserUpdateRequest request) {
        ApiAdminUserModel entry = new ApiAdminUserModel();
        BeanUtils.copyProperties(request,entry);
        Boolean update = apiAdminUserService.saveOrUpdate(entry);
        return ResponseEntity.CONDITION(update,"更新失败");
    }

    /**
     * 更新后台用户状态
     * @param id {@link Long}
     * @return {@link ResponseEntity}
     */
    @RequestMapping(value = "updateAdminUserStatus", method = RequestMethod.POST)
    public ResponseEntity updateAdminUserStatus(Long id) {
        Boolean update = apiAdminUserService.updateUserStatus(id);
        return ResponseEntity.CONDITION(update,"更新失败");
    }

    /**
     * 添加后台用户
     * @param request {@link AdminUserAddRequest}
     * @return {@link ResponseEntity}
     */
    @RequestMapping(value = "addAdminUser", method = RequestMethod.POST)
    public ResponseEntity addAdminUser(AdminUserAddRequest request) {

        ApiAdminUserModel exited = apiAdminUserService.getUserByUserName(request.getUsername());
        if (null != exited) {
            return ResponseEntity.FAIL("用户名已存在,请更换用户名");
        }
        ApiAdminUserModel entry = new ApiAdminUserModel();
        BeanUtils.copyProperties(request,entry);
        entry.setPassword(DigestUtils.md5DigestAsHex(request.getPassword().getBytes()));
        entry.setUserSalt(RandomStringUtils.randomAlphanumeric(4));
        Boolean save = apiAdminUserService.saveOrUpdate(entry);
        return ResponseEntity.CONDITION(save,"添加失败");
    }

    /**
     * 删除后台用户
     * @param id 删除目标后台用户id
     * @return {@link ResponseEntity}
     */
    @RequestMapping(value = "deleteAdminUser", method = RequestMethod.GET)
    public ResponseEntity deleteAdminUser(Long id) {
        Boolean delete = apiAdminUserService.deleteUser(id);
        return ResponseEntity.CONDITION(delete,"删除失败");
    }

    /**
     * 后台用户登录
     * @param username 用户名
     * @param password 密码
     * @return {@link ResponseEntity}
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseEntity login(String username,String password) {
        String md5Pass = DigestUtils.md5DigestAsHex(password.getBytes());

        ApiAdminUserModel user = apiAdminUserService.getUserByUserName(username);
        if (null == user) {
            return ResponseEntity.FAIL("用户名不存在");
        }
        if (null == user.getUserStatus() || user.getUserStatus() != 1) {
            return ResponseEntity.FAIL("用户状态异常,登录失败");
        }

        md5Pass = DigestUtils.md5DigestAsHex(md5Pass.concat(user.getUserSalt()).getBytes());
        if (!md5Pass.equals(user.getPassword())) {
            return ResponseEntity.FAIL("密码错误,请重试");
        }
        user.setPassword("");
        String token = JwtUtils.create(username);
        Map<String,Object> result = new HashMap<>();
        result.put("token",token);
        result.put("user",user);
        return ResponseEntity.SUCCESS(result);
    }

    /**
     * 退出(目前是前端删除token,后端没有做出来)
     * @return {@link ResponseEntity}
     */
    @RequestMapping(value = "logout", method = RequestMethod.POST)
    public ResponseEntity logout() {
        return ResponseEntity.SUCCESS();
    }

    /**
     * 获取用户信息
     * @param request request对象
     * @return {@link ResponseEntity}
     */
    @RequestMapping(value = "info", method = RequestMethod.GET)
    public ResponseEntity info(HttpServletRequest request) {
        //TODO 用户信息保存放在切面做
        String header = request.getHeader("token");

        Map<String,Object> result = new HashMap<>();
        String username = JwtUtils.parse(header);
        ApiAdminUserModel user = apiAdminUserService.getUserByUserName(username);
        if (null == user) {
            return ResponseEntity.FAIL("");
        }
        List<ApiAdminResourceModel> menus = apiAdminResourceService.userResourceList(user.getId(), ApiAdminResourceType.RESOURCE_MENU);
        List<ApiAdminResourceModel> buttons = apiAdminResourceService.userResourceList(user.getId(), ApiAdminResourceType.RESOURCE_BUTTON);
        result.put("roles",menus);
        result.put("buttons",buttons);
        result.put("avatar",user.getAvatarUrl());
        result.put("name",user.getNickName());
        return ResponseEntity.SUCCESS(result);
    }
}
