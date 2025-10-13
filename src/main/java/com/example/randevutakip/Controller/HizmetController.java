package com.example.randevutakip.Controller;

import com.example.randevutakip.Repository.HizmetRepository;
import com.example.randevutakip.model.Hizmet;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/hizmet")
public class HizmetController
{
    private HizmetRepository hizmetRepository;

    public HizmetController(HizmetRepository hizmetRepository)
    {
        this.hizmetRepository = hizmetRepository;
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
}
