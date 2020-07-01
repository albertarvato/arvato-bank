package com.arvato.spring.jms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class Sender {
    @Autowired
    private JmsTemplate jmsTemplate;

    public final static String SENDER_CONTENT_KEY = "content";
    public final static String SENDER_SUBJECT_KEY = "subject";
    public final static String SENDER_EMAIL_KEY = "email";

    public void send(SenderSource source){
        Map<String, String> dataMap = new HashMap<>();
        //TODO add proper places from where to call it
        switch (source){
            case ACCOUNT_TEST:{
                dataMap.put(SENDER_CONTENT_KEY, "content");
                dataMap.put(SENDER_SUBJECT_KEY, "sub");
                dataMap.put(SENDER_EMAIL_KEY, "email"); //TODO take it from the user that made the request
                break;
            }
        }
        if (dataMap.isEmpty()){
            log.error("Configuration error for JMS");
            return;
        }
        jmsTemplate.convertAndSend("jmsMessageString",dataMap);
    }

}
