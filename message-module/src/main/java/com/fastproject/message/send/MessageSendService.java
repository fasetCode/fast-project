package com.fastproject.message.send;

import com.fastproject.message.vo.config.MessageConfigVo;

import java.util.Map;

public interface MessageSendService {

    /**
     * 发送消息
     * @param receiver 接收者
     * @param title 标题
     * @param message 内容
     * @param config 配置
     */
    void send(
            String receiver,
            String title,
            String message,
            MessageConfigVo config,
            String messageType,
            Map<String, Object> params
    );

}
