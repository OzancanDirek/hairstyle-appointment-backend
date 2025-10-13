package com.example.randevutakip.Service.Notification;

import com.example.randevutakip.model.Randevu;
import org.springframework.stereotype.Service;

@Service
public class NotificationService
{
    public void sendInstantNotification(Randevu randevu)
    {
        String message = String.format("Sayın %s, %s tarihinde saat %s için randevunuz başarıyla oluşturuldu.",
                randevu.getAd(), randevu.getTarih(), randevu.getSaat());
        System.out.println("[BİLDİRİM] " + message);
    }

    public void sendReminder(Randevu randevu)
    {
        String message = String.format("Merhaba %s, %s saat %s tarihli kuaför randevunuza 30 dakika kaldı 💈",
                randevu.getAd(), randevu.getTarih(), randevu.getSaat());
        System.out.println("[HATIRLATMA] " + message);
    }
}