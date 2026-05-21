package com.fastproject.message.service.impl;

import com.fastproject.db.QueryHelp;
import com.fastproject.exception.BusinessException;
import com.fastproject.message.domain.MessageType;
import com.fastproject.message.mapper.MessageTypeMapper;
import com.fastproject.message.repository.db.MessageTypeRepository;
import com.fastproject.message.service.MessageTypeService;
import com.fastproject.message.vo.type.MessageTypeUpdate;
import com.fastproject.message.vo.type.MessageTypeCreate;
import com.fastproject.message.vo.type.MessageTypeQuery;
import com.fastproject.message.vo.type.MessageTypeVo;
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
public class MessageTypeServiceImpl implements MessageTypeService {

    private final MessageTypeRepository messageTypeRepository;
    private final QueryHelp<MessageType> queryHelp;
    private final MessageTypeMapper messageTypeMapper;

    @Override
    public Long save(MessageTypeCreate create) {
        log.info("保存消息类型：{}", create);

        if (messageTypeRepository.existsByCode(create.getCode())) {
            throw new BusinessException("类型代码已存在");
        }

        MessageType type = messageTypeMapper.toType(create);
        messageTypeRepository.save(type);
        return type.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(MessageTypeUpdate update) {
        log.info("修改消息类型：{}", update);
        MessageType type = messageTypeRepository.findById(update.getId())
                .orElseThrow(() -> new BusinessException("类型不存在"));

        if (messageTypeRepository.existsByCodeAndIdNot(update.getCode(), update.getId())) {
            throw new BusinessException("类型代码已存在");
        }

        messageTypeMapper.updateTypeFromDto(update, type);
        messageTypeRepository.save(type);
    }

    @Override
    public void delete(Long id) {
        log.info("删除消息类型：{}", id);
        if (!messageTypeRepository.existsById(id)) {
            throw new BusinessException("类型不存在");
        }
        messageTypeRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> ids) {
        log.info("批量删除消息类型：{}", ids);
        messageTypeRepository.deleteAllById(ids);
    }

    @Override
    public MessageTypeVo findById(Long id) {
        log.info("根据ID查询消息类型：{}", id);
        return messageTypeRepository.findById(id)
                .map(messageTypeMapper::toVo)
                .orElse(null);
    }

    @Override
    public PageVo<List<MessageTypeVo>> findPage(MessageTypeQuery query) {
        log.info("分页查询消息类型：{}", query);
        Pageable pageable = PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("createTime").descending());

        Specification<MessageType> spec = queryHelp.getWhere(query, (root, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(query.getTitle())) {
                predicates.add(cb.like(root.get("title"), "%" + query.getTitle() + "%"));
            }
            if (StringUtils.hasText(query.getCode())) {
                predicates.add(cb.equal(root.get("code"), query.getCode()));
            }
            if (query.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), query.getStatus()));
            }
            return predicates;
        });

        Page<MessageType> page = messageTypeRepository.findAll(spec, pageable);
        List<MessageTypeVo> list = messageTypeMapper.toVo(page.getContent());

        return PageVo.of(page.getTotalElements(), list);
    }

    @Override
    public List<IdTitleVo> selectAll() {
        Specification<MessageType> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("status"), StatusEnum.NORMAL.getCode()));
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        List<MessageType> all = messageTypeRepository.findAll(spec, Sort.by("createTime").descending());
        return all.stream()
                .map(type -> {
                    IdTitleVo vo = new IdTitleVo();
                    vo.setId(type.getId());
                    vo.setTitle(type.getTitle());
                    return vo;
                })
                .collect(Collectors.toList());
    }
}