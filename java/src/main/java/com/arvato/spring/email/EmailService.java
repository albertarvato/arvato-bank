package com.arvato.spring.email;

public interface EmailService {
    void sendMail(String to, String subject, String content);
}
