package com.example.randevutakip.Controller;

import com.example.randevutakip.Service.YorumService.YorumService;
import com.example.randevutakip.dto.YorumDTO;
import com.example.randevutakip.model.yorum;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/yorum")
@CrossOrigin(origins = "http://localhost:3000")
public class yorumController
{

    private final YorumService yorumService;

    public yorumController(YorumService yorumService)
    {
        this.yorumService = yorumService;
    }

    @PostMapping("/ekle")
    public ResponseEntity<Object> yorumEkle(@RequestBody YorumDTO yorumDTO)
    {
        try
        {
            yorum yorum = yorumService.yorumEkle(yorumDTO);
            return ResponseEntity.ok(yorum);
        }
        catch (RuntimeException e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/tumu")
    public ResponseEntity<List<yorum>> tumYorumlar()
    {
        List<yorum> yorumlar = yorumService.tumYorumlariGetir();
        return ResponseEntity.ok(yorumlar);
    }

    @DeleteMapping("/sil/{yorumId}")
    public ResponseEntity<?> yorumSil(@PathVariable String yorumId)
    {
        try
        {
            yorumService.yorumSil(yorumId);
            return ResponseEntity.ok("Yorum silindi");
        }
        catch (RuntimeException e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/ortalama-puan")
    public ResponseEntity<Map<String, Object>> ortalamaPuan()
    {
        Double ortalama = yorumService.ortalamaPuanGetir();
        Map<String, Object> response = new HashMap<>();
        response.put("ortalamaPuan", ortalama != null ? ortalama : 0.0);
        return ResponseEntity.ok(response);
    }
}