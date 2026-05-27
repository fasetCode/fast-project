package com.fastproject.module.system.controller;

import com.fastproject.idempotent.annotation.Idempotent;
import com.fastproject.logs.annotation.Log;
import com.fastproject.logs.enums.LogAction;
import com.fastproject.logs.enums.LogType;
import com.fastproject.system.service.SysUsersService;
import com.fastproject.system.vo.users.SysUserPasswordUpdate;
import com.fastproject.system.vo.users.SysUserUpdate;
import com.fastproject.system.vo.users.SysUsersCreate;
import com.fastproject.system.vo.users.SysUsersDetailVo;
import com.fastproject.system.vo.users.SysUsersPickerQuery;
import com.fastproject.system.vo.users.SysUsersPickerVo;
import com.fastproject.system.vo.users.SysUsersQuery;
import com.fastproject.system.vo.users.SysUsersVo;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.utils.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/sys/users")
public class SysUsersController {
    private final SysUsersService sysUsersService;

    /**
     * 添加
     */
    @PostMapping
    @PreAuthorize("@ps.hasPermission('admin:system:user:add')")
    @Log(value = "后台用户添加", type = LogType.BUSINESS, action = LogAction.CREATE)
    @Idempotent(prefix = "add:sys:user:", expireTime = 120, title = "后台用户添加")
    public ResultVo<Object> add(@RequestBody SysUsersCreate sysUsersCreate) {
        return ResultVo.success(sysUsersService.save(sysUsersCreate));
    }

    /**
     * 修改
     */
    @PutMapping
    @PreAuthorize("@ps.hasPermission('admin:system:user:update')")
    @Log(value = "后台用户修改",type = LogType.BUSINESS, action = LogAction.UPDATE)
    @Idempotent(prefix = "update:sys:user:", expireTime = 120,title = "后台用户修改")
    public ResultVo<Object> update(@RequestBody SysUserUpdate sysUsersCreate) {
        sysUsersService.update(sysUsersCreate);
        return ResultVo.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:system:user:delete')")
    @Log(value = "后台用户删除", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> delete(@PathVariable Long id) {
        sysUsersService.delete(id);
        return ResultVo.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/batch")
    @PreAuthorize("@ps.hasPermission('admin:system:user:delete')")
    @Log(value = "后台用户批量删除", type = LogType.BUSINESS, action = LogAction.DELETE)
    public ResultVo<Object> batchDelete(@RequestBody List<Long> ids) {
        sysUsersService.batchDelete(ids);
        return ResultVo.success();
    }

    /**
     * 分页
     */
    @PostMapping("/page")
    @PreAuthorize("@ps.hasPermission('admin:system:user:page')")
    public ResultVo<PageVo<List<SysUsersVo>>> page(@RequestBody SysUsersQuery query) {
        return ResultVo.success(sysUsersService.findPage(query));
    }

    /**
     * 详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:system:user:page')")
    public ResultVo<SysUsersDetailVo> get(@PathVariable Long id) {
        return ResultVo.success(sysUsersService.findById(id));
    }

    /**
     * 修改密码
     */
    @PutMapping("/password")
    @PreAuthorize("@ps.hasPermission('admin:system:user:update')")
    @Log(value = "后台用户修改密码", type = LogType.BUSINESS, action = LogAction.UPDATE)
    @Idempotent(prefix = "update:sys:user:password:", expireTime = 120, title = "后台用户修改密码")
    public ResultVo<Object> updatePassword(@Validated @RequestBody SysUserPasswordUpdate passwordUpdate) {
        sysUsersService.updatePassword(passwordUpdate);
        return ResultVo.success();
    }

    /**
     * 模糊搜索用户（限制20条）
     */
    @GetMapping("/search")
    public ResultVo<List<SysUsersVo>> search(@RequestParam(required = false) String keyword) {
        return ResultVo.success(sysUsersService.searchUsers(keyword, 20));
    }

    @PostMapping("/picker/page")
    @PreAuthorize("@ps.hasPermission('admin:system:user:page')")
    public ResultVo<PageVo<List<SysUsersPickerVo>>> pickerPage(@RequestBody SysUsersPickerQuery query) {
        return ResultVo.success(sysUsersService.findPickerPage(query));
    }

    @GetMapping("/picker/{id}")
    @PreAuthorize("@ps.hasPermission('admin:system:user:page')")
    public ResultVo<SysUsersPickerVo> pickerGet(@PathVariable Long id) {
        return ResultVo.success(sysUsersService.getPickerById(id));
    }
}
