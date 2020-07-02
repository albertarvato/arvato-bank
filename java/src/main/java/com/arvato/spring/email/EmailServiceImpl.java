package com.arvato.spring.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "mail.sender.implementation", havingValue = "prod")
@Slf4j
public class EmailServiceImpl implements EmailService{
    private JavaMailSender mailSender;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender){
        log.info("constructor EmailServiceImpl");
        this.mailSender = javaMailSender;
    }

    public void sendMail(String to, String subject, String content){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);

        mailSender.send(message);
    }
}
