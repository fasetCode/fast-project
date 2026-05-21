package com.fastproject.module.system.controller;

import com.fastproject.idempotent.annotation.Idempotent;
import com.fastproject.logs.annotation.Log;
import com.fastproject.logs.enums.LogAction;
import com.fastproject.logs.enums.LogType;
import com.fastproject.system.service.SysPostService;
import com.fastproject.system.vo.post.SysPostUpdate;
import com.fastproject.system.vo.post.SysPostCreate;
import com.fastproject.system.vo.post.SysPostQuery;
import com.fastproject.system.vo.post.SysPostVo;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.utils.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 岗位 Controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/sys/post")
public class SysPostController {

    private final SysPostService sysPostService;

    /**
     * 添加
     */
    @PostMapping
    @PreAuthorize("@ps.hasPermission('admin:system:post:add')")
    @Log(value = "添加岗位", type = LogType.BUSINESS, action = LogAction.CREATE)
    @Idempotent(prefix = "add:sys:post:", expireTime = 120, title = "添加岗位")
    public ResultVo<Object> add(@RequestBody SysPostCreate sysPostCreate) {
        return ResultVo.success(sysPostService.save(sysPostCreate));
    }

    /**
     * 修改
     */
    @PutMapping
    @PreAuthorize("@ps.hasPermission('admin:system:post:update')")
    @Log(value = "修改岗位", type = LogType.BUSINESS, action = LogAction.UPDATE)
    @Idempotent(prefix = "update:sys:post:", expireTime = 120, title = "修改岗位")
    public ResultVo<Object> update(@RequestBody SysPostUpdate sysPostUpdate) {
        sysPostService.update(sysPostUpdate);
        return ResultVo.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:system:post:delete')")
    @Log(value = "删除岗位", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> delete(@PathVariable Long id) {
        sysPostService.delete(id);
        return ResultVo.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/batch")
    @PreAuthorize("@ps.hasPermission('admin:system:post:delete')")
    @Log(value = "批量删除岗位", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> batchDelete(@RequestBody List<Long> ids) {
        sysPostService.batchDelete(ids);
        return ResultVo.success();
    }

    /**
     * 分页
     */
    @PostMapping("/page")
    @PreAuthorize("@ps.hasPermission('admin:system:post:page')")
    public ResultVo<PageVo<List<SysPostVo>>> page(@RequestBody SysPostQuery query) {
        return ResultVo.success(sysPostService.findPage(query));
    }

    /**
     * 详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:system:post:page')")
    public ResultVo<SysPostVo> get(@PathVariable Long id) {
        return ResultVo.success(sysPostService.findById(id));
    }

    /**
     * 查询所有岗位(列表)
     */
    @GetMapping("/list")
    @PreAuthorize("@ps.hasPermission('admin:system:post:page')")
    public ResultVo<List<SysPostVo>> list() {
        return ResultVo.success(sysPostService.findAll());
    }

    /**
     * 查询所有正常状态的岗位，用于选择框
     */
    @GetMapping("/selectAll")
    public ResultVo<List<SysPostVo>> selectAll() {
        return ResultVo.success(sysPostService.selectAll());
    }
}
