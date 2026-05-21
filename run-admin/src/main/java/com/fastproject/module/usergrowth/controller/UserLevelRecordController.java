package com.fastproject.module.usergrowth.controller;

import com.fastproject.usergrowth.service.UserLevelRecordService;
import com.fastproject.usergrowth.vo.levelrecord.UserLevelRecordCreate;
import com.fastproject.usergrowth.vo.levelrecord.UserLevelRecordQuery;
import com.fastproject.usergrowth.vo.levelrecord.UserLevelRecordUpdate;
import com.fastproject.usergrowth.vo.levelrecord.UserLevelRecordVo;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.utils.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usergrowth/userlevelrecord")
@RequiredArgsConstructor
public class UserLevelRecordController {

    private final UserLevelRecordService service;

    @PostMapping
    @PreAuthorize("@ps.hasPermission('usergrowth:userlevelrecord:add')")
    public ResultVo<Long> add(@Validated @RequestBody UserLevelRecordCreate create) {
        return ResultVo.success(service.save(create));
    }

    @PutMapping
    @PreAuthorize("@ps.hasPermission('usergrowth:userlevelrecord:update')")
    public ResultVo<Void> update(@Validated @RequestBody UserLevelRecordUpdate update) {
        service.update(update);
        return ResultVo.success();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('usergrowth:userlevelrecord:delete')")
    public ResultVo<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResultVo.success();
    }

    @DeleteMapping("/batch")
    @PreAuthorize("@ps.hasPermission('usergrowth:userlevelrecord:delete')")
    public ResultVo<Void> batchDelete(@RequestBody List<Long> ids) {
        service.batchDelete(ids);
        return ResultVo.success();
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('usergrowth:userlevelrecord:query')")
    public ResultVo<UserLevelRecordVo> findById(@PathVariable Long id) {
        return ResultVo.success(service.findById(id));
    }

    @PostMapping("/page")
    @PreAuthorize("@ps.hasPermission('usergrowth:userlevelrecord:query')")
    public ResultVo<PageVo<List<UserLevelRecordVo>>> findPage(@RequestBody UserLevelRecordQuery query) {
        return ResultVo.success(service.findPage(query));
    }
}
