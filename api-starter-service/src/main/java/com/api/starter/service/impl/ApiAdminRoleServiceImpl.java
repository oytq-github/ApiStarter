package com.api.starter.service.impl;

import com.api.starter.dao.mapper.ApiAdminRoleMapper;
import com.api.starter.dao.mapper.ApiAdminRoleResourceMapper;
import com.api.starter.dao.mapper.ApiAdminUserRoleMapper;
import com.api.starter.dao.model.ApiAdminRoleModel;
import com.api.starter.dao.model.ApiAdminRoleResourceModel;
import com.api.starter.dao.model.ApiAdminUserRoleModel;
import com.api.starter.service.interfaces.ApiAdminRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;


@Service
public class ApiAdminRoleServiceImpl implements ApiAdminRoleService {

    @Autowired
    private ApiAdminRoleMapper apiAdminRoleMapper;

    @Autowired
    private ApiAdminUserRoleMapper apiAdminUserRoleMapper;

    @Autowired
    private ApiAdminRoleResourceMapper apiAdminRoleResourceMapper;

    @Override
    public List<ApiAdminRoleModel> listByNameAndDesc(String roleName, String roleDesc) {
        return apiAdminRoleMapper.listByNameAndDesc(roleName,roleDesc);
    }

    @Override
    public List<ApiAdminUserRoleModel> userRoles(Long userId) {
        return apiAdminUserRoleMapper.userRoles(userId);
    }

    @Override
    public Boolean userAddRoles(Long userId, String roleIds) {
        apiAdminUserRoleMapper.deleteUserRolesByUserId(userId);
        if (StringUtils.isEmpty(roleIds)) {
            return Boolean.TRUE;
        }
        Arrays.asList(roleIds.split(",")).forEach(item -> {
            ApiAdminUserRoleModel model = new ApiAdminUserRoleModel();
            model.setRoleId(Long.valueOf(item));
            model.setUserId(userId);
            apiAdminUserRoleMapper.insert(model);
        });
        return Boolean.TRUE;
    }

    @Override
    public List<ApiAdminRoleResourceModel> rolesResources(Long roleId) {
        return apiAdminRoleResourceMapper.getListByRoleId(roleId);
    }

    @Override
    public Boolean deleteRole(Long roleId) {
        int delete = apiAdminRoleMapper.delete(roleId);
        if (delete == 1) {
            //删除角色成功,删除角色资源关系
            apiAdminRoleResourceMapper.deleteRolesResourceByRoleId(roleId);
            //删除角色成功,删除用户角色关系
            apiAdminUserRoleMapper.deleteUserRolesByRoleId(roleId);
        }
        return delete == 1 ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public Boolean saveOrUpdate(ApiAdminRoleModel model) {
        int result;
        if (null == model.getId()) {
            result = apiAdminRoleMapper.insert(model);
        } else {
            result = apiAdminRoleMapper.update(model);
        }
        return result == 1 ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public Boolean saveRoleResources(Long roleId, String resourceIds) {
        //先删除所有角色关联的资源
        apiAdminRoleResourceMapper.deleteRolesResourceByRoleId(roleId);
        if (!StringUtils.isEmpty(resourceIds)) {
            Arrays.asList(resourceIds.split(",")).forEach(item -> {
                ApiAdminRoleResourceModel model = new ApiAdminRoleResourceModel();
                model.setResourceId(Long.valueOf(item));
                model.setRoleId(roleId);
                apiAdminRoleResourceMapper.insert(model);
            });
        }
        return Boolean.TRUE;
    }
}