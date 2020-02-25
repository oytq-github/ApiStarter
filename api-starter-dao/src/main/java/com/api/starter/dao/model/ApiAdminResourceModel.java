package com.api.starter.dao.model;

import lombok.Data;

import java.util.Date;

@Data
public class ApiAdminResourceModel {

    private Long id;
    private String resourceType;
    private String resourceName;
    private String resourceDesc;
    private Long parentId;
    private Date createTime;
    private Date updateTime;
}
