package com.fastproject.module.content.controller;

import com.fastproject.content.service.ContentTagRelService;
import com.fastproject.content.vo.tagrel.ContentTagRelCreate;
import com.fastproject.content.vo.tagrel.ContentTagRelQuery;
import com.fastproject.content.vo.tagrel.ContentTagRelUpdate;
import com.fastproject.content.vo.tagrel.ContentTagRelVo;
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
@RequestMapping("/content/tagRel")
public class ContentTagRelController {

    private final ContentTagRelService contentTagRelService;

    @PostMapping
    @PreAuthorize("@ps.hasPermission('admin:content:tagRel:add')")
    @Log(value = "添加内容标签关联", type = LogType.BUSINESS, action = LogAction.CREATE)
    @Idempotent(prefix = "add:content:tagRel:", expireTime = 120, title = "添加内容标签关联")
    public ResultVo<Long> add(@RequestBody ContentTagRelCreate create) {
        return ResultVo.success(contentTagRelService.save(create));
    }

    @PutMapping
    @PreAuthorize("@ps.hasPermission('admin:content:tagRel:update')")
    @Log(value = "修改内容标签关联", type = LogType.BUSINESS, action = LogAction.UPDATE)
    @Idempotent(prefix = "update:content:tagRel:", expireTime = 120, title = "修改内容标签关联")
    public ResultVo<Object> update(@RequestBody ContentTagRelUpdate update) {
        contentTagRelService.update(update);
        return ResultVo.success();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:content:tagRel:delete')")
    @Log(value = "删除内容标签关联", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> delete(@PathVariable Long id) {
        contentTagRelService.delete(id);
        return ResultVo.success();
    }

    @DeleteMapping("/batch")
    @PreAuthorize("@ps.hasPermission('admin:content:tagRel:delete')")
    @Log(value = "批量删除内容标签关联", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> batchDelete(@RequestBody List<Long> ids) {
        contentTagRelService.batchDelete(ids);
        return ResultVo.success();
    }

    @PostMapping("/page")
    @PreAuthorize("@ps.hasPermission('admin:content:tagRel:page')")
    public ResultVo<PageVo<List<ContentTagRelVo>>> page(@RequestBody ContentTagRelQuery query) {
        return ResultVo.success(contentTagRelService.findPage(query));
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:content:tagRel:page')")
    public ResultVo<ContentTagRelVo> get(@PathVariable Long id) {
        return ResultVo.success(contentTagRelService.findById(id));
    }

    @GetMapping("/list")
    @PreAuthorize("@ps.hasPermission('admin:content:tagRel:page')")
    public ResultVo<List<ContentTagRelVo>> list() {
        return ResultVo.success(contentTagRelService.findAll());
    }

    @GetMapping("/selectAll")
    public ResultVo<List<ContentTagRelVo>> selectAll() {
        return ResultVo.success(contentTagRelService.selectAll());
    }
}
