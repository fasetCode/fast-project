package com.fastproject.message.service.impl;

import com.fastproject.db.QueryHelp;
import com.fastproject.exception.BusinessException;
import com.fastproject.message.domain.MessageRecord;
import com.fastproject.message.mapper.MessageRecordMapper;
import com.fastproject.message.repository.db.MessageRecordRepository;
import com.fastproject.message.service.MessageRecordService;
import com.fastproject.message.vo.record.MessageRecordCreate;
import com.fastproject.message.vo.record.MessageRecordUpdate;
import com.fastproject.message.vo.record.MessageRecordQuery;
import com.fastproject.message.vo.record.MessageRecordVo;
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
public class MessageRecordServiceImpl implements MessageRecordService {

    private final MessageRecordRepository messageRecordRepository;
    private final QueryHelp<MessageRecord> queryHelp;
    private final MessageRecordMapper messageRecordMapper;

    @Override
    public Long save(MessageRecordCreate create) {
        log.info("保存消息记录：{}", create);
        MessageRecord record = messageRecordMapper.toRecord(create);
        messageRecordRepository.save(record);
        return record.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(MessageRecordUpdate update) {
        log.info("修改消息记录：{}", update);
        MessageRecord record = messageRecordRepository.findById(update.getId())
                .orElseThrow(() -> new BusinessException("记录不存在"));
        messageRecordMapper.updateRecordFromDto(update, record);
        messageRecordRepository.save(record);
    }

    @Override
    public void delete(Long id) {
        log.info("删除消息记录：{}", id);
        if (!messageRecordRepository.existsById(id)) {
            throw new BusinessException("记录不存在");
        }
        messageRecordRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> ids) {
        log.info("批量删除消息记录：{}", ids);
        messageRecordRepository.deleteAllById(ids);
    }

    @Override
    public MessageRecordVo findById(Long id) {
        log.info("根据ID查询消息记录：{}", id);
        return messageRecordRepository.findById(id)
                .map(messageRecordMapper::toVo)
                .orElse(null);
    }

    @Override
    public PageVo<List<MessageRecordVo>> findPage(MessageRecordQuery query) {
        log.info("分页查询消息记录：{}", query);
        Pageable pageable = PageRequest.of(query.getPage(), query.getPageSize(), Sort.by("createTime").descending());

        Specification<MessageRecord> spec = queryHelp.getWhere(query, (root, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(query.getTitle())) {
                predicates.add(cb.like(root.get("title"), "%" + query.getTitle() + "%"));
            }
            if (StringUtils.hasText(query.getReceiver())) {
                predicates.add(cb.equal(root.get("receiver"), query.getReceiver()));
            }
            if (StringUtils.hasText(query.getMessageType())) {
                predicates.add(cb.equal(root.get("messageType"), query.getMessageType()));
            }
            if (StringUtils.hasText(query.getStatus())) {
                predicates.add(cb.equal(root.get("status"), query.getStatus()));
            }
            return predicates;
        });

        Page<MessageRecord> page = messageRecordRepository.findAll(spec, pageable);
        List<MessageRecordVo> list = messageRecordMapper.toVo(page.getContent());

        return PageVo.of(page.getTotalElements(), list);
    }
}
