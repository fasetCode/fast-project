package com.fastproject.ratelimit.vo.ipbw;

import com.fastproject.db.PageQuery;
import com.fastproject.ratelimit.enums.BlackWhiteListType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * IP黑白名单配置 查询
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class IpBlackWhiteListQuery extends PageQuery {

    private String appCode;

    private String ipAddress;

    private BlackWhiteListType listType;

    private Boolean enabled;
}
