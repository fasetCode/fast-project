package com.fastproject.module.system.controller;

import com.fastproject.logs.annotation.Log;
import com.fastproject.system.service.SlowQueryLogService;
import com.fastproject.system.vo.slowquery.SlowQueryLogQuery;
import com.fastproject.system.vo.slowquery.SlowQueryLogVo;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.utils.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 慢查询日志控制层
 */
@RestController
@RequestMapping("/api/system/slow-query")
@RequiredArgsConstructor
public class SlowQueryLogController {

    private final SlowQueryLogService slowQueryLogService;

    @PostMapping
    @PreAuthorize("@ps.hasPermission('monitor:slow-query:list')")
    public ResultVo<PageVo<List<SlowQueryLogVo>>> list(@RequestBody SlowQueryLogQuery query) {
        return ResultVo.success(slowQueryLogService.findPage(query));
    }

    @DeleteMapping
    @PreAuthorize("@ps.hasPermission('monitor:slow-query:delete')")
    @Log(value = "删除慢查询日志")
    public ResultVo<Void> delete(@RequestBody List<Long> ids) {
        slowQueryLogService.batchDelete(ids);
        return ResultVo.success();
    }

    @PutMapping
    @PreAuthorize("@ps.hasPermission('monitor:slow-query:update')")
    @Log(value = "修改慢查询日志")
    public ResultVo<Void> update(@RequestBody SlowQueryLogVo vo) {
        slowQueryLogService.update(vo);
        return ResultVo.success();
    }
}
