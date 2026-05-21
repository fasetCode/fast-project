package com.fastproject.message.service.impl;

import com.fastproject.db.QueryHelp;
import com.fastproject.exception.BusinessException;
import com.fastproject.message.domain.MessageConfig;
import com.fastproject.message.mapper.MessageConfigMapper;
import com.fastproject.message.repository.db.MessageConfigRepository;
import com.fastproject.message.service.MessageConfigService;
import com.fastproject.message.vo.config.MessageConfigUpdate;
import com.fastproject.message.vo.config.MessageConfigCreate;
import com.fastproject.message.vo.config.MessageConfigQuery;
import com.fastproject.message.vo.config.MessageConfigVo;
import com.fastproject.utils.enums.StatusEnum;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.vo.IdTitleVo;
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
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageConfigServiceImpl implements MessageConfigService {

    private final MessageConfigRepository messageConfigRepository;
    private final QueryHelp<MessageConfig> queryHelp;
    private final MessageConfigMapper messageConfigMapper;

    @Override
    public Long save(MessageConfigCreate create) {
        log.info("保存消息配置：{}", create);

        if (messageConfigRepository.existsByType(create.getType())) {
            throw new BusinessException("配置类型已存在");
        }

        MessageConfig config = messageConfigMapper.toConfig(create);
        messageConfigRepository.save(config);
        return config.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(MessageConfigUpdate update) {
        log.info("修改消息配置：{}", update);
        MessageConfig config = messageConfigRepository.findById(update.getId())
                .orElseThrow(() -> new BusinessException("配置不存在"));

        if (messageConfigRepository.existsByTypeAndIdNot(update.getType(), update.getId())) {
            throw new BusinessException("配置类型已存在");
        }

        messageConfigMapper.updateConfigFromDto(update, config);
        messageConfigRepository.save(config);
    }

    @Override
    public void delete(Long id) {
        log.info("删除消息配置：{}", id);
        if (!messageConfigRepository.existsById(id)) {
            throw new BusinessException("配置不存在");
        }
        messageConfigRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> ids) {
        log.info("批量删除消息配置：{}", ids);
        messageConfigRepository.deleteAllById(ids);
    }

    @Override
    public MessageConfigVo findById(Long id) {
        log.info("根据ID查询消息配置：{}", id);
        return messageConfigRepository.findById(id)
                .map(messageConfigMapper::toVo)
                .orElse(null);
    }

    @Override
    public PageVo<List<MessageConfigVo>> findPage(MessageConfigQuery query) {
        log.info("分页查询消息配置：{}", query);
        Pageable pageable = PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("createTime").descending());

        Specification<MessageConfig> spec = queryHelp.getWhere(query, (root, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(query.getTitle())) {
                predicates.add(cb.like(root.get("title"), "%" + query.getTitle() + "%"));
            }
            if (StringUtils.hasText(query.getType())) {
                predicates.add(cb.equal(root.get("type"), query.getType()));
            }
            if (query.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), query.getStatus()));
            }
            return predicates;
        });

        Page<MessageConfig> page = messageConfigRepository.findAll(spec, pageable);
        List<MessageConfigVo> list = messageConfigMapper.toVo(page.getContent());

        return PageVo.of(page.getTotalElements(), list);
    }

    @Override
    public List<IdTitleVo> selectAll() {
        Specification<MessageConfig> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("status"), StatusEnum.NORMAL.getCode()));
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        List<MessageConfig> all = messageConfigRepository.findAll(spec, Sort.by("createTime").descending());
        return all.stream()
                .map(config -> {
                    IdTitleVo vo = new IdTitleVo();
                    vo.setId(config.getId());
                    vo.setTitle(config.getTitle());
                    return vo;
                })
                .collect(Collectors.toList());
    }
}