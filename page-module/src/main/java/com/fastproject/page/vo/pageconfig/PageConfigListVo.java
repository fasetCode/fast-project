package com.fastproject.page.vo.pageconfig;

import lombok.Data;

@Data
public class PageConfigListVo {

    /**
     * ID
     */
    private Long id;
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

    /**
     * 应用名称
     */
    private String applicationName;

}
