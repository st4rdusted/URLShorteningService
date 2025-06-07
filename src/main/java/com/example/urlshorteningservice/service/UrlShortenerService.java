package com.example.urlshorteningservice.service;

import com.example.urlshorteningservice.dto.Url;
import com.example.urlshorteningservice.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UrlShortenerService {
    private final UrlRepository urlRepository;
    private final RandomStringGeneratorService randomStringGeneratorService;

    public String shortenUrl(String longUrl) {
        String shortCode = randomStringGeneratorService.generateRandomCode();
        if(urlRepository.existsById(shortCode)){
            shortenUrl(longUrl);
        }
        String shortUrl = "http://localhost:8080/urlShortener/" + shortCode;
        urlRepository.save(new Url(shortCode, longUrl));
        return shortUrl;
    }

    public Optional<Url> getOriginalUrl(String shortCode) {
        return urlRepository.findById(shortCode);
    }
}
