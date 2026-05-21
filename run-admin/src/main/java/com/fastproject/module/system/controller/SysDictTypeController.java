package com.fastproject.module.system.controller;

import com.fastproject.idempotent.annotation.Idempotent;
import com.fastproject.logs.annotation.Log;
import com.fastproject.logs.enums.LogAction;
import com.fastproject.logs.enums.LogType;
import com.fastproject.system.service.SysDictTypeService;
import com.fastproject.system.vo.dicttype.SysDictTypeUpdate;
import com.fastproject.system.vo.dicttype.SysDictTypeCreate;
import com.fastproject.system.vo.dicttype.SysDictTypeQuery;
import com.fastproject.system.vo.dicttype.SysDictTypeVo;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.utils.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 字典类型 Controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/sys/dict/type")
public class SysDictTypeController {

    private final SysDictTypeService sysDictTypeService;

    /**
     * 添加
     */
    @PostMapping
    @PreAuthorize("@ps.hasPermission('admin:system:dict:type:add')")
    @Log(value = "添加字典类型", type = LogType.BUSINESS, action = LogAction.CREATE)
    @Idempotent(prefix = "add:sys:dict:type:", expireTime = 120, title = "添加字典类型")
    public ResultVo<Object> add(@RequestBody SysDictTypeCreate sysDictTypeCreate) {
        return ResultVo.success(sysDictTypeService.save(sysDictTypeCreate));
    }

    /**
     * 修改
     */
    @PutMapping
    @PreAuthorize("@ps.hasPermission('admin:system:dict:type:update')")
    @Log(value = "修改字典类型", type = LogType.BUSINESS, action = LogAction.UPDATE)
    @Idempotent(prefix = "update:sys:dict:type:", expireTime = 120, title = "修改字典类型")
    public ResultVo<Object> update(@RequestBody SysDictTypeUpdate sysDictTypeUpdate) {
        sysDictTypeService.update(sysDictTypeUpdate);
        return ResultVo.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:system:dict:type:delete')")
    @Log(value = "删除字典类型", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> delete(@PathVariable Long id) {
        sysDictTypeService.delete(id);
        return ResultVo.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/batch")
    @PreAuthorize("@ps.hasPermission('admin:system:dict:type:delete')")
    @Log(value = "批量删除字典类型", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> batchDelete(@RequestBody List<Long> ids) {
        sysDictTypeService.batchDelete(ids);
        return ResultVo.success();
    }

    /**
     * 分页
     */
    @PostMapping("/page")
    @PreAuthorize("@ps.hasPermission('admin:system:dict:type:page')")
    public ResultVo<PageVo<List<SysDictTypeVo>>> page(@RequestBody SysDictTypeQuery query) {
        return ResultVo.success(sysDictTypeService.findPage(query));
    }

    /**
     * 详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:system:dict:type:page')")
    public ResultVo<SysDictTypeVo> get(@PathVariable Long id) {
        return ResultVo.success(sysDictTypeService.findById(id));
    }

    /**
     * 查询所有字典类型
     */
    @GetMapping("/list")
    @PreAuthorize("@ps.hasPermission('admin:system:dict:type:page')")
    public ResultVo<List<SysDictTypeVo>> list() {
        return ResultVo.success(sysDictTypeService.findAll());
    }

    /**
     * 查询所有字典类型（下拉选择用）
     */
    @GetMapping("/selectAll")
    public ResultVo<List<SysDictTypeVo>> selectAll() {
        return ResultVo.success(sysDictTypeService.selectAll());
    }
}
