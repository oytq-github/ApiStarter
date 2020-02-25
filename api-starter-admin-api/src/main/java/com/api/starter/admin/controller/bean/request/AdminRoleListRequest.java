package com.api.starter.admin.controller.bean.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AdminRoleListRequest extends PageRequest{

    private String roleName;

    private String roleDesc;
}
