package com.fastproject.module.system.service.impl;

import com.fastproject.module.system.service.AdminProfileService;
import com.fastproject.usergrowth.api.UserGrowthApi;
import com.fastproject.usergrowth.vo.UserGrowthAccountVo;
import com.fastproject.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminProfileServiceImpl implements AdminProfileService {

    private final UserGrowthApi userGrowthApi;
    private final TokenUtils tokenUtils;

    @Override
    public UserGrowthAccountVo getUserGrowthAccount(Long userId) {
        return userGrowthApi.getUserGrowthAccount(userId);
    }
}
