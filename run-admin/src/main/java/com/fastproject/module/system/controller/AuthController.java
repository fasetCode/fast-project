package com.fastproject.module.system.controller;

import com.fastproject.idempotent.annotation.Idempotent;
import com.fastproject.logs.annotation.Log;
import com.fastproject.logs.enums.LogAction;
import com.fastproject.logs.enums.LogType;
import com.fastproject.utils.TokenUtils;
import com.fastproject.module.system.service.AuthService;
import com.fastproject.module.system.vo.CaptchaVo;
import com.fastproject.module.system.vo.LoginDto;
import com.fastproject.system.service.SysDictTypeService;
import com.fastproject.system.service.SysPermissionsService;
import com.fastproject.system.service.SysUsersService;
import com.fastproject.system.vo.dictdata.Dict;
import com.fastproject.system.vo.route.RouteVo;
import com.fastproject.system.vo.users.UserInfoVo;
import com.fastproject.utils.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final SysPermissionsService sysPermissionsService;
    private final SysDictTypeService sysDictTypeService;
    private final AuthService authService;
    private final TokenUtils tokenUtils;
    private final SysUsersService sysUsersService;

    /**
     * 获取所有路由数据（动态路由）
     * 
     * @return 路由数据树
     */
    @GetMapping("/getAsyncRoutes")
    public ResultVo<List<RouteVo>> getAsyncRoutes() {
        return ResultVo.success(authService.findTree());
    }

    @PostMapping("/login")
    @Log(value = "用户登录", type = LogType.LOGIN, action = LogAction.LOGIN)
    @Idempotent(prefix = "auth:login:", expireTime = 120, title = "用户登录")
    public ResultVo<String> login(@RequestBody LoginDto loginDto) {
        return ResultVo.success(authService.login(loginDto));
    }

    @PostMapping("/logout")
    @Log(value = "用户退出", type = LogType.LOGIN, action = LogAction.LOGOUT)
    public ResultVo<Object> logout() {
        authService.logout();
        return ResultVo.success();
    }

    /**
     * 获取当前用户的按钮权限
     *
     * @return 按钮权限列表
     */
    @GetMapping("/getButtonPermissions")
    public ResultVo<List<String>> getButtonPermissions() {
        List<String> permissions =  authService.findButtonPermissions();
        return ResultVo.success(permissions);
    }

    @GetMapping("/getDict")
    public ResultVo<List<Dict>> getDict() {
        return ResultVo.success(sysDictTypeService.findAllDict());
    }

    /**
     * 获取当前登录用户基本信息
     *
     * @return 用户基本信息（ID、用户名、昵称、头像）
     */
    @GetMapping("/getUserInfo")
    public ResultVo<UserInfoVo> getUserInfo() {
        Long userId = tokenUtils.getUser().getUserId();
        UserInfoVo userInfo = sysUsersService.getUserInfoById(userId);
        return ResultVo.success(userInfo);
    }

    /**
     * 生成验证码
     * 生成数学运算验证码，将答案存入Redis（5分钟过期）
     *
     * @return 验证码图片Base64和验证码Key
     */
    @GetMapping("/captcha")
    public ResultVo<CaptchaVo> generateCaptcha() {
        return ResultVo.success(authService.generateCaptcha());
    }

}
