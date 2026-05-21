package com.fastproject.page.vo.pageapplication;

import lombok.Data;

@Data
public class PageApplicationVo {
    
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
    
    /**
     * 类型名称
     */
    private String typeName;

    /**
     * 类型代码
     */
    private String typeCode;
}
