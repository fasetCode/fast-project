package com.fastproject.module.chat.service.impl;

import com.fastproject.module.chat.domain.Config;
import com.fastproject.module.chat.repository.ConfigRepository;
import com.fastproject.module.chat.service.ConfigService;
import com.fastproject.utils.vo.PageVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConfigServiceImpl implements ConfigService {

    private final ConfigRepository configRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(Config config) {
        log.info("保存配置信息：{}", config);
        return configRepository.save(config).getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Config config) {
        log.info("修改配置信息：{}", config);
        if (!configRepository.existsById(config.getId())) {
            throw new RuntimeException("配置不存在");
        }
        configRepository.save(config);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        log.info("删除配置信息：{}", id);
        configRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> ids) {
        log.info("批量删除配置信息：{}", ids);
        configRepository.deleteAllById(ids);
    }

    @Override
    @Transactional(readOnly = true)
    public Config findById(Long id) {
        log.info("根据ID查询配置信息：{}", id);
        return configRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public PageVo<List<Config>> findPage(int page, int pageSize) {
        log.info("分页查询配置信息：page={}, pageSize={}", page, pageSize);
        PageRequest pageable = PageRequest.of(page, pageSize, Sort.by("id").descending());
        Page<Config> pageResult = configRepository.findAll(pageable);
        return PageVo.of(pageResult.getTotalElements(), pageResult.getContent());
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return configRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Config getConfig() {
        return configRepository.findAll().stream().findFirst().orElse(null);
    }
}
