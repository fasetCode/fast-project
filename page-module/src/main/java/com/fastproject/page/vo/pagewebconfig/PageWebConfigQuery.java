package com.fastproject.page.vo.pagewebconfig;

import com.fastproject.db.PageQuery;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageWebConfigQuery extends PageQuery {
    
    /**
     * 请求地址
     */
    private String pathUrl;
    
    /**
     * 应用ID
     */
    private Long applicationId;
}
