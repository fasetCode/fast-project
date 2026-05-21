package com.fastproject.module.system.controller;

import com.fastproject.idempotent.annotation.Idempotent;
import com.fastproject.logs.annotation.Log;
import com.fastproject.logs.enums.LogAction;
import com.fastproject.logs.enums.LogType;
import com.fastproject.system.service.SysPermissionsService;
import com.fastproject.system.vo.permissions.SysPermissionsUpdate;
import com.fastproject.system.vo.permissions.SysPermissionsCreate;
import com.fastproject.system.vo.permissions.SysPermissionsQuery;
import com.fastproject.system.vo.permissions.SysPermissionsVo;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.utils.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/sys/permissions")
public class SysPermissionsController {
    private final SysPermissionsService sysPermissionsService;

    /**
     * 添加
     */
    @PostMapping
    @PreAuthorize("@ps.hasPermission('admin:system:permissions:add')")
    @Log(value = "添加权限", type = LogType.BUSINESS, action = LogAction.CREATE)
    @Idempotent(prefix = "add:sys:permissions:", expireTime = 120, title = "添加权限")
    public ResultVo<Object> add(@RequestBody SysPermissionsCreate sysPermissionsCreate) {
        return ResultVo.success(sysPermissionsService.save(sysPermissionsCreate));
    }

    /**
     * 修改
     */
    @PutMapping
    @PreAuthorize("@ps.hasPermission('admin:system:permissions:update')")
    @Log(value = "修改权限", type = LogType.BUSINESS, action = LogAction.UPDATE)
    @Idempotent(prefix = "update:sys:permissions:", expireTime = 120, title = "修改权限")
    public ResultVo<Object> update(@RequestBody SysPermissionsUpdate sysPermissionsUpdate) {
        sysPermissionsService.update(sysPermissionsUpdate);
        return ResultVo.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:system:permissions:delete')")
    @Log(value = "删除权限", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> delete(@PathVariable Long id) {
        sysPermissionsService.delete(id);
        return ResultVo.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/batch")
    @PreAuthorize("@ps.hasPermission('admin:system:permissions:delete')")
    @Log(value = "批量删除权限", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> batchDelete(@RequestBody List<Long> ids) {
        sysPermissionsService.batchDelete(ids);
        return ResultVo.success();
    }

    /**
     * 分页
     */
    @PostMapping("/page")
    @PreAuthorize("@ps.hasPermission('admin:system:permissions:page')")
    public ResultVo<PageVo<List<SysPermissionsVo>>> page(@RequestBody SysPermissionsQuery query) {
        return ResultVo.success(sysPermissionsService.findPage(query));
    }

    /**
     * 详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:system:permissions:page')")
    public ResultVo<SysPermissionsVo> get(@PathVariable Long id) {
        return ResultVo.success(sysPermissionsService.findById(id));
    }

    /**
     * 查询所有权限(树形结构)
     */
    @GetMapping("/tree")
    @PreAuthorize("@ps.hasPermission('admin:system:permissions:page')")
    public ResultVo<List<SysPermissionsVo>> tree() {
        return ResultVo.success(sysPermissionsService.findTree());
    }
}
