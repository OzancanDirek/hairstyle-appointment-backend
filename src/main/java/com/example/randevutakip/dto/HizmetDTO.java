package com.example.randevutakip.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class HizmetDTO
{
    public String id = UUID.randomUUID().toString();

    private String ad;

    private double fiyat;

    private int sure; // dakika türünden

    private boolean deleted = false;

    private LocalDateTime olusturulmaTarihi = LocalDateTime.now();
}
