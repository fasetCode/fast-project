package com.fastproject.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
//@RegisterReflectionForBinding
public class SecurityUserVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long userId;
    private Long tenantId;
    private SysUsersLoginVo user;
    private Set<String> permissions;
    private Long loginTime;
    // 登录IP地址
    private String ip;
    // 登录地点
    private String address;
    // 登录浏览器
    private String browser;
    // 登录设备
    private String device;
    // ua
    private String ua;

}
