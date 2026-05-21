package com.fastproject.message.send.impl;

import com.fastproject.exception.BusinessException;
import com.fastproject.message.send.AbstractMessageSendService;
import com.fastproject.message.send.vo.MailConfigVo;
import com.fastproject.message.service.MessageRecordService;
import com.fastproject.message.service.MessageVerificationCodeService;
import com.fastproject.message.vo.config.MessageConfigVo;
import com.fastproject.utils.TokenUtils;
import com.fastproject.utils.utils.JsonUtils;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Properties;

/**
 * 邮件发送实现类
 * @author fastproject
 */
@Service("messageSendMailService")
@Slf4j
public class MessageSendMailServiceImpl extends AbstractMessageSendService {


    /**
     * 构造函数，注入所需的依赖服务
     *
     * @param messageRecordService          消息记录服务
     * @param tokenUtils                    Token 工具类
     * @param messageVerificationCodeService 验证码服务
     */
    protected MessageSendMailServiceImpl(MessageRecordService messageRecordService, TokenUtils tokenUtils, MessageVerificationCodeService messageVerificationCodeService) {
        super(messageRecordService, tokenUtils, messageVerificationCodeService);
    }

    /**
     * 执行具体的邮件发送逻辑
     *
     * @param receiver 接收人邮箱地址
     * @param title    邮件标题
     * @param message  邮件正文内容（支持 HTML）
     * @param config   消息配置对象，包含邮件服务器配置的 JSON 字符串
     */
    @Override
    public void doSend(String receiver, String title, String message, MessageConfigVo config) {
        log.info("发送邮件：receiver={}, title={}, message={}", receiver, title, message);
        
        // 1. 将配置 JSON 解析为邮件配置对象
        MailConfigVo configVo = JsonUtils.fromJson(config.getConfig(), MailConfigVo.class);

        try {
            // 2. 初始化并配置 JavaMailSender
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setHost(configVo.getHost());
            if (configVo.getPort() != null) {
                mailSender.setPort(configVo.getPort());
            }
            mailSender.setUsername(configVo.getUsername());
            mailSender.setPassword(configVo.getPassword());
            if (configVo.getProtocol() != null) {
                mailSender.setProtocol(configVo.getProtocol());
            }
            if (configVo.getDefaultEncoding() != null) {
                mailSender.setDefaultEncoding(configVo.getDefaultEncoding());
            }

            // 3. 配置 JavaMail 属性（认证与安全连接）
            Properties props = mailSender.getJavaMailProperties();
            props.put("mail.smtp.auth", configVo.getAuth() != null ? configVo.getAuth() : true);
            props.put("mail.smtp.starttls.enable", configVo.getStarttlsEnable() != null ? configVo.getStarttlsEnable() : true);

            // 4. 构建 MIME 邮件消息
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, mailSender.getDefaultEncoding());
            
            // 5. 设置发件人（若未配置则默认使用用户名）
            String from = configVo.getFrom();
            if (from == null || from.isEmpty()) {
                from = configVo.getUsername();
            }
            helper.setFrom(from);
            
            // 6. 设置收件人、标题和正文
            helper.setTo(receiver);
            helper.setSubject(title);
            helper.setText(message, true);

            // 7. 执行发送
            mailSender.send(mimeMessage);
            log.info("邮件发送成功：receiver={}", receiver);
        } catch (Exception e) {
            log.error("邮件发送失败", e);
            throw new BusinessException("邮件发送失败: " + e.getMessage());
        }
    }
}
