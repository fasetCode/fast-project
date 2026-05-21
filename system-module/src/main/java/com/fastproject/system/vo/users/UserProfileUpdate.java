package com.fastproject.system.vo.users;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * 个人中心-修改个人信息
 */
@Data
public class UserProfileUpdate implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "昵称不能为空")
    private String nickname;

    private String phone;
    private String email;
    private Integer gender;
    private String remark;
    private String avatar;
}