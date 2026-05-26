package com.fastproject.content.service.impl;

import com.fastproject.content.service.CrudService;
import com.fastproject.db.BaseEntity;
import com.fastproject.db.PageQuery;
import com.fastproject.exception.BusinessException;
import com.fastproject.utils.vo.PageVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public abstract class AbstractCrudServiceImpl<T extends BaseEntity, R extends JpaRepository<T, Long> & JpaSpecificationExecutor<T>>
        implements CrudService<T> {

    protected final R repository;

    protected AbstractCrudServiceImpl(R repository) {
        this.repository = repository;
    }

    @Override
    public Long save(T entity) {
        entity.setId(null);
        repository.save(entity);
        return entity.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(T entity) {
        if (entity.getId() == null) {
            throw new BusinessException("ID不能为空");
        }
        if (!repository.existsById(entity.getId())) {
            throw new BusinessException("数据不存在");
        }
        repository.save(entity);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> ids) {
        repository.deleteAllById(ids);
    }

    @Override
    public T findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public PageVo<List<T>> findPage(PageQuery query) {
        Pageable pageable = PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("id").descending());
        Page<T> page = repository.findAll(pageable);
        return PageVo.of(page.getTotalElements(), page.getContent());
    }

    @Override
    public List<T> findAll() {
        return repository.findAll(Sort.by("id").descending());
    }

    @Override
    public List<T> selectAll() {
        return findAll();
    }
}

