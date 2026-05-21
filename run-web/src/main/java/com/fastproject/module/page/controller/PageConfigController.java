package com.fastproject.module.page.controller;

import com.fastproject.module.page.service.WebPageService;
import com.fastproject.utils.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/common/page/config")
@RestController
@RequiredArgsConstructor
public class PageConfigController {

    private final WebPageService pageService;

    @GetMapping("/getByPath")
    public ResultVo<String> getByPath(@RequestParam String path,
                                      @RequestParam String appCode) {
        return ResultVo.success(pageService.getPathPage(path, appCode));
    }

}
