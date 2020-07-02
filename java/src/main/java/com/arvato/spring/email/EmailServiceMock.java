package com.arvato.spring.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "mail.sender.implementation", havingValue = "mock")
@Slf4j
public class EmailServiceMock implements EmailService{
    @Override
    public void sendMail(String to, String subject, String content) {
        log.info("would have sent email to: " + to + " with subject: " + subject + " and content: " + content);
    }
}
