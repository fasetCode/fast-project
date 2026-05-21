package com.fastproject.module.mall.controller;

import com.fastproject.idempotent.annotation.Idempotent;
import com.fastproject.logs.annotation.Log;
import com.fastproject.logs.enums.LogAction;
import com.fastproject.logs.enums.LogType;
import com.fastproject.mall.service.MallUserAddressService;
import com.fastproject.mall.vo.useraddress.MallUserAddressCreate;
import com.fastproject.mall.vo.useraddress.MallUserAddressQuery;
import com.fastproject.mall.vo.useraddress.MallUserAddressUpdate;
import com.fastproject.mall.vo.useraddress.MallUserAddressVo;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.utils.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户收货地址 Controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/mall/userAddress")
public class MallUserAddressController {

    private final MallUserAddressService mallUserAddressService;

    @PostMapping
    @PreAuthorize("@ps.hasPermission('admin:mall:userAddress:add')")
    @Log(value = "添加用户收货地址", type = LogType.BUSINESS, action = LogAction.CREATE)
    @Idempotent(prefix = "add:mall:userAddress:", expireTime = 120, title = "添加用户收货地址")
    public ResultVo<Object> add(@RequestBody MallUserAddressCreate create) {
        return ResultVo.success(mallUserAddressService.save(create));
    }

    @PutMapping
    @PreAuthorize("@ps.hasPermission('admin:mall:userAddress:update')")
    @Log(value = "修改用户收货地址", type = LogType.BUSINESS, action = LogAction.UPDATE)
    @Idempotent(prefix = "update:mall:userAddress:", expireTime = 120, title = "修改用户收货地址")
    public ResultVo<Object> update(@RequestBody MallUserAddressUpdate update) {
        mallUserAddressService.update(update);
        return ResultVo.success();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:mall:userAddress:delete')")
    @Log(value = "删除用户收货地址", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> delete(@PathVariable Long id) {
        mallUserAddressService.delete(id);
        return ResultVo.success();
    }

    @DeleteMapping("/batch")
    @PreAuthorize("@ps.hasPermission('admin:mall:userAddress:delete')")
    @Log(value = "批量删除用户收货地址", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> batchDelete(@RequestBody List<Long> ids) {
        mallUserAddressService.batchDelete(ids);
        return ResultVo.success();
    }

    @PostMapping("/page")
    @PreAuthorize("@ps.hasPermission('admin:mall:userAddress:page')")
    public ResultVo<PageVo<List<MallUserAddressVo>>> page(@RequestBody MallUserAddressQuery query) {
        return ResultVo.success(mallUserAddressService.findPage(query));
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:mall:userAddress:page')")
    public ResultVo<MallUserAddressVo> get(@PathVariable Long id) {
        return ResultVo.success(mallUserAddressService.findById(id));
    }

    @GetMapping("/byUser/{userId}")
    @PreAuthorize("@ps.hasPermission('admin:mall:userAddress:page')")
    public ResultVo<List<MallUserAddressVo>> byUser(@PathVariable Long userId) {
        return ResultVo.success(mallUserAddressService.findByUserId(userId));
    }

    @GetMapping("/default/{userId}")
    @PreAuthorize("@ps.hasPermission('admin:mall:userAddress:page')")
    public ResultVo<MallUserAddressVo> defaultAddress(@PathVariable Long userId) {
        return ResultVo.success(mallUserAddressService.findDefaultByUserId(userId));
    }

    @GetMapping("/list")
    @PreAuthorize("@ps.hasPermission('admin:mall:userAddress:page')")
    public ResultVo<List<MallUserAddressVo>> list() {
        return ResultVo.success(mallUserAddressService.findAll());
    }
}
