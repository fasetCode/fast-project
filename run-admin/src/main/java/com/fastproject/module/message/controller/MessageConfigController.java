package com.fastproject.module.message.controller;

import com.fastproject.idempotent.annotation.Idempotent;
import com.fastproject.logs.annotation.Log;
import com.fastproject.logs.enums.LogAction;
import com.fastproject.logs.enums.LogType;
import com.fastproject.message.service.MessageConfigService;
import com.fastproject.message.vo.config.MessageConfigCreate;
import com.fastproject.message.vo.config.MessageConfigQuery;
import com.fastproject.message.vo.config.MessageConfigUpdate;
import com.fastproject.message.vo.config.MessageConfigVo;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.utils.vo.ResultVo;
import com.fastproject.vo.IdTitleVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 消息配置 Controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/message/config")
public class MessageConfigController {

    private final MessageConfigService messageConfigService;

    /**
     * 添加
     */
    @PostMapping
    @PreAuthorize("@ps.hasPermission('admin:message:config:add')")
    @Log(value = "添加消息配置", type = LogType.BUSINESS, action = LogAction.CREATE)
    @Idempotent(prefix = "add:message:config:", expireTime = 120, title = "添加消息配置")
    public ResultVo<Object> add(@RequestBody MessageConfigCreate create) {
        return ResultVo.success(messageConfigService.save(create));
    }

    /**
     * 修改
     */
    @PutMapping
    @PreAuthorize("@ps.hasPermission('admin:message:config:update')")
    @Log(value = "修改消息配置", type = LogType.BUSINESS, action = LogAction.UPDATE)
    @Idempotent(prefix = "update:message:config:", expireTime = 120, title = "修改消息配置")
    public ResultVo<Object> update(@RequestBody MessageConfigUpdate update) {
        messageConfigService.update(update);
        return ResultVo.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:message:config:delete')")
    @Log(value = "删除消息配置", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> delete(@PathVariable Long id) {
        messageConfigService.delete(id);
        return ResultVo.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/batch")
    @PreAuthorize("@ps.hasPermission('admin:message:config:delete')")
    @Log(value = "批量删除消息配置", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> batchDelete(@RequestBody List<Long> ids) {
        messageConfigService.batchDelete(ids);
        return ResultVo.success();
    }

    /**
     * 分页
     */
    @PostMapping("/page")
    @PreAuthorize("@ps.hasPermission('admin:message:config:page')")
    public ResultVo<PageVo<List<MessageConfigVo>>> page(@RequestBody MessageConfigQuery query) {
        return ResultVo.success(messageConfigService.findPage(query));
    }

    /**
     * 详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:message:config:page')")
    public ResultVo<MessageConfigVo> get(@PathVariable Long id) {
        return ResultVo.success(messageConfigService.findById(id));
    }

    /**
     * 查询所有正常状态的配置，用于选择框
     */
    @GetMapping("/selectAll")
    public ResultVo<List<IdTitleVo>> selectAll() {
        return ResultVo.success(messageConfigService.selectAll());
    }
}