package com.fastproject.module.chat.service.impl;

import com.fastproject.module.chat.domain.MessageList;
import com.fastproject.module.chat.repository.MessageListRepository;
import com.fastproject.module.chat.service.MessageListService;
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
 * 消息列表Service实现类
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MessageListServiceImpl implements MessageListService {

    private final MessageListRepository messageListRepository;

    /**
     * 保存消息列表
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(MessageList messageList) {
        log.info("保存消息列表信息：{}", messageList);
        return messageListRepository.save(messageList).getId();
    }

    /**
     * 更新消息列表
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(MessageList messageList) {
        log.info("修改消息列表信息：{}", messageList);
        if (!messageListRepository.existsById(messageList.getId())) {
            throw new RuntimeException("消息列表不存在");
        }
        messageListRepository.save(messageList);
    }

    /**
     * 删除消息列表
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        log.info("删除消息列表信息：{}", id);
        messageListRepository.deleteById(id);
    }

    /**
     * 批量删除消息列表
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> ids) {
        log.info("批量删除消息列表信息：{}", ids);
        messageListRepository.deleteAllById(ids);
    }

    /**
     * 根据ID查询消息列表
     */
    @Override
    @Transactional(readOnly = true)
    public MessageList findById(Long id) {
        log.info("根据ID查询消息列表信息：{}", id);
        return messageListRepository.findById(id).orElse(null);
    }

    /**
     * 分页查询消息列表
     */
    @Override
    @Transactional(readOnly = true)
    public PageVo<List<MessageList>> findPage(int page, int pageSize) {
        log.info("分页查询消息列表信息：page={}, pageSize={}", page, pageSize);
        PageRequest pageable = PageRequest.of(page, pageSize, Sort.by("id").descending());
        Page<MessageList> pageResult = messageListRepository.findAll(pageable);
        return PageVo.of(pageResult.getTotalElements(), pageResult.getContent());
    }

    /**
     * 统计消息列表数量
     */
    @Override
    @Transactional(readOnly = true)
    public long count() {
        return messageListRepository.count();
    }
}
