package com.fastproject.system.service;

import com.fastproject.system.domain.SlowQueryLog;
import com.fastproject.system.vo.slowquery.SlowQueryLogQuery;
import com.fastproject.system.vo.slowquery.SlowQueryLogVo;
import com.fastproject.utils.vo.PageVo;

import java.util.List;

public interface SlowQueryLogService {
    
    /**
     * 记录慢查询
     */
    void log(String sql, long executionTime);

    /**
     * 分页查询
     */
    PageVo<List<SlowQueryLogVo>> findPage(SlowQueryLogQuery query);

    /**
     * 批量删除
     */
    void batchDelete(List<Long> ids);

    /**
     * 更新备注和后台标识
     */
    void update(SlowQueryLogVo vo);
}
