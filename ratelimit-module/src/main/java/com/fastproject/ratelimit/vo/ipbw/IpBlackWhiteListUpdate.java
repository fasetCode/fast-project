package com.fastproject.ratelimit.vo.ipbw;

import com.fastproject.ratelimit.enums.BlackWhiteListType;
import lombok.Data;

/**
 * IP黑白名单配置 修改
 */
@Data
public class IpBlackWhiteListUpdate {

    private Long id;

    private String appCode;

    private String ipAddress;

    private BlackWhiteListType listType;

    private String limitMsg;

    private Boolean enabled;

    private String remark;
}
