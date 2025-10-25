package com.example.randevutakip.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse
{
    private boolean basarili;
    private String mesaj;
    private String kullaniciAdi;
    private String ad;
    private String soyad;
    private List<String> roller;
}