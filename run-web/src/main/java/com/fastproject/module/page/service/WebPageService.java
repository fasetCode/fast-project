package com.fastproject.module.page.service;

public interface WebPageService {

    /**
     * 获取页面配置
     * @param path 路径
     * @param appCode 应用代码
     * @return 页面数据
     */
    String getPathPage(String path,String appCode);

}
