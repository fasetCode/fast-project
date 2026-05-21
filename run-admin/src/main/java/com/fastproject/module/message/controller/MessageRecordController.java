package com.fastproject.module.message.controller;

import com.fastproject.logs.annotation.Log;
import com.fastproject.logs.enums.LogAction;
import com.fastproject.logs.enums.LogType;
import com.fastproject.message.service.MessageRecordService;
import com.fastproject.message.vo.record.MessageRecordQuery;
import com.fastproject.message.vo.record.MessageRecordVo;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.utils.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 消息记录 Controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/message/record")
public class MessageRecordController {

    private final MessageRecordService messageRecordService;

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:message:record:delete')")
    @Log(value = "删除消息记录", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> delete(@PathVariable Long id) {
        messageRecordService.delete(id);
        return ResultVo.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/batch")
    @PreAuthorize("@ps.hasPermission('admin:message:record:delete')")
    @Log(value = "批量删除消息记录", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> batchDelete(@RequestBody List<Long> ids) {
        messageRecordService.batchDelete(ids);
        return ResultVo.success();
    }

    /**
     * 分页查询
     */
    @PostMapping("/page")
    @PreAuthorize("@ps.hasPermission('admin:message:record:page')")
    public ResultVo<PageVo<List<MessageRecordVo>>> page(@RequestBody MessageRecordQuery query) {
        return ResultVo.success(messageRecordService.findPage(query));
    }

    /**
     * 详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:message:record:page')")
    public ResultVo<MessageRecordVo> get(@PathVariable Long id) {
        return ResultVo.success(messageRecordService.findById(id));
    }
}
