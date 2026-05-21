package com.fastproject.module.page.controller;

import com.fastproject.idempotent.annotation.Idempotent;
import com.fastproject.logs.annotation.Log;
import com.fastproject.logs.enums.LogAction;
import com.fastproject.logs.enums.LogType;
import com.fastproject.page.service.PageWebConfigService;
import com.fastproject.page.vo.pagewebconfig.PageWebConfigCreate;
import com.fastproject.page.vo.pagewebconfig.PageWebConfigQuery;
import com.fastproject.page.vo.pagewebconfig.PageWebConfigUpdate;
import com.fastproject.page.vo.pagewebconfig.PageWebConfigVo;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.utils.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 页面 Web 配置（发布记录）Controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/page/web/config")
public class PageWebConfigController {

    private final PageWebConfigService pageWebConfigService;

    /**
     * 分页查询
     */
    @PostMapping("/page")
    @PreAuthorize("@ps.hasPermission('admin:page:application:config')")
    public ResultVo<PageVo<List<PageWebConfigVo>>> page(@RequestBody PageWebConfigQuery query) {
        return ResultVo.success(pageWebConfigService.findPage(query));
    }

    /**
     * 详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:page:application:config')")
    public ResultVo<PageWebConfigVo> findById(@PathVariable Long id) {
        return ResultVo.success(pageWebConfigService.findById(id));
    }

    /**
     * 查询全部列表
     */
    @GetMapping("/list")
    @PreAuthorize("@ps.hasPermission('admin:page:application:config')")
    public ResultVo<List<PageWebConfigVo>> list() {
        return ResultVo.success(pageWebConfigService.findAll());
    }

    /**
     * 查询可选项
     */
    @GetMapping("/selectAll")
    @PreAuthorize("@ps.hasPermission('admin:page:application:config')")
    public ResultVo<List<PageWebConfigVo>> selectAll() {
        return ResultVo.success(pageWebConfigService.selectAll());
    }

    /**
     * 新增
     */
    @PostMapping
    @PreAuthorize("@ps.hasPermission('admin:page:application:config')")
    @Idempotent
    @Log(value = "新增页面Web配置", type = LogType.BUSINESS, action = LogAction.CREATE)
    public ResultVo<Object> save(@RequestBody PageWebConfigCreate create) {
        return ResultVo.success(pageWebConfigService.save(create));
    }

    /**
     * 修改
     */
    @PutMapping
    @PreAuthorize("@ps.hasPermission('admin:page:application:config')")
    @Idempotent
    @Log(value = "修改页面Web配置", type = LogType.BUSINESS, action = LogAction.UPDATE)
    public ResultVo<Object> update(@RequestBody PageWebConfigUpdate update) {
        pageWebConfigService.update(update);
        return ResultVo.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:page:application:config')")
    @Log(value = "删除页面Web配置", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> delete(@PathVariable Long id) {
        pageWebConfigService.delete(id);
        return ResultVo.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/batch")
    @PreAuthorize("@ps.hasPermission('admin:page:application:config')")
    @Log(value = "批量删除页面Web配置", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> batchDelete(@RequestBody List<Long> ids) {
        pageWebConfigService.batchDelete(ids);
        return ResultVo.success();
    }
}
