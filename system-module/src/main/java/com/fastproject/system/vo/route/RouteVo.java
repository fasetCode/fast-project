package com.fastproject.system.vo.route;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 路由展示对象
 */
@Data
public class RouteVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 路由名称
     */
    private String name;

    /**
     * 路由路径
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 路由元信息
     */
    private RouteMeta meta;

    /**
     * 子路由
     */
    private List<RouteVo> children;
}
