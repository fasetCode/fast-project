package com.fastproject.usergrowth.vo.integralrecord;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 用户积分记录 - 视图对象
 */
@Data
public class UserIntegralRecordVo {
    
    /**
     * 主键ID
     */
    private Long id;
    
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 交易前 积分
     */
    private Integer beforeIntegral;

    /**
     * 交易后 积分
     */
    private Integer afterIntegral;
    
    /**
     * 变更 积分
     */
    private Long changeValue;
    
    /**
     * 说明
     */
    private String description;
    
    /**
     * 状态 (1-正常, 2-禁用)
     */
    private Integer status;
    
    /**
     * 类型
     */
    private Integer type;
    
    /**
     * 业务ID
     */
    private Long businessId;
    
    /**
     * 业务名称
     */
    private String businessName;
    
    /**
     * 业务类型
     */
    private String bizType;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
