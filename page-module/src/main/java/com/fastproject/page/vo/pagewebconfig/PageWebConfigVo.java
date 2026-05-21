package com.fastproject.page.vo.pagewebconfig;

import lombok.Data;

@Data
public class PageWebConfigVo {
    
    /**
     * ID
     */
    private Long id;
    
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
    
    /**
     * 应用名称
     */
    private String applicationName;
}
