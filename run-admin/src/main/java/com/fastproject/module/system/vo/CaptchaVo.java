package com.fastproject.module.system.vo;

import lombok.Builder;
import lombok.Data;

/**
 * 验证码返回VO
 */
@Data
@Builder
public class CaptchaVo {

    /**
     * 验证码唯一标识
     */
    private String captchaKey;

    /**
     * 验证码图片Base64
     */
    private String captchaImage;
}
