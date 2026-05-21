package com.fastproject.page.vo.pageapplication;

import com.fastproject.db.PageQuery;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageApplicationQuery extends PageQuery {
    
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
     * 类型ID
     */
    private Long typeId;
}
