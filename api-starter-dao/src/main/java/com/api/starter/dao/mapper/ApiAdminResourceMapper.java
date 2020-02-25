package com.api.starter.dao.mapper;

import com.api.starter.dao.model.ApiAdminResourceModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface ApiAdminResourceMapper {

    List<ApiAdminResourceModel> allParent(String resourceType);

    List<ApiAdminResourceModel> childs(Long parentId);

    List<ApiAdminResourceModel> userResourceList(@Param("userId") Long userId, @Param("resourceType") String resourceType);

    List<ApiAdminResourceModel> list(@Param("model") ApiAdminResourceModel model,@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
