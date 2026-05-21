package com.fastproject.system.vo.users;

import lombok.Data;

import java.util.List;

@Data
public class SysUsersCreate {
    /**
     * 账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 电话
     */
    private String phone;

    /**
     * 性别 0男 1女 2未知
     */
    private Integer gender;

    /**
     * 角色状态
     */
    private Integer status;

    /**
     * 部门ID
     */
    private Long departmentId;

    /**
     * 岗位ID
     */
    private Long postId;

    /**
     * 角色ID列表
     */
    private List<Long> roleIds;

    /**
     * 头像
     */
    private String avatar;
}
