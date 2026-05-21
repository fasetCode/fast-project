package com.fastproject.ratelimit.service.impl;

import com.fastproject.db.QueryHelp;
import com.fastproject.exception.BusinessException;
import com.fastproject.ratelimit.domain.IpRateLimitConfig;
import com.fastproject.ratelimit.enums.IpType;
import com.fastproject.ratelimit.mapper.IpRateLimitConfigMapper;
import com.fastproject.ratelimit.repository.db.IpRateLimitConfigRepository;
import com.fastproject.ratelimit.service.IpRateLimitConfigService;
import com.fastproject.ratelimit.vo.ip.IpRateLimitConfigCreate;
import com.fastproject.ratelimit.vo.ip.IpRateLimitConfigUpdate;
import com.fastproject.ratelimit.vo.ip.IpRateLimitConfigQuery;
import com.fastproject.ratelimit.vo.ip.IpRateLimitConfigVo;
import com.fastproject.utils.IpUtils;
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
 * IP限流配置 Service 实现类
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class IpRateLimitConfigServiceImpl implements IpRateLimitConfigService {

    private final IpRateLimitConfigRepository ipRateLimitConfigRepository;
    private final QueryHelp<IpRateLimitConfig> queryHelp;
    private final IpRateLimitConfigMapper ipRateLimitConfigMapper;

    @Override
    public Long save(IpRateLimitConfigCreate create) {
        log.info("保存IP限流配置信息：{}", create);

        if (IpType.ALL.equals(create.getIpType())) {
            // 检查该应用是否已存在全部IP配置
            if (ipRateLimitConfigRepository.findByAppCodeAndIpType(create.getAppCode(), IpType.ALL) != null) {
                throw new BusinessException("该应用已配置全部IP限流");
            }
            create.setIpAddress(null);
        } else {
            // 检查IP地址是否为空
            if (!StringUtils.hasText(create.getIpAddress())) {
                throw new BusinessException("指定IP类型时IP地址不能为空");
            }
            // 检查IP地址是否已存在
            if (ipRateLimitConfigRepository.existsByIpAddress(create.getIpAddress())) {
                throw new BusinessException("IP地址已存在");
            }
        }

        IpRateLimitConfig config = ipRateLimitConfigMapper.toConfig(create);
        ipRateLimitConfigRepository.save(config);
        return config.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(IpRateLimitConfigUpdate update) {
        log.info("修改IP限流配置信息：{}", update);
        IpRateLimitConfig config = ipRateLimitConfigRepository.findById(update.getId())
                .orElseThrow(() -> new BusinessException("配置不存在"));

        if (IpType.ALL.equals(update.getIpType())) {
            // 检查该应用是否已存在全部IP配置（排除自身）
            IpRateLimitConfig existing = ipRateLimitConfigRepository.findByAppCodeAndIpType(update.getAppCode(), IpType.ALL);
            if (existing != null && !existing.getId().equals(update.getId())) {
                throw new BusinessException("该应用已配置全部IP限流");
            }
            update.setIpAddress(null);
        } else {
            // 检查IP地址是否为空
            if (!StringUtils.hasText(update.getIpAddress())) {
                throw new BusinessException("指定IP类型时IP地址不能为空");
            }
            // 检查IP地址是否已存在（排除自身）
            if (ipRateLimitConfigRepository.existsByIpAddressAndIdNot(update.getIpAddress(), update.getId())) {
                throw new BusinessException("IP地址已存在");
            }
        }

        ipRateLimitConfigMapper.updateConfigFromDto(update, config);
        ipRateLimitConfigRepository.save(config);
    }

    @Override
    public void delete(Long id) {
        log.info("删除IP限流配置信息：{}", id);
        ipRateLimitConfigRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> ids) {
        log.info("批量删除IP限流配置信息：{}", ids);
        ipRateLimitConfigRepository.deleteAllById(ids);
    }

    @Override
    public IpRateLimitConfigVo findById(Long id) {
        log.info("根据ID查询IP限流配置信息：{}", id);
        IpRateLimitConfig config = ipRateLimitConfigRepository.findById(id).orElse(null);
        if (config != null) {
            return ipRateLimitConfigMapper.toVo(config);
        }
        return null;
    }

    @Override
    public PageVo<List<IpRateLimitConfigVo>> findPage(IpRateLimitConfigQuery query) {
        log.info("分页查询IP限流配置信息：{}", query);
        Pageable pageable = PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("id").descending());

        Specification<IpRateLimitConfig> spec = queryHelp.getWhere(query, (root, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(query.getAppCode())) {
                predicates.add(cb.equal(root.get("appCode"), query.getAppCode()));
            }
            if (StringUtils.hasText(query.getIpAddress())) {
                predicates.add(cb.like(root.get("ipAddress"), "%" + query.getIpAddress() + "%"));
            }
            if (query.getIpType() != null) {
                predicates.add(cb.equal(root.get("ipType"), query.getIpType()));
            }
            if (query.getEnabled() != null) {
                predicates.add(cb.equal(root.get("enabled"), query.getEnabled()));
            }
            return predicates;
        });

        Page<IpRateLimitConfig> page = ipRateLimitConfigRepository.findAll(spec, pageable);
        List<IpRateLimitConfigVo> list = ipRateLimitConfigMapper.toVo(page.getContent());

        return PageVo.of(page.getTotalElements(), list);
    }

    @Override
    public IpRateLimitConfigVo findByIpAddress(String ipAddress) {
        IpRateLimitConfig config = ipRateLimitConfigRepository.findByIpAddress(ipAddress);
        if (config != null) {
            return ipRateLimitConfigMapper.toVo(config);
        }
        return null;
    }

    @Override
    public IpRateLimitConfigVo findMatchingConfig(String appCode, String ip) {
        List<IpRateLimitConfig> configs = ipRateLimitConfigRepository.findByAppCodeAndEnabledTrue(appCode);
        if (configs == null || configs.isEmpty()) {
            return null;
        }

        // 1. 优先匹配精确 IP (SINGLE)
        for (IpRateLimitConfig config : configs) {
            if (IpType.SINGLE.equals(config.getIpType()) && ip.equals(config.getIpAddress())) {
                return ipRateLimitConfigMapper.toVo(config);
            }
        }

        // 2. 匹配 IP 段 (SEGMENT)
        for (IpRateLimitConfig config : configs) {
            if (IpType.SEGMENT.equals(config.getIpType()) && IpUtils.isInRange(ip, config.getIpAddress())) {
                return ipRateLimitConfigMapper.toVo(config);
            }
        }

        // 3. 最后匹配全部 IP (ALL)
        for (IpRateLimitConfig config : configs) {
            if (IpType.ALL.equals(config.getIpType())) {
                return ipRateLimitConfigMapper.toVo(config);
            }
        }

        return null;
    }
}