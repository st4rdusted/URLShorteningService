package com.example.urlshorteningservice.dto;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "urls")
public class Url {
    @Id
    public String shortCode;
    public String longUrl;
    public Date createdAt;
    public Date updatedAt;
    public int accessCount;

    public Url(String shortCode, String longUrl) {
        this.shortCode = shortCode;
        this.longUrl = longUrl;
    }

    @PrePersist
    void createdAt() {
        this.createdAt = this.updatedAt = new Date();
    }

    @PreUpdate
    void updatedAt() {
        this.updatedAt = new Date();
    }
}

