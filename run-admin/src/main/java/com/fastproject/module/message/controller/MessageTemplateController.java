package com.fastproject.module.message.controller;

import com.fastproject.idempotent.annotation.Idempotent;
import com.fastproject.logs.annotation.Log;
import com.fastproject.logs.enums.LogAction;
import com.fastproject.logs.enums.LogType;
import com.fastproject.message.service.MessageTemplateService;
import com.fastproject.message.vo.template.MessageTemplateCreate;
import com.fastproject.message.vo.template.MessageTemplateQuery;
import com.fastproject.message.vo.template.MessageTemplateUpdate;
import com.fastproject.message.vo.template.MessageTemplateVo;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.utils.vo.ResultVo;
import com.fastproject.vo.IdTitleVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 消息模版 Controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/message/template")
public class MessageTemplateController {

    private final MessageTemplateService messageTemplateService;

    /**
     * 添加
     */
    @PostMapping
    @PreAuthorize("@ps.hasPermission('admin:message:template:add')")
    @Log(value = "添加消息模版", type = LogType.BUSINESS, action = LogAction.CREATE)
    @Idempotent(prefix = "add:message:template:", expireTime = 120, title = "添加消息模版")
    public ResultVo<Object> add(@RequestBody MessageTemplateCreate create) {
        return ResultVo.success(messageTemplateService.save(create));
    }

    /**
     * 修改
     */
    @PutMapping
    @PreAuthorize("@ps.hasPermission('admin:message:template:update')")
    @Log(value = "修改消息模版", type = LogType.BUSINESS, action = LogAction.UPDATE)
    @Idempotent(prefix = "update:message:template:", expireTime = 120, title = "修改消息模版")
    public ResultVo<Object> update(@RequestBody MessageTemplateUpdate update) {
        messageTemplateService.update(update);
        return ResultVo.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:message:template:delete')")
    @Log(value = "删除消息模版", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> delete(@PathVariable Long id) {
        messageTemplateService.delete(id);
        return ResultVo.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/batch")
    @PreAuthorize("@ps.hasPermission('admin:message:template:delete')")
    @Log(value = "批量删除消息模版", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> batchDelete(@RequestBody List<Long> ids) {
        messageTemplateService.batchDelete(ids);
        return ResultVo.success();
    }

    /**
     * 分页
     */
    @PostMapping("/page")
    @PreAuthorize("@ps.hasPermission('admin:message:template:page')")
    public ResultVo<PageVo<List<MessageTemplateVo>>> page(@RequestBody MessageTemplateQuery query) {
        return ResultVo.success(messageTemplateService.findPage(query));
    }

    /**
     * 详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:message:template:page')")
    public ResultVo<MessageTemplateVo> get(@PathVariable Long id) {
        return ResultVo.success(messageTemplateService.findById(id));
    }

    /**
     * 查询所有正常状态的模版，用于选择框
     */
    @GetMapping("/selectAll")
    public ResultVo<List<MessageTemplateVo>> selectAll() {
        return ResultVo.success(messageTemplateService.selectAll());
    }
}