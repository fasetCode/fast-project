package com.fastproject.module.system.service;

import com.fastproject.module.system.vo.CaptchaVo;
import com.fastproject.module.system.vo.LoginDto;
import com.fastproject.system.vo.route.RouteVo;

import java.util.List;

public interface AuthService {
    /**
     * 登录
     * @param loginDto 登录信息
     * @return token
     */
    String login(LoginDto loginDto);

    /**
     * 退出登录
     */
    void logout();

    /**
     * 构建路由树
     * @param permissionsList 权限树列表
     * @return 路由树列表
     */
    List<RouteVo> buildRouteTree(List<com.fastproject.system.vo.permissions.SysPermissionsVo> permissionsList);

    /**
     * 获取所有路由数据（静态+动态）
     * @return 路由数据树
     */
    List<RouteVo> findTree();

    /**
     * 生成验证码
     * @return 验证码信息（包含captchaKey和captchaImage）
     */
    CaptchaVo generateCaptcha();


    /**
     * 获取按钮权限
     * @return 按钮权限列表
     */
    List<String> findButtonPermissions();
}
