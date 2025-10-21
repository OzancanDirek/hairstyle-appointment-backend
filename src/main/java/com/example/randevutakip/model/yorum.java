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
@Table(name = "yorum")
@Getter
@Setter
public class yorum {
    @Id
    @Column(name = "id", columnDefinition = "VARCHAR(36)")
    private String id = UUID.randomUUID().toString();

    @Column(name = "ad", nullable = false)
    private String ad;

    @Column(name = "soyad", nullable = false)
    private String soyad;

    @Column(name = "telefon")
    private String telefon;

    @Column(name = "yildiz", nullable = false)
    private Integer yildiz; // 1-5 arası

    @Column(name = "yorum_metni", columnDefinition = "TEXT")
    private String yorumMetni;

    @Column(name = "olusturma_tarihi")
    private LocalDateTime olusturmaTarihi = LocalDateTime.now();

    @Column(name = "onaylandi")
    private boolean onaylandi = false;
}