package com.fastproject.module.mall.controller;

import com.fastproject.idempotent.annotation.Idempotent;
import com.fastproject.logs.annotation.Log;
import com.fastproject.logs.enums.LogAction;
import com.fastproject.logs.enums.LogType;
import com.fastproject.mall.service.MallOrderLogService;
import com.fastproject.mall.vo.orderlog.MallOrderLogCreate;
import com.fastproject.mall.vo.orderlog.MallOrderLogQuery;
import com.fastproject.mall.vo.orderlog.MallOrderLogUpdate;
import com.fastproject.mall.vo.orderlog.MallOrderLogVo;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.utils.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 订单日志 Controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/mall/orderLog")
public class MallOrderLogController {

    private final MallOrderLogService mallOrderLogService;

    @PostMapping
    @PreAuthorize("@ps.hasPermission('admin:mall:orderLog:add')")
    @Log(value = "添加订单日志", type = LogType.BUSINESS, action = LogAction.CREATE)
    @Idempotent(prefix = "add:mall:orderLog:", expireTime = 120, title = "添加订单日志")
    public ResultVo<Object> add(@RequestBody MallOrderLogCreate create) {
        return ResultVo.success(mallOrderLogService.save(create));
    }

    @PutMapping
    @PreAuthorize("@ps.hasPermission('admin:mall:orderLog:update')")
    @Log(value = "修改订单日志", type = LogType.BUSINESS, action = LogAction.UPDATE)
    @Idempotent(prefix = "update:mall:orderLog:", expireTime = 120, title = "修改订单日志")
    public ResultVo<Object> update(@RequestBody MallOrderLogUpdate update) {
        mallOrderLogService.update(update);
        return ResultVo.success();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:mall:orderLog:delete')")
    @Log(value = "删除订单日志", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> delete(@PathVariable Long id) {
        mallOrderLogService.delete(id);
        return ResultVo.success();
    }

    @DeleteMapping("/batch")
    @PreAuthorize("@ps.hasPermission('admin:mall:orderLog:delete')")
    @Log(value = "批量删除订单日志", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> batchDelete(@RequestBody List<Long> ids) {
        mallOrderLogService.batchDelete(ids);
        return ResultVo.success();
    }

    @PostMapping("/page")
    @PreAuthorize("@ps.hasPermission('admin:mall:orderLog:page')")
    public ResultVo<PageVo<List<MallOrderLogVo>>> page(@RequestBody MallOrderLogQuery query) {
        return ResultVo.success(mallOrderLogService.findPage(query));
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:mall:orderLog:page')")
    public ResultVo<MallOrderLogVo> get(@PathVariable Long id) {
        return ResultVo.success(mallOrderLogService.findById(id));
    }

    @GetMapping("/list")
    @PreAuthorize("@ps.hasPermission('admin:mall:orderLog:page')")
    public ResultVo<List<MallOrderLogVo>> list() {
        return ResultVo.success(mallOrderLogService.findAll());
    }
}
