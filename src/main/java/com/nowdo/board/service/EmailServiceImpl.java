package com.nowdo.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private JavaMailSender javaMailSender;

    @Value("${frontend.url}")
    private String frontendUrl;


    @Autowired
    public EmailServiceImpl (JavaMailSender theJavaMailSender) {
        javaMailSender = theJavaMailSender;
    }

    @Override
    public void sendResetPasswordEmail(String toEmail, String token) {
//        String link = "http://192.168.0.106:3000/reset-password?token=" + token;
        String link = frontendUrl + "/reset-password?token=" + token;
        String text = "請點擊以下連結重設密碼（1 小時內有效）：\n" + link;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("重設密碼通知");
        message.setText(text);
        javaMailSender.send(message);
    }
}
