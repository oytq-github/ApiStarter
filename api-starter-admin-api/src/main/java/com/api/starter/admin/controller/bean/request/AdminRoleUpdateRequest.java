package com.api.starter.admin.controller.bean.request;

import lombok.Data;

@Data
public class AdminRoleUpdateRequest {

    private Long id;

    private String roleName;

    private String roleDesc;

}
