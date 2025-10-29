package com.example.randevutakip.dto;

import com.example.randevutakip.model.RandevuDurumu;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Randevudto
{
    private String randevuId;
    private String ad;
    private String soyad;
    private String tarih;
    private String saat;
    private String calisanAd;
    private String calisanSoyad;
    private String hizmetAd;
    private String durum;

    public Randevudto(String randevuId, String ad, String soyad,
                      LocalDate tarih, LocalTime saat, RandevuDurumu durum,
                      String calisanAd, String calisanSoyad, String hizmetAd)
    {
        this.randevuId = randevuId;
        this.ad = ad;
        this.soyad = soyad;
        this.tarih = tarih != null ? tarih.toString() : "";
        this.saat = saat != null ? saat.toString() : "";
        this.durum = durum != null ? durum.name() : "";
        this.calisanAd = calisanAd;
        this.calisanSoyad = calisanSoyad;
        this.hizmetAd = hizmetAd;
    }
}