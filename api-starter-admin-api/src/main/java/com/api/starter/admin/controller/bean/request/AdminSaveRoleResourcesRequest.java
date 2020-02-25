package com.api.starter.admin.controller.bean.request;

import lombok.Data;

@Data
public class AdminSaveRoleResourcesRequest {

    private Long roleId;

    private String resources;

}
