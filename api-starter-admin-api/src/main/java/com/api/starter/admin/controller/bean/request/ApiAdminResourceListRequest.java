package com.api.starter.admin.controller.bean.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ApiAdminResourceListRequest extends PageRequest{

    private String resourceType;
    private String resourceName;
    private String resourceDesc;

    private String startDateStr;
    private String endDateStr;
}
