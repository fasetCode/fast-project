package com.fastproject.module.content.controller;

import com.fastproject.content.service.ContentAuditLogService;
import com.fastproject.content.vo.auditlog.ContentAuditLogCreate;
import com.fastproject.content.vo.auditlog.ContentAuditLogQuery;
import com.fastproject.content.vo.auditlog.ContentAuditLogUpdate;
import com.fastproject.content.vo.auditlog.ContentAuditLogVo;
import com.fastproject.idempotent.annotation.Idempotent;
import com.fastproject.logs.annotation.Log;
import com.fastproject.logs.enums.LogAction;
import com.fastproject.logs.enums.LogType;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.utils.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/content/auditLog")
public class ContentAuditLogController {

    private final ContentAuditLogService contentAuditLogService;

    @PostMapping
    @PreAuthorize("@ps.hasPermission('admin:content:auditLog:add')")
    @Log(value = "添加审核记录", type = LogType.BUSINESS, action = LogAction.CREATE)
    @Idempotent(prefix = "add:content:auditLog:", expireTime = 120, title = "添加审核记录")
    public ResultVo<Long> add(@RequestBody ContentAuditLogCreate create) {
        return ResultVo.success(contentAuditLogService.save(create));
    }

    @PutMapping
    @PreAuthorize("@ps.hasPermission('admin:content:auditLog:update')")
    @Log(value = "修改审核记录", type = LogType.BUSINESS, action = LogAction.UPDATE)
    @Idempotent(prefix = "update:content:auditLog:", expireTime = 120, title = "修改审核记录")
    public ResultVo<Object> update(@RequestBody ContentAuditLogUpdate update) {
        contentAuditLogService.update(update);
        return ResultVo.success();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:content:auditLog:delete')")
    @Log(value = "删除审核记录", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> delete(@PathVariable Long id) {
        contentAuditLogService.delete(id);
        return ResultVo.success();
    }

    @DeleteMapping("/batch")
    @PreAuthorize("@ps.hasPermission('admin:content:auditLog:delete')")
    @Log(value = "批量删除审核记录", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> batchDelete(@RequestBody List<Long> ids) {
        contentAuditLogService.batchDelete(ids);
        return ResultVo.success();
    }

    @PostMapping("/page")
    @PreAuthorize("@ps.hasPermission('admin:content:auditLog:page')")
    public ResultVo<PageVo<List<ContentAuditLogVo>>> page(@RequestBody ContentAuditLogQuery query) {
        return ResultVo.success(contentAuditLogService.findPage(query));
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:content:auditLog:page')")
    public ResultVo<ContentAuditLogVo> get(@PathVariable Long id) {
        return ResultVo.success(contentAuditLogService.findById(id));
    }

    @GetMapping("/list")
    @PreAuthorize("@ps.hasPermission('admin:content:auditLog:page')")
    public ResultVo<List<ContentAuditLogVo>> list() {
        return ResultVo.success(contentAuditLogService.findAll());
    }

    @GetMapping("/selectAll")
    public ResultVo<List<ContentAuditLogVo>> selectAll() {
        return ResultVo.success(contentAuditLogService.selectAll());
    }
}
