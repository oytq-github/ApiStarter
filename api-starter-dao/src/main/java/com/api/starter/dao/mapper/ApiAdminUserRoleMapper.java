package com.api.starter.dao.mapper;


import com.api.starter.dao.model.ApiAdminUserRoleModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ApiAdminUserRoleMapper {

    int insert(ApiAdminUserRoleModel model);

    int delete(Long id);

    List<ApiAdminUserRoleModel> userRoles(Long userId);

    int deleteUserRolesByRoleId(Long roleId);

    int deleteUserRolesByUserId(Long userId);
}
