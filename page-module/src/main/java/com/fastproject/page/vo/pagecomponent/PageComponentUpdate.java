package com.fastproject.page.vo.pagecomponent;

import lombok.Data;

@Data
public class PageComponentUpdate {
    
    /**
     * ID
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
     * 类型ID
     */
    private Long typeId;
    
    /**
     * 代码
     */
    private String code;
    
    /**
     * 状态
     */
    private Integer status;
    
    /**
     * 排序
     */
    private Integer sort;
}
