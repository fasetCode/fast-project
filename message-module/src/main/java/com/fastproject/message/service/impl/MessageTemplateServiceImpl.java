package com.fastproject.message.service.impl;

import com.fastproject.db.QueryHelp;
import com.fastproject.exception.BusinessException;
import com.fastproject.message.domain.MessageTemplate;
import com.fastproject.message.mapper.MessageTemplateMapper;
import com.fastproject.message.repository.db.MessageTemplateRepository;
import com.fastproject.message.service.MessageTemplateService;
import com.fastproject.message.vo.template.MessageTemplateUpdate;
import com.fastproject.message.vo.template.MessageTemplateCreate;
import com.fastproject.message.vo.template.MessageTemplateQuery;
import com.fastproject.message.vo.template.MessageTemplateVo;
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
public class MessageTemplateServiceImpl implements MessageTemplateService {

    private final MessageTemplateRepository messageTemplateRepository;
    private final QueryHelp<MessageTemplate> queryHelp;
    private final MessageTemplateMapper messageTemplateMapper;

    @Override
    public Long save(MessageTemplateCreate create) {
        log.info("保存消息模版：{}", create);

        if (messageTemplateRepository.existsByCode(create.getCode())) {
            throw new BusinessException("模版代码已存在");
        }

        MessageTemplate template = messageTemplateMapper.toTemplate(create);
        messageTemplateRepository.save(template);
        return template.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(MessageTemplateUpdate update) {
        log.info("修改消息模版：{}", update);
        MessageTemplate template = messageTemplateRepository.findById(update.getId())
                .orElseThrow(() -> new BusinessException("模版不存在"));

        if (messageTemplateRepository.existsByCodeAndIdNot(update.getCode(), update.getId())) {
            throw new BusinessException("模版代码已存在");
        }

        messageTemplateMapper.updateTemplateFromDto(update, template);
        messageTemplateRepository.save(template);
    }

    @Override
    public void delete(Long id) {
        log.info("删除消息模版：{}", id);
        if (!messageTemplateRepository.existsById(id)) {
            throw new BusinessException("模版不存在");
        }
        messageTemplateRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> ids) {
        log.info("批量删除消息模版：{}", ids);
        messageTemplateRepository.deleteAllById(ids);
    }

    @Override
    public MessageTemplateVo findById(Long id) {
        log.info("根据ID查询消息模版：{}", id);
        return messageTemplateRepository.findById(id)
                .map(messageTemplateMapper::toVo)
                .orElse(null);
    }

    @Override
    public PageVo<List<MessageTemplateVo>> findPage(MessageTemplateQuery query) {
        log.info("分页查询消息模版：{}", query);
        Pageable pageable = PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("createTime").descending());

        Specification<MessageTemplate> spec = queryHelp.getWhere(query, (root, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(query.getTitle())) {
                predicates.add(cb.like(root.get("title"), "%" + query.getTitle() + "%"));
            }
            if (StringUtils.hasText(query.getCode())) {
                predicates.add(cb.equal(root.get("code"), query.getCode()));
            }
            if (query.getTypeId() != null) {
                predicates.add(cb.equal(root.get("typeId"), query.getTypeId()));
            }
            if (query.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), query.getStatus()));
            }
            return predicates;
        });

        Page<MessageTemplate> page = messageTemplateRepository.findAll(spec, pageable);
        List<MessageTemplateVo> list = messageTemplateMapper.toVo(page.getContent());

        return PageVo.of(page.getTotalElements(), list);
    }

    @Override
    public List<MessageTemplateVo> selectAll() {
        Specification<MessageTemplate> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("status"), StatusEnum.NORMAL.getCode()));
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        List<MessageTemplate> all = messageTemplateRepository.findAll(spec, Sort.by("createTime").descending());
        return messageTemplateMapper.toVo(all);
    }

    @Override
    public MessageTemplateVo getCode(String templateCode) {
        log.info("根据模版代码查询消息模版：{}", templateCode);
        MessageTemplate template = messageTemplateRepository.findByCodeAndStatus(templateCode, StatusEnum.NORMAL.getCode());
        if (template != null) {
            return messageTemplateMapper.toVo(template);
        }
        return null;
    }
}