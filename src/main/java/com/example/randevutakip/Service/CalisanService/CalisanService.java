package com.example.randevutakip.Service.CalisanService;

import com.example.randevutakip.dto.CalisanDto;
import com.example.randevutakip.model.Calisan;

import java.util.List;

public interface CalisanService
{
    Calisan createCalisan(Calisan calisan);

    List<Calisan> getCalisanList();

    void deleteCalisan(String id);

    Calisan updateCalisan(String id,CalisanDto calisanBilgileri);
}
