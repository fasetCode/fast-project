package com.fastproject.usergrowth.service;

import com.fastproject.usergrowth.vo.levelrecord.UserLevelRecordCreate;
import com.fastproject.usergrowth.vo.levelrecord.UserLevelRecordQuery;
import com.fastproject.usergrowth.vo.levelrecord.UserLevelRecordUpdate;
import com.fastproject.usergrowth.vo.levelrecord.UserLevelRecordVo;
import com.fastproject.utils.vo.PageVo;

import java.util.List;

public interface UserLevelRecordService {

    Long save(UserLevelRecordCreate create);

    void update(UserLevelRecordUpdate update);

    void delete(Long id);

    void batchDelete(List<Long> ids);

    UserLevelRecordVo findById(Long id);

    PageVo<List<UserLevelRecordVo>> findPage(UserLevelRecordQuery query);
}
