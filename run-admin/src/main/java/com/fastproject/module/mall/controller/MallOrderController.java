package com.fastproject.module.mall.controller;

import com.fastproject.idempotent.annotation.Idempotent;
import com.fastproject.logs.annotation.Log;
import com.fastproject.logs.enums.LogAction;
import com.fastproject.logs.enums.LogType;
import com.fastproject.mall.service.MallOrderService;
import com.fastproject.mall.vo.order.MallOrderCreate;
import com.fastproject.mall.vo.order.MallOrderQuery;
import com.fastproject.mall.vo.order.MallOrderUpdate;
import com.fastproject.mall.vo.order.MallOrderVo;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.utils.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 订单 Controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/mall/order")
public class MallOrderController {

    private final MallOrderService mallOrderService;

    @PostMapping
    @PreAuthorize("@ps.hasPermission('admin:mall:order:add')")
    @Log(value = "添加订单", type = LogType.BUSINESS, action = LogAction.CREATE)
    @Idempotent(prefix = "add:mall:order:", expireTime = 120, title = "添加订单")
    public ResultVo<Object> add(@RequestBody MallOrderCreate create) {
        return ResultVo.success(mallOrderService.save(create));
    }

    @PutMapping
    @PreAuthorize("@ps.hasPermission('admin:mall:order:update')")
    @Log(value = "修改订单", type = LogType.BUSINESS, action = LogAction.UPDATE)
    @Idempotent(prefix = "update:mall:order:", expireTime = 120, title = "修改订单")
    public ResultVo<Object> update(@RequestBody MallOrderUpdate update) {
        mallOrderService.update(update);
        return ResultVo.success();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:mall:order:delete')")
    @Log(value = "删除订单", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> delete(@PathVariable Long id) {
        mallOrderService.delete(id);
        return ResultVo.success();
    }

    @DeleteMapping("/batch")
    @PreAuthorize("@ps.hasPermission('admin:mall:order:delete')")
    @Log(value = "批量删除订单", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> batchDelete(@RequestBody List<Long> ids) {
        mallOrderService.batchDelete(ids);
        return ResultVo.success();
    }

    @PostMapping("/page")
    @PreAuthorize("@ps.hasPermission('admin:mall:order:page')")
    public ResultVo<PageVo<List<MallOrderVo>>> page(@RequestBody MallOrderQuery query) {
        return ResultVo.success(mallOrderService.findPage(query));
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:mall:order:page')")
    public ResultVo<MallOrderVo> get(@PathVariable Long id) {
        return ResultVo.success(mallOrderService.findById(id));
    }

    @GetMapping("/byOrderNo/{orderNo}")
    @PreAuthorize("@ps.hasPermission('admin:mall:order:page')")
    public ResultVo<MallOrderVo> getByOrderNo(@PathVariable String orderNo) {
        return ResultVo.success(mallOrderService.findByOrderNo(orderNo));
    }

    @GetMapping("/list")
    @PreAuthorize("@ps.hasPermission('admin:mall:order:page')")
    public ResultVo<List<MallOrderVo>> list() {
        return ResultVo.success(mallOrderService.findAll());
    }
}
