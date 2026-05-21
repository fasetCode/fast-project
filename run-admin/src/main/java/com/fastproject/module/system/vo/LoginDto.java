package com.fastproject.module.system.vo;

import lombok.Data;

@Data
public class LoginDto {
    private String username;
    private String password;
    private String captchaKey;
    private String captcha;
}
