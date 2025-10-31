package com.example.randevutakip.Service.HizmetService;

import com.example.randevutakip.dto.HizmetDTO;
import com.example.randevutakip.model.Hizmet;

import java.util.List;

public interface HizmetServiceImpl
{
    List<Hizmet> listAllHizmet();

    Hizmet updateHizmet(HizmetDTO hizmetBilgileri);

    Long getAktifHizmetSayisi();
}
