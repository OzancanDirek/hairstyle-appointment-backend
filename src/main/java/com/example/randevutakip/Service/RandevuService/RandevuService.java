package com.example.randevutakip.Service.RandevuService;

import com.example.randevutakip.dto.Randevudto;
import com.example.randevutakip.model.Randevu;

import java.util.List;

public interface RandevuService
{
    List<Randevudto> getAllRandevular();

    Randevu createRandevu(Randevu randevu);

    void deleteRandevu(String id);

    Randevu updateRandevu(Randevudto dto);

    List<Randevu> getRandevularByCalisanId(String calisanId);

    List<Randevudto> getRandevuAktif();

    List<Randevudto> getRandevuBeklemede();
}

