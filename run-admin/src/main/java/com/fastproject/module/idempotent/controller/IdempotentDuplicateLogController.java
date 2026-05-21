package com.fastproject.module.idempotent.controller;

import com.fastproject.idempotent.service.IdempotentDuplicateLogService;
import com.fastproject.idempotent.vo.IdempotentDuplicateLogCreate;
import com.fastproject.idempotent.vo.IdempotentDuplicateLogQuery;
import com.fastproject.idempotent.vo.IdempotentDuplicateLogUpdate;
import com.fastproject.idempotent.vo.IdempotentDuplicateLogVo;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.utils.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 幂等重复提交记录 Controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/idempotent/duplicate-log")
public class IdempotentDuplicateLogController {

    private final IdempotentDuplicateLogService duplicateLogService;

    /**
     * 添加
     */
    @PostMapping
    @PreAuthorize("@ps.hasPermission('admin:idempotent:duplicate-log:add')")
    public ResultVo<Object> add(@RequestBody IdempotentDuplicateLogCreate create) {
        return ResultVo.success(duplicateLogService.save(create));
    }

    /**
     * 修改
     */
    @PutMapping
    @PreAuthorize("@ps.hasPermission('admin:idempotent:duplicate-log:update')")
    public ResultVo<Object> update(@RequestBody IdempotentDuplicateLogUpdate update) {
        duplicateLogService.update(update);
        return ResultVo.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:idempotent:duplicate-log:delete')")
    public ResultVo<Object> delete(@PathVariable Long id) {
        duplicateLogService.delete(id);
        return ResultVo.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/batch")
    @PreAuthorize("@ps.hasPermission('admin:idempotent:duplicate-log:delete')")
    public ResultVo<Object> batchDelete(@RequestBody List<Long> ids) {
        duplicateLogService.batchDelete(ids);
        return ResultVo.success();
    }

    /**
     * 分页
     */
    @PostMapping("/page")
    @PreAuthorize("@ps.hasPermission('admin:idempotent:duplicate-log:page')")
    public ResultVo<PageVo<List<IdempotentDuplicateLogVo>>> page(@RequestBody IdempotentDuplicateLogQuery query) {
        return ResultVo.success(duplicateLogService.findPage(query));
    }

    /**
     * 详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:idempotent:duplicate-log:page')")
    public ResultVo<IdempotentDuplicateLogVo> get(@PathVariable Long id) {
        return ResultVo.success(duplicateLogService.findById(id));
    }
}
