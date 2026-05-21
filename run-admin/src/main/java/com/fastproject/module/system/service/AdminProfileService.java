package com.fastproject.module.system.service;

import com.fastproject.usergrowth.vo.UserGrowthAccountVo;

public interface AdminProfileService {

    /**
     * 获取用户成长账户信息（等级、积分等）
     *
     * @param userId 用户ID
     * @return UserGrowthAccountVo
     */
    UserGrowthAccountVo getUserGrowthAccount(Long userId);

}
