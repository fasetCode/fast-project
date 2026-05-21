package com.fastproject.idempotent.service;

import com.fastproject.idempotent.vo.IdempotentDuplicateLogCreate;
import com.fastproject.idempotent.vo.IdempotentDuplicateLogQuery;
import com.fastproject.idempotent.vo.IdempotentDuplicateLogUpdate;
import com.fastproject.idempotent.vo.IdempotentDuplicateLogVo;
import com.fastproject.utils.vo.PageVo;

import java.util.List;

/**
 * 幂等重复提交记录 Service 接口
 */
public interface IdempotentDuplicateLogService {

    /**
     * 添加
     */
    Long save(IdempotentDuplicateLogCreate create);

    /**
     * 修改
     */
    void update(IdempotentDuplicateLogUpdate update);

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
    IdempotentDuplicateLogVo findById(Long id);

    /**
     * 分页查询
     */
    PageVo<List<IdempotentDuplicateLogVo>> findPage(IdempotentDuplicateLogQuery query);
}
