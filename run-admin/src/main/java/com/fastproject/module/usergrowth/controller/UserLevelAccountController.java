package com.fastproject.module.usergrowth.controller;

import com.fastproject.usergrowth.service.UserLevelAccountService;
import com.fastproject.usergrowth.vo.levelaccount.UserLevelAccountCreate;
import com.fastproject.usergrowth.vo.levelaccount.UserLevelAccountQuery;
import com.fastproject.usergrowth.vo.levelaccount.UserLevelAccountUpdate;
import com.fastproject.usergrowth.vo.levelaccount.UserLevelAccountVo;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.utils.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usergrowth/userlevelaccount")
@RequiredArgsConstructor
public class UserLevelAccountController {

    private final UserLevelAccountService service;

    @PostMapping
    @PreAuthorize("@ps.hasPermission('usergrowth:userlevelaccount:add')")
    public ResultVo<Long> add(@Validated @RequestBody UserLevelAccountCreate create) {
        return ResultVo.success(service.save(create));
    }

    @PutMapping
    @PreAuthorize("@ps.hasPermission('usergrowth:userlevelaccount:update')")
    public ResultVo<Void> update(@Validated @RequestBody UserLevelAccountUpdate update) {
        service.update(update);
        return ResultVo.success();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('usergrowth:userlevelaccount:delete')")
    public ResultVo<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResultVo.success();
    }

    @DeleteMapping("/batch")
    @PreAuthorize("@ps.hasPermission('usergrowth:userlevelaccount:delete')")
    public ResultVo<Void> batchDelete(@RequestBody List<Long> ids) {
        service.batchDelete(ids);
        return ResultVo.success();
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('usergrowth:userlevelaccount:query')")
    public ResultVo<UserLevelAccountVo> findById(@PathVariable Long id) {
        return ResultVo.success(service.findById(id));
    }

    @PostMapping("/page")
    @PreAuthorize("@ps.hasPermission('usergrowth:userlevelaccount:query')")
    public ResultVo<PageVo<List<UserLevelAccountVo>>> findPage(@RequestBody UserLevelAccountQuery query) {
        return ResultVo.success(service.findPage(query));
    }
}
