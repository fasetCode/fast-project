package com.fastproject.module.usergrowth.controller;

import com.fastproject.usergrowth.service.UserIntegralRecordService;
import com.fastproject.usergrowth.vo.integralrecord.UserIntegralRecordCreate;
import com.fastproject.usergrowth.vo.integralrecord.UserIntegralRecordQuery;
import com.fastproject.usergrowth.vo.integralrecord.UserIntegralRecordUpdate;
import com.fastproject.usergrowth.vo.integralrecord.UserIntegralRecordVo;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.utils.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usergrowth/userintegralrecord")
@RequiredArgsConstructor
public class UserIntegralRecordController {

    private final UserIntegralRecordService service;

    @PostMapping
    @PreAuthorize("@ps.hasPermission('usergrowth:userintegralrecord:add')")
    public ResultVo<Long> add(@Validated @RequestBody UserIntegralRecordCreate create) {
        return ResultVo.success(service.save(create));
    }

    @PutMapping
    @PreAuthorize("@ps.hasPermission('usergrowth:userintegralrecord:update')")
    public ResultVo<Void> update(@Validated @RequestBody UserIntegralRecordUpdate update) {
        service.update(update);
        return ResultVo.success();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('usergrowth:userintegralrecord:delete')")
    public ResultVo<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResultVo.success();
    }

    @DeleteMapping("/batch")
    @PreAuthorize("@ps.hasPermission('usergrowth:userintegralrecord:delete')")
    public ResultVo<Void> batchDelete(@RequestBody List<Long> ids) {
        service.batchDelete(ids);
        return ResultVo.success();
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('usergrowth:userintegralrecord:query')")
    public ResultVo<UserIntegralRecordVo> findById(@PathVariable Long id) {
        return ResultVo.success(service.findById(id));
    }

    @PostMapping("/page")
    @PreAuthorize("@ps.hasPermission('usergrowth:userintegralrecord:query')")
    public ResultVo<PageVo<List<UserIntegralRecordVo>>> findPage(@RequestBody UserIntegralRecordQuery query) {
        return ResultVo.success(service.findPage(query));
    }
}
