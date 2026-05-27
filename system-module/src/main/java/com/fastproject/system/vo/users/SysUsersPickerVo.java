package com.fastproject.system.vo.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUsersPickerVo {
    private Long id;
    private String username;
    private String nickname;
    private String email;
    private String phone;
    private String avatar;
}

