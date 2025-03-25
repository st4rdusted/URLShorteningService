package com.example.urlshorteningservice.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "urls")
public class Url {
    @Id
    public String shortCode;
    public String longUrl;
}
