package com.fastproject.page.vo.pageconfig;

import com.fastproject.db.PageQuery;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageConfigQuery extends PageQuery {
    /**
     * 标题
     */
    private String title;
    /**
     * 请求地址
     */
    private String pathUrl;
    
    /**
     * 状态
     */
    private Integer status;
    
    /**
     * 版本
     */
    private String version;
    
    /**
     * 应用ID
     */
    private Long applicationId;
}
