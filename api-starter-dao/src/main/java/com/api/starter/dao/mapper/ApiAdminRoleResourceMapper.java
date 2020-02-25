package com.api.starter.dao.mapper;

import com.api.starter.dao.model.ApiAdminRoleResourceModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ApiAdminRoleResourceMapper {

    List<ApiAdminRoleResourceModel> getListByRoleId(Long roleId);

    int insert(ApiAdminRoleResourceModel model);

    int deleteRolesResource(Long roleId,Long resourceId);

    int delete(Long id);

    int deleteRolesResourceByRoleId(Long roleId);
}
