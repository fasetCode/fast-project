package com.fastproject.message.service;

import com.fastproject.message.vo.verificationcode.MessageVerificationCodeUpdate;
import com.fastproject.message.vo.verificationcode.MessageVerificationCodeCreate;
import com.fastproject.message.vo.verificationcode.MessageVerificationCodeQuery;
import com.fastproject.message.vo.verificationcode.MessageVerificationCodeVo;
import com.fastproject.utils.vo.PageVo;

import java.util.List;

/**
 * 验证码 Service 接口
 */
public interface MessageVerificationCodeService {

    /**
     * 添加
     */
    Long save(MessageVerificationCodeCreate create);

    /**
     * 修改
     */
    void update(MessageVerificationCodeUpdate update);

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
    MessageVerificationCodeVo findById(Long id);

    /**
     * 分页查询
     */
    PageVo<List<MessageVerificationCodeVo>> findPage(MessageVerificationCodeQuery query);

    /**
     * 校验验证码
     * @param target 目标
     * @param code 验证码
     * @return 校验结果
     */
    boolean verify(String target, String code);
}