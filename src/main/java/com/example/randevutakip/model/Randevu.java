package com.example.randevutakip.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "randevu")
@Getter
@Setter
public class Randevu
{
    @Id
    @Column(name = "randevu_id", columnDefinition = "VARCHAR(36)")
    private String randevuId = UUID.randomUUID().toString();

    @Column(name = "ad")
    private String ad;

    @Column(name = "soyad")
    private String soyad;

    @ManyToOne
    @JoinColumn(name = "calisan_id", nullable = false)
    @JsonBackReference
    private Calisan calisan;

    @Column(name = "tarih")
    private LocalDate tarih;

    @Column(name = "saat")
    private LocalTime saat;

    @Column(name = "olusturulma_tarihi")
    private LocalDateTime olusturulmaTarihi = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "durum", nullable = false)
    private RandevuDurumu durum = RandevuDurumu.BEKLEMEDE;

    @Column(name = "deleted")
    private boolean deleted = false;

    @ManyToOne
    @JoinColumn(name = "hizmet_id")
    private Hizmet hizmet;

    @Column(name = "reminder_sent")
    private boolean reminderSent = false;


}
