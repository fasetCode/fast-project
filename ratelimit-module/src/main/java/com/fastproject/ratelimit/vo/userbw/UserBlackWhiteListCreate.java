package com.fastproject.ratelimit.vo.userbw;

import com.fastproject.ratelimit.enums.BlackWhiteListType;
import lombok.Data;

/**
 * 用户黑白名单配置 添加
 */
@Data
public class UserBlackWhiteListCreate {

    private String appCode;

    private Long userId;

    private BlackWhiteListType listType;

    private String limitMsg;

    private Boolean enabled;

    private String remark;
}
