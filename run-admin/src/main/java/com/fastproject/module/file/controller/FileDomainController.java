package com.fastproject.module.file.controller;

import com.fastproject.file.service.FileDomainService;
import com.fastproject.file.vo.domain.FileDomainCreate;
import com.fastproject.file.vo.domain.FileDomainQuery;
import com.fastproject.file.vo.domain.FileDomainUpdate;
import com.fastproject.file.vo.domain.FileDomainVo;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.utils.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 文件域名 Controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/file/domain")
public class FileDomainController {

    private final FileDomainService fileDomainService;

    /**
     * 添加
     */
    @PostMapping
    @PreAuthorize("@ps.hasPermission('admin:file:domain:add')")
    public ResultVo<Object> add(@RequestBody FileDomainCreate create) {
        return ResultVo.success(fileDomainService.save(create));
    }

    /**
     * 修改
     */
    @PutMapping
    @PreAuthorize("@ps.hasPermission('admin:file:domain:update')")
    public ResultVo<Object> update(@RequestBody FileDomainUpdate update) {
        fileDomainService.update(update);
        return ResultVo.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:file:domain:delete')")
    public ResultVo<Object> delete(@PathVariable Long id) {
        fileDomainService.delete(id);
        return ResultVo.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/batch")
    @PreAuthorize("@ps.hasPermission('admin:file:domain:delete')")
    public ResultVo<Object> batchDelete(@RequestBody List<Long> ids) {
        fileDomainService.batchDelete(ids);
        return ResultVo.success();
    }

    /**
     * 分页
     */
    @PostMapping("/page")
    @PreAuthorize("@ps.hasPermission('admin:file:domain:page')")
    public ResultVo<PageVo<List<FileDomainVo>>> page(@RequestBody FileDomainQuery query) {
        return ResultVo.success(fileDomainService.findPage(query));
    }

    /**
     * 详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:file:domain:page')")
    public ResultVo<FileDomainVo> get(@PathVariable Long id) {
        return ResultVo.success(fileDomainService.findById(id));
    }

    /**
     * 根据域名查询
     */
    @GetMapping("/domain/{domain}")
    @PreAuthorize("@ps.hasPermission('admin:file:domain:page')")
    public ResultVo<FileDomainVo> getByDomain(@PathVariable String domain) {
        return ResultVo.success(fileDomainService.findByDomain(domain));
    }

    /**
     * 根据配置ID查询域名列表
     */
    @GetMapping("/config/{configId}")
    @PreAuthorize("@ps.hasPermission('admin:file:domain:page')")
    public ResultVo<List<FileDomainVo>> getByConfigId(@PathVariable Long configId) {
        return ResultVo.success(fileDomainService.findByConfigId(configId));
    }

    /**
     * 获取所有启用的域名
     */
    @GetMapping("/enabled")
    @PreAuthorize("@ps.hasPermission('admin:file:domain:page')")
    public ResultVo<List<FileDomainVo>> getAllEnabled() {
        return ResultVo.success(fileDomainService.findAllEnabled());
    }
}
