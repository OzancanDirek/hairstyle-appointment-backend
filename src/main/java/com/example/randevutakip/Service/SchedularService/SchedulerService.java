package com.example.randevutakip.Service.SchedularService;

import com.example.randevutakip.Repository.RandevuRepository;
import com.example.randevutakip.Service.Notification.NotificationService;
import com.example.randevutakip.model.Randevu;
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
                r.setReminderSent(true); // aynı randevuda birden fazla gönderilmesin
                randevuRepository.save(r);
            }
        }
    }
}