package com.fastproject.page.service.impl;

import com.fastproject.db.QueryHelp;
import com.fastproject.exception.BusinessException;
import com.fastproject.page.domain.PageApplication;
import com.fastproject.page.domain.PageConfig;
import com.fastproject.page.domain.PageWebConfig;
import com.fastproject.page.mapper.PageConfigMapper;
import com.fastproject.page.repository.db.PageApplicationRepository;
import com.fastproject.page.repository.db.PageConfigRepository;
import com.fastproject.page.repository.db.PageWebConfigRepository;
import com.fastproject.page.service.PageConfigService;
import com.fastproject.page.vo.pageconfig.*;
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
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PageConfigServiceImpl implements PageConfigService {

    private final PageConfigRepository pageConfigRepository;
    private final PageApplicationRepository pageApplicationRepository;
    private final QueryHelp<PageConfig> queryHelp;
    private final PageConfigMapper pageConfigMapper;
    private final PageWebConfigRepository pageWebConfigRepository;

    @Override
    public Long save(PageConfigCreate create) {
        log.info("保存页面配置信息：{}", create);

        PageConfig config = pageConfigMapper.toPageConfig(create);
        if (create.getApplicationId() != null) {
            PageApplication application = pageApplicationRepository.findById(create.getApplicationId())
                    .orElseThrow(() -> new BusinessException("应用不存在"));
            config.setApplication(application);
        }
        pageConfigRepository.save(config);
        return config.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(PageConfigUpdate update) {
        log.info("修改页面配置信息：{}", update);
        PageConfig config = pageConfigRepository.findById(update.getId())
                .orElseThrow(() -> new BusinessException("配置不存在"));

        pageConfigMapper.updatePageConfigFromDto(update, config);
        if (update.getApplicationId() != null) {
            PageApplication application = pageApplicationRepository.findById(update.getApplicationId())
                    .orElseThrow(() -> new BusinessException("应用不存在"));
            config.setApplication(application);
        }
        pageConfigRepository.save(config);
    }

    @Override
    public void delete(Long id) {
        log.info("删除页面配置信息：{}", id);
        PageConfig config = pageConfigRepository.findById(id)
                .orElseThrow(() -> new BusinessException("配置不存在"));
        pageConfigRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> ids) {
        log.info("批量删除页面配置信息：{}", ids);
        pageConfigRepository.deleteAllById(ids);
    }

    @Override
    @Transactional(readOnly = true)
    public PageConfigVo findById(Long id) {
        log.info("根据ID查询页面配置信息：{}", id);
        PageConfig config = pageConfigRepository.findById(id).orElse(null);
        if (config != null) {
            PageConfigVo vo = pageConfigMapper.toVo(config);
            if (config.getApplication() != null) {
                vo.setApplicationId(config.getApplication().getId());
                vo.setApplicationName(config.getApplication().getTitle());
            }
            return vo;
        }
        return null;
    }

    @Override
    public PageVo<List<PageConfigVo>> findPage(PageConfigQuery query) {
        log.info("分页查询页面配置信息：{}", query);
        Pageable pageable = PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("id").descending());

        Specification<PageConfig> spec = queryHelp.getWhere(query, (root,  cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(query.getPathUrl())) {
                predicates.add(cb.like(root.get("pathUrl"), "%" + query.getPathUrl() + "%"));
            }
            if (query.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), query.getStatus()));
            }
            if (StringUtils.hasText(query.getVersion())) {
                predicates.add(cb.equal(root.get("version"), query.getVersion()));
            }
            if (query.getApplicationId() != null) {
                predicates.add(cb.equal(root.get("application").get("id"), query.getApplicationId()));
            }
            return predicates;
        });

        Page<PageConfig> page = pageConfigRepository.findAll(spec, pageable);
        List<PageConfigVo> list = new ArrayList<>();
        for (PageConfig cfg : page.getContent()) {
            PageConfigVo vo = pageConfigMapper.toVo(cfg);
            if (cfg.getApplication() != null) {
                vo.setApplicationId(cfg.getApplication().getId());
                vo.setApplicationName(cfg.getApplication().getTitle());
            }
            list.add(vo);
        }

        return PageVo.of(page.getTotalElements(), list);
    }

    @Override
    public List<PageConfigVo> findAll() {
        List<PageConfig> all = pageConfigRepository.findAll(Sort.by("id").descending());
        List<PageConfigVo> list = new ArrayList<>();
        for (PageConfig cfg : all) {
            PageConfigVo vo = pageConfigMapper.toVo(cfg);
            if (cfg.getApplication() != null) {
                vo.setApplicationId(cfg.getApplication().getId());
                vo.setApplicationName(cfg.getApplication().getTitle());
            }
            list.add(vo);
        }
        return list;
    }

    @Override
    public List<PageConfigVo> selectAll() {
        Specification<PageConfig> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("status"), StatusEnum.NORMAL.getCode()));
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        List<PageConfig> all = pageConfigRepository.findAll(spec, Sort.by("id").descending());
        List<PageConfigVo> list = new ArrayList<>();
        for (PageConfig cfg : all) {
            PageConfigVo vo = pageConfigMapper.toVo(cfg);
            if (cfg.getApplication() != null) {
                vo.setApplicationId(cfg.getApplication().getId());
                vo.setApplicationName(cfg.getApplication().getTitle());
            }
            list.add(vo);
        }
        return list;
    }

    @Override
    public List<PageConfigListVo> findAllList(Long applicationId) {
        List<PageConfig> list = pageConfigRepository.findByApplicationId(applicationId);
        return pageConfigMapper.toListVo(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void publish(PageConfigUpdate update) {
        PageConfig pageConfig = pageConfigRepository.findById(update.getId()).orElse(null);
        this.update(update);
        PageWebConfig webConfig = pageWebConfigRepository.findByPathUrlAndApplication_Id(update.getPathUrl(), pageConfig.getApplication().getId());
        if (webConfig!=null) {
            webConfig.setConfig(update.getConfig());
        }else{
            webConfig = new PageWebConfig();
            webConfig.setPathUrl(update.getPathUrl());
            webConfig.setApplication(pageConfig.getApplication());
            webConfig.setConfig(update.getConfig());
        }
        pageWebConfigRepository.save(webConfig);
    }

}
