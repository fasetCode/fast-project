package com.fastproject.module.logs.controller;

import com.fastproject.logs.service.OperationLogService;
import com.fastproject.logs.vo.OperationLogCreate;
import com.fastproject.logs.vo.OperationLogQuery;
import com.fastproject.logs.vo.OperationLogUpdate;
import com.fastproject.logs.vo.OperationLogVo;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.utils.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 操作日志 Controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/logs/operation")
public class OperationLogController {

    private final OperationLogService operationLogService;

    /**
     * 添加
     */
    @PostMapping
    @PreAuthorize("@ps.hasPermission('admin:logs:operation:add')")
    public ResultVo<Object> add(@RequestBody OperationLogCreate create) {
        return ResultVo.success(operationLogService.save(create));
    }

    /**
     * 修改
     */
    @PutMapping
    @PreAuthorize("@ps.hasPermission('admin:logs:operation:update')")
    public ResultVo<Object> update(@RequestBody OperationLogUpdate update) {
        operationLogService.update(update);
        return ResultVo.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:logs:operation:delete')")
    public ResultVo<Object> delete(@PathVariable Long id) {
        operationLogService.delete(id);
        return ResultVo.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/batch")
    @PreAuthorize("@ps.hasPermission('admin:logs:operation:delete')")
    public ResultVo<Object> batchDelete(@RequestBody List<Long> ids) {
        operationLogService.batchDelete(ids);
        return ResultVo.success();
    }

    /**
     * 分页查询
     */
    @PostMapping("/page")
    @PreAuthorize("@ps.hasPermission('admin:logs:operation:page')")
    public ResultVo<PageVo<List<OperationLogVo>>> page(@RequestBody OperationLogQuery query) {
        return ResultVo.success(operationLogService.findPage(query));
    }

    /**
     * 详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:logs:operation:page')")
    public ResultVo<OperationLogVo> get(@PathVariable Long id) {
        return ResultVo.success(operationLogService.findById(id));
    }
}
