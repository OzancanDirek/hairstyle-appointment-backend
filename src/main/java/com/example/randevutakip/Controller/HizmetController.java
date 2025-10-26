package com.example.randevutakip.Controller;

import com.example.randevutakip.Repository.HizmetRepository;
import com.example.randevutakip.Service.HizmetService.HizmetService;
import com.example.randevutakip.dto.HizmetDTO;
import com.example.randevutakip.model.Hizmet;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000", "https://hairstyle-appointment-web-production.up.railway.app"})
@RestController
@RequestMapping("/api/hizmet")
public class HizmetController
{
    private HizmetRepository hizmetRepository;
    private HizmetService hizmetService;

    public HizmetController(HizmetRepository hizmetRepository, HizmetService hizmetService)
    {
        this.hizmetRepository = hizmetRepository;
        this.hizmetService = hizmetService;
    }

    @GetMapping("/aktif")
    public List<Hizmet> getAktifHizmetler()
    {
        return hizmetRepository.findAllByDeletedFalse();
    }

    @PostMapping("/ekle")
    public Hizmet createHizmet(@RequestBody Hizmet hizmet)
    {
        return hizmetRepository.save(hizmet);
    }

    @DeleteMapping("/{id}")
    public String deleteHizmet(@PathVariable String id)
    {
        Hizmet hizmet = hizmetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hizmet bulunamadı!"));
        hizmet.setDeleted(true);
        hizmetRepository.save(hizmet);
        return "Hizmet silindi.";
    }

    @GetMapping("/tumHizmetler")
    public List<Hizmet> getAllHizmet()
    {
        return hizmetRepository.findAll();
    }

    @PutMapping("/guncelle")
    public ResponseEntity<?> hizmetGuncelle(@RequestBody HizmetDTO hizmetDTO)
    {
        try
        {
            Hizmet guncelHizmet = hizmetService.updateHizmet(hizmetDTO);
            return ResponseEntity.ok(guncelHizmet);
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
