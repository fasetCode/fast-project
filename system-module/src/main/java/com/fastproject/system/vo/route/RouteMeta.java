package com.fastproject.system.vo.route;

import lombok.Data;

/**
 * 路由元信息
 */
@Data
public class RouteMeta {
    /**
     * 路由标题
     */
    private String title;

    /**
     * 路由图标
     */
    private String icon;

    /**
     * 排序
     */
    private Integer rank;

    /**
     * 是否在菜单中显示
     */
    private Boolean showLink;
}
