package com.fastproject.module.content.controller;

import com.fastproject.content.domain.ContentRevision;
import com.fastproject.content.service.ContentRevisionService;
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
@RequestMapping("/content/revision")
public class ContentRevisionController {

    private final ContentRevisionService contentRevisionService;

    @PostMapping
    @PreAuthorize("@ps.hasPermission('admin:content:revision:add')")
    @Log(value = "添加修订记录", type = LogType.BUSINESS, action = LogAction.CREATE)
    @Idempotent(prefix = "add:content:revision:", expireTime = 120, title = "添加修订记录")
    public ResultVo<Long> add(@RequestBody ContentRevision contentRevision) {
        return ResultVo.success(contentRevisionService.save(contentRevision));
    }

    @PutMapping
    @PreAuthorize("@ps.hasPermission('admin:content:revision:update')")
    @Log(value = "修改修订记录", type = LogType.BUSINESS, action = LogAction.UPDATE)
    @Idempotent(prefix = "update:content:revision:", expireTime = 120, title = "修改修订记录")
    public ResultVo<Object> update(@RequestBody ContentRevision contentRevision) {
        contentRevisionService.update(contentRevision);
        return ResultVo.success();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:content:revision:delete')")
    @Log(value = "删除修订记录", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> delete(@PathVariable Long id) {
        contentRevisionService.delete(id);
        return ResultVo.success();
    }

    @DeleteMapping("/batch")
    @PreAuthorize("@ps.hasPermission('admin:content:revision:delete')")
    @Log(value = "批量删除修订记录", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> batchDelete(@RequestBody List<Long> ids) {
        contentRevisionService.batchDelete(ids);
        return ResultVo.success();
    }

    @PostMapping("/page")
    @PreAuthorize("@ps.hasPermission('admin:content:revision:page')")
    public ResultVo<PageVo<List<ContentRevision>>> page(@RequestBody PageQuery query) {
        return ResultVo.success(contentRevisionService.findPage(query));
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:content:revision:page')")
    public ResultVo<ContentRevision> get(@PathVariable Long id) {
        return ResultVo.success(contentRevisionService.findById(id));
    }

    @GetMapping("/list")
    @PreAuthorize("@ps.hasPermission('admin:content:revision:page')")
    public ResultVo<List<ContentRevision>> list() {
        return ResultVo.success(contentRevisionService.findAll());
    }

    @GetMapping("/selectAll")
    public ResultVo<List<ContentRevision>> selectAll() {
        return ResultVo.success(contentRevisionService.selectAll());
    }
}

