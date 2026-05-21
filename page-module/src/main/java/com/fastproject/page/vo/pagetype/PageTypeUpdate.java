package com.fastproject.page.vo.pagetype;

import lombok.Data;

@Data
public class PageTypeUpdate {
    
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
}
