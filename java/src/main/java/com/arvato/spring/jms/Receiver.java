package com.arvato.spring.jms;

import com.arvato.spring.email.EmailService;
import com.arvato.spring.email.EmailServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class Receiver {
    @Autowired
    private EmailService emailService;

    @JmsListener(destination = "jmsMessageString")
    public void receive(Map<String, String> message) {
        log.info("Received message " + message);
        emailService.sendMail(message.get(Sender.SENDER_EMAIL_KEY), message.get(Sender.SENDER_SUBJECT_KEY), message.get(Sender.SENDER_CONTENT_KEY));
    }
}
