package com.fastproject.ratelimit.service;

import com.fastproject.ratelimit.vo.api.ApiRateLimitConfigCreate;
import com.fastproject.ratelimit.vo.api.ApiRateLimitConfigUpdate;
import com.fastproject.ratelimit.vo.api.ApiRateLimitConfigQuery;
import com.fastproject.ratelimit.vo.api.ApiRateLimitConfigVo;
import com.fastproject.utils.vo.PageVo;

import java.util.List;

/**
 * API限流配置 Service 接口
 */
public interface ApiRateLimitConfigService {

    /**
     * 添加
     */
    Long save(ApiRateLimitConfigCreate create);

    /**
     * 修改
     */
    void update(ApiRateLimitConfigUpdate update);

    /**
     * 删除
     */
    void delete(Long id);

    /**
     * 批量删除
     */
    void batchDelete(List<Long> ids);

    /**
     * 根据ID查询
     */
    ApiRateLimitConfigVo findById(Long id);

    /**
     * 分页查询
     */
    PageVo<List<ApiRateLimitConfigVo>> findPage(ApiRateLimitConfigQuery query);

    /**
     * 根据应用代码、API路径和HTTP方法查询配置
     */
    ApiRateLimitConfigVo findByAppCodeAndApiPathAndHttpMethod(String appCode, String apiPath, String httpMethod);
}