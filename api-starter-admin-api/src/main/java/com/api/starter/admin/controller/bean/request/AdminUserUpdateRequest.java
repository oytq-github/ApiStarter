package com.api.starter.admin.controller.bean.request;

import lombok.Data;

import java.util.Date;

@Data
public class AdminUserUpdateRequest {
    private Long id;

    private String username;

    private String realName;

    private String nickName;

    private String userEmail;

    private String userPhone;
}
