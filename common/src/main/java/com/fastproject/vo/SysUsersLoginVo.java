package com.fastproject.vo;

 import lombok.Data;

import java.io.Serializable;
 import java.util.Set;

@Data
public class SysUsersLoginVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 用户ID
     */
    private Long id;

    /**
     * 密码
     */
    private String password;

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
     * 租户ID
     */
    private Long tenantId;

    private Set<SysRoleVo> roles;

    @Data
    public static class SysRoleVo{

        /**
         * ID
         */
        private Long id;

        /**
         * 角色代码
         */
        private String code;

        /**
         * 状态码
         */
        private Integer status;
    }
}
