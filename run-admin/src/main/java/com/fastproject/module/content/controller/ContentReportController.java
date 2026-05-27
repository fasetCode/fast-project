package com.fastproject.module.content.controller;

import com.fastproject.content.service.ContentReportService;
import com.fastproject.content.vo.report.ContentReportCreate;
import com.fastproject.content.vo.report.ContentReportQuery;
import com.fastproject.content.vo.report.ContentReportUpdate;
import com.fastproject.content.vo.report.ContentReportVo;
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
@RequestMapping("/content/report")
public class ContentReportController {

    private final ContentReportService contentReportService;

    @PostMapping
    @PreAuthorize("@ps.hasPermission('admin:content:report:add')")
    @Log(value = "添加举报", type = LogType.BUSINESS, action = LogAction.CREATE)
    @Idempotent(prefix = "add:content:report:", expireTime = 120, title = "添加举报")
    public ResultVo<Long> add(@RequestBody ContentReportCreate create) {
        return ResultVo.success(contentReportService.save(create));
    }

    @PutMapping
    @PreAuthorize("@ps.hasPermission('admin:content:report:update')")
    @Log(value = "修改举报", type = LogType.BUSINESS, action = LogAction.UPDATE)
    @Idempotent(prefix = "update:content:report:", expireTime = 120, title = "修改举报")
    public ResultVo<Object> update(@RequestBody ContentReportUpdate update) {
        contentReportService.update(update);
        return ResultVo.success();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:content:report:delete')")
    @Log(value = "删除举报", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> delete(@PathVariable Long id) {
        contentReportService.delete(id);
        return ResultVo.success();
    }

    @DeleteMapping("/batch")
    @PreAuthorize("@ps.hasPermission('admin:content:report:delete')")
    @Log(value = "批量删除举报", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> batchDelete(@RequestBody List<Long> ids) {
        contentReportService.batchDelete(ids);
        return ResultVo.success();
    }

    @PostMapping("/page")
    @PreAuthorize("@ps.hasPermission('admin:content:report:page')")
    public ResultVo<PageVo<List<ContentReportVo>>> page(@RequestBody ContentReportQuery query) {
        return ResultVo.success(contentReportService.findPage(query));
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:content:report:page')")
    public ResultVo<ContentReportVo> get(@PathVariable Long id) {
        return ResultVo.success(contentReportService.findById(id));
    }

    @GetMapping("/list")
    @PreAuthorize("@ps.hasPermission('admin:content:report:page')")
    public ResultVo<List<ContentReportVo>> list() {
        return ResultVo.success(contentReportService.findAll());
    }

    @GetMapping("/selectAll")
    public ResultVo<List<ContentReportVo>> selectAll() {
        return ResultVo.success(contentReportService.selectAll());
    }
}
