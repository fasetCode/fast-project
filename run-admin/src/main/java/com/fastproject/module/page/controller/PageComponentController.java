package com.fastproject.module.page.controller;

import com.fastproject.idempotent.annotation.Idempotent;
import com.fastproject.logs.annotation.Log;
import com.fastproject.logs.enums.LogAction;
import com.fastproject.logs.enums.LogType;
import com.fastproject.page.service.PageComponentService;
import com.fastproject.page.vo.pagecomponent.PageComponentUpdate;
import com.fastproject.page.vo.pagecomponent.PageComponentCreate;
import com.fastproject.page.vo.pagecomponent.PageComponentQuery;
import com.fastproject.page.vo.pagecomponent.PageComponentVo;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.utils.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 页面组件 Controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/page/component")
public class PageComponentController {

    private final PageComponentService pageComponentService;

    /**
     * 添加
     */
    @PostMapping
    @PreAuthorize("@ps.hasPermission('admin:page:component:add')")
    @Log(value = "添加页面组件", type = LogType.BUSINESS, action = LogAction.CREATE)
    @Idempotent(prefix = "add:page:component:", expireTime = 120, title = "添加页面组件")
    public ResultVo<Object> add(@RequestBody PageComponentCreate pageComponentCreate) {
        return ResultVo.success(pageComponentService.save(pageComponentCreate));
    }

    /**
     * 修改
     */
    @PutMapping
    @PreAuthorize("@ps.hasPermission('admin:page:component:update')")
    @Log(value = "修改页面组件", type = LogType.BUSINESS, action = LogAction.UPDATE)
    @Idempotent(prefix = "update:page:component:", expireTime = 120, title = "修改页面组件")
    public ResultVo<Object> update(@RequestBody PageComponentUpdate pageComponentUpdate) {
        pageComponentService.update(pageComponentUpdate);
        return ResultVo.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:page:component:delete')")
    @Log(value = "删除页面组件", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> delete(@PathVariable Long id) {
        pageComponentService.delete(id);
        return ResultVo.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/batch")
    @PreAuthorize("@ps.hasPermission('admin:page:component:delete')")
    @Log(value = "批量删除页面组件", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> batchDelete(@RequestBody List<Long> ids) {
        pageComponentService.batchDelete(ids);
        return ResultVo.success();
    }

    /**
     * 分页
     */
    @PostMapping("/page")
    @PreAuthorize("@ps.hasPermission('admin:page:component:page')")
    public ResultVo<PageVo<List<PageComponentVo>>> page(@RequestBody PageComponentQuery query) {
        return ResultVo.success(pageComponentService.findPage(query));
    }

    /**
     * 详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:page:component:page')")
    public ResultVo<PageComponentVo> get(@PathVariable Long id) {
        return ResultVo.success(pageComponentService.findById(id));
    }

}
