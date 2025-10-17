package com.example.randevutakip.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GunlukKarDTO
{
    private LocalDate tarih;
    private Double toplamKar;
}