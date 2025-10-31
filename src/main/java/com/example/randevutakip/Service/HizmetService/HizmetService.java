package com.example.randevutakip.Service.HizmetService;

import com.example.randevutakip.Repository.HizmetRepository;
import com.example.randevutakip.dto.HizmetDTO;
import com.example.randevutakip.model.Hizmet;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HizmetService implements HizmetServiceImpl
{
    private HizmetRepository _hizmetRepository;

    public HizmetService(HizmetRepository hizmetRepository)
    {
        this._hizmetRepository = hizmetRepository;
    }

    @Override
    public List<Hizmet> listAllHizmet()
    {
        return _hizmetRepository.findAll();
    }

    @Override
    public Hizmet updateHizmet(HizmetDTO hizmetBilgileri)
    {
        Hizmet hizmet = _hizmetRepository.findById(hizmetBilgileri.getId())
                .orElseThrow(() -> new RuntimeException("Hizmet bulunamadı"));

        boolean degisti = false;

        if (!hizmet.getAd().equals(hizmetBilgileri.getAd()))
        {
            hizmet.setAd(hizmetBilgileri.getAd());
            degisti = true;
        }

        if (hizmet.getSure() != hizmetBilgileri.getSure())
        {
            hizmet.setSure(hizmetBilgileri.getSure());
            degisti = true;
        }

        if (hizmet.getFiyat() != hizmetBilgileri.getFiyat())
        {
            hizmet.setFiyat(hizmetBilgileri.getFiyat());
            degisti = true;
        }
        if (degisti)
        {
            return _hizmetRepository.save(hizmet);
        }
        return hizmet;
    }

    @Override
    public Long getAktifHizmetSayisi()
    {
        return _hizmetRepository.count();
    }
}
