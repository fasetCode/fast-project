package com.fastproject.file.service.impl;

import com.fastproject.db.QueryHelp;
import com.fastproject.exception.BusinessException;
import com.fastproject.file.domain.FileConfig;
import com.fastproject.file.mapper.FileConfigMapper;
import com.fastproject.file.repository.db.FileConfigRepository;
import com.fastproject.file.service.FileConfigService;
import com.fastproject.file.vo.config.FileConfigCreate;
import com.fastproject.file.vo.config.FileConfigQuery;
import com.fastproject.file.vo.config.FileConfigUpdate;
import com.fastproject.file.vo.config.FileConfigVo;
import com.fastproject.utils.vo.PageVo;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件配置 Service 实现类
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class FileConfigServiceImpl implements FileConfigService {

    private final FileConfigRepository fileConfigRepository;
    private final QueryHelp<FileConfig> queryHelp;
    private final FileConfigMapper fileConfigMapper;

    @Override
    public Long save(FileConfigCreate create) {
        log.info("保存文件配置信息：{}", create);

        // 检查类型是否已存在
        if (fileConfigRepository.existsByType(create.getType())) {
            throw new BusinessException("配置类型已存在");
        }

        FileConfig config = fileConfigMapper.toConfig(create);
        fileConfigRepository.save(config);
        return config.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(FileConfigUpdate update) {
        log.info("修改文件配置信息：{}", update);
        FileConfig config = fileConfigRepository.findById(update.getId())
                .orElseThrow(() -> new BusinessException("配置不存在"));

        // 检查类型是否已存在（排除自身）
        if (fileConfigRepository.existsByTypeAndIdNot(update.getType(), update.getId())) {
            throw new BusinessException("配置类型已存在");
        }

        fileConfigMapper.updateConfigFromDto(update, config);
        fileConfigRepository.save(config);
    }

    @Override
    public void delete(Long id) {
        log.info("删除文件配置信息：{}", id);
        fileConfigRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> ids) {
        log.info("批量删除文件配置信息：{}", ids);
        fileConfigRepository.deleteAllById(ids);
    }

    @Override
    public FileConfigVo findById(Long id) {
        log.info("根据ID查询文件配置信息：{}", id);
        FileConfig config = fileConfigRepository.findById(id).orElse(null);
        if (config != null) {
            return fileConfigMapper.toVo(config);
        }
        return null;
    }

    @Override
    public PageVo<List<FileConfigVo>> findPage(FileConfigQuery query) {
        log.info("分页查询文件配置信息：{}", query);
        Pageable pageable = PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("id").descending());

        Specification<FileConfig> spec = queryHelp.getWhere(query, (root, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(query.getType())) {
                predicates.add(cb.equal(root.get("type"), query.getType()));
            }
            if (query.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), query.getStatus()));
            }
            if (StringUtils.hasText(query.getDescription())) {
                predicates.add(cb.like(root.get("description"), "%" + query.getDescription() + "%"));
            }
            return predicates;
        });

        Page<FileConfig> page = fileConfigRepository.findAll(spec, pageable);
        List<FileConfigVo> list = fileConfigMapper.toVo(page.getContent());

        return PageVo.of(page.getTotalElements(), list);
    }

    @Override
    public FileConfigVo findByType(String type) {
        log.info("根据类型查询文件配置信息：{}", type);
        FileConfig config = fileConfigRepository.findByType(type);
        if (config != null) {
            return fileConfigMapper.toVo(config);
        }
        return null;
    }

    @Override
    public List<FileConfigVo> findAllEnabled() {
        log.info("查询所有启用的文件配置");
        List<FileConfig> configs = fileConfigRepository.findByStatusOrderByWeightDesc(1);
        return fileConfigMapper.toVo(configs);
    }
}
