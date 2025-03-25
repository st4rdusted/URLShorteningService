package com.example.urlshorteningservice.controller;
import com.example.urlshorteningservice.service.UrlShortenerService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/urlShortener")
public class UrlShortenerController {
    private final UrlShortenerService urlShortenerService;

    @PostMapping
    public String shortenUrl(@RequestParam String url) {
        return urlShortenerService.shortenUrl(url);
    }
    @GetMapping("/{shortCode}")
    public void redirect(@PathVariable String shortCode, HttpServletResponse response) throws IOException {
        String originalUrl = urlShortenerService.getOriginalUrl(shortCode);
        response.sendRedirect(originalUrl);
    }
}
