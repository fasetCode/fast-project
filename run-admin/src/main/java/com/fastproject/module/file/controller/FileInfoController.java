package com.fastproject.module.file.controller;

import com.fastproject.file.service.FileInfoService;
import com.fastproject.file.vo.info.FileInfoCreate;
import com.fastproject.file.vo.info.FileInfoQuery;
import com.fastproject.file.vo.info.FileInfoUpdate;
import com.fastproject.file.vo.info.FileInfoVo;
import com.fastproject.file.vo.info.FileTypeStatVo;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.utils.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 文件信息 Controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/file/info")
public class FileInfoController {

    private final FileInfoService fileInfoService;

    /**
     * 文件类型统计
     */
    @GetMapping("/type-stats")
    @PreAuthorize("@ps.hasPermission('admin:file:info:page')")
    public ResultVo<List<FileTypeStatVo>> getTypeStats() {
        return ResultVo.success(fileInfoService.getTypeStats());
    }

    /**
     * 添加
     */
    @PostMapping
    @PreAuthorize("@ps.hasPermission('admin:file:info:add')")
    public ResultVo<Object> add(@RequestBody FileInfoCreate create) {
        return ResultVo.success(fileInfoService.save(create));
    }

    /**
     * 修改
     */
    @PutMapping
    @PreAuthorize("@ps.hasPermission('admin:file:info:update')")
    public ResultVo<Object> update(@RequestBody FileInfoUpdate update) {
        fileInfoService.update(update);
        return ResultVo.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:file:info:delete')")
    public ResultVo<Object> delete(@PathVariable Long id) {
        fileInfoService.delete(id);
        return ResultVo.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/batch")
    @PreAuthorize("@ps.hasPermission('admin:file:info:delete')")
    public ResultVo<Object> batchDelete(@RequestBody List<Long> ids) {
        fileInfoService.batchDelete(ids);
        return ResultVo.success();
    }

    /**
     * 分页
     */
    @PostMapping("/page")
    @PreAuthorize("@ps.hasPermission('admin:file:info:page')")
    public ResultVo<PageVo<List<FileInfoVo>>> page(@RequestBody FileInfoQuery query) {
        return ResultVo.success(fileInfoService.findPage(query));
    }

    /**
     * 详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:file:info:page')")
    public ResultVo<FileInfoVo> get(@PathVariable Long id) {
        return ResultVo.success(fileInfoService.findById(id));
    }

    /**
     * 根据文件MD5查询
     */
    @GetMapping("/md5/{fileMd5}")
    @PreAuthorize("@ps.hasPermission('admin:file:info:page')")
    public ResultVo<FileInfoVo> getByMd5(@PathVariable String fileMd5) {
        return ResultVo.success(fileInfoService.findByFileMd5(fileMd5));
    }
}
