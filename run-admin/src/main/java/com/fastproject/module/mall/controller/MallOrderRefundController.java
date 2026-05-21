package com.fastproject.module.mall.controller;

import com.fastproject.idempotent.annotation.Idempotent;
import com.fastproject.logs.annotation.Log;
import com.fastproject.logs.enums.LogAction;
import com.fastproject.logs.enums.LogType;
import com.fastproject.mall.service.MallOrderRefundService;
import com.fastproject.mall.vo.orderrefund.MallOrderRefundCreate;
import com.fastproject.mall.vo.orderrefund.MallOrderRefundQuery;
import com.fastproject.mall.vo.orderrefund.MallOrderRefundUpdate;
import com.fastproject.mall.vo.orderrefund.MallOrderRefundVo;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.utils.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 退款单 Controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/mall/orderRefund")
public class MallOrderRefundController {

    private final MallOrderRefundService mallOrderRefundService;

    @PostMapping
    @PreAuthorize("@ps.hasPermission('admin:mall:orderRefund:add')")
    @Log(value = "添加退款单", type = LogType.BUSINESS, action = LogAction.CREATE)
    @Idempotent(prefix = "add:mall:orderRefund:", expireTime = 120, title = "添加退款单")
    public ResultVo<Object> add(@RequestBody MallOrderRefundCreate create) {
        return ResultVo.success(mallOrderRefundService.save(create));
    }

    @PutMapping
    @PreAuthorize("@ps.hasPermission('admin:mall:orderRefund:update')")
    @Log(value = "修改退款单", type = LogType.BUSINESS, action = LogAction.UPDATE)
    @Idempotent(prefix = "update:mall:orderRefund:", expireTime = 120, title = "修改退款单")
    public ResultVo<Object> update(@RequestBody MallOrderRefundUpdate update) {
        mallOrderRefundService.update(update);
        return ResultVo.success();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:mall:orderRefund:delete')")
    @Log(value = "删除退款单", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> delete(@PathVariable Long id) {
        mallOrderRefundService.delete(id);
        return ResultVo.success();
    }

    @DeleteMapping("/batch")
    @PreAuthorize("@ps.hasPermission('admin:mall:orderRefund:delete')")
    @Log(value = "批量删除退款单", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> batchDelete(@RequestBody List<Long> ids) {
        mallOrderRefundService.batchDelete(ids);
        return ResultVo.success();
    }

    @PostMapping("/page")
    @PreAuthorize("@ps.hasPermission('admin:mall:orderRefund:page')")
    public ResultVo<PageVo<List<MallOrderRefundVo>>> page(@RequestBody MallOrderRefundQuery query) {
        return ResultVo.success(mallOrderRefundService.findPage(query));
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:mall:orderRefund:page')")
    public ResultVo<MallOrderRefundVo> get(@PathVariable Long id) {
        return ResultVo.success(mallOrderRefundService.findById(id));
    }

    @GetMapping("/byRefundNo/{refundNo}")
    @PreAuthorize("@ps.hasPermission('admin:mall:orderRefund:page')")
    public ResultVo<MallOrderRefundVo> getByRefundNo(@PathVariable String refundNo) {
        return ResultVo.success(mallOrderRefundService.findByRefundNo(refundNo));
    }

    @GetMapping("/list")
    @PreAuthorize("@ps.hasPermission('admin:mall:orderRefund:page')")
    public ResultVo<List<MallOrderRefundVo>> list() {
        return ResultVo.success(mallOrderRefundService.findAll());
    }
}
