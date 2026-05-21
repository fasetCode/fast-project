package com.fastproject.message.service;

import com.fastproject.message.vo.config.MessageConfigUpdate;
import com.fastproject.message.vo.config.MessageConfigCreate;
import com.fastproject.message.vo.config.MessageConfigQuery;
import com.fastproject.message.vo.config.MessageConfigVo;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.vo.IdTitleVo;

import java.util.List;

/**
 * 消息配置 Service 接口
 */
public interface MessageConfigService {

    /**
     * 添加
     */
    Long save(MessageConfigCreate create);

    /**
     * 修改
     */
    void update(MessageConfigUpdate update);

    /**
     * 删除
     */
    void delete(Long id);

    /**
     * 批量删除
     */
    void batchDelete(List<Long> ids);

    /**
     * 根据ID查询
     */
    MessageConfigVo findById(Long id);

    /**
     * 分页查询
     */
    PageVo<List<MessageConfigVo>> findPage(MessageConfigQuery query);

    /**
     * 查询所有正常状态的配置，用于选择框
     */
    List<IdTitleVo> selectAll();
}