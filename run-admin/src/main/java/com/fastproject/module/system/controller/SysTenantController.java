package com.fastproject.module.system.controller;

import com.fastproject.idempotent.annotation.Idempotent;
import com.fastproject.logs.annotation.Log;
import com.fastproject.logs.enums.LogAction;
import com.fastproject.logs.enums.LogType;
import com.fastproject.system.service.SysTenantService;
import com.fastproject.system.vo.tenant.SysTenantCreate;
import com.fastproject.system.vo.tenant.SysTenantQuery;
import com.fastproject.system.vo.tenant.SysTenantUpdate;
import com.fastproject.system.vo.tenant.SysTenantVo;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.utils.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 租户 Controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/sys/tenant")
public class SysTenantController {

    private final SysTenantService sysTenantService;

    /**
     * 添加
     */
    @PostMapping
    @PreAuthorize("@ps.hasPermission('admin:system:tenant:add')")
    @Log(value = "添加租户", type = LogType.BUSINESS, action = LogAction.CREATE)
    @Idempotent(prefix = "add:sys:tenant:", expireTime = 120, title = "添加租户")
    public ResultVo<Object> add(@RequestBody SysTenantCreate sysTenantCreate) {
        return ResultVo.success(sysTenantService.save(sysTenantCreate));
    }

    /**
     * 修改
     */
    @PutMapping
    @PreAuthorize("@ps.hasPermission('admin:system:tenant:update')")
    @Log(value = "修改租户", type = LogType.BUSINESS, action = LogAction.UPDATE)
    @Idempotent(prefix = "update:sys:tenant:", expireTime = 120, title = "修改租户")
    public ResultVo<Object> update(@RequestBody SysTenantUpdate sysTenantUpdate) {
        sysTenantService.update(sysTenantUpdate);
        return ResultVo.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:system:tenant:delete')")
    @Log(value = "删除租户", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> delete(@PathVariable Long id) {
        sysTenantService.delete(id);
        return ResultVo.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/batch")
    @PreAuthorize("@ps.hasPermission('admin:system:tenant:delete')")
    @Log(value = "批量删除租户", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> batchDelete(@RequestBody List<Long> ids) {
        sysTenantService.batchDelete(ids);
        return ResultVo.success();
    }

    /**
     * 分页
     */
    @PostMapping("/page")
    @PreAuthorize("@ps.hasPermission('admin:system:tenant:page')")
    public ResultVo<PageVo<List<SysTenantVo>>> page(@RequestBody SysTenantQuery query) {
        return ResultVo.success(sysTenantService.findPage(query));
    }

    /**
     * 详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:system:tenant:page')")
    public ResultVo<SysTenantVo> get(@PathVariable Long id) {
        return ResultVo.success(sysTenantService.findById(id));
    }
}
