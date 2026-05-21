package com.fastproject.module.ratelimit.controller;

import com.fastproject.idempotent.annotation.Idempotent;
import com.fastproject.logs.annotation.Log;
import com.fastproject.logs.enums.LogAction;
import com.fastproject.logs.enums.LogType;
import com.fastproject.ratelimit.service.RateLimitRecordService;
import com.fastproject.ratelimit.vo.record.RateLimitRecordCreate;
import com.fastproject.ratelimit.vo.record.RateLimitRecordUpdate;
import com.fastproject.ratelimit.vo.record.RateLimitRecordQuery;
import com.fastproject.ratelimit.vo.record.RateLimitRecordVo;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.utils.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 限流记录 Controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/ratelimit/record")
public class RateLimitRecordController {

    private final RateLimitRecordService rateLimitRecordService;

    /**
     * 添加
     */
    @PostMapping
    @PreAuthorize("@ps.hasPermission('admin:ratelimit:record:add')")
    @Log(value = "添加限流记录", type = LogType.BUSINESS, action = LogAction.CREATE)
    @Idempotent(prefix = "add:ratelimit:record:", expireTime = 120, title = "添加限流记录")
    public ResultVo<Object> add(@RequestBody RateLimitRecordCreate create) {
        return ResultVo.success(rateLimitRecordService.save(create));
    }

    /**
     * 修改
     */
    @PutMapping
    @PreAuthorize("@ps.hasPermission('admin:ratelimit:record:update')")
    @Log(value = "修改限流记录", type = LogType.BUSINESS, action = LogAction.UPDATE)
    @Idempotent(prefix = "update:ratelimit:record:", expireTime = 120, title = "修改限流记录")
    public ResultVo<Object> update(@RequestBody RateLimitRecordUpdate update) {
        rateLimitRecordService.update(update);
        return ResultVo.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:ratelimit:record:delete')")
    @Log(value = "删除限流记录", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> delete(@PathVariable Long id) {
        rateLimitRecordService.delete(id);
        return ResultVo.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/batch")
    @PreAuthorize("@ps.hasPermission('admin:ratelimit:record:delete')")
    @Log(value = "批量删除限流记录", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> batchDelete(@RequestBody List<Long> ids) {
        rateLimitRecordService.batchDelete(ids);
        return ResultVo.success();
    }

    /**
     * 分页
     */
    @PostMapping("/page")
    @PreAuthorize("@ps.hasPermission('admin:ratelimit:record:page')")
    public ResultVo<PageVo<List<RateLimitRecordVo>>> page(@RequestBody RateLimitRecordQuery query) {
        return ResultVo.success(rateLimitRecordService.findPage(query));
    }

    /**
     * 详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:ratelimit:record:page')")
    public ResultVo<RateLimitRecordVo> get(@PathVariable Long id) {
        return ResultVo.success(rateLimitRecordService.findById(id));
    }
}