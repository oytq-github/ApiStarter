package com.api.starter.service.bo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ApiAdminResourceBo {

    private Long id;

    private String resourceType;

    private String resourceName;

    private String resourceDesc;

    private Long parentId;

    private Date createTime;

    private Date updateTime;

    private List<ApiAdminResourceBo> childs;
}
