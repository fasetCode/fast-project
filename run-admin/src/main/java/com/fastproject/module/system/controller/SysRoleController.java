package com.fastproject.module.system.controller;

import com.fastproject.idempotent.annotation.Idempotent;
import com.fastproject.logs.annotation.Log;
import com.fastproject.logs.enums.LogAction;
import com.fastproject.logs.enums.LogType;
import com.fastproject.system.service.SysRoleService;
import com.fastproject.system.vo.role.SysRoleUpdate;
import com.fastproject.system.vo.role.SysRoleCreate;
import com.fastproject.system.vo.role.SysRoleQuery;
import com.fastproject.system.vo.role.SysRoleVo;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.utils.vo.ResultVo;
import com.fastproject.utils.vo.SelectVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/sys/role")
public class SysRoleController {
    private final SysRoleService sysRoleService;

    /**
     * 查询列表(用于选择框)
     */
    @GetMapping("/selectAll")
    @PreAuthorize("@ps.hasPermission('admin:system:role:page')")
    public ResultVo<List<SelectVo>> selectAll() {
        return ResultVo.success(sysRoleService.selectAll());
    }

    /**
     * 添加
     */
    @PostMapping
    @PreAuthorize("@ps.hasPermission('admin:system:role:add')")
    @Log(value = "添加角色", type = LogType.BUSINESS, action = LogAction.CREATE)
    @Idempotent(prefix = "add:sys:role:", expireTime = 120, title = "添加角色")
    public ResultVo<Object> add(@RequestBody SysRoleCreate sysRoleCreate) {
        return ResultVo.success(sysRoleService.save(sysRoleCreate));
    }

    /**
     * 修改
     */
    @PutMapping
    @PreAuthorize("@ps.hasPermission('admin:system:role:update')")
    @Log(value = "修改角色", type = LogType.BUSINESS, action = LogAction.UPDATE)
    @Idempotent(prefix = "update:sys:role:", expireTime = 120, title = "修改角色")
    public ResultVo<Object> update(@RequestBody SysRoleUpdate sysRoleUpdate) {
        sysRoleService.update(sysRoleUpdate);
        return ResultVo.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:system:role:delete')")
    @Log(value = "删除角色", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> delete(@PathVariable Long id) {
        sysRoleService.delete(id);
        return ResultVo.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/batch")
    @PreAuthorize("@ps.hasPermission('admin:system:role:delete')")
    @Log(value = "批量删除角色", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> batchDelete(@RequestBody List<Long> ids) {
        sysRoleService.batchDelete(ids);
        return ResultVo.success();
    }

    /**
     * 分页
     */
    @PostMapping("/page")
    @PreAuthorize("@ps.hasPermission('admin:system:role:page')")
    public ResultVo<PageVo<List<SysRoleVo>>> page(@RequestBody SysRoleQuery query) {
        return ResultVo.success(sysRoleService.findPage(query));
    }

    /**
     * 详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:system:role:page')")
    public ResultVo<SysRoleVo> get(@PathVariable Long id) {
        return ResultVo.success(sysRoleService.findById(id));
    }
}
