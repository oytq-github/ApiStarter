package com.api.starter.dao.model;

import lombok.Data;

import java.util.Date;

@Data
public class ApiAdminRoleModel {

    private Long id;

    private String roleName;

    private String roleDesc;

    private Date createTime;

    private Date updateTime;

    private Integer apiVersion;
}
