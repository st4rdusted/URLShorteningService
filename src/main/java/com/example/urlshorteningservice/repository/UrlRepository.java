package com.example.urlshorteningservice.repository;

import com.example.urlshorteningservice.dto.Url;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends CrudRepository<Url, String> {}

