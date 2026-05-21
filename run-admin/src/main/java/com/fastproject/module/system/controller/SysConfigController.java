package com.fastproject.module.system.controller;

import com.fastproject.idempotent.annotation.Idempotent;
import com.fastproject.logs.annotation.Log;
import com.fastproject.logs.enums.LogAction;
import com.fastproject.logs.enums.LogType;
import com.fastproject.system.service.SysConfigService;
import com.fastproject.system.vo.config.SysConfigUpdate;
import com.fastproject.system.vo.config.SysConfigCreate;
import com.fastproject.system.vo.config.SysConfigQuery;
import com.fastproject.system.vo.config.SysConfigVo;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.utils.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统配置 Controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/sys/config")
public class SysConfigController {

    private final SysConfigService sysConfigService;

    /**
     * 添加
     */
    @PostMapping
    @PreAuthorize("@ps.hasPermission('admin:system:config:add')")
    @Log(value = "添加系统配置", type = LogType.BUSINESS, action = LogAction.CREATE)
    @Idempotent(prefix = "add:sys:config:", expireTime = 120, title = "添加系统配置")
    public ResultVo<Object> add(@RequestBody SysConfigCreate sysConfigCreate) {
        return ResultVo.success(sysConfigService.save(sysConfigCreate));
    }

    /**
     * 修改
     */
    @PutMapping
    @PreAuthorize("@ps.hasPermission('admin:system:config:update')")
    @Log(value = "修改系统配置", type = LogType.BUSINESS, action = LogAction.UPDATE)
    @Idempotent(prefix = "update:sys:config:", expireTime = 120, title = "修改系统配置")
    public ResultVo<Object> update(@RequestBody SysConfigUpdate sysConfigUpdate) {
        sysConfigService.update(sysConfigUpdate);
        return ResultVo.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:system:config:delete')")
    @Log(value = "删除系统配置", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> delete(@PathVariable Long id) {
        sysConfigService.delete(id);
        return ResultVo.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/batch")
    @PreAuthorize("@ps.hasPermission('admin:system:config:delete')")
    @Log(value = "批量删除系统配置", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> batchDelete(@RequestBody List<Long> ids) {
        sysConfigService.batchDelete(ids);
        return ResultVo.success();
    }

    /**
     * 分页
     */
    @PostMapping("/page")
    @PreAuthorize("@ps.hasPermission('admin:system:config:page')")
    public ResultVo<PageVo<List<SysConfigVo>>> page(@RequestBody SysConfigQuery query) {
        return ResultVo.success(sysConfigService.findPage(query));
    }

    /**
     * 详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:system:config:page')")
    public ResultVo<SysConfigVo> get(@PathVariable Long id) {
        return ResultVo.success(sysConfigService.findById(id));
    }

}
