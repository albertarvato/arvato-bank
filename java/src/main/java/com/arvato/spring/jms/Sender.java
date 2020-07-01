package com.arvato.spring.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class Sender {
    @Autowired
    JmsTemplate jmsTemplate;

    public void send(String text){
        jmsTemplate.convertAndSend("jmsMessageString",text);
    }

}
