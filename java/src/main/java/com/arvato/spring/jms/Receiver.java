package com.arvato.spring.jms;

import com.arvato.spring.models.HelloMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class Receiver {
    @JmsListener(destination = "jmsMessageString")
    public void receive(String message) {
        log.info("Received message " + message);
    }
}
