package com.fastproject.module.system.controller;

import com.fastproject.idempotent.annotation.Idempotent;
import com.fastproject.logs.annotation.Log;
import com.fastproject.logs.enums.LogAction;
import com.fastproject.logs.enums.LogType;
import com.fastproject.module.system.service.AdminProfileService;
import com.fastproject.usergrowth.vo.UserGrowthAccountVo;
import com.fastproject.utils.TokenUtils;
import com.fastproject.system.service.SysUsersService;
import com.fastproject.system.vo.users.UserProfilePwdUpdate;
import com.fastproject.system.vo.users.UserProfileUpdate;
import com.fastproject.system.vo.users.UserProfileVo;
import com.fastproject.utils.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 个人中心接口
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/sys/profile")
public class ProfileController {

    private final SysUsersService sysUsersService;
    private final TokenUtils tokenUtils;
    private final AdminProfileService adminProfileService;

    /**
     * 获取个人信息
     */
    @GetMapping
    public ResultVo<UserProfileVo> getProfile() {
        Long userId = tokenUtils.getUser().getUserId();
        return ResultVo.success(sysUsersService.getUserProfile(userId));
    }

    /**
     * 获取个人成长账户信息（等级、积分）
     */
    @GetMapping("/growth")
    public ResultVo<UserGrowthAccountVo> getGrowthAccount() {
        Long userId = tokenUtils.getUser().getUserId();
        return ResultVo.success(adminProfileService.getUserGrowthAccount(userId));
    }

    /**
     * 更新个人信息
     */
    @PutMapping
    @Log(value = "更新个人信息", type = LogType.BUSINESS, action = LogAction.UPDATE)
    @Idempotent(prefix = "update:profile:", expireTime = 120, title = "更新个人信息")
    public ResultVo<Object> updateProfile(@Validated @RequestBody UserProfileUpdate update) {
        Long userId = tokenUtils.getUser().getUserId();
        sysUsersService.updateUserProfile(userId, update);
        return ResultVo.success();
    }

    /**
     * 更新个人密码
     */
    @PutMapping("/password")
    @Log(value = "更新个人密码", type = LogType.BUSINESS, action = LogAction.UPDATE)
    @Idempotent(prefix = "update:profile:password:", expireTime = 120, title = "更新个人密码")
    public ResultVo<Object> updatePassword(@Validated @RequestBody UserProfilePwdUpdate update) {
        Long userId = tokenUtils.getUser().getUserId();
        sysUsersService.updateProfilePassword(userId, update);
        return ResultVo.success();
    }
}