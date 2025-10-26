package com.example.randevutakip.Controller;

import com.example.randevutakip.Service.StatisticService.StatisticService;
import com.example.randevutakip.dto.GunlukKarDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/istatistik")
@CrossOrigin(origins = {"http://localhost:3000", "https://hairstyle-appointment-web-production.up.railway.app"})
public class StatisticController
{
    @Autowired
    private StatisticService istatistikService;

    @GetMapping("/gunluk-kar")
    public ResponseEntity<List<GunlukKarDTO>> getGunlukKar(
            @RequestParam(defaultValue = "30") int sonKacGun
    )
    {
        return ResponseEntity.ok(istatistikService.getGunlukKarlar(sonKacGun));
    }

    @GetMapping("/gunluk-kar/aralik")
    public ResponseEntity<List<GunlukKarDTO>> getGunlukKarAralik(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate baslangic,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate bitis
    )
    {
        return ResponseEntity.ok(istatistikService.getGunlukKarlar(baslangic, bitis));
    }
}
