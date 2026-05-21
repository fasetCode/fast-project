package com.fastproject.message.service;

import com.fastproject.message.vo.type.MessageTypeUpdate;
import com.fastproject.message.vo.type.MessageTypeCreate;
import com.fastproject.message.vo.type.MessageTypeQuery;
import com.fastproject.message.vo.type.MessageTypeVo;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.vo.IdTitleVo;

import java.util.List;

/**
 * 消息类型 Service 接口
 */
public interface MessageTypeService {

    /**
     * 添加
     */
    Long save(MessageTypeCreate create);

    /**
     * 修改
     */
    void update(MessageTypeUpdate update);

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
    MessageTypeVo findById(Long id);

    /**
     * 分页查询
     */
    PageVo<List<MessageTypeVo>> findPage(MessageTypeQuery query);

    /**
     * 查询所有正常状态的配置，用于选择框
     */
    List<IdTitleVo> selectAll();
}