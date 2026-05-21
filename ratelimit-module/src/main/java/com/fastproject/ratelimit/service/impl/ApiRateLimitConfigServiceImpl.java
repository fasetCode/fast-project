package com.fastproject.ratelimit.service.impl;

import com.fastproject.db.QueryHelp;
import com.fastproject.exception.BusinessException;
import com.fastproject.ratelimit.domain.ApiRateLimitConfig;
import com.fastproject.ratelimit.mapper.ApiRateLimitConfigMapper;
import com.fastproject.ratelimit.repository.db.ApiRateLimitConfigRepository;
import com.fastproject.ratelimit.service.ApiRateLimitConfigService;
import com.fastproject.ratelimit.vo.api.ApiRateLimitConfigCreate;
import com.fastproject.ratelimit.vo.api.ApiRateLimitConfigUpdate;
import com.fastproject.ratelimit.vo.api.ApiRateLimitConfigQuery;
import com.fastproject.ratelimit.vo.api.ApiRateLimitConfigVo;
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
 * API限流配置 Service 实现类
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ApiRateLimitConfigServiceImpl implements ApiRateLimitConfigService {

    private final ApiRateLimitConfigRepository apiRateLimitConfigRepository;
    private final QueryHelp<ApiRateLimitConfig> queryHelp;
    private final ApiRateLimitConfigMapper apiRateLimitConfigMapper;

    @Override
    public Long save(ApiRateLimitConfigCreate create) {
        log.info("保存API限流配置信息：{}", create);

        // 检查应用代码、API路径和HTTP方法组合是否已存在
        if (apiRateLimitConfigRepository.existsByAppCodeAndApiPathAndHttpMethod(create.getAppCode(), create.getApiPath(), create.getHttpMethod())) {
            throw new BusinessException("该应用已配置该API路径和HTTP方法组合");
        }

        ApiRateLimitConfig config = apiRateLimitConfigMapper.toConfig(create);
        apiRateLimitConfigRepository.save(config);
        return config.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ApiRateLimitConfigUpdate update) {
        log.info("修改API限流配置信息：{}", update);
        ApiRateLimitConfig config = apiRateLimitConfigRepository.findById(update.getId())
                .orElseThrow(() -> new BusinessException("配置不存在"));

        // 检查应用代码、API路径和HTTP方法组合是否已存在（排除自身）
        if (apiRateLimitConfigRepository.existsByAppCodeAndApiPathAndHttpMethodAndIdNot(update.getAppCode(), update.getApiPath(), update.getHttpMethod(), update.getId())) {
            throw new BusinessException("该应用已配置该API路径和HTTP方法组合");
        }

        apiRateLimitConfigMapper.updateConfigFromDto(update, config);
        apiRateLimitConfigRepository.save(config);
    }

    @Override
    public void delete(Long id) {
        log.info("删除API限流配置信息：{}", id);
        apiRateLimitConfigRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> ids) {
        log.info("批量删除API限流配置信息：{}", ids);
        apiRateLimitConfigRepository.deleteAllById(ids);
    }

    @Override
    public ApiRateLimitConfigVo findById(Long id) {
        log.info("根据ID查询API限流配置信息：{}", id);
        ApiRateLimitConfig config = apiRateLimitConfigRepository.findById(id).orElse(null);
        if (config != null) {
            return apiRateLimitConfigMapper.toVo(config);
        }
        return null;
    }

    @Override
    public PageVo<List<ApiRateLimitConfigVo>> findPage(ApiRateLimitConfigQuery query) {
        log.info("分页查询API限流配置信息：{}", query);
        Pageable pageable = PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("id").descending());

        Specification<ApiRateLimitConfig> spec = queryHelp.getWhere(query, (root, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(query.getAppCode())) {
                predicates.add(cb.equal(root.get("appCode"), query.getAppCode()));
            }
            if (StringUtils.hasText(query.getApiPath())) {
                predicates.add(cb.like(root.get("apiPath"), "%" + query.getApiPath() + "%"));
            }
            if (StringUtils.hasText(query.getHttpMethod())) {
                predicates.add(cb.equal(root.get("httpMethod"), query.getHttpMethod()));
            }
            if (query.getLimitDimension() != null) {
                predicates.add(cb.equal(root.get("limitDimension"), query.getLimitDimension()));
            }
            if (query.getEnabled() != null) {
                predicates.add(cb.equal(root.get("enabled"), query.getEnabled()));
            }
            return predicates;
        });

        Page<ApiRateLimitConfig> page = apiRateLimitConfigRepository.findAll(spec, pageable);
        List<ApiRateLimitConfigVo> list = apiRateLimitConfigMapper.toVo(page.getContent());

        return PageVo.of(page.getTotalElements(), list);
    }

    @Override
    public ApiRateLimitConfigVo findByAppCodeAndApiPathAndHttpMethod(String appCode, String apiPath, String httpMethod) {
        ApiRateLimitConfig config = apiRateLimitConfigRepository.findByAppCodeAndApiPathAndHttpMethod(appCode, apiPath, httpMethod);
        if (config != null) {
            return apiRateLimitConfigMapper.toVo(config);
        }
        return null;
    }
}