package com.example.randevutakip.Service.StatisticService;

import com.example.randevutakip.Repository.RandevuRepository;
import com.example.randevutakip.dto.GunlukKarDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatisticServiceImpl implements StatisticService
{
    @Autowired
    private RandevuRepository randevuRepository;

    @Override
    public List<GunlukKarDTO> getGunlukKarlar(int sonKacGun)
    {
        LocalDate baslangic = LocalDate.now().minusDays(sonKacGun);

        List<Object[]> sonuclar = randevuRepository.getSonNGunKarIstatistikleri(baslangic);

        return sonuclar.stream()
                .map(row -> new GunlukKarDTO(
                        ((java.sql.Date) row[0]).toLocalDate(),
                        ((Number) row[1]).doubleValue()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<GunlukKarDTO> getGunlukKarlar(LocalDate baslangic, LocalDate bitis)
    {
        List<Object[]> sonuclar = randevuRepository.getGunlukKarIstatistikleri(baslangic, bitis);

        return sonuclar.stream()
                .map(row -> new GunlukKarDTO(
                        ((java.sql.Date) row[0]).toLocalDate(),
                        ((Number) row[1]).doubleValue()
                ))
                .collect(Collectors.toList());
    }
}