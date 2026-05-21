package com.fastproject.usergrowth.service;

import com.fastproject.usergrowth.vo.integralrecord.UserIntegralRecordCreate;
import com.fastproject.usergrowth.vo.integralrecord.UserIntegralRecordQuery;
import com.fastproject.usergrowth.vo.integralrecord.UserIntegralRecordUpdate;
import com.fastproject.usergrowth.vo.integralrecord.UserIntegralRecordVo;
import com.fastproject.utils.vo.PageVo;

import java.util.List;

public interface UserIntegralRecordService {

    Long save(UserIntegralRecordCreate create);

    void update(UserIntegralRecordUpdate update);

    void delete(Long id);

    void batchDelete(List<Long> ids);

    UserIntegralRecordVo findById(Long id);

    PageVo<List<UserIntegralRecordVo>> findPage(UserIntegralRecordQuery query);
}
