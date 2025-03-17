package com.example.urlshorteningservice.controller;
import com.example.urlshorteningservice.service.UrlShortenerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/urlShortener")
public class UrlShortenerController {
    private final UrlShortenerService urlShortenerService;
    public UrlShortenerController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @PostMapping
    public String shortenUrl(@RequestParam String url) {
        return urlShortenerService.shortenUrl(url);
    }
    @GetMapping("/{shortCode}")
    public void redirect(@PathVariable String shortCode, HttpServletResponse response) throws IOException {
        String longUrl = urlShortenerService.getOriginalUrl(shortCode);
        response.sendRedirect(longUrl);
    }
}
