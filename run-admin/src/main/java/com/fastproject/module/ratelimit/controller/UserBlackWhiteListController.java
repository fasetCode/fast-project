package com.fastproject.module.ratelimit.controller;

import com.fastproject.idempotent.annotation.Idempotent;
import com.fastproject.logs.annotation.Log;
import com.fastproject.logs.enums.LogAction;
import com.fastproject.logs.enums.LogType;
import com.fastproject.ratelimit.service.UserBlackWhiteListService;
import com.fastproject.ratelimit.vo.userbw.UserBlackWhiteListCreate;
import com.fastproject.ratelimit.vo.userbw.UserBlackWhiteListQuery;
import com.fastproject.ratelimit.vo.userbw.UserBlackWhiteListUpdate;
import com.fastproject.ratelimit.vo.userbw.UserBlackWhiteListVo;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.utils.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户黑白名单配置 Controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/ratelimit/user-bw-config")
public class UserBlackWhiteListController {

    private final UserBlackWhiteListService userBlackWhiteListService;

    /**
     * 添加
     */
    @PostMapping
    @PreAuthorize("@ps.hasPermission('admin:ratelimit:user-bw-config:add')")
    @Log(value = "添加用户黑白名单配置", type = LogType.BUSINESS, action = LogAction.CREATE)
    @Idempotent(prefix = "add:ratelimit:user-bw-config:", expireTime = 120, title = "添加用户黑白名单配置")
    public ResultVo<Object> add(@RequestBody UserBlackWhiteListCreate create) {
        return ResultVo.success(userBlackWhiteListService.save(create));
    }

    /**
     * 修改
     */
    @PutMapping
    @PreAuthorize("@ps.hasPermission('admin:ratelimit:user-bw-config:update')")
    @Log(value = "修改用户黑白名单配置", type = LogType.BUSINESS, action = LogAction.UPDATE)
    @Idempotent(prefix = "update:ratelimit:user-bw-config:", expireTime = 120, title = "修改用户黑白名单配置")
    public ResultVo<Object> update(@RequestBody UserBlackWhiteListUpdate update) {
        userBlackWhiteListService.update(update);
        return ResultVo.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:ratelimit:user-bw-config:delete')")
    @Log(value = "删除用户黑白名单配置", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> delete(@PathVariable Long id) {
        userBlackWhiteListService.delete(id);
        return ResultVo.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/batch")
    @PreAuthorize("@ps.hasPermission('admin:ratelimit:user-bw-config:delete')")
    @Log(value = "批量删除用户黑白名单配置", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> batchDelete(@RequestBody List<Long> ids) {
        userBlackWhiteListService.batchDelete(ids);
        return ResultVo.success();
    }

    /**
     * 分页
     */
    @PostMapping("/page")
    @PreAuthorize("@ps.hasPermission('admin:ratelimit:user-bw-config:page')")
    public ResultVo<PageVo<List<UserBlackWhiteListVo>>> page(@RequestBody UserBlackWhiteListQuery query) {
        return ResultVo.success(userBlackWhiteListService.findPage(query));
    }

    /**
     * 详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:ratelimit:user-bw-config:page')")
    public ResultVo<UserBlackWhiteListVo> get(@PathVariable Long id) {
        return ResultVo.success(userBlackWhiteListService.findById(id));
    }
}
