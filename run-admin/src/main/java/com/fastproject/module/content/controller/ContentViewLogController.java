package com.fastproject.module.content.controller;

import com.fastproject.content.domain.ContentViewLog;
import com.fastproject.content.service.ContentViewLogService;
import com.fastproject.db.PageQuery;
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
@RequestMapping("/content/viewLog")
public class ContentViewLogController {

    private final ContentViewLogService contentViewLogService;

    @PostMapping
    @PreAuthorize("@ps.hasPermission('admin:content:viewLog:add')")
    @Log(value = "添加浏览记录", type = LogType.BUSINESS, action = LogAction.CREATE)
    @Idempotent(prefix = "add:content:viewLog:", expireTime = 120, title = "添加浏览记录")
    public ResultVo<Long> add(@RequestBody ContentViewLog contentViewLog) {
        return ResultVo.success(contentViewLogService.save(contentViewLog));
    }

    @PutMapping
    @PreAuthorize("@ps.hasPermission('admin:content:viewLog:update')")
    @Log(value = "修改浏览记录", type = LogType.BUSINESS, action = LogAction.UPDATE)
    @Idempotent(prefix = "update:content:viewLog:", expireTime = 120, title = "修改浏览记录")
    public ResultVo<Object> update(@RequestBody ContentViewLog contentViewLog) {
        contentViewLogService.update(contentViewLog);
        return ResultVo.success();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:content:viewLog:delete')")
    @Log(value = "删除浏览记录", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> delete(@PathVariable Long id) {
        contentViewLogService.delete(id);
        return ResultVo.success();
    }

    @DeleteMapping("/batch")
    @PreAuthorize("@ps.hasPermission('admin:content:viewLog:delete')")
    @Log(value = "批量删除浏览记录", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> batchDelete(@RequestBody List<Long> ids) {
        contentViewLogService.batchDelete(ids);
        return ResultVo.success();
    }

    @PostMapping("/page")
    @PreAuthorize("@ps.hasPermission('admin:content:viewLog:page')")
    public ResultVo<PageVo<List<ContentViewLog>>> page(@RequestBody PageQuery query) {
        return ResultVo.success(contentViewLogService.findPage(query));
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:content:viewLog:page')")
    public ResultVo<ContentViewLog> get(@PathVariable Long id) {
        return ResultVo.success(contentViewLogService.findById(id));
    }

    @GetMapping("/list")
    @PreAuthorize("@ps.hasPermission('admin:content:viewLog:page')")
    public ResultVo<List<ContentViewLog>> list() {
        return ResultVo.success(contentViewLogService.findAll());
    }

    @GetMapping("/selectAll")
    public ResultVo<List<ContentViewLog>> selectAll() {
        return ResultVo.success(contentViewLogService.selectAll());
    }
}

