package com.fastproject.module.mall.controller;

import com.fastproject.idempotent.annotation.Idempotent;
import com.fastproject.logs.annotation.Log;
import com.fastproject.logs.enums.LogAction;
import com.fastproject.logs.enums.LogType;
import com.fastproject.mall.service.MallPickupShopService;
import com.fastproject.mall.vo.pickupshop.MallPickupShopCreate;
import com.fastproject.mall.vo.pickupshop.MallPickupShopQuery;
import com.fastproject.mall.vo.pickupshop.MallPickupShopUpdate;
import com.fastproject.mall.vo.pickupshop.MallPickupShopVo;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.utils.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 自提门店 Controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/mall/pickupShop")
public class MallPickupShopController {

    private final MallPickupShopService mallPickupShopService;

    @PostMapping
    @PreAuthorize("@ps.hasPermission('admin:mall:pickupShop:add')")
    @Log(value = "添加自提门店", type = LogType.BUSINESS, action = LogAction.CREATE)
    @Idempotent(prefix = "add:mall:pickupShop:", expireTime = 120, title = "添加自提门店")
    public ResultVo<Object> add(@RequestBody MallPickupShopCreate create) {
        return ResultVo.success(mallPickupShopService.save(create));
    }

    @PutMapping
    @PreAuthorize("@ps.hasPermission('admin:mall:pickupShop:update')")
    @Log(value = "修改自提门店", type = LogType.BUSINESS, action = LogAction.UPDATE)
    @Idempotent(prefix = "update:mall:pickupShop:", expireTime = 120, title = "修改自提门店")
    public ResultVo<Object> update(@RequestBody MallPickupShopUpdate update) {
        mallPickupShopService.update(update);
        return ResultVo.success();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:mall:pickupShop:delete')")
    @Log(value = "删除自提门店", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> delete(@PathVariable Long id) {
        mallPickupShopService.delete(id);
        return ResultVo.success();
    }

    @DeleteMapping("/batch")
    @PreAuthorize("@ps.hasPermission('admin:mall:pickupShop:delete')")
    @Log(value = "批量删除自提门店", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> batchDelete(@RequestBody List<Long> ids) {
        mallPickupShopService.batchDelete(ids);
        return ResultVo.success();
    }

    @PostMapping("/page")
    @PreAuthorize("@ps.hasPermission('admin:mall:pickupShop:page')")
    public ResultVo<PageVo<List<MallPickupShopVo>>> page(@RequestBody MallPickupShopQuery query) {
        return ResultVo.success(mallPickupShopService.findPage(query));
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:mall:pickupShop:page')")
    public ResultVo<MallPickupShopVo> get(@PathVariable Long id) {
        return ResultVo.success(mallPickupShopService.findById(id));
    }

    @GetMapping("/list")
    @PreAuthorize("@ps.hasPermission('admin:mall:pickupShop:page')")
    public ResultVo<List<MallPickupShopVo>> list() {
        return ResultVo.success(mallPickupShopService.findAll());
    }

    @GetMapping("/selectAll")
    public ResultVo<List<MallPickupShopVo>> selectAll() {
        return ResultVo.success(mallPickupShopService.selectAll());
    }
}
