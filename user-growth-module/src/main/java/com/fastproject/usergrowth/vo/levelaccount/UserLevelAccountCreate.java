package com.fastproject.usergrowth.vo.levelaccount;

import lombok.Data;

/**
 * 用户等级账户 - 新增入参
 */
@Data
public class UserLevelAccountCreate {
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 等级积分/成长值
     */
    private Long growthValue;
    
    /**
     * 状态 (1-正常, 2-禁用)
     */
    private Integer status;
    
    /**
     * 个性图标
     */
    private String avatar;
    
    /**
     * 个性头像框
     */
    private String avatarFrame;
}
