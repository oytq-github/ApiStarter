package com.api.starter.admin.controller.bean.request;

import lombok.Data;

@Data
public class PageRequest {

    private Integer current;

    private Integer pageSize;

}
