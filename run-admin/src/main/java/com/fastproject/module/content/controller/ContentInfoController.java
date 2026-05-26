package com.fastproject.module.content.controller;

import com.fastproject.content.domain.ContentInfo;
import com.fastproject.content.service.ContentInfoService;
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
@RequestMapping("/content/info")
public class ContentInfoController {

    private final ContentInfoService contentInfoService;

    @PostMapping
    @PreAuthorize("@ps.hasPermission('admin:content:info:add')")
    @Log(value = "添加内容", type = LogType.BUSINESS, action = LogAction.CREATE)
    @Idempotent(prefix = "add:content:info:", expireTime = 120, title = "添加内容")
    public ResultVo<Long> add(@RequestBody ContentInfo contentInfo) {
        return ResultVo.success(contentInfoService.save(contentInfo));
    }

    @PutMapping
    @PreAuthorize("@ps.hasPermission('admin:content:info:update')")
    @Log(value = "修改内容", type = LogType.BUSINESS, action = LogAction.UPDATE)
    @Idempotent(prefix = "update:content:info:", expireTime = 120, title = "修改内容")
    public ResultVo<Object> update(@RequestBody ContentInfo contentInfo) {
        contentInfoService.update(contentInfo);
        return ResultVo.success();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:content:info:delete')")
    @Log(value = "删除内容", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> delete(@PathVariable Long id) {
        contentInfoService.delete(id);
        return ResultVo.success();
    }

    @DeleteMapping("/batch")
    @PreAuthorize("@ps.hasPermission('admin:content:info:delete')")
    @Log(value = "批量删除内容", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> batchDelete(@RequestBody List<Long> ids) {
        contentInfoService.batchDelete(ids);
        return ResultVo.success();
    }

    @PostMapping("/page")
    @PreAuthorize("@ps.hasPermission('admin:content:info:page')")
    public ResultVo<PageVo<List<ContentInfo>>> page(@RequestBody PageQuery query) {
        return ResultVo.success(contentInfoService.findPage(query));
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:content:info:page')")
    public ResultVo<ContentInfo> get(@PathVariable Long id) {
        return ResultVo.success(contentInfoService.findById(id));
    }

    @GetMapping("/list")
    @PreAuthorize("@ps.hasPermission('admin:content:info:page')")
    public ResultVo<List<ContentInfo>> list() {
        return ResultVo.success(contentInfoService.findAll());
    }

    @GetMapping("/selectAll")
    public ResultVo<List<ContentInfo>> selectAll() {
        return ResultVo.success(contentInfoService.selectAll());
    }
}

