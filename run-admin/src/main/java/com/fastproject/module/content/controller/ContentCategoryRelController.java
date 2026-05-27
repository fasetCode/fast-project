package com.fastproject.module.content.controller;

import com.fastproject.content.service.ContentCategoryRelService;
import com.fastproject.content.vo.categoryrel.ContentCategoryRelCreate;
import com.fastproject.content.vo.categoryrel.ContentCategoryRelQuery;
import com.fastproject.content.vo.categoryrel.ContentCategoryRelUpdate;
import com.fastproject.content.vo.categoryrel.ContentCategoryRelVo;
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
@RequestMapping("/content/categoryRel")
public class ContentCategoryRelController {

    private final ContentCategoryRelService contentCategoryRelService;

    @PostMapping
    @PreAuthorize("@ps.hasPermission('admin:content:categoryRel:add')")
    @Log(value = "添加内容分类关联", type = LogType.BUSINESS, action = LogAction.CREATE)
    @Idempotent(prefix = "add:content:categoryRel:", expireTime = 120, title = "添加内容分类关联")
    public ResultVo<Long> add(@RequestBody ContentCategoryRelCreate create) {
        return ResultVo.success(contentCategoryRelService.save(create));
    }

    @PutMapping
    @PreAuthorize("@ps.hasPermission('admin:content:categoryRel:update')")
    @Log(value = "修改内容分类关联", type = LogType.BUSINESS, action = LogAction.UPDATE)
    @Idempotent(prefix = "update:content:categoryRel:", expireTime = 120, title = "修改内容分类关联")
    public ResultVo<Object> update(@RequestBody ContentCategoryRelUpdate update) {
        contentCategoryRelService.update(update);
        return ResultVo.success();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:content:categoryRel:delete')")
    @Log(value = "删除内容分类关联", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> delete(@PathVariable Long id) {
        contentCategoryRelService.delete(id);
        return ResultVo.success();
    }

    @DeleteMapping("/batch")
    @PreAuthorize("@ps.hasPermission('admin:content:categoryRel:delete')")
    @Log(value = "批量删除内容分类关联", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> batchDelete(@RequestBody List<Long> ids) {
        contentCategoryRelService.batchDelete(ids);
        return ResultVo.success();
    }

    @PostMapping("/page")
    @PreAuthorize("@ps.hasPermission('admin:content:categoryRel:page')")
    public ResultVo<PageVo<List<ContentCategoryRelVo>>> page(@RequestBody ContentCategoryRelQuery query) {
        return ResultVo.success(contentCategoryRelService.findPage(query));
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:content:categoryRel:page')")
    public ResultVo<ContentCategoryRelVo> get(@PathVariable Long id) {
        return ResultVo.success(contentCategoryRelService.findById(id));
    }

    @GetMapping("/list")
    @PreAuthorize("@ps.hasPermission('admin:content:categoryRel:page')")
    public ResultVo<List<ContentCategoryRelVo>> list() {
        return ResultVo.success(contentCategoryRelService.findAll());
    }

    @GetMapping("/selectAll")
    public ResultVo<List<ContentCategoryRelVo>> selectAll() {
        return ResultVo.success(contentCategoryRelService.selectAll());
    }
}
