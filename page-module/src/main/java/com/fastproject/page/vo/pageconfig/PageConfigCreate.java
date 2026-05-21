package com.fastproject.page.vo.pageconfig;

import lombok.Data;

@Data
public class PageConfigCreate {
    /**
     * 标题
     */
    private String title;
    /**
     * 请求地址
     */
    private String pathUrl;
    
    /**
     * 页面配置
     */
    private String config;
    
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
