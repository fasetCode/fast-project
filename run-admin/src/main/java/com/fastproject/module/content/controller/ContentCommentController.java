package com.fastproject.module.content.controller;

import com.fastproject.content.service.ContentCommentService;
import com.fastproject.content.vo.comment.ContentCommentCreate;
import com.fastproject.content.vo.comment.ContentCommentQuery;
import com.fastproject.content.vo.comment.ContentCommentUpdate;
import com.fastproject.content.vo.comment.ContentCommentVo;
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
@RequestMapping("/content/comment")
public class ContentCommentController {

    private final ContentCommentService contentCommentService;

    @PostMapping
    @PreAuthorize("@ps.hasPermission('admin:content:comment:add')")
    @Log(value = "添加评论", type = LogType.BUSINESS, action = LogAction.CREATE)
    @Idempotent(prefix = "add:content:comment:", expireTime = 120, title = "添加评论")
    public ResultVo<Long> add(@RequestBody ContentCommentCreate create) {
        return ResultVo.success(contentCommentService.save(create));
    }

    @PutMapping
    @PreAuthorize("@ps.hasPermission('admin:content:comment:update')")
    @Log(value = "修改评论", type = LogType.BUSINESS, action = LogAction.UPDATE)
    @Idempotent(prefix = "update:content:comment:", expireTime = 120, title = "修改评论")
    public ResultVo<Object> update(@RequestBody ContentCommentUpdate update) {
        contentCommentService.update(update);
        return ResultVo.success();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:content:comment:delete')")
    @Log(value = "删除评论", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> delete(@PathVariable Long id) {
        contentCommentService.delete(id);
        return ResultVo.success();
    }

    @DeleteMapping("/batch")
    @PreAuthorize("@ps.hasPermission('admin:content:comment:delete')")
    @Log(value = "批量删除评论", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> batchDelete(@RequestBody List<Long> ids) {
        contentCommentService.batchDelete(ids);
        return ResultVo.success();
    }

    @PostMapping("/page")
    @PreAuthorize("@ps.hasPermission('admin:content:comment:page')")
    public ResultVo<PageVo<List<ContentCommentVo>>> page(@RequestBody ContentCommentQuery query) {
        return ResultVo.success(contentCommentService.findPage(query));
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:content:comment:page')")
    public ResultVo<ContentCommentVo> get(@PathVariable Long id) {
        return ResultVo.success(contentCommentService.findById(id));
    }

    @GetMapping("/list")
    @PreAuthorize("@ps.hasPermission('admin:content:comment:page')")
    public ResultVo<List<ContentCommentVo>> list() {
        return ResultVo.success(contentCommentService.findAll());
    }

    @GetMapping("/selectAll")
    public ResultVo<List<ContentCommentVo>> selectAll() {
        return ResultVo.success(contentCommentService.selectAll());
    }
}
