package com.api.starter.dao.mapper;

import com.api.starter.dao.model.ApiAdminUserModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface ApiAdminUserMapper {

    ApiAdminUserModel getById(Long id);

    int insert(ApiAdminUserModel model);

    int update(ApiAdminUserModel model);

    int updatePassword(@Param("password") String password,@Param("id") Long id, @Param("userVersion") Integer userVersion);

    int delete(Long id);

    List<ApiAdminUserModel> list(@Param("model") ApiAdminUserModel model, @Param("startDate") Date startDate,@Param("endDate") Date endDate);

    ApiAdminUserModel getUserByUsername(String username);

    int updateUserStatus(@Param("userStatus") Integer userStatus,@Param("id") Long id, @Param("userVersion") Integer userVersion);
}