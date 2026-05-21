package com.fastproject.module.page.controller;

import com.fastproject.idempotent.annotation.Idempotent;
import com.fastproject.logs.annotation.Log;
import com.fastproject.logs.enums.LogAction;
import com.fastproject.logs.enums.LogType;
import com.fastproject.page.service.PageTypeService;
import com.fastproject.page.vo.pagetype.PageTypeUpdate;
import com.fastproject.page.vo.pagetype.PageTypeCreate;
import com.fastproject.page.vo.pagetype.PageTypeQuery;
import com.fastproject.page.vo.pagetype.PageTypeVo;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.utils.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 页面类型 Controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/page/type")
public class PageTypeController {

    private final PageTypeService pageTypeService;

    /**
     * 添加
     */
    @PostMapping
    @PreAuthorize("@ps.hasPermission('admin:page:type:add')")
    @Log(value = "添加页面类型", type = LogType.BUSINESS, action = LogAction.CREATE)
    @Idempotent(prefix = "add:page:type:", expireTime = 120, title = "添加页面类型")
    public ResultVo<Object> add(@RequestBody PageTypeCreate pageTypeCreate) {
        return ResultVo.success(pageTypeService.save(pageTypeCreate));
    }

    /**
     * 修改
     */
    @PutMapping
    @PreAuthorize("@ps.hasPermission('admin:page:type:update')")
    @Log(value = "修改页面类型", type = LogType.BUSINESS, action = LogAction.UPDATE)
    @Idempotent(prefix = "update:page:type:", expireTime = 120, title = "修改页面类型")
    public ResultVo<Object> update(@RequestBody PageTypeUpdate pageTypeUpdate) {
        pageTypeService.update(pageTypeUpdate);
        return ResultVo.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:page:type:delete')")
    @Log(value = "删除页面类型", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> delete(@PathVariable Long id) {
        pageTypeService.delete(id);
        return ResultVo.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/batch")
    @PreAuthorize("@ps.hasPermission('admin:page:type:delete')")
    @Log(value = "批量删除页面类型", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> batchDelete(@RequestBody List<Long> ids) {
        pageTypeService.batchDelete(ids);
        return ResultVo.success();
    }

    /**
     * 分页
     */
    @PostMapping("/page")
    @PreAuthorize("@ps.hasPermission('admin:page:type:page')")
    public ResultVo<PageVo<List<PageTypeVo>>> page(@RequestBody PageTypeQuery query) {
        return ResultVo.success(pageTypeService.findPage(query));
    }

    /**
     * 详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:page:type:page')")
    public ResultVo<PageTypeVo> get(@PathVariable Long id) {
        return ResultVo.success(pageTypeService.findById(id));
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    @PreAuthorize("@ps.hasPermission('admin:page:component:list')")
    public ResultVo<List<PageTypeVo>> list() {
        return ResultVo.success(pageTypeService.findAll());
    }
}
