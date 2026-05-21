package com.fastproject.module.system.controller;

import com.fastproject.idempotent.annotation.Idempotent;
import com.fastproject.logs.annotation.Log;
import com.fastproject.logs.enums.LogAction;
import com.fastproject.logs.enums.LogType;
import com.fastproject.system.service.SysDepartmentService;
import com.fastproject.system.vo.department.SysDepartmentUpdate;
import com.fastproject.system.vo.department.SysDepartmentCreate;
import com.fastproject.system.vo.department.SysDepartmentQuery;
import com.fastproject.system.vo.department.SysDepartmentVo;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.utils.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门 Controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/sys/department")
public class SysDepartmentController {

    private final SysDepartmentService sysDepartmentService;

    /**
     * 添加
     */
    @PostMapping
    @PreAuthorize("@ps.hasPermission('admin:system:department:add')")
    @Log(value = "添加部门", type = LogType.BUSINESS, action = LogAction.CREATE)
    @Idempotent(prefix = "add:sys:department:", expireTime = 120, title = "添加部门")
    public ResultVo<Object> add(@RequestBody SysDepartmentCreate sysDepartmentCreate) {
        return ResultVo.success(sysDepartmentService.save(sysDepartmentCreate));
    }

    /**
     * 修改
     */
    @PutMapping
    @PreAuthorize("@ps.hasPermission('admin:system:department:update')")
    @Log(value = "修改部门", type = LogType.BUSINESS, action = LogAction.UPDATE)
    @Idempotent(prefix = "update:sys:department:", expireTime = 120, title = "修改部门")
    public ResultVo<Object> update(@RequestBody SysDepartmentUpdate sysDepartmentUpdate) {
        sysDepartmentService.update(sysDepartmentUpdate);
        return ResultVo.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:system:department:delete')")
    @Log(value = "删除部门", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> delete(@PathVariable Long id) {
        sysDepartmentService.delete(id);
        return ResultVo.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/batch")
    @PreAuthorize("@ps.hasPermission('admin:system:department:delete')")
    @Log(value = "批量删除部门", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> batchDelete(@RequestBody List<Long> ids) {
        sysDepartmentService.batchDelete(ids);
        return ResultVo.success();
    }

    /**
     * 分页
     */
    @PostMapping("/page")
    @PreAuthorize("@ps.hasPermission('admin:system:department:page')")
    public ResultVo<PageVo<List<SysDepartmentVo>>> page(@RequestBody SysDepartmentQuery query) {
        return ResultVo.success(sysDepartmentService.findPage(query));
    }

    /**
     * 详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:system:department:page')")
    public ResultVo<SysDepartmentVo> get(@PathVariable Long id) {
        return ResultVo.success(sysDepartmentService.findById(id));
    }

    /**
     * 查询所有部门(树形结构)
     */
    @GetMapping("/tree")
    @PreAuthorize("@ps.hasPermission('admin:system:department:page')")
    public ResultVo<List<SysDepartmentVo>> tree() {
        return ResultVo.success(sysDepartmentService.findTree());
    }

    /**
     * 查询所有正常状态的部门(树形结构)，用于选择框
     */
    @GetMapping("/selectAll")
    public ResultVo<List<SysDepartmentVo>> selectAll() {
        return ResultVo.success(sysDepartmentService.selectAll());
    }
}
