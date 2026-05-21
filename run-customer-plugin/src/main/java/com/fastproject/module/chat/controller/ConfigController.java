package com.fastproject.module.chat.controller;

import com.fastproject.module.chat.domain.Config;
import com.fastproject.module.chat.service.ConfigService;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.utils.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat/config")
public class ConfigController {

    private final ConfigService configService;

    @PostMapping
    public ResultVo<Object> add(@RequestBody Config config) {
        return ResultVo.success(configService.save(config));
    }

    @PutMapping
    public ResultVo<Object> update(@RequestBody Config config) {
        configService.update(config);
        return ResultVo.success();
    }

    @DeleteMapping("/{id}")
    public ResultVo<Object> delete(@PathVariable Long id) {
        configService.delete(id);
        return ResultVo.success();
    }

    @DeleteMapping("/batch")
    public ResultVo<Object> batchDelete(@RequestBody List<Long> ids) {
        configService.batchDelete(ids);
        return ResultVo.success();
    }

    @PostMapping("/page")
    public ResultVo<PageVo<List<Config>>> page(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int pageSize) {
        return ResultVo.success(configService.findPage(page, pageSize));
    }

    @GetMapping("/{id}")
    public ResultVo<Config> get(@PathVariable Long id) {
        return ResultVo.success(configService.findById(id));
    }
}
