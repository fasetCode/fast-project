package com.fastproject.usergrowth.service;

import com.fastproject.usergrowth.vo.integralaccount.UserIntegralAccountCreate;
import com.fastproject.usergrowth.vo.integralaccount.UserIntegralAccountQuery;
import com.fastproject.usergrowth.vo.integralaccount.UserIntegralAccountUpdate;
import com.fastproject.usergrowth.vo.integralaccount.UserIntegralAccountVo;
import com.fastproject.utils.vo.PageVo;

import java.util.List;

public interface UserIntegralAccountService {

    Long save(UserIntegralAccountCreate create);

    void update(UserIntegralAccountUpdate update);

    void delete(Long id);

    void batchDelete(List<Long> ids);

    UserIntegralAccountVo findById(Long id);

    PageVo<List<UserIntegralAccountVo>> findPage(UserIntegralAccountQuery query);

    UserIntegralAccountVo getAccountByUserId(Long userId);
}
