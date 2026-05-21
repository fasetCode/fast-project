package com.fastproject.module.chat.service;

import com.fastproject.module.chat.domain.Message;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.utils.vo.ResultVo;

import java.util.List;

public interface MessageService {

    /**
     * 保存消息
     */
    Long save(Message message);

    /**
     * 更新消息
     */
    void update(Message message);

    /**
     * 删除消息
     */
    void delete(Long id);

    /**
     * 批量删除消息
     */
    void batchDelete(List<Long> ids);

    /**
     * 根据ID查询消息
     */
    Message findById(Long id);

    /**
     * 分页查询消息
     */
    PageVo<List<Message>> findPage(int page, int pageSize);

    /**
     * 统计消息数量
     */
    long count();
}
