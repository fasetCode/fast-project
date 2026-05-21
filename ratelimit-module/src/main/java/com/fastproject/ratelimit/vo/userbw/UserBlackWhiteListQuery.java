package com.fastproject.ratelimit.vo.userbw;

import com.fastproject.db.PageQuery;
import com.fastproject.ratelimit.enums.BlackWhiteListType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户黑白名单配置 查询
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserBlackWhiteListQuery extends PageQuery {

    private String appCode;

    private Long userId;

    private BlackWhiteListType listType;

    private Boolean enabled;
}
