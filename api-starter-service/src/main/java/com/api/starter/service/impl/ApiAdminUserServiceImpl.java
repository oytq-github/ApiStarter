package com.api.starter.service.impl;

import com.api.starter.dao.mapper.ApiAdminUserRoleMapper;
import com.api.starter.dao.model.ApiAdminUserModel;
import com.api.starter.dao.mapper.ApiAdminUserMapper;
import com.api.starter.service.interfaces.ApiAdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Service
public class ApiAdminUserServiceImpl implements ApiAdminUserService {

    @Autowired
    private ApiAdminUserMapper apiAdminUserMapper;

    @Autowired
    private ApiAdminUserRoleMapper apiAdminUserRoleMapper;

    public Boolean saveOrUpdate(ApiAdminUserModel user) {
        Integer result;
        if (null != user.getId()) {
            ApiAdminUserModel source = apiAdminUserMapper.getById(user.getId());
            user.setUserVersion(source.getUserVersion());
            result = apiAdminUserMapper.update(user);
        } else {
            result = apiAdminUserMapper.insert(user);
        }

        return result == 1;
    }

    @Override
    public Boolean updateUserStatus(Long id) {
        ApiAdminUserModel model = apiAdminUserMapper.getById(id);
        if (null == model) {
            return Boolean.FALSE;
        }
        int result = apiAdminUserMapper.updateUserStatus(model.getUserStatus() == 1 ? -1 : 1, id, model.getUserVersion());
        return result == 1;
    }

    public Boolean deleteUser(Long id) {
        int delete = apiAdminUserMapper.delete(id);
        if (delete == 1) {
            //删除用户,删除用户关联的角色关系
            apiAdminUserRoleMapper.deleteUserRolesByUserId(id);
        }
        return delete == 1;
    }

    public List<ApiAdminUserModel> userList(ApiAdminUserModel user, Date startDate, Date endDate) {
        if (null == user) {
            return null;
        }
        List<ApiAdminUserModel> list = apiAdminUserMapper.list(user,startDate,endDate);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        list.forEach(item -> item.setPassword(""));
        return list;
    }

    @Override
    public ApiAdminUserModel getUserByUserName(String username) {
        if (StringUtils.isEmpty(username)) {
            return null;
        }
        return apiAdminUserMapper.getUserByUsername(username);
    }
}
