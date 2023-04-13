package com.example.diploma.util;

import org.springframework.stereotype.Component;

import java.util.UUID;
@Component
public class RandomStringGenerator {
    public String generateRandomString(int length) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return uuid.substring(0, length);
    }
}
