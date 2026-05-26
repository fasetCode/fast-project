package com.fastproject.content.service;

import com.fastproject.db.PageQuery;
import com.fastproject.utils.vo.PageVo;

import java.util.List;

public interface CrudService<T> {

    Long save(T entity);

    void update(T entity);

    void delete(Long id);

    void batchDelete(List<Long> ids);

    T findById(Long id);

    PageVo<List<T>> findPage(PageQuery query);

    List<T> findAll();

    List<T> selectAll();
}

