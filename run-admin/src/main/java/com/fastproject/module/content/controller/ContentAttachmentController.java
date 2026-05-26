package com.fastproject.module.content.controller;

import com.fastproject.content.domain.ContentAttachment;
import com.fastproject.content.service.ContentAttachmentService;
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
@RequestMapping("/content/attachment")
public class ContentAttachmentController {

    private final ContentAttachmentService contentAttachmentService;

    @PostMapping
    @PreAuthorize("@ps.hasPermission('admin:content:attachment:add')")
    @Log(value = "添加附件", type = LogType.BUSINESS, action = LogAction.CREATE)
    @Idempotent(prefix = "add:content:attachment:", expireTime = 120, title = "添加附件")
    public ResultVo<Long> add(@RequestBody ContentAttachment contentAttachment) {
        return ResultVo.success(contentAttachmentService.save(contentAttachment));
    }

    @PutMapping
    @PreAuthorize("@ps.hasPermission('admin:content:attachment:update')")
    @Log(value = "修改附件", type = LogType.BUSINESS, action = LogAction.UPDATE)
    @Idempotent(prefix = "update:content:attachment:", expireTime = 120, title = "修改附件")
    public ResultVo<Object> update(@RequestBody ContentAttachment contentAttachment) {
        contentAttachmentService.update(contentAttachment);
        return ResultVo.success();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:content:attachment:delete')")
    @Log(value = "删除附件", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> delete(@PathVariable Long id) {
        contentAttachmentService.delete(id);
        return ResultVo.success();
    }

    @DeleteMapping("/batch")
    @PreAuthorize("@ps.hasPermission('admin:content:attachment:delete')")
    @Log(value = "批量删除附件", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> batchDelete(@RequestBody List<Long> ids) {
        contentAttachmentService.batchDelete(ids);
        return ResultVo.success();
    }

    @PostMapping("/page")
    @PreAuthorize("@ps.hasPermission('admin:content:attachment:page')")
    public ResultVo<PageVo<List<ContentAttachment>>> page(@RequestBody PageQuery query) {
        return ResultVo.success(contentAttachmentService.findPage(query));
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:content:attachment:page')")
    public ResultVo<ContentAttachment> get(@PathVariable Long id) {
        return ResultVo.success(contentAttachmentService.findById(id));
    }

    @GetMapping("/list")
    @PreAuthorize("@ps.hasPermission('admin:content:attachment:page')")
    public ResultVo<List<ContentAttachment>> list() {
        return ResultVo.success(contentAttachmentService.findAll());
    }

    @GetMapping("/selectAll")
    public ResultVo<List<ContentAttachment>> selectAll() {
        return ResultVo.success(contentAttachmentService.selectAll());
    }
}

