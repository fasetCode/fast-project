package com.fastproject.module.ratelimit.controller;

import com.fastproject.idempotent.annotation.Idempotent;
import com.fastproject.logs.annotation.Log;
import com.fastproject.logs.enums.LogAction;
import com.fastproject.logs.enums.LogType;
import com.fastproject.ratelimit.service.IpBlackWhiteListService;
import com.fastproject.ratelimit.vo.ipbw.IpBlackWhiteListCreate;
import com.fastproject.ratelimit.vo.ipbw.IpBlackWhiteListQuery;
import com.fastproject.ratelimit.vo.ipbw.IpBlackWhiteListUpdate;
import com.fastproject.ratelimit.vo.ipbw.IpBlackWhiteListVo;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.utils.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * IP黑白名单配置 Controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/ratelimit/ip-bw-config")
public class IpBlackWhiteListController {

    private final IpBlackWhiteListService ipBlackWhiteListService;

    /**
     * 添加
     */
    @PostMapping
    @PreAuthorize("@ps.hasPermission('admin:ratelimit:ip-bw-config:add')")
    @Log(value = "添加IP黑白名单配置", type = LogType.BUSINESS, action = LogAction.CREATE)
    @Idempotent(prefix = "add:ratelimit:ip-bw-config:", expireTime = 120, title = "添加IP黑白名单配置")
    public ResultVo<Object> add(@RequestBody IpBlackWhiteListCreate create) {
        return ResultVo.success(ipBlackWhiteListService.save(create));
    }

    /**
     * 修改
     */
    @PutMapping
    @PreAuthorize("@ps.hasPermission('admin:ratelimit:ip-bw-config:update')")
    @Log(value = "修改IP黑白名单配置", type = LogType.BUSINESS, action = LogAction.UPDATE)
    @Idempotent(prefix = "update:ratelimit:ip-bw-config:", expireTime = 120, title = "修改IP黑白名单配置")
    public ResultVo<Object> update(@RequestBody IpBlackWhiteListUpdate update) {
        ipBlackWhiteListService.update(update);
        return ResultVo.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:ratelimit:ip-bw-config:delete')")
    @Log(value = "删除IP黑白名单配置", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> delete(@PathVariable Long id) {
        ipBlackWhiteListService.delete(id);
        return ResultVo.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/batch")
    @PreAuthorize("@ps.hasPermission('admin:ratelimit:ip-bw-config:delete')")
    @Log(value = "批量删除IP黑白名单配置", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> batchDelete(@RequestBody List<Long> ids) {
        ipBlackWhiteListService.batchDelete(ids);
        return ResultVo.success();
    }

    /**
     * 分页
     */
    @PostMapping("/page")
    @PreAuthorize("@ps.hasPermission('admin:ratelimit:ip-bw-config:page')")
    public ResultVo<PageVo<List<IpBlackWhiteListVo>>> page(@RequestBody IpBlackWhiteListQuery query) {
        return ResultVo.success(ipBlackWhiteListService.findPage(query));
    }

    /**
     * 详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:ratelimit:ip-bw-config:page')")
    public ResultVo<IpBlackWhiteListVo> get(@PathVariable Long id) {
        return ResultVo.success(ipBlackWhiteListService.findById(id));
    }
}
