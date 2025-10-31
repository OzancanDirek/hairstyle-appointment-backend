package com.example.randevutakip.Controller;

import com.example.randevutakip.Repository.RandevuRepository;
import com.example.randevutakip.Service.RandevuService.RandevuService;
import com.example.randevutakip.dto.Randevudto;
import com.example.randevutakip.model.Randevu;
import com.example.randevutakip.model.RandevuDurumu;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@RestController
@RequestMapping("/api/randevu")
@CrossOrigin(origins = {"http://localhost:3000", "https://hairstyle-appointment-web-production.up.railway.app"})
public class RandevuController
{
    private final RandevuService randevuService;
    private final RandevuRepository randevuRepository;


    public RandevuController(RandevuService randevuService, RandevuRepository randevuRepository)
    {
        this.randevuService = randevuService;
        this.randevuRepository = randevuRepository;
    }

    @GetMapping("/getAllRandevuList")
    public List<Randevudto> getAllRandevular()
    {
        return randevuService.getAllRandevular();
    }

    @PostMapping("/createRandevu")
    public Randevu createRandevu(@RequestBody Randevu randevu)
    {
        return randevuService.createRandevu(randevu);
    }

    @DeleteMapping("/delete/{id}") //randevu silmeye yarayan endpoint
    public String deleteRandevu(@PathVariable String id)
    {
        randevuService.deleteRandevu(id);
        return "Randevu basariyla silindi!";
    }

    @PutMapping("/update/{id}") //randevu durumunu günceller
    public Randevu updateRandevu(@RequestBody Randevudto dto)
    {
        return randevuService.updateRandevu(dto);
    }

    @GetMapping("/byCalisan/{id}") // seçili olan çalışanın randevularını listeler.
    public List<Randevu> getByCalisanId(@PathVariable String id)
    {
        return randevuService.getRandevularByCalisanId(id);
    }

    @GetMapping("/aktif") //deletedı = 0 olan aktif durumdaki randevuları listeler çalışan için.
    public ResponseEntity<List<Randevudto>> getAktifRandevular()
    {
        return ResponseEntity.ok(randevuService.getRandevuAktif());
    }

    @GetMapping("/getBeklemede")
    public List<Randevudto> getBeklemedeRandevu()
    {
        return randevuService.getRandevuBeklemede();
    }

    @GetMapping("dolu-saatler")
    public ResponseEntity<List<String>> getDoluSaatler(
            @RequestParam String calisanId,
            @RequestParam String tarih)
    {
        return ResponseEntity.ok(randevuService.getDoluSaatler(calisanId, tarih));
    }

    @GetMapping("/bugunki-sayisi")
    public int bugunTamamlananRandevuSayisi()
    {
        // Tarihi UTC olarak al ve sonra Istanbul’a çevir
        ZonedDateTime nowZoned = ZonedDateTime.now(ZoneId.of("Europe/Istanbul"));
        LocalDate bugun = nowZoned.toLocalDate();

        System.out.println("📅 Sistem saati (Europe/Istanbul): " + nowZoned);
        System.out.println("📅 Bugünün tarihi (LocalDate): " + bugun);

        List<Randevu> randevular = randevuRepository.findAll();
        System.out.println("📊 Veritabanından çekilen toplam randevu: " + randevular.size());

        long sayi = randevular.stream()
                .filter(r -> {
                    LocalDate randevuTarihi = r.getTarih(); // Zaten LocalDate

                    boolean tarihEslesme = bugun.equals(randevuTarihi);
                    boolean durumTamamlandi = r.getDurum() == RandevuDurumu.TAMAMLANDI;
                    boolean silinmemis = !r.isDeleted();

                    System.out.println("🔍 RandevuID: " + r.getRandevuId()
                            + " | Tarih: " + randevuTarihi
                            + " | Eşleşti mi? " + tarihEslesme
                            + " | Durum: " + r.getDurum()
                            + " | TAMAMLANDI mı? " + durumTamamlandi
                            + " | Silinmemiş mi? " + silinmemis);

                    return tarihEslesme && durumTamamlandi && silinmemis;
                })
                .count();

        System.out.println("✅ Bugün TAMAMLANDI olan randevu sayısı: " + sayi);
        return (int) sayi;
    }
}
