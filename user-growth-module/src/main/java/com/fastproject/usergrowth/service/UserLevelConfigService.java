package com.fastproject.usergrowth.service;

import com.fastproject.usergrowth.vo.levelconfig.*;
import com.fastproject.utils.vo.PageVo;

import java.util.List;

public interface UserLevelConfigService {

    Long save(UserLevelConfigCreate create);

    void update(UserLevelConfigUpdate update);

    void delete(Long id);

    void batchDelete(List<Long> ids);

    UserLevelConfigVo findById(Long id);

    PageVo<List<UserLevelConfigVo>> findPage(UserLevelConfigQuery query);

    QueryLevelConfigVo getAccountByGrowthValue(Long growthValue);
}
