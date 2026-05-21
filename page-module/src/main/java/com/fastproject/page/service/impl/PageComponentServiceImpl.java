package com.fastproject.page.service.impl;

import com.fastproject.db.QueryHelp;
import com.fastproject.exception.BusinessException;
import com.fastproject.page.domain.PageComponent;
import com.fastproject.page.domain.PageType;
import com.fastproject.page.mapper.PageComponentMapper;
import com.fastproject.page.repository.db.PageComponentRepository;
import com.fastproject.page.repository.db.PageTypeRepository;
import com.fastproject.page.service.PageComponentService;
import com.fastproject.page.vo.pagecomponent.PageComponentUpdate;
import com.fastproject.page.vo.pagecomponent.PageComponentCreate;
import com.fastproject.page.vo.pagecomponent.PageComponentQuery;
import com.fastproject.page.vo.pagecomponent.PageComponentVo;
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
public class PageComponentServiceImpl implements PageComponentService {

    private final PageComponentRepository pageComponentRepository;
    private final PageTypeRepository pageTypeRepository;
    private final QueryHelp<PageComponent> queryHelp;
    private final PageComponentMapper pageComponentMapper;

    @Override
    public Long save(PageComponentCreate create) {
        log.info("保存页面组件信息：{}", create);
        
        if (pageComponentRepository.existsByCode(create.getCode())) {
            throw new BusinessException("组件代码已存在");
        }

        PageComponent component = pageComponentMapper.toPageComponent(create);
        if (create.getTypeId() != null) {
            PageType type = pageTypeRepository.findById(create.getTypeId())
                    .orElseThrow(() -> new BusinessException("类型不存在"));
            component.setType(type);
        }
        pageComponentRepository.save(component);
        return component.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(PageComponentUpdate update) {
        log.info("修改页面组件信息：{}", update);
        PageComponent component = pageComponentRepository.findById(update.getId())
                .orElseThrow(() -> new BusinessException("组件不存在"));

        if (pageComponentRepository.existsByCodeAndIdNot(update.getCode(), update.getId())) {
            throw new BusinessException("组件代码已存在");
        }

        pageComponentMapper.updatePageComponentFromDto(update, component);
        if (update.getTypeId() != null) {
            PageType type = pageTypeRepository.findById(update.getTypeId())
                    .orElseThrow(() -> new BusinessException("类型不存在"));
            component.setType(type);
        }
        pageComponentRepository.save(component);
    }

    @Override
    public void delete(Long id) {
        log.info("删除页面组件信息：{}", id);
        PageComponent component = pageComponentRepository.findById(id)
                .orElseThrow(() -> new BusinessException("组件不存在"));
        pageComponentRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> ids) {
        log.info("批量删除页面组件信息：{}", ids);
        pageComponentRepository.deleteAllById(ids);
    }

    @Override
    @Transactional(readOnly = true)
    public PageComponentVo findById(Long id) {
        log.info("根据ID查询页面组件信息：{}", id);
        PageComponent component = pageComponentRepository.findById(id).orElse(null);
        if (component != null) {
            PageComponentVo vo = pageComponentMapper.toVo(component);
            if (component.getType() != null) {
                vo.setTypeId(component.getType().getId());
                vo.setTypeName(component.getType().getTitle());
            }
            return vo;
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public PageVo<List<PageComponentVo>> findPage(PageComponentQuery query) {
        log.info("分页查询页面组件信息：{}", query);
        Pageable pageable = PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("sort").ascending());

        Specification<PageComponent> spec = queryHelp.getWhere(query, (root,  cb) -> {
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
            if (query.getTypeId() != null) {
                predicates.add(cb.equal(root.get("type").get("id"), query.getTypeId()));
            }
            return predicates;
        });

        Page<PageComponent> page = pageComponentRepository.findAll(spec, pageable);
        List<PageComponentVo> list = new ArrayList<>();
        for (PageComponent comp : page.getContent()) {
            PageComponentVo vo = pageComponentMapper.toVo(comp);
            if (comp.getType() != null) {
                vo.setTypeId(comp.getType().getId());
                vo.setTypeName(comp.getType().getTitle());
            }
            list.add(vo);
        }

        return PageVo.of(page.getTotalElements(), list);
    }

    @Override
    public List<PageComponentVo> findAll() {
        List<PageComponent> all = pageComponentRepository.findAll(Sort.by("sort").ascending());
        List<PageComponentVo> list = new ArrayList<>();
        for (PageComponent comp : all) {
            PageComponentVo vo = pageComponentMapper.toVo(comp);
            if (comp.getType() != null) {
                vo.setTypeId(comp.getType().getId());
                vo.setTypeName(comp.getType().getTitle());
            }
            list.add(vo);
        }
        return list;
    }

    @Override
    public List<PageComponentVo> selectAll() {
        Specification<PageComponent> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("status"), StatusEnum.NORMAL.getCode()));
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        List<PageComponent> all = pageComponentRepository.findAll(spec, Sort.by("sort").ascending());
        List<PageComponentVo> list = new ArrayList<>();
        for (PageComponent comp : all) {
            PageComponentVo vo = pageComponentMapper.toVo(comp);
            if (comp.getType() != null) {
                vo.setTypeId(comp.getType().getId());
                vo.setTypeName(comp.getType().getTitle());
            }
            list.add(vo);
        }
        return list;
    }

    @Override
    public List<PageComponentVo> findByTypeId(Long typeId) {
        List<PageComponent> byTypeId = pageComponentRepository.findByType_Id(typeId);

        return pageComponentMapper.toVo(byTypeId);
    }
}
