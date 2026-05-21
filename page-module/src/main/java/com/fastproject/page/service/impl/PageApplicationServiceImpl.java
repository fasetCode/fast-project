package com.fastproject.page.service.impl;

import com.fastproject.db.QueryHelp;
import com.fastproject.exception.BusinessException;
import com.fastproject.file.api.FileHandle;
import com.fastproject.page.domain.PageApplication;
import com.fastproject.page.domain.PageType;
import com.fastproject.page.mapper.PageApplicationMapper;
import com.fastproject.page.repository.db.PageApplicationRepository;
import com.fastproject.page.repository.db.PageTypeRepository;
import com.fastproject.page.service.PageApplicationService;
import com.fastproject.page.vo.pageapplication.PageApplicationUpdate;
import com.fastproject.page.vo.pageapplication.PageApplicationCreate;
import com.fastproject.page.vo.pageapplication.PageApplicationQuery;
import com.fastproject.page.vo.pageapplication.PageApplicationVo;
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
public class PageApplicationServiceImpl implements PageApplicationService {

    private final PageApplicationRepository pageApplicationRepository;
    private final PageTypeRepository pageTypeRepository;
    private final QueryHelp<PageApplication> queryHelp;
    private final PageApplicationMapper pageApplicationMapper;
    private final FileHandle fileHandle;

    @Override
    public Long save(PageApplicationCreate create) {
        log.info("保存页面应用信息：{}", create);
        
        if (pageApplicationRepository.existsByCode(create.getCode())) {
            throw new BusinessException("应用代码已存在");
        }

        PageApplication application = pageApplicationMapper.toPageApplication(create);
        if (create.getTypeId() != null) {
            PageType type = pageTypeRepository.findById(create.getTypeId())
                    .orElseThrow(() -> new BusinessException("类型不存在"));
            application.setType(type);
        }
        pageApplicationRepository.save(application);
        return application.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(PageApplicationUpdate update) {
        log.info("修改页面应用信息：{}", update);
        PageApplication application = pageApplicationRepository.findById(update.getId())
                .orElseThrow(() -> new BusinessException("应用不存在"));

        if (pageApplicationRepository.existsByCodeAndIdNot(update.getCode(), update.getId())) {
            throw new BusinessException("应用代码已存在");
        }

        pageApplicationMapper.updatePageApplicationFromDto(update, application);
        if (update.getTypeId() != null) {
            PageType type = pageTypeRepository.findById(update.getTypeId())
                    .orElseThrow(() -> new BusinessException("类型不存在"));
            application.setType(type);
        }
        pageApplicationRepository.save(application);
    }

    @Override
    public void delete(Long id) {
        log.info("删除页面应用信息：{}", id);
        PageApplication application = pageApplicationRepository.findById(id)
                .orElseThrow(() -> new BusinessException("应用不存在"));
        pageApplicationRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> ids) {
        log.info("批量删除页面应用信息：{}", ids);
        pageApplicationRepository.deleteAllById(ids);
    }

    @Override
    @Transactional(readOnly = true )
    public PageApplicationVo findById(Long id) {
        log.info("根据ID查询页面应用信息：{}", id);
        PageApplication application = pageApplicationRepository.findById(id).orElse(null);
        if (application != null) {
            PageApplicationVo vo = pageApplicationMapper.toVo(application);
            if (application.getType() != null) {
                vo.setTypeId(application.getType().getId());
                vo.setTypeName(application.getType().getTitle());
                vo.setTypeCode(application.getType().getCode());
            }
            return vo;
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true )
    public PageVo<List<PageApplicationVo>> findPage(PageApplicationQuery query) {
        log.info("分页查询页面应用信息：{}", query);
        Pageable pageable = PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("id").descending());

        Specification<PageApplication> spec = queryHelp.getWhere(query, (root,  cb) -> {
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

        Page<PageApplication> page = pageApplicationRepository.findAll(spec, pageable);
        List<PageApplicationVo> list = new ArrayList<>();
        for (PageApplication app : page.getContent()) {
            PageApplicationVo vo = pageApplicationMapper.toVo(app);
            if (app.getType() != null) {
                vo.setTypeId(app.getType().getId());
                vo.setTypeName(app.getType().getTitle());
            }
            list.add(vo);
        }

        return PageVo.of(page.getTotalElements(), list);
    }

    @Override
    public List<PageApplicationVo> findAll() {
        List<PageApplication> all = pageApplicationRepository.findAll(Sort.by("id").descending());
        List<PageApplicationVo> list = new ArrayList<>();
        for (PageApplication app : all) {
            PageApplicationVo vo = pageApplicationMapper.toVo(app);
            if (app.getType() != null) {
                vo.setTypeId(app.getType().getId());
                vo.setTypeName(app.getType().getTitle());
            }
            list.add(vo);
        }
        return list;
    }

    @Override
    public List<PageApplicationVo> selectAll() {
        Specification<PageApplication> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("status"), StatusEnum.NORMAL.getCode()));
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        List<PageApplication> all = pageApplicationRepository.findAll(spec, Sort.by("id").descending());
        List<PageApplicationVo> list = new ArrayList<>();
        for (PageApplication app : all) {
            PageApplicationVo vo = pageApplicationMapper.toVo(app);
            if (app.getType() != null) {
                vo.setTypeId(app.getType().getId());
                vo.setTypeName(app.getType().getTitle());
            }
            list.add(vo);
        }
        return list;
    }
}
