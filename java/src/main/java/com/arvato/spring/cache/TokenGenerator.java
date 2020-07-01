package com.arvato.spring.cache;

import java.security.SecureRandom;
import java.util.Base64;

public class TokenGenerator {

    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();
    private static final SecureRandom secureRandom = new SecureRandom();

    public static String generateNewToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

}

