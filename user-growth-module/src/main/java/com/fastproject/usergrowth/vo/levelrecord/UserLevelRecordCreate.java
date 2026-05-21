package com.fastproject.usergrowth.vo.levelrecord;

import lombok.Getter;
import lombok.Data;

/**
 * 用户等级记录 - 新增入参
 */
@Data
public class UserLevelRecordCreate {
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 交易前 成长值
     */
    private Integer beforeGrowthValue;
    
    /**
     * 交易后 成长值
     */
    private Integer afterGrowthValue;
    
    /**
     * 变更 成长值
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
}
