package com.fastproject.controller;

import com.fastproject.domain.Application;
import com.fastproject.service.ApplicationService;
import com.fastproject.utils.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/apps")
@RequiredArgsConstructor
public class AppController {

    private final ApplicationService applicationService;

    @PostMapping("/create")
    @ResponseBody
    public ResultVo<Application> create(@ModelAttribute Application app, HttpSession session) {
        if (session.getAttribute("loginUser") == null) {
            return ResultVo.fail("未登录");
        }

        Application saved = applicationService.create(app);
        return ResultVo.success(saved);
    }

    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("loginUser") == null) {
            return "redirect:/login";
        }
        return "edit_app";
    }

    @GetMapping("/api/{id}")
    @ResponseBody
    public ResultVo<Application> getApp(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("loginUser") == null) {
            return ResultVo.fail("未登录");
        }
        return applicationService.findById(id)
                .map(ResultVo::success)
                .orElse(ResultVo.fail("应用不存在"));
    }

    @PostMapping("/update")
    @ResponseBody
    public ResultVo<Application> update(@ModelAttribute Application app, HttpSession session) {
        if (session.getAttribute("loginUser") == null) {
            return ResultVo.fail("未登录");
        }
        Application updated = applicationService.update(app);
        return ResultVo.success(updated);
    }

    @GetMapping("/delete/{id}")
    @ResponseBody
    public ResultVo<String> delete(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("loginUser") == null) {
            return ResultVo.fail("未登录");
        }
        applicationService.deleteById(id);
        return ResultVo.success("删除成功");
    }
}
