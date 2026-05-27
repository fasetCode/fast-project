package com.fastproject.module.content.controller;

import com.fastproject.content.service.ContentCommentLikeService;
import com.fastproject.content.vo.commentlike.ContentCommentLikeCreate;
import com.fastproject.content.vo.commentlike.ContentCommentLikeQuery;
import com.fastproject.content.vo.commentlike.ContentCommentLikeUpdate;
import com.fastproject.content.vo.commentlike.ContentCommentLikeVo;
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
@RequestMapping("/content/commentLike")
public class ContentCommentLikeController {

    private final ContentCommentLikeService contentCommentLikeService;

    @PostMapping
    @PreAuthorize("@ps.hasPermission('admin:content:commentLike:add')")
    @Log(value = "添加评论点赞记录", type = LogType.BUSINESS, action = LogAction.CREATE)
    @Idempotent(prefix = "add:content:commentLike:", expireTime = 120, title = "添加评论点赞记录")
    public ResultVo<Long> add(@RequestBody ContentCommentLikeCreate create) {
        return ResultVo.success(contentCommentLikeService.save(create));
    }

    @PutMapping
    @PreAuthorize("@ps.hasPermission('admin:content:commentLike:update')")
    @Log(value = "修改评论点赞记录", type = LogType.BUSINESS, action = LogAction.UPDATE)
    @Idempotent(prefix = "update:content:commentLike:", expireTime = 120, title = "修改评论点赞记录")
    public ResultVo<Object> update(@RequestBody ContentCommentLikeUpdate update) {
        contentCommentLikeService.update(update);
        return ResultVo.success();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:content:commentLike:delete')")
    @Log(value = "删除评论点赞记录", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> delete(@PathVariable Long id) {
        contentCommentLikeService.delete(id);
        return ResultVo.success();
    }

    @DeleteMapping("/batch")
    @PreAuthorize("@ps.hasPermission('admin:content:commentLike:delete')")
    @Log(value = "批量删除评论点赞记录", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> batchDelete(@RequestBody List<Long> ids) {
        contentCommentLikeService.batchDelete(ids);
        return ResultVo.success();
    }

    @PostMapping("/page")
    @PreAuthorize("@ps.hasPermission('admin:content:commentLike:page')")
    public ResultVo<PageVo<List<ContentCommentLikeVo>>> page(@RequestBody ContentCommentLikeQuery query) {
        return ResultVo.success(contentCommentLikeService.findPage(query));
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:content:commentLike:page')")
    public ResultVo<ContentCommentLikeVo> get(@PathVariable Long id) {
        return ResultVo.success(contentCommentLikeService.findById(id));
    }

    @GetMapping("/list")
    @PreAuthorize("@ps.hasPermission('admin:content:commentLike:page')")
    public ResultVo<List<ContentCommentLikeVo>> list() {
        return ResultVo.success(contentCommentLikeService.findAll());
    }

    @GetMapping("/selectAll")
    public ResultVo<List<ContentCommentLikeVo>> selectAll() {
        return ResultVo.success(contentCommentLikeService.selectAll());
    }
}
