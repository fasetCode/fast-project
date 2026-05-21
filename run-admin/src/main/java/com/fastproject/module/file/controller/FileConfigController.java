package com.fastproject.module.file.controller;

import com.fastproject.file.service.FileConfigService;
import com.fastproject.file.vo.config.FileConfigCreate;
import com.fastproject.file.vo.config.FileConfigQuery;
import com.fastproject.file.vo.config.FileConfigUpdate;
import com.fastproject.file.vo.config.FileConfigVo;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.utils.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 文件配置 Controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/file/config")
public class FileConfigController {

    private final FileConfigService fileConfigService;

    /**
     * 添加
     */
    @PostMapping
    @PreAuthorize("@ps.hasPermission('admin:file:config:add')")
    public ResultVo<Object> add(@RequestBody FileConfigCreate create) {
        return ResultVo.success(fileConfigService.save(create));
    }

    /**
     * 修改
     */
    @PutMapping
    @PreAuthorize("@ps.hasPermission('admin:file:config:update')")
    public ResultVo<Object> update(@RequestBody FileConfigUpdate update) {
        fileConfigService.update(update);
        return ResultVo.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:file:config:delete')")
    public ResultVo<Object> delete(@PathVariable Long id) {
        fileConfigService.delete(id);
        return ResultVo.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/batch")
    @PreAuthorize("@ps.hasPermission('admin:file:config:delete')")
    public ResultVo<Object> batchDelete(@RequestBody List<Long> ids) {
        fileConfigService.batchDelete(ids);
        return ResultVo.success();
    }

    /**
     * 分页
     */
    @PostMapping("/page")
    @PreAuthorize("@ps.hasPermission('admin:file:config:page')")
    public ResultVo<PageVo<List<FileConfigVo>>> page(@RequestBody FileConfigQuery query) {
        return ResultVo.success(fileConfigService.findPage(query));
    }

    /**
     * 详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:file:config:page')")
    public ResultVo<FileConfigVo> get(@PathVariable Long id) {
        return ResultVo.success(fileConfigService.findById(id));
    }

    /**
     * 根据类型查询
     */
    @GetMapping("/type/{type}")
    @PreAuthorize("@ps.hasPermission('admin:file:config:page')")
    public ResultVo<FileConfigVo> getByType(@PathVariable String type) {
        return ResultVo.success(fileConfigService.findByType(type));
    }
}
