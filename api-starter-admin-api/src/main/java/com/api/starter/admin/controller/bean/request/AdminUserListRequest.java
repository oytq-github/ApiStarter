package com.api.starter.admin.controller.bean.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AdminUserListRequest extends PageRequest {

    private Long id;

    private String username;

    private String realName;

    private String nickName;

    private Integer userStatus;

    private String userEmail;

    private String userPhone;

    private String startDateStr;

    private String endDateStr;

}
