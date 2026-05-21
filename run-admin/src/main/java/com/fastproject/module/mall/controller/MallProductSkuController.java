package com.fastproject.module.mall.controller;

import com.fastproject.idempotent.annotation.Idempotent;
import com.fastproject.logs.annotation.Log;
import com.fastproject.logs.enums.LogAction;
import com.fastproject.logs.enums.LogType;
import com.fastproject.mall.service.MallProductSkuService;
import com.fastproject.mall.vo.sku.MallProductSkuCreate;
import com.fastproject.mall.vo.sku.MallProductSkuQuery;
import com.fastproject.mall.vo.sku.MallProductSkuUpdate;
import com.fastproject.mall.vo.sku.MallProductSkuVo;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.utils.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品SKU Controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/mall/sku")
public class MallProductSkuController {

    private final MallProductSkuService mallProductSkuService;

    @PostMapping
    @PreAuthorize("@ps.hasPermission('admin:mall:sku:add')")
    @Log(value = "添加商品SKU", type = LogType.BUSINESS, action = LogAction.CREATE)
    @Idempotent(prefix = "add:mall:sku:", expireTime = 120, title = "添加商品SKU")
    public ResultVo<Object> add(@RequestBody MallProductSkuCreate create) {
        return ResultVo.success(mallProductSkuService.save(create));
    }

    @PutMapping
    @PreAuthorize("@ps.hasPermission('admin:mall:sku:update')")
    @Log(value = "修改商品SKU", type = LogType.BUSINESS, action = LogAction.UPDATE)
    @Idempotent(prefix = "update:mall:sku:", expireTime = 120, title = "修改商品SKU")
    public ResultVo<Object> update(@RequestBody MallProductSkuUpdate update) {
        mallProductSkuService.update(update);
        return ResultVo.success();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:mall:sku:delete')")
    @Log(value = "删除商品SKU", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> delete(@PathVariable Long id) {
        mallProductSkuService.delete(id);
        return ResultVo.success();
    }

    @DeleteMapping("/batch")
    @PreAuthorize("@ps.hasPermission('admin:mall:sku:delete')")
    @Log(value = "批量删除商品SKU", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> batchDelete(@RequestBody List<Long> ids) {
        mallProductSkuService.batchDelete(ids);
        return ResultVo.success();
    }

    @PostMapping("/page")
    @PreAuthorize("@ps.hasPermission('admin:mall:sku:page')")
    public ResultVo<PageVo<List<MallProductSkuVo>>> page(@RequestBody MallProductSkuQuery query) {
        return ResultVo.success(mallProductSkuService.findPage(query));
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:mall:sku:page')")
    public ResultVo<MallProductSkuVo> get(@PathVariable Long id) {
        return ResultVo.success(mallProductSkuService.findById(id));
    }

    @GetMapping("/byProduct/{productId}")
    @PreAuthorize("@ps.hasPermission('admin:mall:sku:page')")
    public ResultVo<List<MallProductSkuVo>> byProduct(@PathVariable Long productId) {
        return ResultVo.success(mallProductSkuService.findByProductId(productId));
    }

    @GetMapping("/byShop/{shopId}")
    @PreAuthorize("@ps.hasPermission('admin:mall:sku:page')")
    public ResultVo<List<MallProductSkuVo>> byShop(@PathVariable Long shopId) {
        return ResultVo.success(mallProductSkuService.findByShopId(shopId));
    }

    @GetMapping("/list")
    @PreAuthorize("@ps.hasPermission('admin:mall:sku:page')")
    public ResultVo<List<MallProductSkuVo>> list() {
        return ResultVo.success(mallProductSkuService.findAll());
    }
}
