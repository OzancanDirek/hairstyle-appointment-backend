package com.example.randevutakip.Controller;

import com.example.randevutakip.Repository.RandevuRepository;
import com.example.randevutakip.Service.RandevuService.RandevuService;
import com.example.randevutakip.dto.Randevudto;
import com.example.randevutakip.model.Randevu;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/randevu")
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
    public ResponseEntity<List<Randevudto>> getAktifRandevular() {
        return ResponseEntity.ok(randevuService.getRandevuAktif());
    }
}
