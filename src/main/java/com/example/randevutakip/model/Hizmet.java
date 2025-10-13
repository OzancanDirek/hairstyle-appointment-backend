package com.example.randevutakip.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "hizmet")
@Getter
@Setter
public class Hizmet
{
    @Id
    @Column(name = "id", columnDefinition = "VARCHAR(36)")
    public String id = UUID.randomUUID().toString();

    @Column(name = "ad", nullable = false)
    private String ad;

    @Column(name = "fiyat", nullable = false)
    private double fiyat;

    @Column(name = "sure", nullable = false)
    private int sure; // dakika türünden

    @Column(name = "deleted")
    private boolean deleted = false;

    @Column(name = "olusturulma_tarihi")
    private LocalDateTime olusturulmaTarihi = LocalDateTime.now();
}