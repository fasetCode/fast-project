package com.fastproject.module.message.controller;

import com.fastproject.idempotent.annotation.Idempotent;
import com.fastproject.logs.annotation.Log;
import com.fastproject.logs.enums.LogAction;
import com.fastproject.logs.enums.LogType;
import com.fastproject.message.service.MessageTypeService;
import com.fastproject.message.vo.type.MessageTypeCreate;
import com.fastproject.message.vo.type.MessageTypeQuery;
import com.fastproject.message.vo.type.MessageTypeUpdate;
import com.fastproject.message.vo.type.MessageTypeVo;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.utils.vo.ResultVo;
import com.fastproject.vo.IdTitleVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 消息类型 Controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/message/type")
public class MessageTypeController {

    private final MessageTypeService messageTypeService;

    /**
     * 添加
     */
    @PostMapping
    @PreAuthorize("@ps.hasPermission('admin:message:type:add')")
    @Log(value = "添加消息类型", type = LogType.BUSINESS, action = LogAction.CREATE)
    @Idempotent(prefix = "add:message:type:", expireTime = 120, title = "添加消息类型")
    public ResultVo<Object> add(@RequestBody MessageTypeCreate create) {
        return ResultVo.success(messageTypeService.save(create));
    }

    /**
     * 修改
     */
    @PutMapping
    @PreAuthorize("@ps.hasPermission('admin:message:type:update')")
    @Log(value = "修改消息类型", type = LogType.BUSINESS, action = LogAction.UPDATE)
    @Idempotent(prefix = "update:message:type:", expireTime = 120, title = "修改消息类型")
    public ResultVo<Object> update(@RequestBody MessageTypeUpdate update) {
        messageTypeService.update(update);
        return ResultVo.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:message:type:delete')")
    @Log(value = "删除消息类型", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> delete(@PathVariable Long id) {
        messageTypeService.delete(id);
        return ResultVo.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/batch")
    @PreAuthorize("@ps.hasPermission('admin:message:type:delete')")
    @Log(value = "批量删除消息类型", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> batchDelete(@RequestBody List<Long> ids) {
        messageTypeService.batchDelete(ids);
        return ResultVo.success();
    }

    /**
     * 分页
     */
    @PostMapping("/page")
    @PreAuthorize("@ps.hasPermission('admin:message:type:page')")
    public ResultVo<PageVo<List<MessageTypeVo>>> page(@RequestBody MessageTypeQuery query) {
        return ResultVo.success(messageTypeService.findPage(query));
    }

    /**
     * 详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:message:type:page')")
    public ResultVo<MessageTypeVo> get(@PathVariable Long id) {
        return ResultVo.success(messageTypeService.findById(id));
    }

    /**
     * 查询所有正常状态的类型，用于选择框
     */
    @GetMapping("/selectAll")
    public ResultVo<List<IdTitleVo>> selectAll() {
        return ResultVo.success(messageTypeService.selectAll());
    }
}