package com.api.starter.dao.model;

import lombok.Data;

import java.util.Date;

@Data
public class ApiAdminUserModel {

    private Long id;

    private String username;

    private String password;

    private String userSalt;

    private String realName;

    private String nickName;

    private String userPhone;

    private String avatarUrl;

    private String userEmail;

    private Date createTime;

    private Date updateTime;

    private Integer userStatus;

    private Integer userVersion;
}
