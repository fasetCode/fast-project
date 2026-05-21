package com.fastproject.module.mall.controller;

import com.fastproject.idempotent.annotation.Idempotent;
import com.fastproject.logs.annotation.Log;
import com.fastproject.logs.enums.LogAction;
import com.fastproject.logs.enums.LogType;
import com.fastproject.mall.service.MallShopService;
import com.fastproject.mall.vo.shop.MallShopCreate;
import com.fastproject.mall.vo.shop.MallShopQuery;
import com.fastproject.mall.vo.shop.MallShopUpdate;
import com.fastproject.mall.vo.shop.MallShopVo;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.utils.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商城店铺 Controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/mall/shop")
public class MallShopController {

    private final MallShopService mallShopService;

    @PostMapping
    @PreAuthorize("@ps.hasPermission('admin:mall:shop:add')")
    @Log(value = "添加店铺", type = LogType.BUSINESS, action = LogAction.CREATE)
    @Idempotent(prefix = "add:mall:shop:", expireTime = 120, title = "添加店铺")
    public ResultVo<Object> add(@RequestBody MallShopCreate create) {
        return ResultVo.success(mallShopService.save(create));
    }

    @PutMapping
    @PreAuthorize("@ps.hasPermission('admin:mall:shop:update')")
    @Log(value = "修改店铺", type = LogType.BUSINESS, action = LogAction.UPDATE)
    @Idempotent(prefix = "update:mall:shop:", expireTime = 120, title = "修改店铺")
    public ResultVo<Object> update(@RequestBody MallShopUpdate update) {
        mallShopService.update(update);
        return ResultVo.success();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:mall:shop:delete')")
    @Log(value = "删除店铺", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> delete(@PathVariable Long id) {
        mallShopService.delete(id);
        return ResultVo.success();
    }

    @DeleteMapping("/batch")
    @PreAuthorize("@ps.hasPermission('admin:mall:shop:delete')")
    @Log(value = "批量删除店铺", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> batchDelete(@RequestBody List<Long> ids) {
        mallShopService.batchDelete(ids);
        return ResultVo.success();
    }

    @PostMapping("/page")
    @PreAuthorize("@ps.hasPermission('admin:mall:shop:page')")
    public ResultVo<PageVo<List<MallShopVo>>> page(@RequestBody MallShopQuery query) {
        return ResultVo.success(mallShopService.findPage(query));
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:mall:shop:page')")
    public ResultVo<MallShopVo> get(@PathVariable Long id) {
        return ResultVo.success(mallShopService.findById(id));
    }

    @GetMapping("/list")
    @PreAuthorize("@ps.hasPermission('admin:mall:shop:page')")
    public ResultVo<List<MallShopVo>> list() {
        return ResultVo.success(mallShopService.findAll());
    }

    @GetMapping("/selectAll")
    public ResultVo<List<MallShopVo>> selectAll() {
        return ResultVo.success(mallShopService.selectAll());
    }
}
