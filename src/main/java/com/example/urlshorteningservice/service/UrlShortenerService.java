package com.example.urlshorteningservice.service;

import com.example.urlshorteningservice.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UrlShortenerService {
    private final UrlRepository urlRepository;
    private final RandomStringGeneratorService randomStringGeneratorService = new RandomStringGeneratorService();

    public String shortenUrl(String longUrl) {
        String code = randomStringGeneratorService.generateRandomCode();
        return "http://localhost:8080/urlShortener" + code;
    }

    public String getOriginalUrl(String shortCode) {
        return urlRepository.findByShortCode(shortCode).toString();
    }
}
