package com.example.urlshorteningservice.service;

import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class RandomStringGeneratorService {
    public String generateRandomCode() {
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder shortCode = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            shortCode.append(characters.charAt(random.nextInt(characters.length())));
        }

        return shortCode.toString();
    }
}
