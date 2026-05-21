package com.fastproject.system.vo.users;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 个人中心-用户信息
 */
@Data
public class UserProfileVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String nickname;
    private String avatar;
    private String department;
    private String post;
    private String phone;
    private String email;
    private List<String> roles;
    private Integer gender;
    private String remark;
}