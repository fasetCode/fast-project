package com.fastproject.module.page.controller;

import com.fastproject.idempotent.annotation.Idempotent;
import com.fastproject.logs.annotation.Log;
import com.fastproject.logs.enums.LogAction;
import com.fastproject.logs.enums.LogType;
import com.fastproject.page.service.PageApplicationService;
import com.fastproject.page.vo.pageapplication.PageApplicationUpdate;
import com.fastproject.page.vo.pageapplication.PageApplicationCreate;
import com.fastproject.page.vo.pageapplication.PageApplicationQuery;
import com.fastproject.page.vo.pageapplication.PageApplicationVo;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.utils.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 页面应用 Controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/page/application")
public class PageApplicationController {

    private final PageApplicationService pageApplicationService;

    /**
     * 添加
     */
    @PostMapping
    @PreAuthorize("@ps.hasPermission('admin:page:application:add')")
    @Log(value = "添加页面应用", type = LogType.BUSINESS, action = LogAction.CREATE)
    @Idempotent(prefix = "add:page:application:", expireTime = 120, title = "添加页面应用")
    public ResultVo<Object> add(@RequestBody PageApplicationCreate pageApplicationCreate) {
        return ResultVo.success(pageApplicationService.save(pageApplicationCreate));
    }

    /**
     * 修改
     */
    @PutMapping
    @PreAuthorize("@ps.hasPermission('admin:page:application:update')")
    @Log(value = "修改页面应用", type = LogType.BUSINESS, action = LogAction.UPDATE)
    @Idempotent(prefix = "update:page:application:", expireTime = 120, title = "修改页面应用")
    public ResultVo<Object> update(@RequestBody PageApplicationUpdate pageApplicationUpdate) {
        pageApplicationService.update(pageApplicationUpdate);
        return ResultVo.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:page:application:delete')")
    @Log(value = "删除页面应用", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> delete(@PathVariable Long id) {
        pageApplicationService.delete(id);
        return ResultVo.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/batch")
    @PreAuthorize("@ps.hasPermission('admin:page:application:delete')")
    @Log(value = "批量删除页面应用", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> batchDelete(@RequestBody List<Long> ids) {
        pageApplicationService.batchDelete(ids);
        return ResultVo.success();
    }

    /**
     * 分页
     */
    @PostMapping("/page")
    @PreAuthorize("@ps.hasPermission('admin:page:application:page')")
    public ResultVo<PageVo<List<PageApplicationVo>>> page(@RequestBody PageApplicationQuery query) {
        return ResultVo.success(pageApplicationService.findPage(query));
    }

    /**
     * 详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:page:application:page')")
    public ResultVo<PageApplicationVo> get(@PathVariable Long id) {
        return ResultVo.success(pageApplicationService.findById(id));
    }

}
