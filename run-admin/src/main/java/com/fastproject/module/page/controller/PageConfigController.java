package com.fastproject.module.page.controller;

import com.fastproject.idempotent.annotation.Idempotent;
import com.fastproject.logs.annotation.Log;
import com.fastproject.logs.enums.LogAction;
import com.fastproject.logs.enums.LogType;
import com.fastproject.page.service.PageComponentService;
import com.fastproject.page.service.PageConfigService;
import com.fastproject.page.vo.pagecomponent.PageComponentVo;
import com.fastproject.page.vo.pageconfig.*;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.utils.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 页面配置 Controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/page/config")
public class PageConfigController {

    private final PageConfigService pageConfigService;
    private final PageComponentService pageComponentService;

    /**
     * 查询页面列表
     * @param applicationId 应用ID
     * @return 列表数据
     */
    @GetMapping("/list/{applicationId}")
    @PreAuthorize("@ps.hasPermission('admin:page:application:config')")
    public ResultVo<List<PageConfigListVo>> list(@PathVariable("applicationId") Long applicationId){
        return ResultVo.success(pageConfigService.findAllList(applicationId));
    }

    /**
     * 查询查询页面
     */
    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('admin:page:application:config')")
    public ResultVo<PageConfigVo> findById(@PathVariable("id") Long id){
        return ResultVo.success(pageConfigService.findById(id));
    }

    /**
     * 新增页面
     */
    @PostMapping
    @PreAuthorize("@ps.hasPermission('admin:page:application:config')")
    @Idempotent
    @Log(value = "新增页面", type = LogType.BUSINESS, action = LogAction.CREATE)
    public ResultVo<Object> save(@RequestBody PageConfigCreate create){
        return ResultVo.success(pageConfigService.save(create));
    }

    /**
     * 修改页面
     */
    @PutMapping
    @PreAuthorize("@ps.hasPermission('admin:page:application:config')")
    @Idempotent
    @Log(value = "修改页面", type = LogType.BUSINESS, action = LogAction.UPDATE)
    public ResultVo<Object> update(@RequestBody PageConfigUpdate update){
        pageConfigService.update(update);
        return ResultVo.success();
    }

    /**
     * 组件列表
     */
    @GetMapping("/components/{typeId}")
    @PreAuthorize("@ps.hasPermission('admin:page:application:config')")
    public ResultVo<List<PageComponentVo>> findByComponents(@PathVariable("typeId") Long typeId){
        return ResultVo.success(pageComponentService.findByTypeId(typeId));
    }

    /**
     * 发布 页面
     */
    @PostMapping("/publish")
    @PreAuthorize("@ps.hasPermission('admin:page:application:config')")
    @Idempotent
    @Log(value = "发布页面", type = LogType.BUSINESS, action = LogAction.CREATE)
    public ResultVo<Object> publish(@RequestBody PageConfigUpdate update){
        pageConfigService.publish(update);
        return ResultVo.success();
    }
}
