package com.example.urlshorteningservice.service;

import com.example.urlshorteningservice.dto.Url;
import com.example.urlshorteningservice.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UrlShortenerService {
    private final UrlRepository urlRepository;
    private final RandomStringGeneratorService randomStringGeneratorService = new RandomStringGeneratorService();

    public String shortenUrl(String url) {
        String shortCode = randomStringGeneratorService.generateRandomCode();
        if(urlRepository.existsById(shortCode)){
            return HttpStatus.BAD_REQUEST.getReasonPhrase(); //TODO rewrite
        }
        urlRepository.save(new Url(shortCode, url));
        return "http://localhost:8080/urlShortener/" + shortCode;
    }

    public String getOriginalUrl(String shortCode) {
        Optional<Url> url = urlRepository.findById(shortCode);
        if (url.isEmpty()) {
            return HttpStatus.NOT_FOUND.getReasonPhrase();
        }
        return "https://" + url.get().getLongUrl();
    }
}
