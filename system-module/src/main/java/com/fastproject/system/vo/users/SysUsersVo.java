package com.fastproject.system.vo.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUsersVo {

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
     * 头像
     */
    private String avatar;

}
