package com.fastproject.usergrowth.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class UserGrowthAccountVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 成长值
     */
    private Long growthValue;

    /**
     * 积分值
     */
    private Long integral;

    /**
     * 当前等级
     */
    private LevelVo level;

    /**
     * 下一个等级
     */
    private LevelVo nextLevel;

    /**
     * 等级 对象
     */
    @Data
    public static class LevelVo{
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
}
