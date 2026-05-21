package com.fastproject.module.chat.controller;

import com.fastproject.module.chat.domain.User;
import com.fastproject.module.chat.service.UserService;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.utils.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResultVo<Object> add(@RequestBody User user) {
        return ResultVo.success(userService.save(user));
    }

    @PutMapping
    public ResultVo<Object> update(@RequestBody User user) {
        userService.update(user);
        return ResultVo.success();
    }

    @DeleteMapping("/{id}")
    public ResultVo<Object> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResultVo.success();
    }

    @DeleteMapping("/batch")
    public ResultVo<Object> batchDelete(@RequestBody List<Long> ids) {
        userService.batchDelete(ids);
        return ResultVo.success();
    }

    @PostMapping("/page")
    public ResultVo<PageVo<List<User>>> page(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int pageSize) {
        return ResultVo.success(userService.findPage(page, pageSize));
    }

    @GetMapping("/{id}")
    public ResultVo<User> get(@PathVariable Long id) {
        return ResultVo.success(userService.findById(id));
    }
}
