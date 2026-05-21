package com.fastproject.module.mall.controller;

import com.fastproject.idempotent.annotation.Idempotent;
import com.fastproject.logs.annotation.Log;
import com.fastproject.logs.enums.LogAction;
import com.fastproject.logs.enums.LogType;
import com.fastproject.mall.service.MallProductCategoryService;
import com.fastproject.mall.vo.category.MallProductCategoryCreate;
import com.fastproject.mall.vo.category.MallProductCategoryQuery;
import com.fastproject.mall.vo.category.MallProductCategoryUpdate;
import com.fastproject.mall.vo.category.MallProductCategoryVo;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.utils.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品分类 Controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/mall/category")
public class MallProductCategoryController {

    private final MallProductCategoryService mallProductCategoryService;

    @PostMapping
    @PreAuthorize("@ps.hasPermission('admin:mall:category:add')")
    @Log(value = "添加分类", type = LogType.BUSINESS, action = LogAction.CREATE)
    @Idempotent(prefix = "add:mall:category:", expireTime = 120, title = "添加分类")
    public ResultVo<Object> add(@RequestBody MallProductCategoryCreate create) {
        return ResultVo.success(mallProductCategoryService.save(create));
    }

    @PutMapping
    @PreAuthorize("@ps.hasPermission('admin:mall:category:update')")
    @Log(value = "修改分类", type = LogType.BUSINESS, action = LogAction.UPDATE)
    @Idempotent(prefix = "update:mall:category:", expireTime = 120, title = "修改分类")
    public ResultVo<Object> update(@RequestBody MallProductCategoryUpdate update) {
        mallProductCategoryService.update(update);
        return ResultVo.success();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:mall:category:delete')")
    @Log(value = "删除分类", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> delete(@PathVariable Long id) {
        mallProductCategoryService.delete(id);
        return ResultVo.success();
    }

    @DeleteMapping("/batch")
    @PreAuthorize("@ps.hasPermission('admin:mall:category:delete')")
    @Log(value = "批量删除分类", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> batchDelete(@RequestBody List<Long> ids) {
        mallProductCategoryService.batchDelete(ids);
        return ResultVo.success();
    }

    @PostMapping("/page")
    @PreAuthorize("@ps.hasPermission('admin:mall:category:page')")
    public ResultVo<PageVo<List<MallProductCategoryVo>>> page(@RequestBody MallProductCategoryQuery query) {
        return ResultVo.success(mallProductCategoryService.findPage(query));
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:mall:category:page')")
    public ResultVo<MallProductCategoryVo> get(@PathVariable Long id) {
        return ResultVo.success(mallProductCategoryService.findById(id));
    }

    @GetMapping("/tree")
    @PreAuthorize("@ps.hasPermission('admin:mall:category:page')")
    public ResultVo<List<MallProductCategoryVo>> tree() {
        return ResultVo.success(mallProductCategoryService.findTree());
    }

    @GetMapping("/list")
    @PreAuthorize("@ps.hasPermission('admin:mall:category:page')")
    public ResultVo<List<MallProductCategoryVo>> list() {
        return ResultVo.success(mallProductCategoryService.findAll());
    }

    @GetMapping("/selectAll")
    public ResultVo<List<MallProductCategoryVo>> selectAll() {
        return ResultVo.success(mallProductCategoryService.selectAll());
    }
}
