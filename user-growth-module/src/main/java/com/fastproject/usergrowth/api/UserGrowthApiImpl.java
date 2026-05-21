package com.fastproject.usergrowth.api;

import com.fastproject.usergrowth.service.UserIntegralAccountService;
import com.fastproject.usergrowth.service.UserLevelAccountService;
import com.fastproject.usergrowth.service.UserLevelConfigService;
import com.fastproject.usergrowth.vo.UserGrowthAccountVo;
import com.fastproject.usergrowth.vo.integralaccount.UserIntegralAccountVo;
import com.fastproject.usergrowth.vo.levelaccount.UserLevelAccountVo;
import com.fastproject.usergrowth.vo.levelconfig.QueryLevelConfigVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserGrowthApiImpl implements UserGrowthApi {

    private final UserLevelAccountService userLevelAccountService;
    private final UserIntegralAccountService userIntegralAccountService;
    private final UserLevelConfigService userLevelConfigService;

    @Override
    public UserGrowthAccountVo getUserGrowthAccount(Long userId) {

        UserLevelAccountVo levelAccount = userLevelAccountService.getAccountByUserId(userId);
        UserIntegralAccountVo integralAccount = userIntegralAccountService.getAccountByUserId(userId);
        QueryLevelConfigVo levelConfigVo = userLevelConfigService.getAccountByGrowthValue(levelAccount.getGrowthValue());

        UserGrowthAccountVo accountVo = new UserGrowthAccountVo();
        accountVo.setGrowthValue(levelAccount.getGrowthValue());
        accountVo.setIntegral(integralAccount.getIntegral());

        if (levelConfigVo!=null) {
            UserGrowthAccountVo.LevelVo levelVo = new UserGrowthAccountVo.LevelVo();
            BeanUtils.copyProperties(levelConfigVo.getLevelConfigVo(), levelVo);
            accountVo.setLevel(levelVo);
            if (levelConfigVo.getNextLevelConfigVo()!=null) {
                UserGrowthAccountVo.LevelVo nextLevelVo = new UserGrowthAccountVo.LevelVo();
                BeanUtils.copyProperties(levelConfigVo.getNextLevelConfigVo(), nextLevelVo);
                accountVo.setNextLevel(nextLevelVo);
            }
        }


        return accountVo;
    }
}
