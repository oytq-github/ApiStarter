package com.api.starter.admin.controller;

import com.api.starter.admin.controller.bean.request.AdminRoleListRequest;
import com.api.starter.admin.controller.bean.request.AdminRoleUpdateRequest;
import com.api.starter.admin.controller.bean.request.AdminSaveRoleResourcesRequest;
import com.api.starter.dao.model.ApiAdminRoleModel;
import com.api.starter.dao.model.ApiAdminRoleResourceModel;
import com.api.starter.dao.model.ApiAdminUserRoleModel;
import com.api.starter.service.interfaces.ApiAdminRoleService;
import com.api.starter.util.utils.ResponseEntity;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 角色管理接口
 */
@RestController
@RequestMapping("role")
public class ApiAdminRoleController {

    @Autowired
    private ApiAdminRoleService apiAdminRoleService;

    /**
     * 查询角色列表
     * @param request {@link AdminRoleListRequest}
     * @return {@link ResponseEntity}
     */
    @RequestMapping(value = "list",method = RequestMethod.GET)
    public ResponseEntity list(AdminRoleListRequest request) {
        PageHelper.startPage(request.getCurrent(), request.getPageSize());
        List<ApiAdminRoleModel> bos = apiAdminRoleService.listByNameAndDesc(request.getRoleName(),request.getRoleDesc());
        return ResponseEntity.PAGE_SUCCESS(bos);
    }

    /**
     * 删除角色
     * @param id 角色id
     * @return  {@link ResponseEntity}
     */
    @RequestMapping(value = "deleteRole",method = RequestMethod.DELETE)
    public ResponseEntity deleteRole(Long id) {
        Boolean delete = apiAdminRoleService.deleteRole(id);
        return ResponseEntity.CONDITION(delete,"删除角色失败");
    }

    /**
     * 新增或更新角色信息
     * @param request  {@link AdminRoleUpdateRequest}
     * @return  {@link ResponseEntity}
     */
    @RequestMapping(value = "saveOrUpdateRole",method = RequestMethod.POST)
    public ResponseEntity saveOrUpdateRole(AdminRoleUpdateRequest request) {
        ApiAdminRoleModel model = new ApiAdminRoleModel();
        BeanUtils.copyProperties(request,model);
        Boolean result = apiAdminRoleService.saveOrUpdate(model);
        return ResponseEntity.CONDITION(result,null == request.getId() ? "添加角色失败" : "更新角色失败");
    }

    /**
     * 保存角色关联的资源
     * @param request {@link AdminSaveRoleResourcesRequest}
     * @return  {@link ResponseEntity}
     */
    @RequestMapping(value = "saveRoleResources",method = RequestMethod.POST)
    public ResponseEntity saveRoleResources(AdminSaveRoleResourcesRequest request) {
        Boolean saveResult = apiAdminRoleService.saveRoleResources(request.getRoleId(), request.getResources());
        return ResponseEntity.CONDITION(saveResult,"配置角色资源失败");
    }

    /**
     * 查询角色关联的资源
     * @param roleId 角色id
     * @return  {@link ResponseEntity}
     */
    @RequestMapping(value = "roleResources",method = RequestMethod.GET)
    public ResponseEntity roleResources(Long roleId) {
        List<ApiAdminRoleResourceModel> list = apiAdminRoleService.rolesResources(roleId);
        return ResponseEntity.SUCCESS(list);
    }

    /**
     * 查询用户的角色授权信息
     * @param userId 用户id
     * @return  {@link ResponseEntity}
     */
    @RequestMapping(value = "userRoles",method = RequestMethod.GET)
    public ResponseEntity userRoles(Long userId) {
        List<ApiAdminUserRoleModel> list = apiAdminRoleService.userRoles(userId);
        return ResponseEntity.SUCCESS(list);
    }

    /**
     * 给用户添加角色
     * @param userId 用户id
     * @param ids 角色id数组
     * @return  {@link ResponseEntity}
     */
    @RequestMapping(value = "confirmUserRole",method = RequestMethod.POST)
    public ResponseEntity confirmUserRole(Long userId, String ids) {
        Boolean result = apiAdminRoleService.userAddRoles(userId, ids);
        return ResponseEntity.CONDITION(result,"保存用户角色信息失败");
    }
}
