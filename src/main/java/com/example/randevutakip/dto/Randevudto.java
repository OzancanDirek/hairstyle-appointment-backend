package com.example.randevutakip.dto;

import com.example.randevutakip.model.Calisan;
import com.example.randevutakip.model.Hizmet;
import com.example.randevutakip.model.RandevuDurumu;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
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
}


