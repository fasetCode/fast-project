package com.fastproject.usergrowth.vo.integralaccount;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 用户积分账户 - 视图对象
 */
@Data
public class UserIntegralAccountVo {
    
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
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
