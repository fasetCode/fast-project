package com.fastproject.module.mall.controller;

import com.fastproject.idempotent.annotation.Idempotent;
import com.fastproject.logs.annotation.Log;
import com.fastproject.logs.enums.LogAction;
import com.fastproject.logs.enums.LogType;
import com.fastproject.mall.service.MallFreightTemplateService;
import com.fastproject.mall.vo.freighttemplate.MallFreightTemplateCreate;
import com.fastproject.mall.vo.freighttemplate.MallFreightTemplateQuery;
import com.fastproject.mall.vo.freighttemplate.MallFreightTemplateUpdate;
import com.fastproject.mall.vo.freighttemplate.MallFreightTemplateVo;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.utils.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 运费模板 Controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/mall/freightTemplate")
public class MallFreightTemplateController {

    private final MallFreightTemplateService mallFreightTemplateService;

    @PostMapping
    @PreAuthorize("@ps.hasPermission('admin:mall:freightTemplate:add')")
    @Log(value = "添加运费模板", type = LogType.BUSINESS, action = LogAction.CREATE)
    @Idempotent(prefix = "add:mall:freightTemplate:", expireTime = 120, title = "添加运费模板")
    public ResultVo<Object> add(@RequestBody MallFreightTemplateCreate create) {
        return ResultVo.success(mallFreightTemplateService.save(create));
    }

    @PutMapping
    @PreAuthorize("@ps.hasPermission('admin:mall:freightTemplate:update')")
    @Log(value = "修改运费模板", type = LogType.BUSINESS, action = LogAction.UPDATE)
    @Idempotent(prefix = "update:mall:freightTemplate:", expireTime = 120, title = "修改运费模板")
    public ResultVo<Object> update(@RequestBody MallFreightTemplateUpdate update) {
        mallFreightTemplateService.update(update);
        return ResultVo.success();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:mall:freightTemplate:delete')")
    @Log(value = "删除运费模板", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> delete(@PathVariable Long id) {
        mallFreightTemplateService.delete(id);
        return ResultVo.success();
    }

    @DeleteMapping("/batch")
    @PreAuthorize("@ps.hasPermission('admin:mall:freightTemplate:delete')")
    @Log(value = "批量删除运费模板", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> batchDelete(@RequestBody List<Long> ids) {
        mallFreightTemplateService.batchDelete(ids);
        return ResultVo.success();
    }

    @PostMapping("/page")
    @PreAuthorize("@ps.hasPermission('admin:mall:freightTemplate:page')")
    public ResultVo<PageVo<List<MallFreightTemplateVo>>> page(@RequestBody MallFreightTemplateQuery query) {
        return ResultVo.success(mallFreightTemplateService.findPage(query));
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:mall:freightTemplate:page')")
    public ResultVo<MallFreightTemplateVo> get(@PathVariable Long id) {
        return ResultVo.success(mallFreightTemplateService.findById(id));
    }

    @GetMapping("/list")
    @PreAuthorize("@ps.hasPermission('admin:mall:freightTemplate:page')")
    public ResultVo<List<MallFreightTemplateVo>> list() {
        return ResultVo.success(mallFreightTemplateService.findAll());
    }

    @GetMapping("/selectAll")
    public ResultVo<List<MallFreightTemplateVo>> selectAll() {
        return ResultVo.success(mallFreightTemplateService.selectAll());
    }
}
