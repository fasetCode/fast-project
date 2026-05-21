package com.fastproject.ratelimit.service;

import com.fastproject.ratelimit.vo.global.GlobalRateLimitConfigCreate;
import com.fastproject.ratelimit.vo.global.GlobalRateLimitConfigUpdate;
import com.fastproject.ratelimit.vo.global.GlobalRateLimitConfigQuery;
import com.fastproject.ratelimit.vo.global.GlobalRateLimitConfigVo;
import com.fastproject.utils.vo.PageVo;

import java.util.List;

/**
 * 全局限流配置 Service 接口
 */
public interface GlobalRateLimitConfigService {

    /**
     * 添加
     */
    Long save(GlobalRateLimitConfigCreate create);

    /**
     * 修改
     */
    void update(GlobalRateLimitConfigUpdate update);

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
    GlobalRateLimitConfigVo findById(Long id);

    /**
     * 分页查询
     */
    PageVo<List<GlobalRateLimitConfigVo>> findPage(GlobalRateLimitConfigQuery query);

    /**
     * 获取启用的全局限流配置
     */
    GlobalRateLimitConfigVo findEnabledConfig();
}