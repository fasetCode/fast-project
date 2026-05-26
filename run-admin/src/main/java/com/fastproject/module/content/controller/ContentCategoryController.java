package com.fastproject.module.content.controller;

import com.fastproject.content.domain.ContentCategory;
import com.fastproject.content.service.ContentCategoryService;
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
@RequestMapping("/content/category")
public class ContentCategoryController {

    private final ContentCategoryService contentCategoryService;

    @PostMapping
    @PreAuthorize("@ps.hasPermission('admin:content:category:add')")
    @Log(value = "添加分类", type = LogType.BUSINESS, action = LogAction.CREATE)
    @Idempotent(prefix = "add:content:category:", expireTime = 120, title = "添加分类")
    public ResultVo<Long> add(@RequestBody ContentCategory contentCategory) {
        return ResultVo.success(contentCategoryService.save(contentCategory));
    }

    @PutMapping
    @PreAuthorize("@ps.hasPermission('admin:content:category:update')")
    @Log(value = "修改分类", type = LogType.BUSINESS, action = LogAction.UPDATE)
    @Idempotent(prefix = "update:content:category:", expireTime = 120, title = "修改分类")
    public ResultVo<Object> update(@RequestBody ContentCategory contentCategory) {
        contentCategoryService.update(contentCategory);
        return ResultVo.success();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:content:category:delete')")
    @Log(value = "删除分类", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> delete(@PathVariable Long id) {
        contentCategoryService.delete(id);
        return ResultVo.success();
    }

    @DeleteMapping("/batch")
    @PreAuthorize("@ps.hasPermission('admin:content:category:delete')")
    @Log(value = "批量删除分类", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> batchDelete(@RequestBody List<Long> ids) {
        contentCategoryService.batchDelete(ids);
        return ResultVo.success();
    }

    @PostMapping("/page")
    @PreAuthorize("@ps.hasPermission('admin:content:category:page')")
    public ResultVo<PageVo<List<ContentCategory>>> page(@RequestBody PageQuery query) {
        return ResultVo.success(contentCategoryService.findPage(query));
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:content:category:page')")
    public ResultVo<ContentCategory> get(@PathVariable Long id) {
        return ResultVo.success(contentCategoryService.findById(id));
    }

    @GetMapping("/list")
    @PreAuthorize("@ps.hasPermission('admin:content:category:page')")
    public ResultVo<List<ContentCategory>> list() {
        return ResultVo.success(contentCategoryService.findAll());
    }

    @GetMapping("/selectAll")
    public ResultVo<List<ContentCategory>> selectAll() {
        return ResultVo.success(contentCategoryService.selectAll());
    }
}

