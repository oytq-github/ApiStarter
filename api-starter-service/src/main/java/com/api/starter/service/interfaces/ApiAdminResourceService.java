package com.api.starter.service.interfaces;

import com.api.starter.dao.model.ApiAdminResourceModel;
import com.api.starter.service.bo.ApiAdminResourceBo;
import com.api.starter.util.enums.ApiAdminResourceType;

import java.util.Date;
import java.util.List;

public interface ApiAdminResourceService {

    List<ApiAdminResourceBo> listTree(String resourceType);

    List<ApiAdminResourceModel> list(ApiAdminResourceBo model, Date startDate, Date endDate);

    List<ApiAdminResourceBo> roleResourceList(Long role);

    List<ApiAdminResourceModel> userResourceList(Long userId,ApiAdminResourceType resourceType);
}
