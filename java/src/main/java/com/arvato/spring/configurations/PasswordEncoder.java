package com.arvato.spring.configurations;

public interface PasswordEncoder {
    String encode(String rawPassword);
    boolean matcher(String rawPassword, String encodedPassword);
}
