package com.fastproject.system.vo.users;

import com.fastproject.db.PageQuery;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@RegisterReflectionForBinding
public class SysUsersQuery extends PageQuery {
    public SysUsersQuery() {
    }
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
     * 性别
     */
    private Integer gender;
}
