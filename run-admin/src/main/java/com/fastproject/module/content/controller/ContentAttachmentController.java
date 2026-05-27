package com.fastproject.module.content.controller;

import com.fastproject.content.service.ContentAttachmentService;
import com.fastproject.content.vo.attachment.ContentAttachmentCreate;
import com.fastproject.content.vo.attachment.ContentAttachmentQuery;
import com.fastproject.content.vo.attachment.ContentAttachmentUpdate;
import com.fastproject.content.vo.attachment.ContentAttachmentVo;
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
    public ResultVo<Long> add(@RequestBody ContentAttachmentCreate create) {
        return ResultVo.success(contentAttachmentService.save(create));
    }

    @PutMapping
    @PreAuthorize("@ps.hasPermission('admin:content:attachment:update')")
    @Log(value = "修改附件", type = LogType.BUSINESS, action = LogAction.UPDATE)
    @Idempotent(prefix = "update:content:attachment:", expireTime = 120, title = "修改附件")
    public ResultVo<Object> update(@RequestBody ContentAttachmentUpdate update) {
        contentAttachmentService.update(update);
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
    public ResultVo<PageVo<List<ContentAttachmentVo>>> page(@RequestBody ContentAttachmentQuery query) {
        return ResultVo.success(contentAttachmentService.findPage(query));
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:content:attachment:page')")
    public ResultVo<ContentAttachmentVo> get(@PathVariable Long id) {
        return ResultVo.success(contentAttachmentService.findById(id));
    }

    @GetMapping("/list")
    @PreAuthorize("@ps.hasPermission('admin:content:attachment:page')")
    public ResultVo<List<ContentAttachmentVo>> list() {
        return ResultVo.success(contentAttachmentService.findAll());
    }

    @GetMapping("/selectAll")
    public ResultVo<List<ContentAttachmentVo>> selectAll() {
        return ResultVo.success(contentAttachmentService.selectAll());
    }
}
