package com.api.starter.service.interfaces;

import com.api.starter.dao.model.ApiAdminRoleModel;
import com.api.starter.dao.model.ApiAdminRoleResourceModel;
import com.api.starter.dao.model.ApiAdminUserRoleModel;

import java.util.List;

public interface ApiAdminRoleService {

    List<ApiAdminRoleModel> listByNameAndDesc(String roleName, String roleDesc);

    List<ApiAdminUserRoleModel> userRoles(Long userId);

    Boolean userAddRoles(Long userId, String roleIds);

    List<ApiAdminRoleResourceModel> rolesResources(Long roleId);

    Boolean deleteRole(Long roleId);

    Boolean saveOrUpdate(ApiAdminRoleModel model);

    Boolean saveRoleResources(Long roleId,String resourceIds);

}
