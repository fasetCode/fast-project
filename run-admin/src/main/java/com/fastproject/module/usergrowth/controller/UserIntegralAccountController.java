package com.fastproject.module.usergrowth.controller;

import com.fastproject.usergrowth.service.UserIntegralAccountService;
import com.fastproject.usergrowth.vo.integralaccount.UserIntegralAccountCreate;
import com.fastproject.usergrowth.vo.integralaccount.UserIntegralAccountQuery;
import com.fastproject.usergrowth.vo.integralaccount.UserIntegralAccountUpdate;
import com.fastproject.usergrowth.vo.integralaccount.UserIntegralAccountVo;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.utils.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usergrowth/userintegralaccount")
@RequiredArgsConstructor
public class UserIntegralAccountController {

    private final UserIntegralAccountService service;

    @PostMapping
    @PreAuthorize("@ps.hasPermission('usergrowth:userintegralaccount:add')")
    public ResultVo<Long> add(@Validated @RequestBody UserIntegralAccountCreate create) {
        return ResultVo.success(service.save(create));
    }

    @PutMapping
    @PreAuthorize("@ps.hasPermission('usergrowth:userintegralaccount:update')")
    public ResultVo<Void> update(@Validated @RequestBody UserIntegralAccountUpdate update) {
        service.update(update);
        return ResultVo.success();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('usergrowth:userintegralaccount:delete')")
    public ResultVo<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResultVo.success();
    }

    @DeleteMapping("/batch")
    @PreAuthorize("@ps.hasPermission('usergrowth:userintegralaccount:delete')")
    public ResultVo<Void> batchDelete(@RequestBody List<Long> ids) {
        service.batchDelete(ids);
        return ResultVo.success();
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('usergrowth:userintegralaccount:query')")
    public ResultVo<UserIntegralAccountVo> findById(@PathVariable Long id) {
        return ResultVo.success(service.findById(id));
    }

    @PostMapping("/page")
    @PreAuthorize("@ps.hasPermission('usergrowth:userintegralaccount:query')")
    public ResultVo<PageVo<List<UserIntegralAccountVo>>> findPage(@RequestBody UserIntegralAccountQuery query) {
        return ResultVo.success(service.findPage(query));
    }
}
