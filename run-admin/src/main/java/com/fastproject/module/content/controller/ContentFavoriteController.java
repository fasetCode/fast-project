package com.fastproject.module.content.controller;

import com.fastproject.content.domain.ContentFavorite;
import com.fastproject.content.service.ContentFavoriteService;
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
@RequestMapping("/content/favorite")
public class ContentFavoriteController {

    private final ContentFavoriteService contentFavoriteService;

    @PostMapping
    @PreAuthorize("@ps.hasPermission('admin:content:favorite:add')")
    @Log(value = "添加收藏记录", type = LogType.BUSINESS, action = LogAction.CREATE)
    @Idempotent(prefix = "add:content:favorite:", expireTime = 120, title = "添加收藏记录")
    public ResultVo<Long> add(@RequestBody ContentFavorite contentFavorite) {
        return ResultVo.success(contentFavoriteService.save(contentFavorite));
    }

    @PutMapping
    @PreAuthorize("@ps.hasPermission('admin:content:favorite:update')")
    @Log(value = "修改收藏记录", type = LogType.BUSINESS, action = LogAction.UPDATE)
    @Idempotent(prefix = "update:content:favorite:", expireTime = 120, title = "修改收藏记录")
    public ResultVo<Object> update(@RequestBody ContentFavorite contentFavorite) {
        contentFavoriteService.update(contentFavorite);
        return ResultVo.success();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:content:favorite:delete')")
    @Log(value = "删除收藏记录", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> delete(@PathVariable Long id) {
        contentFavoriteService.delete(id);
        return ResultVo.success();
    }

    @DeleteMapping("/batch")
    @PreAuthorize("@ps.hasPermission('admin:content:favorite:delete')")
    @Log(value = "批量删除收藏记录", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> batchDelete(@RequestBody List<Long> ids) {
        contentFavoriteService.batchDelete(ids);
        return ResultVo.success();
    }

    @PostMapping("/page")
    @PreAuthorize("@ps.hasPermission('admin:content:favorite:page')")
    public ResultVo<PageVo<List<ContentFavorite>>> page(@RequestBody PageQuery query) {
        return ResultVo.success(contentFavoriteService.findPage(query));
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:content:favorite:page')")
    public ResultVo<ContentFavorite> get(@PathVariable Long id) {
        return ResultVo.success(contentFavoriteService.findById(id));
    }

    @GetMapping("/list")
    @PreAuthorize("@ps.hasPermission('admin:content:favorite:page')")
    public ResultVo<List<ContentFavorite>> list() {
        return ResultVo.success(contentFavoriteService.findAll());
    }

    @GetMapping("/selectAll")
    public ResultVo<List<ContentFavorite>> selectAll() {
        return ResultVo.success(contentFavoriteService.selectAll());
    }
}

