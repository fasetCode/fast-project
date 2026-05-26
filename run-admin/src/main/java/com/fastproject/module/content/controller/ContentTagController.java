package com.fastproject.module.content.controller;

import com.fastproject.content.domain.ContentTag;
import com.fastproject.content.service.ContentTagService;
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
@RequestMapping("/content/tag")
public class ContentTagController {

    private final ContentTagService contentTagService;

    @PostMapping
    @PreAuthorize("@ps.hasPermission('admin:content:tag:add')")
    @Log(value = "添加标签", type = LogType.BUSINESS, action = LogAction.CREATE)
    @Idempotent(prefix = "add:content:tag:", expireTime = 120, title = "添加标签")
    public ResultVo<Long> add(@RequestBody ContentTag contentTag) {
        return ResultVo.success(contentTagService.save(contentTag));
    }

    @PutMapping
    @PreAuthorize("@ps.hasPermission('admin:content:tag:update')")
    @Log(value = "修改标签", type = LogType.BUSINESS, action = LogAction.UPDATE)
    @Idempotent(prefix = "update:content:tag:", expireTime = 120, title = "修改标签")
    public ResultVo<Object> update(@RequestBody ContentTag contentTag) {
        contentTagService.update(contentTag);
        return ResultVo.success();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:content:tag:delete')")
    @Log(value = "删除标签", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> delete(@PathVariable Long id) {
        contentTagService.delete(id);
        return ResultVo.success();
    }

    @DeleteMapping("/batch")
    @PreAuthorize("@ps.hasPermission('admin:content:tag:delete')")
    @Log(value = "批量删除标签", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> batchDelete(@RequestBody List<Long> ids) {
        contentTagService.batchDelete(ids);
        return ResultVo.success();
    }

    @PostMapping("/page")
    @PreAuthorize("@ps.hasPermission('admin:content:tag:page')")
    public ResultVo<PageVo<List<ContentTag>>> page(@RequestBody PageQuery query) {
        return ResultVo.success(contentTagService.findPage(query));
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:content:tag:page')")
    public ResultVo<ContentTag> get(@PathVariable Long id) {
        return ResultVo.success(contentTagService.findById(id));
    }

    @GetMapping("/list")
    @PreAuthorize("@ps.hasPermission('admin:content:tag:page')")
    public ResultVo<List<ContentTag>> list() {
        return ResultVo.success(contentTagService.findAll());
    }

    @GetMapping("/selectAll")
    public ResultVo<List<ContentTag>> selectAll() {
        return ResultVo.success(contentTagService.selectAll());
    }
}

