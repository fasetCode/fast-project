package com.fastproject.page.vo.pagewebconfig;

import lombok.Data;

@Data
public class PageWebConfigCreate {
    
    /**
     * 请求地址
     */
    private String pathUrl;
    
    /**
     * 页面配置
     */
    private String config;
    
    /**
     * 应用ID
     */
    private Long applicationId;
}
