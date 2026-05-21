package com.fastproject.module.ratelimit.controller;

import com.fastproject.idempotent.annotation.Idempotent;
import com.fastproject.logs.annotation.Log;
import com.fastproject.logs.enums.LogAction;
import com.fastproject.logs.enums.LogType;
import com.fastproject.ratelimit.service.GlobalRateLimitConfigService;
import com.fastproject.ratelimit.vo.global.GlobalRateLimitConfigCreate;
import com.fastproject.ratelimit.vo.global.GlobalRateLimitConfigUpdate;
import com.fastproject.ratelimit.vo.global.GlobalRateLimitConfigQuery;
import com.fastproject.ratelimit.vo.global.GlobalRateLimitConfigVo;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.utils.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 全局限流配置 Controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/ratelimit/global-config")
public class GlobalRateLimitConfigController {

    private final GlobalRateLimitConfigService globalRateLimitConfigService;

    /**
     * 添加
     */
    @PostMapping
    @PreAuthorize("@ps.hasPermission('admin:ratelimit:global-config:add')")
    @Log(value = "添加全局限流配置", type = LogType.BUSINESS, action = LogAction.CREATE)
    @Idempotent(prefix = "add:ratelimit:global-config:", expireTime = 120, title = "添加全局限流配置")
    public ResultVo<Object> add(@RequestBody GlobalRateLimitConfigCreate create) {
        return ResultVo.success(globalRateLimitConfigService.save(create));
    }

    /**
     * 修改
     */
    @PutMapping
    @PreAuthorize("@ps.hasPermission('admin:ratelimit:global-config:update')")
    @Log(value = "修改全局限流配置", type = LogType.BUSINESS, action = LogAction.UPDATE)
    @Idempotent(prefix = "update:ratelimit:global-config:", expireTime = 120, title = "修改全局限流配置")
    public ResultVo<Object> update(@RequestBody GlobalRateLimitConfigUpdate update) {
        globalRateLimitConfigService.update(update);
        return ResultVo.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:ratelimit:global-config:delete')")
    @Log(value = "删除全局限流配置", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> delete(@PathVariable Long id) {
        globalRateLimitConfigService.delete(id);
        return ResultVo.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/batch")
    @PreAuthorize("@ps.hasPermission('admin:ratelimit:global-config:delete')")
    @Log(value = "批量删除全局限流配置", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> batchDelete(@RequestBody List<Long> ids) {
        globalRateLimitConfigService.batchDelete(ids);
        return ResultVo.success();
    }

    /**
     * 分页
     */
    @PostMapping("/page")
    @PreAuthorize("@ps.hasPermission('admin:ratelimit:global-config:page')")
    public ResultVo<PageVo<List<GlobalRateLimitConfigVo>>> page(@RequestBody GlobalRateLimitConfigQuery query) {
        return ResultVo.success(globalRateLimitConfigService.findPage(query));
    }

    /**
     * 详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:ratelimit:global-config:page')")
    public ResultVo<GlobalRateLimitConfigVo> get(@PathVariable Long id) {
        return ResultVo.success(globalRateLimitConfigService.findById(id));
    }

    /**
     * 获取启用的配置
     */
    @GetMapping("/enabled")
    @PreAuthorize("@ps.hasPermission('admin:ratelimit:global-config:page')")
    public ResultVo<GlobalRateLimitConfigVo> getEnabled() {
        return ResultVo.success(globalRateLimitConfigService.findEnabledConfig());
    }
}