package com.fastproject.module.security;

import com.fastproject.utils.TokenUtils;
import com.fastproject.vo.SecurityUserVo;
import com.fastproject.system.service.SysPermissionsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * 自定义权限实现
 */
@Service("ps")
@Slf4j
@RequiredArgsConstructor
public class PermissionService {

    private final SysPermissionsService permissionsService;
    private final TokenUtils tokenUtils;

    /**
     * 判断接口是否有权限
     *
     * @param permission 权限字符串
     * @return true/false
     */
    public boolean hasPermission(String permission) {
        if (permission == null || permission.isEmpty()) {
            return false;
        }
        SecurityUserVo user = tokenUtils.getUser();

        // 超级管理员直接放行 (这里假设ID为1是超级管理员，或者根据业务逻辑调整)
        if (user.getUserId() != null && user.getUserId() == 1L) {
            return true;
        }
        return !CollectionUtils.isEmpty(user.getPermissions()) && user.getPermissions().contains(permission);
    }
}
