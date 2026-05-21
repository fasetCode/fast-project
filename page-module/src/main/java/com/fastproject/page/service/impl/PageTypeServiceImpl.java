package com.fastproject.page.service.impl;

import com.fastproject.db.QueryHelp;
import com.fastproject.exception.BusinessException;
import com.fastproject.page.domain.PageType;
import com.fastproject.page.mapper.PageTypeMapper;
import com.fastproject.page.repository.db.PageTypeRepository;
import com.fastproject.page.service.PageTypeService;
import com.fastproject.page.vo.pagetype.PageTypeUpdate;
import com.fastproject.page.vo.pagetype.PageTypeCreate;
import com.fastproject.page.vo.pagetype.PageTypeQuery;
import com.fastproject.page.vo.pagetype.PageTypeVo;
import com.fastproject.utils.enums.StatusEnum;
import com.fastproject.utils.vo.PageVo;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PageTypeServiceImpl implements PageTypeService {

    private final PageTypeRepository pageTypeRepository;
    private final QueryHelp<PageType> queryHelp;
    private final PageTypeMapper pageTypeMapper;

    @Override
    public Long save(PageTypeCreate create) {
        log.info("保存页面类型信息：{}", create);
        
        if (pageTypeRepository.existsByCode(create.getCode())) {
            throw new BusinessException("类型代码已存在");
        }

        PageType pageType = pageTypeMapper.toPageType(create);
        pageTypeRepository.save(pageType);
        return pageType.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(PageTypeUpdate update) {
        log.info("修改页面类型信息：{}", update);
        PageType pageType = pageTypeRepository.findById(update.getId())
                .orElseThrow(() -> new BusinessException("类型不存在"));

        if (pageTypeRepository.existsByCodeAndIdNot(update.getCode(), update.getId())) {
            throw new BusinessException("类型代码已存在");
        }

        pageTypeMapper.updatePageTypeFromDto(update, pageType);
        pageTypeRepository.save(pageType);
    }

    @Override
    public void delete(Long id) {
        log.info("删除页面类型信息：{}", id);
        PageType pageType = pageTypeRepository.findById(id)
                .orElseThrow(() -> new BusinessException("类型不存在"));
        pageTypeRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> ids) {
        log.info("批量删除页面类型信息：{}", ids);
        pageTypeRepository.deleteAllById(ids);
    }

    @Override
    public PageTypeVo findById(Long id) {
        log.info("根据ID查询页面类型信息：{}", id);
        PageType pageType = pageTypeRepository.findById(id).orElse(null);
        if (pageType != null) {
            return pageTypeMapper.toVo(pageType);
        }
        return null;
    }

    @Override
    public PageVo<List<PageTypeVo>> findPage(PageTypeQuery query) {
        log.info("分页查询页面类型信息：{}", query);
        Pageable pageable = PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("id").descending());

        Specification<PageType> spec = queryHelp.getWhere(query, (root, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(query.getTitle())) {
                predicates.add(cb.like(root.get("title"), "%" + query.getTitle() + "%"));
            }
            if (StringUtils.hasText(query.getCode())) {
                predicates.add(cb.equal(root.get("code"), query.getCode()));
            }
            if (query.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), query.getStatus()));
            }
            return predicates;
        });

        Page<PageType> page = pageTypeRepository.findAll(spec, pageable);
        List<PageTypeVo> list = pageTypeMapper.toVo(page.getContent());

        return PageVo.of(page.getTotalElements(), list);
    }

    @Override
    public List<PageTypeVo> findAll() {
        List<PageType> all = pageTypeRepository.findAll(Sort.by("id").descending());
        return pageTypeMapper.toVo(all);
    }

    @Override
    public List<PageTypeVo> selectAll() {
        Specification<PageType> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("status"), StatusEnum.NORMAL.getCode()));
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        List<PageType> all = pageTypeRepository.findAll(spec, Sort.by("id").descending());
        return pageTypeMapper.toVo(all);
    }
}
