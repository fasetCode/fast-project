package com.fastproject.module.chat.service;

import com.fastproject.module.chat.domain.User;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.utils.vo.ResultVo;

import java.util.List;

public interface UserService {

    Long save(User user);

    void update(User user);

    void delete(Long id);

    void batchDelete(List<Long> ids);

    User findById(Long id);

    PageVo<List<User>> findPage(int page, int pageSize);

    long count();
}
