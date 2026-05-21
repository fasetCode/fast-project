package com.fastproject.system.service.impl;

import com.fastproject.db.QueryHelp;
import com.fastproject.exception.BusinessException;
import com.fastproject.system.domain.SysConfig;
import com.fastproject.system.mapper.SysConfigMapper;
import com.fastproject.system.repository.db.SysConfigRepository;
import com.fastproject.system.service.SysConfigService;
import com.fastproject.system.vo.config.SysConfigUpdate;
import com.fastproject.system.vo.config.SysConfigCreate;
import com.fastproject.system.vo.config.SysConfigQuery;
import com.fastproject.system.vo.config.SysConfigVo;
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
 * 系统配置 Service 实现类
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SysConfigServiceImpl implements SysConfigService {

    private final SysConfigRepository sysConfigRepository;
    private final QueryHelp<SysConfig> queryHelp;
    private final SysConfigMapper sysConfigMapper;

    @Override
    public Long save(SysConfigCreate create) {
        log.info("保存系统配置信息：{}", create);

        // 检查配置键是否已存在
        if (sysConfigRepository.existsByConfigKey(create.getConfigKey())) {
            throw new BusinessException("配置键已存在");
        }

        SysConfig config = sysConfigMapper.toConfig(create);
        sysConfigRepository.save(config);
        return config.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysConfigUpdate sysConfigUpdate) {
        log.info("修改系统配置信息：{}", sysConfigUpdate);
        SysConfig config = sysConfigRepository.findById(sysConfigUpdate.getId())
                .orElseThrow(() -> new BusinessException("配置不存在"));

        // 检查配置键是否已存在（排除自身）
        if (sysConfigRepository.existsByConfigKeyAndIdNot(sysConfigUpdate.getConfigKey(), sysConfigUpdate.getId())) {
            throw new BusinessException("配置键已存在");
        }

        sysConfigMapper.updateConfigFromDto(sysConfigUpdate, config);
        sysConfigRepository.save(config);
    }

    @Override
    public void delete(Long id) {
        log.info("删除系统配置信息：{}", id);
        sysConfigRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> ids) {
        log.info("批量删除系统配置信息：{}", ids);
        sysConfigRepository.deleteAllById(ids);
    }

    @Override
    public SysConfigVo findById(Long id) {
        log.info("根据ID查询系统配置信息：{}", id);
        SysConfig config = sysConfigRepository.findById(id).orElse(null);
        if (config != null) {
            return sysConfigMapper.toVo(config);
        }
        return null;
    }

    @Override
    public PageVo<List<SysConfigVo>> findPage(SysConfigQuery sysConfigQuery) {
        log.info("分页查询系统配置信息：{}", sysConfigQuery);
        Pageable pageable = PageRequest.of(sysConfigQuery.getPage(), sysConfigQuery.getPageSize(), Sort.by("id").descending());

        Specification<SysConfig> spec = queryHelp.getWhere(sysConfigQuery, (root, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(sysConfigQuery.getName())) {
                predicates.add(cb.like(root.get("name"), "%" + sysConfigQuery.getName() + "%"));
            }
            if (StringUtils.hasText(sysConfigQuery.getConfigKey())) {
                predicates.add(cb.like(root.get("configKey"), "%" + sysConfigQuery.getConfigKey() + "%"));
            }
            if (StringUtils.hasText(sysConfigQuery.getType())) {
                predicates.add(cb.equal(root.get("type"), sysConfigQuery.getType()));
            }
            if (sysConfigQuery.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), sysConfigQuery.getStatus()));
            }
            return predicates;
        });

        Page<SysConfig> page = sysConfigRepository.findAll(spec, pageable);
        List<SysConfigVo> list = sysConfigMapper.toVo(page.getContent());

        return PageVo.of(page.getTotalElements(), list);
    }

    @Override
    public String getConfigValue(String configKey) {
        SysConfig config = sysConfigRepository.findByConfigKey(configKey);
        return config != null ? config.getConfigValue() : null;
    }
}
