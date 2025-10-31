package com.example.randevutakip.Service.CalisanService;

import com.example.randevutakip.Repository.CalisanRepository;
import com.example.randevutakip.dto.CalisanDto;
import com.example.randevutakip.model.Calisan;
import com.example.randevutakip.model.Randevu;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Objects;

@Service
public class CalisanServiceImpl implements CalisanService
{
    private final CalisanRepository calisanRepository;

    public CalisanServiceImpl(CalisanRepository calisanRepository)
    {
        this.calisanRepository = calisanRepository;
    }

    public Calisan createCalisan(Calisan calisan)
    {
        return calisanRepository.save(calisan);
    }

    @Override
    public List<Calisan> getCalisanList()
    {
        return calisanRepository.findAll();
    }

    @Override
    public void deleteCalisan(String id)
    {
        if (!calisanRepository.existsById(id))
        {
            throw new RuntimeException("Çalışan bulunamadı!");
        }
        calisanRepository.deleteById(id);
    }

    @Override
    public Calisan updateCalisan(String id, CalisanDto calisanBilgileri)
    {
        Calisan mevcut = calisanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Çalışan bulunamadı!"));

        boolean degisimVar = false;

        if (!Objects.equals(mevcut.getAd(), calisanBilgileri.getAd()))
        {
            mevcut.setAd(calisanBilgileri.getAd());
            degisimVar = true;
        }
        if (!Objects.equals(mevcut.getSoyad(), calisanBilgileri.getSoyad()))
        {
            mevcut.setSoyad(calisanBilgileri.getSoyad());
            degisimVar = true;
        }
        if (!Objects.equals(mevcut.getTelefon(), calisanBilgileri.getTelefon()))
        {
            mevcut.setTelefon(calisanBilgileri.getTelefon());
            degisimVar = true;
        }

        return degisimVar ? calisanRepository.save(mevcut) : mevcut;
    }

    @Override
    public Long getAktifCalisanSayisi()
    {
        return calisanRepository.count();
    }
}
