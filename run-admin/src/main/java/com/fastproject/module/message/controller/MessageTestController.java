package com.fastproject.module.message.controller;

import com.fastproject.logs.annotation.Log;
import com.fastproject.logs.enums.LogAction;
import com.fastproject.logs.enums.LogType;
import com.fastproject.message.send.MessageSendUtils;
import com.fastproject.message.vo.test.MessageTestSend;
import com.fastproject.message.vo.test.MessageTestVerify;
import com.fastproject.utils.vo.ResultVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 消息测试 Controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/message/test")
public class MessageTestController {

    private final MessageSendUtils messageSendUtils;

    /**
     * 发送测试消息
     */
    @PostMapping("/send")
    @PreAuthorize("@ps.hasPermission('admin:message:test:send')")
    @Log(value = "发送测试消息", type = LogType.BUSINESS, action = LogAction.OTHER)
    public ResultVo<Object> send(@RequestBody MessageTestSend testSend) {
        messageSendUtils.sendMessage(testSend.getTemplateCode(), testSend.getReceiver(), testSend.getParams());
        return ResultVo.success("发送指令已提交");
    }

    /**
     * 验证测试验证码
     */
    @PostMapping("/verify")
    @PreAuthorize("@ps.hasPermission('admin:message:test:verify')")
    @Log(value = "验证测试验证码", type = LogType.BUSINESS, action = LogAction.OTHER)
    public ResultVo<Boolean> verify(@RequestBody MessageTestVerify testVerify) {
        boolean result = messageSendUtils.verifyCode(testVerify.getTarget(), testVerify.getCode());
        return ResultVo.success(result);
    }
}
