package com.fastproject.module.mall.controller;

import com.fastproject.idempotent.annotation.Idempotent;
import com.fastproject.logs.annotation.Log;
import com.fastproject.logs.enums.LogAction;
import com.fastproject.logs.enums.LogType;
import com.fastproject.mall.service.MallCartService;
import com.fastproject.mall.vo.cart.MallCartCreate;
import com.fastproject.mall.vo.cart.MallCartQuery;
import com.fastproject.mall.vo.cart.MallCartUpdate;
import com.fastproject.mall.vo.cart.MallCartVo;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.utils.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 购物车 Controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/mall/cart")
public class MallCartController {

    private final MallCartService mallCartService;

    @PostMapping
    @PreAuthorize("@ps.hasPermission('admin:mall:cart:add')")
    @Log(value = "添加购物车", type = LogType.BUSINESS, action = LogAction.CREATE)
    @Idempotent(prefix = "add:mall:cart:", expireTime = 120, title = "添加购物车")
    public ResultVo<Object> add(@RequestBody MallCartCreate create) {
        return ResultVo.success(mallCartService.save(create));
    }

    @PutMapping
    @PreAuthorize("@ps.hasPermission('admin:mall:cart:update')")
    @Log(value = "修改购物车", type = LogType.BUSINESS, action = LogAction.UPDATE)
    @Idempotent(prefix = "update:mall:cart:", expireTime = 120, title = "修改购物车")
    public ResultVo<Object> update(@RequestBody MallCartUpdate update) {
        mallCartService.update(update);
        return ResultVo.success();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:mall:cart:delete')")
    @Log(value = "删除购物车", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> delete(@PathVariable Long id) {
        mallCartService.delete(id);
        return ResultVo.success();
    }

    @DeleteMapping("/batch")
    @PreAuthorize("@ps.hasPermission('admin:mall:cart:delete')")
    @Log(value = "批量删除购物车", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> batchDelete(@RequestBody List<Long> ids) {
        mallCartService.batchDelete(ids);
        return ResultVo.success();
    }

    @PostMapping("/page")
    @PreAuthorize("@ps.hasPermission('admin:mall:cart:page')")
    public ResultVo<PageVo<List<MallCartVo>>> page(@RequestBody MallCartQuery query) {
        return ResultVo.success(mallCartService.findPage(query));
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:mall:cart:page')")
    public ResultVo<MallCartVo> get(@PathVariable Long id) {
        return ResultVo.success(mallCartService.findById(id));
    }

    @GetMapping("/list")
    @PreAuthorize("@ps.hasPermission('admin:mall:cart:page')")
    public ResultVo<List<MallCartVo>> list() {
        return ResultVo.success(mallCartService.findAll());
    }

    @GetMapping("/byUser/{userId}")
    @PreAuthorize("@ps.hasPermission('admin:mall:cart:page')")
    public ResultVo<List<MallCartVo>> byUser(@PathVariable Long userId) {
        return ResultVo.success(mallCartService.findByUserId(userId));
    }
}
