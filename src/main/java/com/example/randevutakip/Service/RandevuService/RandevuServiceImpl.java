package com.example.randevutakip.Service.RandevuService;

import com.example.randevutakip.Repository.CalisanRepository;
import com.example.randevutakip.Repository.RandevuRepository;
import com.example.randevutakip.dto.Randevudto;
import com.example.randevutakip.model.Calisan;
import com.example.randevutakip.model.Randevu;
import com.example.randevutakip.model.RandevuDurumu;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class RandevuServiceImpl implements RandevuService
{

    private final RandevuRepository randevuRepository;
    private final CalisanRepository calisanRepository;

    public RandevuServiceImpl(RandevuRepository randevuRepository, CalisanRepository calisanRepository)
    {
        this.randevuRepository = randevuRepository;
        this.calisanRepository = calisanRepository;
    }

    @Override
    public List<Randevudto> getAllRandevular()
    {
        return randevuRepository.findAllAsDto();
    }

    @Override
    public Randevu createRandevu(Randevu randevu)
    {
        if (randevu.getHizmet() == null || randevu.getHizmet().getId() == null)
        {
            throw new RuntimeException("Lütfen bir hizmet seçiniz!");
        }

        String calisanId = randevu.getCalisan().getId();
        LocalDate tarih = randevu.getTarih();
        LocalTime saat = randevu.getSaat();

        boolean mevcutRandevuVar = randevuRepository
                .findExistingRandevu(calisanId, tarih, saat)
                .isPresent();

        if (mevcutRandevuVar)
        {
            throw new RuntimeException("Bu tarih ve saatte seçilen çalışanın zaten bir randevusu var!");
        }

        if (randevu.getDurum() == null)
        {
            randevu.setDurum(RandevuDurumu.BEKLEMEDE);
        }
        return randevuRepository.save(randevu);
    }

    @Override
    public void deleteRandevu(String id)
    {
        Randevu randevu = randevuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bu ID'ye sahip bir randevu bulunamadı!"));

        randevu.setDeleted(true);
        randevu.setDurum(RandevuDurumu.IPTAL_EDILDI);
        randevuRepository.save(randevu);
    }

    @Override
    @Transactional
    public Randevu updateRandevu(Randevudto dto)
    {
        Randevu mevcut = randevuRepository.findById(dto.getRandevuId())
                .orElseThrow(() -> new RuntimeException("Randevu bulunamadı!"));

        LocalDate yeniTarih = dto.getTarih() != null && !dto.getTarih().isEmpty()
                ? LocalDate.parse(dto.getTarih())
                : mevcut.getTarih();

        LocalTime yeniSaat = dto.getSaat() != null && !dto.getSaat().isEmpty()
                ? LocalTime.parse(dto.getSaat())
                : mevcut.getSaat();

        boolean degisimVar =
                !Objects.equals(dto.getAd(), mevcut.getAd()) ||
                        !Objects.equals(dto.getSoyad(), mevcut.getSoyad()) ||
                        !Objects.equals(yeniTarih, mevcut.getTarih()) ||
                        !Objects.equals(yeniSaat, mevcut.getSaat());

        if (!degisimVar && dto.getDurum() != null &&
                dto.getDurum().equalsIgnoreCase(mevcut.getDurum().name()))
        {
            throw new RuntimeException("Herhangi bir değişiklik yapılmadı.");
        }

        if (dto.getDurum() != null)
        {
            try
            {
                RandevuDurumu yeniDurum = RandevuDurumu.valueOf(dto.getDurum());
                mevcut.setDurum(yeniDurum);

                if (yeniDurum == RandevuDurumu.IPTAL_EDILDI)
                    mevcut.setDeleted(true);

            }
            catch (IllegalArgumentException e)
            {
                throw new RuntimeException("Geçersiz randevu durumu: " + dto.getDurum());
            }
        }

        boolean dolu = randevuRepository.existsByCalisan_IdAndTarihAndSaatAndRandevuIdNot(
                mevcut.getCalisan().getId(),
                yeniTarih,
                yeniSaat,
                dto.getRandevuId()
        );

        if (dolu)
            throw new RuntimeException("Bu çalışan için aynı tarih-saat zaten dolu.");

        mevcut.setAd(dto.getAd());
        mevcut.setSoyad(dto.getSoyad());
        mevcut.setTarih(yeniTarih);
        mevcut.setSaat(yeniSaat);

        Calisan calisan = calisanRepository.findById(mevcut.getCalisan().getId())
                .orElseThrow(() -> new RuntimeException("Çalışan bulunamadı!"));
        mevcut.setCalisan(calisan);

        return randevuRepository.save(mevcut);
    }


    @Override
    public List<Randevu> getRandevularByCalisanId(String calisanId)
    {
        return randevuRepository.findAllByCalisan_Id(calisanId);
    }

    @Override
    public List<Randevudto> getRandevuAktif()
    {
        return randevuRepository.findAllByDeletedFalse()
                .stream()
                .map(r -> {
                    Randevudto dto = new Randevudto();
                    dto.setRandevuId(r.getRandevuId());
                    dto.setAd(r.getAd());
                    dto.setSoyad(r.getSoyad());

                    dto.setTarih(r.getTarih() != null ? r.getTarih().toString() : "");
                    dto.setSaat(r.getSaat() != null ? r.getSaat().toString() : "");

                    if (r.getCalisan() != null)
                    {
                        dto.setCalisanAd(r.getCalisan().getAd());
                        dto.setCalisanSoyad(r.getCalisan().getSoyad());
                    }

                    if (r.getHizmet() != null)
                    {
                        dto.setHizmetAd(r.getHizmet().getAd());
                    }

                    dto.setDurum(r.getDurum() != null ? r.getDurum().name() : "BELİRTİLMEDİ");

                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Randevudto> getRandevuBeklemede()
    {
        return randevuRepository.findBeklemedekiRandevular()
                .stream()
                .map(r -> {
                    Randevudto dto = new Randevudto();
                    dto.setRandevuId(r.getRandevuId());
                    dto.setAd(r.getAd());
                    dto.setSoyad(r.getSoyad());
                    dto.setTarih(r.getTarih() != null ? r.getTarih().toString() : "");
                    dto.setSaat(r.getSaat() != null ? r.getSaat().toString() : "");

                    if (r.getCalisan() != null)
                    {
                        dto.setCalisanAd(r.getCalisan().getAd());
                        dto.setCalisanSoyad(r.getCalisan().getSoyad());
                    }

                    if (r.getHizmet() != null)
                    {
                        dto.setHizmetAd(r.getHizmet().getAd());
                    }

                    dto.setDurum(r.getDurum() != null ? r.getDurum().name() : "BELİRTİLMEDİ");

                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getDoluSaatler(String calisanId, String tarih)
    {
        LocalDate randevuTarihi = LocalDate.parse(tarih);
        List<Randevu> mevcutRandevular = randevuRepository.findAllByCalisan_IdAndTarihAndDeletedFalse(calisanId, randevuTarihi);
        return mevcutRandevular.stream()
                .map(r -> r.getSaat().toString().substring(0, 5))
                .collect(Collectors.toList());
    }

    @Override
    public Long getBugunkuRandevuSayisi()
    {
        return randevuRepository.countTamamlananRandevular();
    }

}