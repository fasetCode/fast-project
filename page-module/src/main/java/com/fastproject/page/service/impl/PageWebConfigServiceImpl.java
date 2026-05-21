package com.fastproject.page.service.impl;

import com.fastproject.db.QueryHelp;
import com.fastproject.exception.BusinessException;
import com.fastproject.page.domain.PageApplication;
import com.fastproject.page.domain.PageWebConfig;
import com.fastproject.page.mapper.PageWebConfigMapper;
import com.fastproject.page.repository.db.PageApplicationRepository;
import com.fastproject.page.repository.db.PageWebConfigRepository;
import com.fastproject.page.service.PageWebConfigService;
import com.fastproject.page.vo.pagewebconfig.PageWebConfigUpdate;
import com.fastproject.page.vo.pagewebconfig.PageWebConfigCreate;
import com.fastproject.page.vo.pagewebconfig.PageWebConfigQuery;
import com.fastproject.page.vo.pagewebconfig.PageWebConfigVo;
import com.fastproject.utils.utils.JsonUtils;
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
public class PageWebConfigServiceImpl implements PageWebConfigService {

    private final PageWebConfigRepository pageWebConfigRepository;
    private final PageApplicationRepository pageApplicationRepository;
    private final QueryHelp<PageWebConfig> queryHelp;
    private final PageWebConfigMapper pageWebConfigMapper;

    @Override
    public Long save(PageWebConfigCreate create) {
        log.info("保存页面Web配置信息：{}", create);

        PageWebConfig config = pageWebConfigMapper.toPageWebConfig(create);
        if (create.getApplicationId() != null) {
            PageApplication application = pageApplicationRepository.findById(create.getApplicationId())
                    .orElseThrow(() -> new BusinessException("应用不存在"));
            config.setApplication(application);
        }
        pageWebConfigRepository.save(config);
        return config.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(PageWebConfigUpdate update) {
        log.info("修改页面Web配置信息：{}", update);
        PageWebConfig config = pageWebConfigRepository.findById(update.getId())
                .orElseThrow(() -> new BusinessException("配置不存在"));

        pageWebConfigMapper.updatePageWebConfigFromDto(update, config);
        if (update.getApplicationId() != null) {
            PageApplication application = pageApplicationRepository.findById(update.getApplicationId())
                    .orElseThrow(() -> new BusinessException("应用不存在"));
            config.setApplication(application);
        }
        pageWebConfigRepository.save(config);
    }

    @Override
    public void delete(Long id) {
        log.info("删除页面Web配置信息：{}", id);
        PageWebConfig config = pageWebConfigRepository.findById(id)
                .orElseThrow(() -> new BusinessException("配置不存在"));
        pageWebConfigRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> ids) {
        log.info("批量删除页面Web配置信息：{}", ids);
        pageWebConfigRepository.deleteAllById(ids);
    }

    @Override
    public PageWebConfigVo findById(Long id) {
        log.info("根据ID查询页面Web配置信息：{}", id);
        PageWebConfig config = pageWebConfigRepository.findById(id).orElse(null);
        if (config != null) {
            PageWebConfigVo vo = pageWebConfigMapper.toVo(config);
            if (config.getApplication() != null) {
                vo.setApplicationId(config.getApplication().getId());
                vo.setApplicationName(config.getApplication().getTitle());
            }
            return vo;
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public PageVo<List<PageWebConfigVo>> findPage(PageWebConfigQuery query) {
        log.info("分页查询页面Web配置信息：{}", query);
        Pageable pageable = PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("id").descending());

        Specification<PageWebConfig> spec = queryHelp.getWhere(query, (root, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(query.getPathUrl())) {
                predicates.add(cb.like(root.get("pathUrl"), "%" + query.getPathUrl() + "%"));
            }
            if (query.getApplicationId() != null) {
                predicates.add(cb.equal(root.get("application").get("id"), query.getApplicationId()));
            }
            return predicates;
        });

        Page<PageWebConfig> page = pageWebConfigRepository.findAll(spec, pageable);
        List<PageWebConfigVo> list = new ArrayList<>();
        for (PageWebConfig cfg : page.getContent()) {
            PageWebConfigVo vo = pageWebConfigMapper.toVo(cfg);
            if (cfg.getApplication() != null) {
                vo.setApplicationId(cfg.getApplication().getId());
                vo.setApplicationName(cfg.getApplication().getTitle());
            }
            list.add(vo);
        }
        return PageVo.of(page.getTotalElements(), list);
    }

    @Override
    public List<PageWebConfigVo> findAll() {
        List<PageWebConfig> all = pageWebConfigRepository.findAll(Sort.by("id").descending());
        List<PageWebConfigVo> list = new ArrayList<>();
        for (PageWebConfig cfg : all) {
            PageWebConfigVo vo = pageWebConfigMapper.toVo(cfg);
            if (cfg.getApplication() != null) {
                vo.setApplicationId(cfg.getApplication().getId());
                vo.setApplicationName(cfg.getApplication().getTitle());
            }
            list.add(vo);
        }
        return list;
    }

    @Override
    public List<PageWebConfigVo> selectAll() {
        List<PageWebConfig> all = pageWebConfigRepository.findAll(Sort.by("id").descending());
        List<PageWebConfigVo> list = new ArrayList<>();
        for (PageWebConfig cfg : all) {
            PageWebConfigVo vo = pageWebConfigMapper.toVo(cfg);
            if (cfg.getApplication() != null) {
                vo.setApplicationId(cfg.getApplication().getId());
                vo.setApplicationName(cfg.getApplication().getTitle());
            }
            list.add(vo);
        }
        return list;
    }

    @Override
    public PageWebConfigVo findByConfigAndCode(String path, String appCode) {
        PageWebConfig byPathUrlAndApplicationCode = pageWebConfigRepository.findByPathUrlAndApplication_Code(path, appCode);
        return pageWebConfigMapper.toVo(byPathUrlAndApplicationCode);
    }
}
