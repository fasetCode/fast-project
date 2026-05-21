package com.fastproject.logs.service;

import com.fastproject.logs.vo.OperationLogCreate;
import com.fastproject.logs.vo.OperationLogQuery;
import com.fastproject.logs.vo.OperationLogUpdate;
import com.fastproject.logs.vo.OperationLogVo;
import com.fastproject.utils.vo.PageVo;

import java.util.List;

/**
 * 操作日志 Service 接口
 */
public interface OperationLogService {

    /**
     * 添加
     */
    Long save(OperationLogCreate create);

    /**
     * 修改
     */
    void update(OperationLogUpdate update);

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
    OperationLogVo findById(Long id);

    /**
     * 分页查询
     */
    PageVo<List<OperationLogVo>> findPage(OperationLogQuery query);
}
