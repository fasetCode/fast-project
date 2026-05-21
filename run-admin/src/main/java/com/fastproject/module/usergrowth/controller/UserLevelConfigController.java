package com.fastproject.module.usergrowth.controller;

import com.fastproject.usergrowth.service.UserLevelConfigService;
import com.fastproject.usergrowth.vo.levelconfig.UserLevelConfigCreate;
import com.fastproject.usergrowth.vo.levelconfig.UserLevelConfigQuery;
import com.fastproject.usergrowth.vo.levelconfig.UserLevelConfigUpdate;
import com.fastproject.usergrowth.vo.levelconfig.UserLevelConfigVo;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.utils.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usergrowth/userlevelconfig")
@RequiredArgsConstructor
public class UserLevelConfigController {

    private final UserLevelConfigService service;

    @PostMapping
    @PreAuthorize("@ps.hasPermission('usergrowth:userlevelconfig:add')")
    public ResultVo<Long> add(@Validated @RequestBody UserLevelConfigCreate create) {
        return ResultVo.success(service.save(create));
    }

    @PutMapping
    @PreAuthorize("@ps.hasPermission('usergrowth:userlevelconfig:update')")
    public ResultVo<Void> update(@Validated @RequestBody UserLevelConfigUpdate update) {
        service.update(update);
        return ResultVo.success();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('usergrowth:userlevelconfig:delete')")
    public ResultVo<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResultVo.success();
    }

    @DeleteMapping("/batch")
    @PreAuthorize("@ps.hasPermission('usergrowth:userlevelconfig:delete')")
    public ResultVo<Void> batchDelete(@RequestBody List<Long> ids) {
        service.batchDelete(ids);
        return ResultVo.success();
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('usergrowth:userlevelconfig:query')")
    public ResultVo<UserLevelConfigVo> findById(@PathVariable Long id) {
        return ResultVo.success(service.findById(id));
    }

    @PostMapping("/page")
    @PreAuthorize("@ps.hasPermission('usergrowth:userlevelconfig:query')")
    public ResultVo<PageVo<List<UserLevelConfigVo>>> findPage(@RequestBody UserLevelConfigQuery query) {
        return ResultVo.success(service.findPage(query));
    }
}
