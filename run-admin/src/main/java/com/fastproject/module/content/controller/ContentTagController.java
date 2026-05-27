package com.fastproject.module.content.controller;

import com.fastproject.content.service.ContentTagService;
import com.fastproject.content.vo.tag.ContentTagCreate;
import com.fastproject.content.vo.tag.ContentTagQuery;
import com.fastproject.content.vo.tag.ContentTagUpdate;
import com.fastproject.content.vo.tag.ContentTagVo;
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
    public ResultVo<Long> add(@RequestBody ContentTagCreate create) {
        return ResultVo.success(contentTagService.save(create));
    }

    @PutMapping
    @PreAuthorize("@ps.hasPermission('admin:content:tag:update')")
    @Log(value = "修改标签", type = LogType.BUSINESS, action = LogAction.UPDATE)
    @Idempotent(prefix = "update:content:tag:", expireTime = 120, title = "修改标签")
    public ResultVo<Object> update(@RequestBody ContentTagUpdate update) {
        contentTagService.update(update);
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
    public ResultVo<PageVo<List<ContentTagVo>>> page(@RequestBody ContentTagQuery query) {
        return ResultVo.success(contentTagService.findPage(query));
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:content:tag:page')")
    public ResultVo<ContentTagVo> get(@PathVariable Long id) {
        return ResultVo.success(contentTagService.findById(id));
    }

    @GetMapping("/list")
    @PreAuthorize("@ps.hasPermission('admin:content:tag:page')")
    public ResultVo<List<ContentTagVo>> list() {
        return ResultVo.success(contentTagService.findAll());
    }

    @GetMapping("/selectAll")
    public ResultVo<List<ContentTagVo>> selectAll() {
        return ResultVo.success(contentTagService.selectAll());
    }
}
