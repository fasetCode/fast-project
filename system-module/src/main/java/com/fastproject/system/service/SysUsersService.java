package com.fastproject.system.service;

import com.fastproject.system.vo.users.*;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.vo.SysUsersLoginVo;

import java.util.List;

public interface SysUsersService {

    /**
     * 添加
     */
    Long save(SysUsersCreate create);

    /**
     * 修改
     */
    void update(SysUserUpdate sysUserUpdate);

    /**
     * 删除
     */
    void delete(Long id);

    /**
     * 批量删除
     */
    void batchDelete(List<Long> ids);

    /**
     * 根据ID查询详情（包含角色）
     */
    SysUsersDetailVo findById(Long id);

    /**
     * 分页查询
     */
    PageVo<List<SysUsersVo>> findPage(SysUsersQuery query);

    /**
     * 根据用户名查询用户详情
     */
    SysUsersLoginVo getLoginByUsername(String username);

    /**
     * 根据用户ID获取用户基本信息（包含头像URL）
     */
    UserInfoVo getUserInfoById(Long userId);

    /**
     * 修改密码
     */
    void updatePassword(SysUserPasswordUpdate passwordUpdate);

    /**
     * 获取个人中心信息
     */
    UserProfileVo getUserProfile(Long userId);

    /**
     * 更新个人中心信息
     */
    void updateUserProfile(Long userId, UserProfileUpdate update);

    /**
     * 个人中心修改密码
     */
    void updateProfilePassword(Long userId, UserProfilePwdUpdate update);

    /**
     * 模糊搜索用户
     */
    List<SysUsersVo> searchUsers(String keyword, Integer limit);
}
