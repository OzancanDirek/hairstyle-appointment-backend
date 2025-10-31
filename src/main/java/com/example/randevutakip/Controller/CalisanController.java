package com.example.randevutakip.Controller;


import com.example.randevutakip.Service.CalisanService.CalisanService;
import com.example.randevutakip.dto.CalisanDto;
import com.example.randevutakip.model.Calisan;
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

@RestController
@RequestMapping("/api/calisan")
@CrossOrigin(origins = {"http://localhost:3000", "https://hairstyle-appointment-web-production.up.railway.app"})
public class CalisanController
{
    private final CalisanService calisanService;

    public CalisanController(CalisanService calisanService)
    {
        this.calisanService = calisanService;
    }

    @PostMapping("/createCalisan")
    public Calisan createCalisan(@RequestBody Calisan calisan)
    {
        return calisanService.createCalisan(calisan);
    }

    @GetMapping("/getCalisanList")
    public List<Calisan> getListCalisan()
    {
        return calisanService.getCalisanList();
    }

    @DeleteMapping("/deleteCalisan/{id}")
    public ResponseEntity<String> deleteCalisan(@PathVariable String id)
    {
        calisanService.deleteCalisan(id);
        return ResponseEntity.ok("Çalışan başarıyla silindi!");
    }

    @PutMapping("/updateCalisan/{id}")
    public Calisan updateCalisan(@PathVariable String id, @RequestBody CalisanDto calisanBilgileri)
    {
        return calisanService.updateCalisan(id, calisanBilgileri);
    }

    @GetMapping("/aktif-sayisi")
    public ResponseEntity<Long> getAktifCalisanSayisi()
    {
        return ResponseEntity.ok(calisanService.getAktifCalisanSayisi());
    }
}