package com.fastproject.message.send;

import com.fastproject.message.enums.MessageRecordStatusEnum;
import com.fastproject.message.enums.MessageVerificationCodeStatusEnum;
import com.fastproject.message.enums.MessageTypeEnum;
import com.fastproject.message.service.MessageRecordService;
import com.fastproject.message.service.MessageVerificationCodeService;
import com.fastproject.message.vo.config.MessageConfigVo;
import com.fastproject.message.vo.record.MessageRecordCreate;
import com.fastproject.message.vo.verificationcode.MessageVerificationCodeCreate;
import com.fastproject.utils.TokenUtils;
import com.fastproject.vo.SecurityUserVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@RequiredArgsConstructor
@Slf4j
public abstract class AbstractMessageSendService implements MessageSendService {

    private final MessageRecordService messageRecordService;
    private final TokenUtils tokenUtils;
    private final MessageVerificationCodeService messageVerificationCodeService;

    @Override
    public void send(String receiver, String title, String message, MessageConfigVo config,
                     String messageType, Map<String, Object> params) {
        MessageRecordCreate messageRecordCreate = new MessageRecordCreate();
        log.info("发送消息：receiver={}, title={}, message={}, config={}, messageType={}, params={}", receiver, title, message, config, messageType, params);
        try {
            doSend(receiver, title, message, config);
            messageRecordCreate.setStatus(MessageRecordStatusEnum.SENT.getCode());
        } catch (Exception e) {
            log.error("发送消息失败", e);
            messageRecordCreate.setStatus(MessageRecordStatusEnum.FAILED.getCode());
        }
        // 创建发送记录 
        messageRecordCreate.setConfigId(config.getId());
        messageRecordCreate.setReceiver(receiver);
        messageRecordCreate.setContent(message); 
        messageRecordCreate.setTitle(title);
        messageRecordCreate.setMessageType(messageType); 
        SecurityUserVo user = tokenUtils.getUser();
        if (user != null) {
            messageRecordCreate.setOperatorId(user.getUserId());
        }
        messageRecordService.save(messageRecordCreate);

        // 保存验证码
        if (MessageTypeEnum.CODE.getCode().equals(messageType)) {
            log.info("保存验证码：receiver={}, code={}", receiver, params.get("code").toString());
            MessageVerificationCodeCreate verificationCodeCreate = new MessageVerificationCodeCreate();
            verificationCodeCreate.setCode(params.get("code").toString());
            verificationCodeCreate.setTarget(receiver);
            verificationCodeCreate.setConfigId(config.getId());
            verificationCodeCreate.setStatus(MessageVerificationCodeStatusEnum.VALID.getCode());
            // 设置过期时间为10分钟后
            verificationCodeCreate.setExpireTime(System.currentTimeMillis() + 10 * 60 * 1000);
            messageVerificationCodeService.save(verificationCodeCreate);
        }
        log.info("发送消息完成");
    }

    // 发送操作
    public abstract void doSend(String receiver, String title, String message, MessageConfigVo config);
}
