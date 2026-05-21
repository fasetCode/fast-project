package com.fastproject.page.vo.pagetype;

import lombok.Data;

@Data
public class PageTypeCreate {
    
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
