package com.example.urlshorteningservice.repository;

import com.example.urlshorteningservice.dto.Url;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRepository extends CrudRepository<Url, String> {
    Optional<Url> findByShortCode(String shortCode);
}

