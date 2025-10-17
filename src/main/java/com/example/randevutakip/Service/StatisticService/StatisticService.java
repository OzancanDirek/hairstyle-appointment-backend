package com.example.randevutakip.Service.StatisticService;


import com.example.randevutakip.dto.GunlukKarDTO;

import java.time.LocalDate;
import java.util.List;

public interface StatisticService
{
    List<GunlukKarDTO> getGunlukKarlar(int sonKacGun);

    List<GunlukKarDTO> getGunlukKarlar(LocalDate baslangic, LocalDate bitis);
}
