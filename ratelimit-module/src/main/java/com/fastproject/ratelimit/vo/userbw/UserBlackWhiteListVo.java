package com.fastproject.ratelimit.vo.userbw;

import com.fastproject.ratelimit.enums.BlackWhiteListType;
import lombok.Data;

/**
 * 用户黑白名单配置 VO
 */
@Data
public class UserBlackWhiteListVo {

    private Long id;

    private String appCode;

    private Long userId;

    private BlackWhiteListType listType;

    private String limitMsg;

    private Boolean enabled;

    private String remark;
}
