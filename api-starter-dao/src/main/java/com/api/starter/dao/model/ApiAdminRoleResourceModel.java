package com.api.starter.dao.model;

import lombok.Data;

import java.util.Date;

@Data
public class ApiAdminRoleResourceModel {

    private Long id;

    private Long roleId;

    private Long resourceId;

    private Date createTime;

    private Date updateTime;

}
