package com.arvato.spring.configurations;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.mail.MailHealthIndicator;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

@Slf4j
@Configuration
public class CustomMailHealthIndicator extends MailHealthIndicator {
    @Value("${mail.sender.implementation}")
    private String mode;

    public CustomMailHealthIndicator(JavaMailSenderImpl mailSender) {
        super(mailSender);
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        log.info("custom mail health check for mode: " + mode);
        if (mode.equals("prod")) {
            super.doHealthCheck(builder);
        } else {
            builder.up().withDetail("ok","ok");
        }
    }
}
