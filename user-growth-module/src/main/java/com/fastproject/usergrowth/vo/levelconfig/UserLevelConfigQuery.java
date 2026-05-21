package com.fastproject.usergrowth.vo.levelconfig;

import com.fastproject.db.PageQuery;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户等级配置 - 分页查询入参
 */
@Getter
@Setter
public class UserLevelConfigQuery extends PageQuery {
    
    /**
     * 标题
     */
    private String title;
    
    /**
     * 图标
     */
    private String icon;
    
    /**
     * 成长值积分
     */
    private Long growthValue;
    
    /**
     * 数据状态 (1-正常, 2-禁用)
     */
    private Integer status;
    
    /**
     * 背景
     */
    private String background;
    
    /**
     * 颜色
     */
    private String color;
    
    /**
     * 描述
     */
    private String description;
}
