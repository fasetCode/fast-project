package com.fastproject.message.vo.test;

import lombok.Data;
import java.util.Map;

/**
 * 消息测试发送对象
 */
@Data
public class MessageTestSend {
    /**
     * 模板代码
     */
    private String templateCode;
    /**
     * 接收者
     */
    private String receiver;
    /**
     * 参数
     */
    private Map<String, Object> params;
}
