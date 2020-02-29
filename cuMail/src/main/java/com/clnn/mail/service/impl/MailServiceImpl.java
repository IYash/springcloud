package com.clnn.mail.service.impl;

import com.clnn.mail.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendSimpleEmail() {
        // 构造Email消息
        System.out.println("sending simple email...");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("******@163.com");
        message.setTo("****@hotmail.com");
        message.setSubject("邮件主题");
        message.setText("邮件内容");
        mailSender.send(message);
    }
}
