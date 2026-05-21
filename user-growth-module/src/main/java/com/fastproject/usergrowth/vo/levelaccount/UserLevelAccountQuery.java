package com.fastproject.usergrowth.vo.levelaccount;

import com.fastproject.db.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户等级账户 - 分页查询入参
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserLevelAccountQuery extends PageQuery {
    
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
