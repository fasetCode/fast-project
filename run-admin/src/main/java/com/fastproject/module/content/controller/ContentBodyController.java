package com.fastproject.module.content.controller;

import com.fastproject.content.domain.ContentBody;
import com.fastproject.content.service.ContentBodyService;
import com.fastproject.db.PageQuery;
import com.fastproject.idempotent.annotation.Idempotent;
import com.fastproject.logs.annotation.Log;
import com.fastproject.logs.enums.LogAction;
import com.fastproject.logs.enums.LogType;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.utils.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/content/body")
public class ContentBodyController {

    private final ContentBodyService contentBodyService;

    @PostMapping
    @PreAuthorize("@ps.hasPermission('admin:content:body:add')")
    @Log(value = "添加内容正文", type = LogType.BUSINESS, action = LogAction.CREATE)
    @Idempotent(prefix = "add:content:body:", expireTime = 120, title = "添加内容正文")
    public ResultVo<Long> add(@RequestBody ContentBody contentBody) {
        return ResultVo.success(contentBodyService.save(contentBody));
    }

    @PutMapping
    @PreAuthorize("@ps.hasPermission('admin:content:body:update')")
    @Log(value = "修改内容正文", type = LogType.BUSINESS, action = LogAction.UPDATE)
    @Idempotent(prefix = "update:content:body:", expireTime = 120, title = "修改内容正文")
    public ResultVo<Object> update(@RequestBody ContentBody contentBody) {
        contentBodyService.update(contentBody);
        return ResultVo.success();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:content:body:delete')")
    @Log(value = "删除内容正文", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> delete(@PathVariable Long id) {
        contentBodyService.delete(id);
        return ResultVo.success();
    }

    @DeleteMapping("/batch")
    @PreAuthorize("@ps.hasPermission('admin:content:body:delete')")
    @Log(value = "批量删除内容正文", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> batchDelete(@RequestBody List<Long> ids) {
        contentBodyService.batchDelete(ids);
        return ResultVo.success();
    }

    @PostMapping("/page")
    @PreAuthorize("@ps.hasPermission('admin:content:body:page')")
    public ResultVo<PageVo<List<ContentBody>>> page(@RequestBody PageQuery query) {
        return ResultVo.success(contentBodyService.findPage(query));
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:content:body:page')")
    public ResultVo<ContentBody> get(@PathVariable Long id) {
        return ResultVo.success(contentBodyService.findById(id));
    }

    @GetMapping("/list")
    @PreAuthorize("@ps.hasPermission('admin:content:body:page')")
    public ResultVo<List<ContentBody>> list() {
        return ResultVo.success(contentBodyService.findAll());
    }

    @GetMapping("/selectAll")
    public ResultVo<List<ContentBody>> selectAll() {
        return ResultVo.success(contentBodyService.selectAll());
    }
}

