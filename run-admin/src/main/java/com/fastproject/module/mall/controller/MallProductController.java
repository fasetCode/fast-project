package com.fastproject.module.mall.controller;

import com.fastproject.idempotent.annotation.Idempotent;
import com.fastproject.logs.annotation.Log;
import com.fastproject.logs.enums.LogAction;
import com.fastproject.logs.enums.LogType;
import com.fastproject.mall.service.MallProductService;
import com.fastproject.mall.vo.product.MallProductCreate;
import com.fastproject.mall.vo.product.MallProductQuery;
import com.fastproject.mall.vo.product.MallProductUpdate;
import com.fastproject.mall.vo.product.MallProductVo;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.utils.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品 Controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/mall/product")
public class MallProductController {

    private final MallProductService mallProductService;

    @PostMapping
    @PreAuthorize("@ps.hasPermission('admin:mall:product:add')")
    @Log(value = "添加商品", type = LogType.BUSINESS, action = LogAction.CREATE)
    @Idempotent(prefix = "add:mall:product:", expireTime = 120, title = "添加商品")
    public ResultVo<Object> add(@RequestBody MallProductCreate create) {
        return ResultVo.success(mallProductService.save(create));
    }

    @PutMapping
    @PreAuthorize("@ps.hasPermission('admin:mall:product:update')")
    @Log(value = "修改商品", type = LogType.BUSINESS, action = LogAction.UPDATE)
    @Idempotent(prefix = "update:mall:product:", expireTime = 120, title = "修改商品")
    public ResultVo<Object> update(@RequestBody MallProductUpdate update) {
        mallProductService.update(update);
        return ResultVo.success();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:mall:product:delete')")
    @Log(value = "删除商品", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> delete(@PathVariable Long id) {
        mallProductService.delete(id);
        return ResultVo.success();
    }

    @DeleteMapping("/batch")
    @PreAuthorize("@ps.hasPermission('admin:mall:product:delete')")
    @Log(value = "批量删除商品", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> batchDelete(@RequestBody List<Long> ids) {
        mallProductService.batchDelete(ids);
        return ResultVo.success();
    }

    @PostMapping("/page")
    @PreAuthorize("@ps.hasPermission('admin:mall:product:page')")
    public ResultVo<PageVo<List<MallProductVo>>> page(@RequestBody MallProductQuery query) {
        return ResultVo.success(mallProductService.findPage(query));
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:mall:product:page')")
    public ResultVo<MallProductVo> get(@PathVariable Long id) {
        return ResultVo.success(mallProductService.findById(id));
    }

    @GetMapping("/list")
    @PreAuthorize("@ps.hasPermission('admin:mall:product:page')")
    public ResultVo<List<MallProductVo>> list() {
        return ResultVo.success(mallProductService.findAll());
    }

    @GetMapping("/selectAll")
    public ResultVo<List<MallProductVo>> selectAll() {
        return ResultVo.success(mallProductService.selectAll());
    }
}
