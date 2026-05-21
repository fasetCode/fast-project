package com.fastproject.ratelimit.service;

import com.fastproject.ratelimit.vo.ip.IpRateLimitConfigCreate;
import com.fastproject.ratelimit.vo.ip.IpRateLimitConfigUpdate;
import com.fastproject.ratelimit.vo.ip.IpRateLimitConfigQuery;
import com.fastproject.ratelimit.vo.ip.IpRateLimitConfigVo;
import com.fastproject.utils.vo.PageVo;

import java.util.List;

/**
 * IP限流配置 Service 接口
 */
public interface IpRateLimitConfigService {

    /**
     * 添加
     */
    Long save(IpRateLimitConfigCreate create);

    /**
     * 修改
     */
    void update(IpRateLimitConfigUpdate update);

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
    IpRateLimitConfigVo findById(Long id);

    /**
     * 分页查询
     */
    PageVo<List<IpRateLimitConfigVo>> findPage(IpRateLimitConfigQuery query);

    /**
     * 根据IP地址查询配置
     */
    IpRateLimitConfigVo findByIpAddress(String ipAddress);

    /**
     * 获取匹配的IP限流配置 (支持优先级: SINGLE > SEGMENT > ALL)
     */
    IpRateLimitConfigVo findMatchingConfig(String appCode, String ip);
}