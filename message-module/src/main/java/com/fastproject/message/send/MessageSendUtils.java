package com.fastproject.message.send;

import com.fastproject.message.service.MessageConfigService;
import com.fastproject.message.service.MessageTemplateService;
import com.fastproject.message.service.MessageTypeService;
import com.fastproject.message.service.MessageVerificationCodeService;
import com.fastproject.message.vo.config.MessageConfigVo;
import com.fastproject.message.vo.template.MessageTemplateVo;
import com.fastproject.message.vo.type.MessageTypeVo;
import com.fastproject.utils.SpringContextUtil;
import com.fastproject.utils.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageSendUtils {

    private final MessageTemplateService templateService;
    private final MessageConfigService configService;
    private final MessageTypeService typeService;
    private final MessageVerificationCodeService verificationCodeService;

    /**
     * 发送消息
     */
    public void sendMessage(
            String templateCode,
            String receiver,
            Map<String, Object> params
    ) {

        MessageTemplateVo template = templateService.getCode(templateCode);
        if (template==null) {
            throw new RuntimeException("发送消息失败，MB不存在");
        }
        MessageConfigVo config = configService.findById(template.getConfigId());
        if (config==null) {
            throw new RuntimeException("发送消息失败，Config不存在");
        }
        MessageTypeVo typeVo = typeService.findById(template.getTypeId());

        String content = renderTemplate(template.getContent(), params);

        MessageSendService sendService = (MessageSendService) SpringContextUtil.getBean(config.getType());

        sendService.send(
                receiver,
                template.getTitle(),
                content,
                config,
                typeVo!=null?typeVo.getCode():null,
                params
        );
        log.info("发送消息");
    }

    /**
     * 验证验证码
     * @param target 目标
     * @param code 验证码
     * @return 验证结果
     */
    public boolean verifyCode(String target, String code) {
        return verificationCodeService.verify(target, code);
    }


    /**
     * @param content 模板
     * @param params 数据
     * @return 返回渲染后的模板
     */
    public String renderTemplate(String content, Map<String, Object> params) {
         for (Map.Entry<String, Object> entry : params.entrySet()) {
            content = content.replace("#{" + entry.getKey() + "}", String.valueOf(entry.getValue()));
        }
        return content;
    }

}
