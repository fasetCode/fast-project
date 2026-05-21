package com.fastproject.message.service;

import com.fastproject.message.vo.template.MessageTemplateUpdate;
import com.fastproject.message.vo.template.MessageTemplateCreate;
import com.fastproject.message.vo.template.MessageTemplateQuery;
import com.fastproject.message.vo.template.MessageTemplateVo;
import com.fastproject.utils.vo.PageVo;
import com.fastproject.vo.IdTitleVo;

import java.util.List;

/**
 * 消息模版 Service 接口
 */
public interface MessageTemplateService {

    /**
     * 添加
     */
    Long save(MessageTemplateCreate create);

    /**
     * 修改
     */
    void update(MessageTemplateUpdate update);

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
    MessageTemplateVo findById(Long id);

    /**
     * 分页查询
     */
    PageVo<List<MessageTemplateVo>> findPage(MessageTemplateQuery query);

    /**
     * 查询所有正常状态的模版，用于选择框
     */
    List<MessageTemplateVo> selectAll();

    /**
     * 根据模板码查询
     */
    MessageTemplateVo getCode(String templateCode);
}