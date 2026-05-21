package com.fastproject.module.chat.service;

import com.fastproject.module.chat.domain.Config;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.utils.vo.ResultVo;

import java.util.List;

public interface ConfigService {

    Long save(Config config);

    void update(Config config);

    void delete(Long id);

    void batchDelete(List<Long> ids);

    Config findById(Long id);

    PageVo<List<Config>> findPage(int page, int pageSize);

    long count();

    Config getConfig();
}
