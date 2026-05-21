package com.fastproject.ratelimit.service;

import com.fastproject.ratelimit.vo.record.RateLimitRecordCreate;
import com.fastproject.ratelimit.vo.record.RateLimitRecordUpdate;
import com.fastproject.ratelimit.vo.record.RateLimitRecordQuery;
import com.fastproject.ratelimit.vo.record.RateLimitRecordVo;
import com.fastproject.utils.vo.PageVo;

import java.util.List;

/**
 * 限流记录 Service 接口
 */
public interface RateLimitRecordService {

    /**
     * 添加
     */
    Long save(RateLimitRecordCreate create);

    /**
     * 修改
     */
    void update(RateLimitRecordUpdate update);

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
    RateLimitRecordVo findById(Long id);

    /**
     * 分页查询
     */
    PageVo<List<RateLimitRecordVo>> findPage(RateLimitRecordQuery query);
}