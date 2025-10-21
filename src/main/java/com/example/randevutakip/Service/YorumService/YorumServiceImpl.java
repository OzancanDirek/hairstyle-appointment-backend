package com.example.randevutakip.Service.YorumService;

import com.example.randevutakip.Repository.YorumRepository;
import com.example.randevutakip.dto.YorumDTO;
import com.example.randevutakip.model.yorum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class YorumServiceImpl implements YorumService
{

    @Autowired
    private YorumRepository yorumRepository;

    @Override
    public yorum yorumEkle(YorumDTO yorumDTO)
    {
        // Validasyon
        if (yorumDTO.getYildiz() < 1 || yorumDTO.getYildiz() > 5)
        {
            throw new RuntimeException("Yıldız değeri 1-5 arasında olmalıdır");
        }

        if (yorumDTO.getAd() == null || yorumDTO.getAd().trim().isEmpty())
        {
            throw new RuntimeException("Ad alanı zorunludur");
        }

        if (yorumDTO.getSoyad() == null || yorumDTO.getSoyad().trim().isEmpty())
        {
            throw new RuntimeException("Soyad alanı zorunludur");
        }

        // Yorum oluştur
        yorum createYorum = new yorum();
        createYorum.setId(UUID.randomUUID().toString());
        createYorum.setAd(yorumDTO.getAd());
        createYorum.setSoyad(yorumDTO.getSoyad());
        createYorum.setTelefon(yorumDTO.getTelefon());
        createYorum.setYildiz(yorumDTO.getYildiz());
        createYorum.setYorumMetni(yorumDTO.getYorumMetni());
        createYorum.setOlusturmaTarihi(LocalDateTime.now());
        createYorum.setOnaylandi(false);

        return yorumRepository.save(createYorum);
    }

    @Override
    public List<yorum> tumYorumlariGetir()
    {
        return yorumRepository.findAll();
    }

    @Override
    public void yorumSil(String yorumId)
    {
        yorum yorum = yorumRepository.findById(yorumId)
                .orElseThrow(() -> new RuntimeException("Yorum bulunamadı"));

        yorumRepository.delete(yorum);
    }

    @Override
    public Double ortalamaPuanGetir()
    {
        return yorumRepository.findAverageYildiz();
    }
}