package com.fastproject.module.system.service.impl;

import com.fastproject.exception.BusinessException;
import com.fastproject.jedis.JedisTemplate;
import com.fastproject.system.service.SysRoleService;
import com.fastproject.utils.enums.RoleEnum;
import com.fastproject.utils.enums.StatusEnum;
import com.fastproject.utils.TokenUtils;
import com.fastproject.utils.utils.SvgCaptchaUtils;
import com.fastproject.vo.SecurityUserVo;
import com.fastproject.module.system.service.AuthService;
import com.fastproject.module.system.vo.CaptchaVo;
import com.fastproject.module.system.vo.LoginDto;
import com.fastproject.system.service.SysPermissionsService;
import com.fastproject.system.service.SysUsersService;
import com.fastproject.system.vo.permissions.SysPermissionsVo;
import com.fastproject.system.vo.route.RouteMeta;
import com.fastproject.system.vo.route.RouteVo;
import com.fastproject.vo.SysUsersLoginVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final SysUsersService sysUsersService;
    private final SysPermissionsService sysPermissionsService;
    private final TokenUtils tokenUtils;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JedisTemplate jedisTemplate;

    @Override
    public String login(LoginDto loginDto) {
        // 验证验证码
        validateCaptcha(loginDto.getCaptchaKey(), loginDto.getCaptcha());

        SysUsersLoginVo usersLoginVo = sysUsersService.getLoginByUsername(loginDto.getUsername());

        if (usersLoginVo == null) {
            throw new BusinessException("用户名或密码错误");
        }


        // 验证密码
        if (!passwordEncoder.matches(loginDto.getPassword(), usersLoginVo.getPassword())) {
            throw new BusinessException("用户名或密码错误");
        }

        String adminRole = "";
        if (usersLoginVo.getRoles()!=null) {
            for (SysUsersLoginVo.SysRoleVo role : usersLoginVo.getRoles()) {
                if (StatusEnum.NORMAL.getCode().equals(role.getStatus())&& RoleEnum.ADMIN.getCode().equals(role.getCode())) {
                    adminRole = role.getCode();
                }
            }
        }
        List<String> buttonPermissionsByUserId;
        if (!RoleEnum.ADMIN.getCode().equals(adminRole)) {
            buttonPermissionsByUserId = sysPermissionsService.findAllPermissionsByUserId(usersLoginVo.getId());
        }else{
            buttonPermissionsByUserId = sysPermissionsService.findAllPermissionsByAll();
        }

        return tokenUtils.loginToken(usersLoginVo.getId(), usersLoginVo,
                new HashSet<>(buttonPermissionsByUserId));
    }

    /**
     * 验证验证码
     */
    private void validateCaptcha(String captchaKey, String captcha) {
        if (captchaKey == null || captcha == null || captchaKey.isEmpty() || captcha.isEmpty()) {
            throw new BusinessException("请输入验证码");
        }

        String redisKey = "captcha:" + captchaKey;
        String correctAnswer = jedisTemplate.get(redisKey);

        if (correctAnswer == null) {
            throw new BusinessException("验证码已过期，请重新获取");
        }

        // 验证完成后删除验证码，防止重复使用
        jedisTemplate.del(redisKey);

        if (!correctAnswer.toLowerCase().equals(captcha.toLowerCase().trim())) {
            throw new BusinessException("验证码错误");
        }
    }


    @Override
    public void logout() {
        tokenUtils.logoutToken();
    }

    @Override
    public List<RouteVo> buildRouteTree(List<SysPermissionsVo> permissionsList) {
        if (permissionsList == null || permissionsList.isEmpty()) {
            return new ArrayList<>();
        }

        List<RouteVo> routes = new ArrayList<>();
        for (SysPermissionsVo permission : permissionsList) {
            // 过滤掉按钮类型的权限，通常 0:目录 1:菜单 2:按钮，路由中不需要展示按钮
            if (permission.getType() != null && permission.getType() == 2) {
                continue;
            }

            RouteVo routeVo = new RouteVo();
            // 路由名称，必须唯一
            routeVo.setName(permission.getComponentName());
            // 路由路径
            routeVo.setPath(permission.getUrl());
            // 组件路径
            routeVo.setComponent(permission.getComponent());

            // 路由元信息
            RouteMeta meta = new RouteMeta();
            meta.setTitle(permission.getTitle());
            meta.setIcon(permission.getIcon());
            meta.setRank(permission.getSort());
            // 根据状态判断是否在菜单中显示（假设 status 为 1 时正常显示）
            meta.setShowLink(permission.getStatus() == null || permission.getStatus() == 1);
            routeVo.setMeta(meta);

            // 递归处理子节点
            if (permission.getChildren() != null && !permission.getChildren().isEmpty()) {
                routeVo.setChildren(buildRouteTree(permission.getChildren()));
            }
            routes.add(routeVo);
        }

        return routes;
    }

    @Override
    public List<RouteVo> findTree() {
        SecurityUserVo user = tokenUtils.getUser();
        if (user.getUserId() != null && user.getUserId() == 1L) {
            return buildRouteTree(sysPermissionsService.findTree());
        }
        return buildRouteTree(sysPermissionsService.findTreeByUserId(user.getUserId()));
    }

    @Override
    public CaptchaVo generateCaptcha() {
        String captchaKey = UUID.randomUUID().toString().replace("-", "");

        // 生成 4 位随机字符验证码 (排除容易混淆的字符如 o, 0, 1, i, l)
        String chars = "23456789abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ";
        StringBuilder codeBuilder = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            codeBuilder.append(chars.charAt((int)(Math.random() * chars.length())));
        }
        String code = codeBuilder.toString();
        // 使用自定义方法生成 SVG Base64 图片
        String svgBase64 = SvgCaptchaUtils.generateSvgCaptcha(code);
        // 将答案存入 Redis，5 分钟过期
        jedisTemplate.setex("captcha:" + captchaKey, code.toLowerCase(), 300);

        return CaptchaVo.builder()
                .captchaKey(captchaKey)
                .captchaImage(svgBase64)
                .build();
    }

    @Override
    public List<String> findButtonPermissions() {
        SysUsersLoginVo user = tokenUtils.getUser().getUser();
        String adminRole = "";
        if (user.getRoles()!=null) {
            for (SysUsersLoginVo.SysRoleVo role : user.getRoles()) {
                if (StatusEnum.NORMAL.getCode().equals(role.getStatus())&& RoleEnum.ADMIN.getCode().equals(role.getCode())) {
                    adminRole = role.getCode();
                }
            }
        }
        if (!RoleEnum.ADMIN.getCode().equals(adminRole)) {
            return sysPermissionsService.findButtonPermissionsByUserId(user.getId());
        }
        return sysPermissionsService.findButtonPermissionsByALL();
    }
}
