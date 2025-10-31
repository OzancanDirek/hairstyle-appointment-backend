package com.example.randevutakip.Service.SchedularService;

import com.example.randevutakip.Repository.RandevuRepository;
import com.example.randevutakip.Service.Notification.NotificationService;
import com.example.randevutakip.model.Randevu;
import com.example.randevutakip.model.RandevuDurumu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class SchedulerService
{
    @Autowired
    private RandevuRepository randevuRepository;

    @Autowired
    private NotificationService notificationService;

    // Her 1 dakikada bir çalışır - Hatırlatma gönderir
    @Scheduled(fixedRate = 60000)
    public void checkUpcomingAppointments()
    {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
        LocalTime thirtyMinutesLater = now.plusMinutes(30);

        List<Randevu> upcoming = randevuRepository.findByTarihAndSaatBetween(today, now, thirtyMinutesLater);

        for (Randevu r : upcoming)
        {
            if (!r.isReminderSent())
            {
                notificationService.sendReminder(r);
                r.setReminderSent(true);
                randevuRepository.save(r);
            }
        }
    }

    // Her gün gece 00:05'te çalışır - Geçmiş randevuları otomatik tamamlar
    @Scheduled(cron = "0 7 22 * * *")
    public void otomatikTamamla()
    {
        LocalDate bugun = LocalDate.now();

        // Bugünden önceki tüm BEKLEMEDE randevuları bul
        List<Randevu> gecmisRandevular = randevuRepository.findAll()
                .stream()
                .filter(r -> r.getTarih() != null && r.getTarih().isBefore(bugun))
                .filter(r -> r.getDurum() == RandevuDurumu.BEKLEMEDE)
                .filter(r -> !r.isDeleted())
                .toList();

        for (Randevu r : gecmisRandevular)
        {
            r.setDurum(RandevuDurumu.TAMAMLANDI);
            randevuRepository.save(r);
        }

        System.out.println("Otomatik tamamlama: " + gecmisRandevular.size() + " randevu tamamlandı.");
    }
}