package com.example.urlshorteningservice.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@Table(name = "urls")
public class Url {
    @Id
    public String shortCode;
    public String longUrl;

    public Url(String shortCode, String longUrl) {
        this.shortCode = shortCode;
        this.longUrl = longUrl;
    }
}
