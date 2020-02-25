package com.api.starter.admin.controller.bean.request;

import lombok.Data;

import java.util.Date;

@Data
public class AdminUserAddRequest {


    private String username;

    private String password;

    private String realName;

    private String nickName;

    private Integer userStatus = 1;

    private String userEmail;

    private String userPhone;

}
