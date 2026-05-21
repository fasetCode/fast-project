package com.fastproject.usergrowth.api;

import com.fastproject.usergrowth.vo.UserGrowthAccountVo;

public interface UserGrowthApi {

    /**
     * 获取 等级 积分 数据
     */
    UserGrowthAccountVo getUserGrowthAccount(Long userId);

}
