package com.example.urlshorteningservice.controller;
import com.example.urlshorteningservice.dto.Url;
import com.example.urlshorteningservice.repository.UrlRepository;
import com.example.urlshorteningservice.service.UrlShortenerService;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;
import java.util.regex.Pattern;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/urlShortener")
public class UrlShortenerController {
    private final UrlShortenerService urlShortenerService;
    private final UrlRepository urlRepository;
    private final Logger logger = LoggerFactory.getLogger(UrlShortenerController.class);

    @PostMapping
    public ResponseEntity<String> shortenUrl(@RequestParam String url) {
        Pattern pattern = Pattern.compile("^(https?|ftp)://[^\\s/$.?#].\\S*$");
        if (!pattern.matcher(url).matches()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Not a valid URL");
        }

        var shortUrl = urlShortenerService.shortenUrl(url);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(shortUrl);
    }

    @Hidden
    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {
        Optional<Url> optionalUrl = urlShortenerService.getOriginalUrl(shortCode);
        if (optionalUrl.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
        Url originalUrl = optionalUrl.get();

        originalUrl.setAccessCount(originalUrl.getAccessCount() + 1);
        urlRepository.save(originalUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(originalUrl.longUrl));

        return ResponseEntity
                .status(HttpStatus.PERMANENT_REDIRECT)
                .headers(headers)
                .build();
    }

    @GetMapping("/{shortCode}/statistics")
    public ResponseEntity<String> getStatistics(@PathVariable String shortCode) {
        Optional<Url> optionalUrl = urlShortenerService.getOriginalUrl(shortCode);
        if (optionalUrl.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
        Url originalUrl = optionalUrl.get();

        ObjectMapper Obj = new ObjectMapper();
        String json = null;
        try {
            json = Obj.writeValueAsString(originalUrl);
        }
        catch (IOException e) {
            logger.error(e.getMessage());
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(json);
    }
}
