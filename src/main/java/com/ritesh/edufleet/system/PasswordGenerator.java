package com.ritesh.edufleet.system;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class PasswordGenerator {
    private static final String CHAT_POOL = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom random = new SecureRandom();

    public String generatePassword(int len) {
        StringBuilder password = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            int index = random.nextInt(CHAT_POOL.length());
            password.append(CHAT_POOL.charAt(index));
        }
        return password.toString();
    }
}
