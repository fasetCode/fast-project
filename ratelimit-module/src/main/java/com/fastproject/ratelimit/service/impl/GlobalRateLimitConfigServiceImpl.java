package com.fastproject.ratelimit.service.impl;

import com.fastproject.db.QueryHelp;
import com.fastproject.exception.BusinessException;
import com.fastproject.ratelimit.config.RateLimitProps;
import com.fastproject.ratelimit.domain.GlobalRateLimitConfig;
import com.fastproject.ratelimit.mapper.GlobalRateLimitConfigMapper;
import com.fastproject.ratelimit.repository.db.GlobalRateLimitConfigRepository;
import com.fastproject.ratelimit.service.GlobalRateLimitConfigService;
import com.fastproject.ratelimit.vo.global.GlobalRateLimitConfigCreate;
import com.fastproject.ratelimit.vo.global.GlobalRateLimitConfigUpdate;
import com.fastproject.ratelimit.vo.global.GlobalRateLimitConfigQuery;
import com.fastproject.ratelimit.vo.global.GlobalRateLimitConfigVo;
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

/**
 * 全局限流配置 Service 实现类
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class GlobalRateLimitConfigServiceImpl implements GlobalRateLimitConfigService {

    private final GlobalRateLimitConfigRepository globalRateLimitConfigRepository;
    private final QueryHelp<GlobalRateLimitConfig> queryHelp;
    private final GlobalRateLimitConfigMapper globalRateLimitConfigMapper;
    private final RateLimitProps rateLimitProps;

    @Override
    public Long save(GlobalRateLimitConfigCreate create) {
        log.info("保存全局限流配置信息：{}", create);

        // 检查是否已存在启用的配置
        if (create.getEnabled() != null && create.getEnabled()) {
            GlobalRateLimitConfig existing;
            if (StringUtils.hasText(create.getAppCode())) {
                existing = globalRateLimitConfigRepository.findByAppCodeAndEnabledTrue(create.getAppCode());
            } else {
                existing = globalRateLimitConfigRepository.findByEnabledTrue();
            }
            if (existing != null) {
                throw new BusinessException("已存在启用的全局限流配置");
            }
        }

        GlobalRateLimitConfig config = globalRateLimitConfigMapper.toConfig(create);
        globalRateLimitConfigRepository.save(config);
        return config.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(GlobalRateLimitConfigUpdate update) {
        log.info("修改全局限流配置信息：{}", update);
        GlobalRateLimitConfig config = globalRateLimitConfigRepository.findById(update.getId())
                .orElseThrow(() -> new BusinessException("配置不存在"));

        // 检查是否已存在其他启用的配置（排除自身）
        if (update.getEnabled() != null && update.getEnabled()) {
            GlobalRateLimitConfig existing;
            if (StringUtils.hasText(update.getAppCode())) {
                existing = globalRateLimitConfigRepository.findByAppCodeAndEnabledTrue(update.getAppCode());
            } else {
                existing = globalRateLimitConfigRepository.findByEnabledTrue();
            }
            if (existing != null && !existing.getId().equals(update.getId())) {
                throw new BusinessException("已存在其他启用的全局限流配置");
            }
        }

        globalRateLimitConfigMapper.updateConfigFromDto(update, config);
        globalRateLimitConfigRepository.save(config);
    }

    @Override
    public void delete(Long id) {
        log.info("删除全局限流配置信息：{}", id);
        globalRateLimitConfigRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> ids) {
        log.info("批量删除全局限流配置信息：{}", ids);
        globalRateLimitConfigRepository.deleteAllById(ids);
    }

    @Override
    public GlobalRateLimitConfigVo findById(Long id) {
        log.info("根据ID查询全局限流配置信息：{}", id);
        GlobalRateLimitConfig config = globalRateLimitConfigRepository.findById(id).orElse(null);
        if (config != null) {
            return globalRateLimitConfigMapper.toVo(config);
        }
        return null;
    }

    @Override
    public PageVo<List<GlobalRateLimitConfigVo>> findPage(GlobalRateLimitConfigQuery query) {
        log.info("分页查询全局限流配置信息：{}", query);
        Pageable pageable = PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("id").descending());

        Specification<GlobalRateLimitConfig> spec = queryHelp.getWhere(query, (root, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (query.getAppCode() != null && !query.getAppCode().isEmpty()) {
                predicates.add(cb.like(root.get("appCode"), "%" + query.getAppCode() + "%"));
            }
            if (query.getEnabled() != null) {
                predicates.add(cb.equal(root.get("enabled"), query.getEnabled()));
            }
            return predicates;
        });

        Page<GlobalRateLimitConfig> page = globalRateLimitConfigRepository.findAll(spec, pageable);
        List<GlobalRateLimitConfigVo> list = globalRateLimitConfigMapper.toVo(page.getContent());

        return PageVo.of(page.getTotalElements(), list);
    }

    @Override
    public GlobalRateLimitConfigVo findEnabledConfig() {
        GlobalRateLimitConfig config;
        if (rateLimitProps != null && StringUtils.hasText(rateLimitProps.getAppCode())) {
            config = globalRateLimitConfigRepository.findByAppCodeAndEnabledTrue(rateLimitProps.getAppCode());
        } else {
            config = globalRateLimitConfigRepository.findByEnabledTrue();
        }
        if (config != null) {
            return globalRateLimitConfigMapper.toVo(config);
        }
        return null;
    }
}
