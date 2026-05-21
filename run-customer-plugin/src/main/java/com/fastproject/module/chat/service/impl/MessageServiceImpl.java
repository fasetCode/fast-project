package com.fastproject.module.chat.service.impl;

import com.fastproject.module.chat.domain.Message;
import com.fastproject.module.chat.repository.MessageRepository;
import com.fastproject.module.chat.service.MessageService;
import com.fastproject.utils.vo.PageVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 消息Service实现类
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    /**
     * 保存消息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(Message message) {
        log.info("保存消息信息：{}", message);
        return messageRepository.save(message).getId();
    }

    /**
     * 更新消息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Message message) {
        log.info("修改消息信息：{}", message);
        if (!messageRepository.existsById(message.getId())) {
            throw new RuntimeException("消息不存在");
        }
        messageRepository.save(message);
    }

    /**
     * 删除消息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        log.info("删除消息信息：{}", id);
        messageRepository.deleteById(id);
    }

    /**
     * 批量删除消息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> ids) {
        log.info("批量删除消息信息：{}", ids);
        messageRepository.deleteAllById(ids);
    }

    /**
     * 根据ID查询消息
     */
    @Override
    @Transactional(readOnly = true)
    public Message findById(Long id) {
        log.info("根据ID查询消息信息：{}", id);
        return messageRepository.findById(id).orElse(null);
    }

    /**
     * 分页查询消息
     */
    @Override
    @Transactional(readOnly = true)
    public PageVo<List<Message>> findPage(int page, int pageSize) {
        log.info("分页查询消息信息：page={}, pageSize={}", page, pageSize);
        PageRequest pageable = PageRequest.of(page, pageSize, Sort.by("id").descending());
        Page<Message> pageResult = messageRepository.findAll(pageable);
        return PageVo.of(pageResult.getTotalElements(), pageResult.getContent());
    }

    /**
     * 统计消息数量
     */
    @Override
    @Transactional(readOnly = true)
    public long count() {
        return messageRepository.count();
    }
}
