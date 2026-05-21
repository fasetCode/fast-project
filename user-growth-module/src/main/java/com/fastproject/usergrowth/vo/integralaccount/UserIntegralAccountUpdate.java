package com.fastproject.usergrowth.vo.integralaccount;

import lombok.Data;

/**
 * 用户积分账户 - 修改入参
 */
@Data
public class UserIntegralAccountUpdate {
    
    /**
     * 主键ID
     */
    private Long id;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 积分值
     */
    private Long integral;
    
    /**
     * 状态 (1-正常, 2-禁用)
     */
    private Integer status;
}
