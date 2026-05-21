package com.fastproject.system.vo.users;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户基本信息VO
 */
@Data
public class UserInfoVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 账号
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像URL
     */
    private String avatar;
}
