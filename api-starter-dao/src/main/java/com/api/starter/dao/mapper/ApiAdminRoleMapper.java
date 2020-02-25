package com.api.starter.dao.mapper;

import com.api.starter.dao.model.ApiAdminRoleModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ApiAdminRoleMapper {

    List<ApiAdminRoleModel> listByNameAndDesc(@Param("roleName") String roleName, @Param("roleDesc") String roleDesc);

    int insert(ApiAdminRoleModel model);

    int update(ApiAdminRoleModel model);

    int delete(Long id);
}
