package com.api.starter.service.interfaces;


import com.api.starter.dao.model.ApiAdminUserModel;

import java.util.Date;
import java.util.List;

public interface ApiAdminUserService {

    Boolean saveOrUpdate(ApiAdminUserModel user);

    Boolean deleteUser(Long id);

    Boolean updateUserStatus(Long id);

    List<ApiAdminUserModel> userList(ApiAdminUserModel user, Date startDate, Date endDate);

    ApiAdminUserModel getUserByUserName(String username);

}
