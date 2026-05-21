package com.fastproject.module.system.controller;

import com.fastproject.idempotent.annotation.Idempotent;
import com.fastproject.logs.annotation.Log;
import com.fastproject.logs.enums.LogAction;
import com.fastproject.logs.enums.LogType;
import com.fastproject.system.service.SysDictDataService;
import com.fastproject.system.vo.dictdata.SysDictDataUpdate;
import com.fastproject.system.vo.dictdata.SysDictDataCreate;
import com.fastproject.system.vo.dictdata.SysDictDataQuery;
import com.fastproject.system.vo.dictdata.SysDictDataVo;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.utils.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 字典数据 Controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/sys/dict/data")
public class SysDictDataController {

    private final SysDictDataService sysDictDataService;

    /**
     * 添加
     */
    @PostMapping
    @PreAuthorize("@ps.hasPermission('admin:system:dict:data:add')")
    @Log(value = "添加字典数据", type = LogType.BUSINESS, action = LogAction.CREATE)
    @Idempotent(prefix = "add:sys:dict:data:", expireTime = 120, title = "添加字典数据")
    public ResultVo<Object> add(@RequestBody SysDictDataCreate sysDictDataCreate) {
        return ResultVo.success(sysDictDataService.save(sysDictDataCreate));
    }

    /**
     * 修改
     */
    @PutMapping
    @PreAuthorize("@ps.hasPermission('admin:system:dict:data:update')")
    @Log(value = "修改字典数据", type = LogType.BUSINESS, action = LogAction.UPDATE)
    @Idempotent(prefix = "update:sys:dict:data:", expireTime = 120, title = "修改字典数据")
    public ResultVo<Object> update(@RequestBody SysDictDataUpdate sysDictDataUpdate) {
        sysDictDataService.update(sysDictDataUpdate);
        return ResultVo.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:system:dict:data:delete')")
    @Log(value = "删除字典数据", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> delete(@PathVariable Long id) {
        sysDictDataService.delete(id);
        return ResultVo.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/batch")
    @PreAuthorize("@ps.hasPermission('admin:system:dict:data:delete')")
    @Log(value = "批量删除字典数据", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> batchDelete(@RequestBody List<Long> ids) {
        sysDictDataService.batchDelete(ids);
        return ResultVo.success();
    }

    /**
     * 分页
     */
    @PostMapping("/page")
    @PreAuthorize("@ps.hasPermission('admin:system:dict:data:page')")
    public ResultVo<PageVo<List<SysDictDataVo>>> page(@RequestBody SysDictDataQuery query) {
        return ResultVo.success(sysDictDataService.findPage(query));
    }

    /**
     * 详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:system:dict:data:page')")
    public ResultVo<SysDictDataVo> get(@PathVariable Long id) {
        return ResultVo.success(sysDictDataService.findById(id));
    }

    /**
     * 根据字典类型ID查询字典数据
     */
    @GetMapping("/type/{dictTypeId}")
    @PreAuthorize("@ps.hasPermission('admin:system:dict:data:page')")
    public ResultVo<List<SysDictDataVo>> getByDictTypeId(@PathVariable Long dictTypeId) {
        return ResultVo.success(sysDictDataService.findByDictTypeId(dictTypeId));
    }
}
