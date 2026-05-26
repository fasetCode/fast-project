package com.fastproject.module.content.controller;

import com.fastproject.content.domain.ContentLike;
import com.fastproject.content.service.ContentLikeService;
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
@RequestMapping("/content/like")
public class ContentLikeController {

    private final ContentLikeService contentLikeService;

    @PostMapping
    @PreAuthorize("@ps.hasPermission('admin:content:like:add')")
    @Log(value = "添加点赞记录", type = LogType.BUSINESS, action = LogAction.CREATE)
    @Idempotent(prefix = "add:content:like:", expireTime = 120, title = "添加点赞记录")
    public ResultVo<Long> add(@RequestBody ContentLike contentLike) {
        return ResultVo.success(contentLikeService.save(contentLike));
    }

    @PutMapping
    @PreAuthorize("@ps.hasPermission('admin:content:like:update')")
    @Log(value = "修改点赞记录", type = LogType.BUSINESS, action = LogAction.UPDATE)
    @Idempotent(prefix = "update:content:like:", expireTime = 120, title = "修改点赞记录")
    public ResultVo<Object> update(@RequestBody ContentLike contentLike) {
        contentLikeService.update(contentLike);
        return ResultVo.success();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:content:like:delete')")
    @Log(value = "删除点赞记录", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> delete(@PathVariable Long id) {
        contentLikeService.delete(id);
        return ResultVo.success();
    }

    @DeleteMapping("/batch")
    @PreAuthorize("@ps.hasPermission('admin:content:like:delete')")
    @Log(value = "批量删除点赞记录", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> batchDelete(@RequestBody List<Long> ids) {
        contentLikeService.batchDelete(ids);
        return ResultVo.success();
    }

    @PostMapping("/page")
    @PreAuthorize("@ps.hasPermission('admin:content:like:page')")
    public ResultVo<PageVo<List<ContentLike>>> page(@RequestBody PageQuery query) {
        return ResultVo.success(contentLikeService.findPage(query));
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:content:like:page')")
    public ResultVo<ContentLike> get(@PathVariable Long id) {
        return ResultVo.success(contentLikeService.findById(id));
    }

    @GetMapping("/list")
    @PreAuthorize("@ps.hasPermission('admin:content:like:page')")
    public ResultVo<List<ContentLike>> list() {
        return ResultVo.success(contentLikeService.findAll());
    }

    @GetMapping("/selectAll")
    public ResultVo<List<ContentLike>> selectAll() {
        return ResultVo.success(contentLikeService.selectAll());
    }
}

