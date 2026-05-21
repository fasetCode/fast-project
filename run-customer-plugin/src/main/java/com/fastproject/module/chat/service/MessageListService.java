package com.fastproject.module.chat.service;

import com.fastproject.module.chat.domain.MessageList;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.utils.vo.ResultVo;

import java.util.List;

public interface MessageListService {

    /**
     * 保存消息列表
     */
    Long save(MessageList messageList);

    /**
     * 更新消息列表
     */
    void update(MessageList messageList);

    /**
     * 删除消息列表
     */
    void delete(Long id);

    /**
     * 批量删除消息列表
     */
    void batchDelete(List<Long> ids);

    /**
     * 根据ID查询消息列表
     */
    MessageList findById(Long id);

    /**
     * 分页查询消息列表
     */
    PageVo<List<MessageList>> findPage(int page, int pageSize);

    /**
     * 统计消息列表数量
     */
    long count();
}
