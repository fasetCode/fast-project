package com.fastproject.page.vo.pageapplication;

import lombok.Data;

@Data
public class PageApplicationUpdate {
    
    /**
     * ID
     */
    private Long id;
    
    /**
     * 标题
     */
    private String title;
    
    /**
     * 代码
     */
    private String code;
    
    /**
     * 状态
     */
    private Integer status;
    
    /**
     * 图标
     */
    private String icon;
    
    /**
     * 类型ID
     */
    private Long typeId;
}
