package com.api.starter.dao.model;

import lombok.Data;

import java.util.Date;

@Data
public class ApiAdminUserRoleModel {

    private Long id;

    private Long userId;

    private Long roleId;

    private Date createTime;

    private Date updateTime;

}
