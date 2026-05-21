package com.fastproject.module.ratelimit.controller;

import com.fastproject.idempotent.annotation.Idempotent;
import com.fastproject.logs.annotation.Log;
import com.fastproject.logs.enums.LogAction;
import com.fastproject.logs.enums.LogType;
import com.fastproject.ratelimit.service.IpRateLimitConfigService;
import com.fastproject.ratelimit.vo.ip.IpRateLimitConfigCreate;
import com.fastproject.ratelimit.vo.ip.IpRateLimitConfigUpdate;
import com.fastproject.ratelimit.vo.ip.IpRateLimitConfigQuery;
import com.fastproject.ratelimit.vo.ip.IpRateLimitConfigVo;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.utils.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * IP限流配置 Controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/ratelimit/ip-config")
public class IpRateLimitConfigController {

    private final IpRateLimitConfigService ipRateLimitConfigService;

    /**
     * 添加
     */
    @PostMapping
    @PreAuthorize("@ps.hasPermission('admin:ratelimit:ip-config:add')")
    @Log(value = "添加IP限流配置", type = LogType.BUSINESS, action = LogAction.CREATE)
    @Idempotent(prefix = "add:ratelimit:ip-config:", expireTime = 120, title = "添加IP限流配置")
    public ResultVo<Object> add(@RequestBody IpRateLimitConfigCreate create) {
        return ResultVo.success(ipRateLimitConfigService.save(create));
    }

    /**
     * 修改
     */
    @PutMapping
    @PreAuthorize("@ps.hasPermission('admin:ratelimit:ip-config:update')")
    @Log(value = "修改IP限流配置", type = LogType.BUSINESS, action = LogAction.UPDATE)
    @Idempotent(prefix = "update:ratelimit:ip-config:", expireTime = 120, title = "修改IP限流配置")
    public ResultVo<Object> update(@RequestBody IpRateLimitConfigUpdate update) {
        ipRateLimitConfigService.update(update);
        return ResultVo.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:ratelimit:ip-config:delete')")
    @Log(value = "删除IP限流配置", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> delete(@PathVariable Long id) {
        ipRateLimitConfigService.delete(id);
        return ResultVo.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/batch")
    @PreAuthorize("@ps.hasPermission('admin:ratelimit:ip-config:delete')")
    @Log(value = "批量删除IP限流配置", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> batchDelete(@RequestBody List<Long> ids) {
        ipRateLimitConfigService.batchDelete(ids);
        return ResultVo.success();
    }

    /**
     * 分页
     */
    @PostMapping("/page")
    @PreAuthorize("@ps.hasPermission('admin:ratelimit:ip-config:page')")
    public ResultVo<PageVo<List<IpRateLimitConfigVo>>> page(@RequestBody IpRateLimitConfigQuery query) {
        return ResultVo.success(ipRateLimitConfigService.findPage(query));
    }

    /**
     * 详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:ratelimit:ip-config:page')")
    public ResultVo<IpRateLimitConfigVo> get(@PathVariable Long id) {
        return ResultVo.success(ipRateLimitConfigService.findById(id));
    }

    /**
     * 根据IP地址查询
     */
    @GetMapping("/ip/{ipAddress}")
    @PreAuthorize("@ps.hasPermission('admin:ratelimit:ip-config:page')")
    public ResultVo<IpRateLimitConfigVo> getByIpAddress(@PathVariable String ipAddress) {
        return ResultVo.success(ipRateLimitConfigService.findByIpAddress(ipAddress));
    }
}