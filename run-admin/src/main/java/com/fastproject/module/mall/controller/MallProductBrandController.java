package com.fastproject.module.mall.controller;

import com.fastproject.idempotent.annotation.Idempotent;
import com.fastproject.logs.annotation.Log;
import com.fastproject.logs.enums.LogAction;
import com.fastproject.logs.enums.LogType;
import com.fastproject.mall.service.MallProductBrandService;
import com.fastproject.mall.vo.brand.MallProductBrandCreate;
import com.fastproject.mall.vo.brand.MallProductBrandQuery;
import com.fastproject.mall.vo.brand.MallProductBrandUpdate;
import com.fastproject.mall.vo.brand.MallProductBrandVo;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.utils.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品品牌 Controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/mall/brand")
public class MallProductBrandController {

    private final MallProductBrandService mallProductBrandService;

    @PostMapping
    @PreAuthorize("@ps.hasPermission('admin:mall:brand:add')")
    @Log(value = "添加品牌", type = LogType.BUSINESS, action = LogAction.CREATE)
    @Idempotent(prefix = "add:mall:brand:", expireTime = 120, title = "添加品牌")
    public ResultVo<Object> add(@RequestBody MallProductBrandCreate create) {
        return ResultVo.success(mallProductBrandService.save(create));
    }

    @PutMapping
    @PreAuthorize("@ps.hasPermission('admin:mall:brand:update')")
    @Log(value = "修改品牌", type = LogType.BUSINESS, action = LogAction.UPDATE)
    @Idempotent(prefix = "update:mall:brand:", expireTime = 120, title = "修改品牌")
    public ResultVo<Object> update(@RequestBody MallProductBrandUpdate update) {
        mallProductBrandService.update(update);
        return ResultVo.success();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:mall:brand:delete')")
    @Log(value = "删除品牌", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> delete(@PathVariable Long id) {
        mallProductBrandService.delete(id);
        return ResultVo.success();
    }

    @DeleteMapping("/batch")
    @PreAuthorize("@ps.hasPermission('admin:mall:brand:delete')")
    @Log(value = "批量删除品牌", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> batchDelete(@RequestBody List<Long> ids) {
        mallProductBrandService.batchDelete(ids);
        return ResultVo.success();
    }

    @PostMapping("/page")
    @PreAuthorize("@ps.hasPermission('admin:mall:brand:page')")
    public ResultVo<PageVo<List<MallProductBrandVo>>> page(@RequestBody MallProductBrandQuery query) {
        return ResultVo.success(mallProductBrandService.findPage(query));
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:mall:brand:page')")
    public ResultVo<MallProductBrandVo> get(@PathVariable Long id) {
        return ResultVo.success(mallProductBrandService.findById(id));
    }

    @GetMapping("/list")
    @PreAuthorize("@ps.hasPermission('admin:mall:brand:page')")
    public ResultVo<List<MallProductBrandVo>> list() {
        return ResultVo.success(mallProductBrandService.findAll());
    }

    @GetMapping("/selectAll")
    public ResultVo<List<MallProductBrandVo>> selectAll() {
        return ResultVo.success(mallProductBrandService.selectAll());
    }
}
