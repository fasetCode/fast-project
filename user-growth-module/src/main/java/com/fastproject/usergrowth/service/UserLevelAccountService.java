package com.fastproject.usergrowth.service;

import com.fastproject.usergrowth.vo.levelaccount.UserLevelAccountCreate;
import com.fastproject.usergrowth.vo.levelaccount.UserLevelAccountQuery;
import com.fastproject.usergrowth.vo.levelaccount.UserLevelAccountUpdate;
import com.fastproject.usergrowth.vo.levelaccount.UserLevelAccountVo;
import com.fastproject.utils.vo.PageVo;

import java.util.List;

public interface UserLevelAccountService {

    Long save(UserLevelAccountCreate create);

    void update(UserLevelAccountUpdate update);

    void delete(Long id);

    void batchDelete(List<Long> ids);

    UserLevelAccountVo findById(Long id);

    PageVo<List<UserLevelAccountVo>> findPage(UserLevelAccountQuery query);

    UserLevelAccountVo getAccountByUserId(Long userId);
}
