package com.example.urlshorteningservice.controller;
import com.example.urlshorteningservice.dto.Url;
import com.example.urlshorteningservice.service.UrlShortenerService;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
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
        Optional<Url> originalUrl = urlShortenerService.getOriginalUrl(shortCode);
        if (originalUrl.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(originalUrl.get().longUrl));

        return ResponseEntity
                .status(HttpStatus.MOVED_PERMANENTLY)
                .headers(headers)
                .build();
    }

    @GetMapping("/{shortCode}/statistics")
    public ResponseEntity<String> getStatistics(@PathVariable String shortCode) {
        Optional<Url> originalUrl = urlShortenerService.getOriginalUrl(shortCode);
        if (originalUrl.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }

        ObjectMapper Obj = new ObjectMapper();
        String json = null;
        try {
            json = Obj.writeValueAsString(originalUrl.get());
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(json);
    }
}
