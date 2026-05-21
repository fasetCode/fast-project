package com.fastproject.usergrowth.vo.levelconfig;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 用户等级配置 - 视图对象
 */
@Data
public class UserLevelConfigVo {
    
    /**
     * 主键ID
     */
    private Long id;
    
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
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
