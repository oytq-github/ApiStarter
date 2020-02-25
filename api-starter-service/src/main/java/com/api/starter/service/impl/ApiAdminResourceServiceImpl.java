package com.api.starter.service.impl;

import com.api.starter.dao.mapper.ApiAdminResourceMapper;
import com.api.starter.dao.model.ApiAdminResourceModel;
import com.api.starter.service.bo.ApiAdminResourceBo;
import com.api.starter.service.interfaces.ApiAdminResourceService;
import com.api.starter.util.enums.ApiAdminResourceType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ApiAdminResourceServiceImpl implements ApiAdminResourceService {

    @Autowired
    private ApiAdminResourceMapper apiAdminResourceMapper;

    public List<ApiAdminResourceBo> listTree(String resourceType) {

        List<ApiAdminResourceBo> result = new ArrayList<>();
        List<ApiAdminResourceModel> parents = apiAdminResourceMapper.allParent(resourceType);

        if (CollectionUtils.isEmpty(parents)) {
            return new ArrayList<>();
        }

        for (ApiAdminResourceModel item : parents) {
            ApiAdminResourceBo parent = getBo(item);
            result.add(parent);
        }

        return result;
    }

    @Override
    public List<ApiAdminResourceModel> list(ApiAdminResourceBo bo, Date startDate, Date endDate) {
        ApiAdminResourceModel model = new ApiAdminResourceModel();
        BeanUtils.copyProperties(bo,model);
        List<ApiAdminResourceModel> list = apiAdminResourceMapper.list(model, startDate, endDate);
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        return list;
    }

    public List<ApiAdminResourceBo> roleResourceList(Long roleId) {
        return null;
    }

    @Override
    public List<ApiAdminResourceModel> userResourceList(Long userId,ApiAdminResourceType resourceType) {

        List<ApiAdminResourceModel> resourceList = apiAdminResourceMapper.userResourceList(userId, resourceType.name());

        return !CollectionUtils.isEmpty(resourceList) ? resourceList : new ArrayList<>();
    }

    private ApiAdminResourceBo getBo(ApiAdminResourceModel current) {
        ApiAdminResourceBo bo = new ApiAdminResourceBo();
        BeanUtils.copyProperties(current,bo);
        List<ApiAdminResourceModel> childs = apiAdminResourceMapper.childs(current.getId());
        if (CollectionUtils.isEmpty(childs)) {
            return bo;
        }
        List<ApiAdminResourceBo> childList = new ArrayList<>();
        for (ApiAdminResourceModel item : childs) {
            ApiAdminResourceBo child = getBo(item);
            childList.add(child);
        }
        bo.setChilds(childList);
        return bo;
    }
}
