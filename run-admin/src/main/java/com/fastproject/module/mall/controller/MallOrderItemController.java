package com.fastproject.module.mall.controller;

import com.fastproject.idempotent.annotation.Idempotent;
import com.fastproject.logs.annotation.Log;
import com.fastproject.logs.enums.LogAction;
import com.fastproject.logs.enums.LogType;
import com.fastproject.mall.service.MallOrderItemService;
import com.fastproject.mall.vo.orderitem.MallOrderItemCreate;
import com.fastproject.mall.vo.orderitem.MallOrderItemQuery;
import com.fastproject.mall.vo.orderitem.MallOrderItemUpdate;
import com.fastproject.mall.vo.orderitem.MallOrderItemVo;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.utils.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 订单明细 Controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/mall/orderItem")
public class MallOrderItemController {

    private final MallOrderItemService mallOrderItemService;

    @PostMapping
    @PreAuthorize("@ps.hasPermission('admin:mall:orderItem:add')")
    @Log(value = "添加订单明细", type = LogType.BUSINESS, action = LogAction.CREATE)
    @Idempotent(prefix = "add:mall:orderItem:", expireTime = 120, title = "添加订单明细")
    public ResultVo<Object> add(@RequestBody MallOrderItemCreate create) {
        return ResultVo.success(mallOrderItemService.save(create));
    }

    @PutMapping
    @PreAuthorize("@ps.hasPermission('admin:mall:orderItem:update')")
    @Log(value = "修改订单明细", type = LogType.BUSINESS, action = LogAction.UPDATE)
    @Idempotent(prefix = "update:mall:orderItem:", expireTime = 120, title = "修改订单明细")
    public ResultVo<Object> update(@RequestBody MallOrderItemUpdate update) {
        mallOrderItemService.update(update);
        return ResultVo.success();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:mall:orderItem:delete')")
    @Log(value = "删除订单明细", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> delete(@PathVariable Long id) {
        mallOrderItemService.delete(id);
        return ResultVo.success();
    }

    @DeleteMapping("/batch")
    @PreAuthorize("@ps.hasPermission('admin:mall:orderItem:delete')")
    @Log(value = "批量删除订单明细", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> batchDelete(@RequestBody List<Long> ids) {
        mallOrderItemService.batchDelete(ids);
        return ResultVo.success();
    }

    @PostMapping("/page")
    @PreAuthorize("@ps.hasPermission('admin:mall:orderItem:page')")
    public ResultVo<PageVo<List<MallOrderItemVo>>> page(@RequestBody MallOrderItemQuery query) {
        return ResultVo.success(mallOrderItemService.findPage(query));
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:mall:orderItem:page')")
    public ResultVo<MallOrderItemVo> get(@PathVariable Long id) {
        return ResultVo.success(mallOrderItemService.findById(id));
    }

    @GetMapping("/byOrder/{orderId}")
    @PreAuthorize("@ps.hasPermission('admin:mall:orderItem:page')")
    public ResultVo<List<MallOrderItemVo>> byOrder(@PathVariable Long orderId) {
        return ResultVo.success(mallOrderItemService.findByOrderId(orderId));
    }

    @GetMapping("/list")
    @PreAuthorize("@ps.hasPermission('admin:mall:orderItem:page')")
    public ResultVo<List<MallOrderItemVo>> list() {
        return ResultVo.success(mallOrderItemService.findAll());
    }
}
