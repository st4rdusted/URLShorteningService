package com.example.urlshorteningservice.repository;

import com.example.urlshorteningservice.dto.Url;

public interface UrlRepository {
    void post(Url url);
    Url getByShortCode(String shortCode);
    Url deleteByShortCode(String shortCode);
}
