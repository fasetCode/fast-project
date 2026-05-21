package com.fastproject.module.ratelimit.controller;

import com.fastproject.idempotent.annotation.Idempotent;
import com.fastproject.logs.annotation.Log;
import com.fastproject.logs.enums.LogAction;
import com.fastproject.logs.enums.LogType;
import com.fastproject.ratelimit.service.ApiRateLimitConfigService;
import com.fastproject.ratelimit.vo.api.ApiRateLimitConfigCreate;
import com.fastproject.ratelimit.vo.api.ApiRateLimitConfigUpdate;
import com.fastproject.ratelimit.vo.api.ApiRateLimitConfigQuery;
import com.fastproject.ratelimit.vo.api.ApiRateLimitConfigVo;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.utils.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * API限流配置 Controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/ratelimit/api-config")
public class ApiRateLimitConfigController {

    private final ApiRateLimitConfigService apiRateLimitConfigService;

    /**
     * 添加
     */
    @PostMapping
    @PreAuthorize("@ps.hasPermission('admin:ratelimit:api-config:add')")
    @Log(value = "添加API限流配置", type = LogType.BUSINESS, action = LogAction.CREATE)
    @Idempotent(prefix = "add:ratelimit:api-config:", expireTime = 120, title = "添加API限流配置")
    public ResultVo<Object> add(@RequestBody ApiRateLimitConfigCreate create) {
        return ResultVo.success(apiRateLimitConfigService.save(create));
    }

    /**
     * 修改
     */
    @PutMapping
    @PreAuthorize("@ps.hasPermission('admin:ratelimit:api-config:update')")
    @Log(value = "修改API限流配置", type = LogType.BUSINESS, action = LogAction.UPDATE)
    @Idempotent(prefix = "update:ratelimit:api-config:", expireTime = 120, title = "修改API限流配置")
    public ResultVo<Object> update(@RequestBody ApiRateLimitConfigUpdate update) {
        apiRateLimitConfigService.update(update);
        return ResultVo.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:ratelimit:api-config:delete')")
    @Log(value = "删除API限流配置", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> delete(@PathVariable Long id) {
        apiRateLimitConfigService.delete(id);
        return ResultVo.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/batch")
    @PreAuthorize("@ps.hasPermission('admin:ratelimit:api-config:delete')")
    @Log(value = "批量删除API限流配置", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> batchDelete(@RequestBody List<Long> ids) {
        apiRateLimitConfigService.batchDelete(ids);
        return ResultVo.success();
    }

    /**
     * 分页
     */
    @PostMapping("/page")
    @PreAuthorize("@ps.hasPermission('admin:ratelimit:api-config:page')")
    public ResultVo<PageVo<List<ApiRateLimitConfigVo>>> page(@RequestBody ApiRateLimitConfigQuery query) {
        return ResultVo.success(apiRateLimitConfigService.findPage(query));
    }

    /**
     * 详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:ratelimit:api-config:page')")
    public ResultVo<ApiRateLimitConfigVo> get(@PathVariable Long id) {
        return ResultVo.success(apiRateLimitConfigService.findById(id));
    }
}