package com.fastproject.usergrowth.vo.integralaccount;

import com.fastproject.db.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户积分账户 - 分页查询入参
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserIntegralAccountQuery extends PageQuery {
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 状态 (1-正常, 2-禁用)
     */
    private Integer status;
}
