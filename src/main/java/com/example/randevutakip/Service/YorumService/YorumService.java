package com.example.randevutakip.Service.YorumService;

import com.example.randevutakip.dto.YorumDTO;
import com.example.randevutakip.model.Randevu;
import com.example.randevutakip.model.yorum;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface YorumService
{
    yorum yorumEkle(@RequestBody YorumDTO yorum);

    List<yorum> tumYorumlariGetir();

    void yorumSil(String yorumId);

    Double ortalamaPuanGetir();
}
