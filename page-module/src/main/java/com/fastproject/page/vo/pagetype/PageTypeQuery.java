package com.fastproject.page.vo.pagetype;

import com.fastproject.db.PageQuery;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageTypeQuery extends PageQuery {
    
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
