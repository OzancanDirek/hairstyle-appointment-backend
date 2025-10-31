package com.example.randevutakip.Repository;

import com.example.randevutakip.dto.Randevudto;
import com.example.randevutakip.model.Randevu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RandevuRepository extends JpaRepository<Randevu, String>
{
    List<Randevu> findAllByCalisan_Id(String calisanId);

    @Query("SELECT r FROM Randevu r " +
            "LEFT JOIN FETCH r.calisan " +
            "LEFT JOIN FETCH r.hizmet")
    List<Randevudto> getAllRandevular();

    @Query("SELECT r FROM Randevu r WHERE r.calisan.id = :calisanId AND r.tarih = :tarih AND r.saat = :saat")
    Optional<Randevu> findExistingRandevu(String calisanId, LocalDate tarih, LocalTime saat);

    boolean existsByCalisan_IdAndTarihAndSaatAndRandevuIdNot(
            String calisanId, LocalDate tarih, LocalTime saat, String randevuId
    );

    @Query("SELECT r FROM Randevu r WHERE r.deleted = false")
    List<Randevu> findAllByDeletedFalse();

    @Query("SELECT r FROM Randevu r WHERE r.tarih = :tarih AND r.saat BETWEEN :start AND :end")
    List<Randevu> findByTarihAndSaatBetween(
            @Param("tarih") LocalDate tarih,
            @Param("start") LocalTime start,
            @Param("end") LocalTime end
    );

    @Query("""
        SELECT DATE(r.tarih) as tarih, 
               SUM(h.fiyat) as toplamKar
        FROM Randevu r
        INNER JOIN r.hizmet h
        WHERE r.deleted = false
        AND r.durum = 'TAMAMLANDI'
        AND r.tarih BETWEEN :baslangicTarihi AND :bitisTarihi
        GROUP BY DATE(r.tarih)
        ORDER BY DATE(r.tarih) ASC
        """)
    List<Object[]> getGunlukKarIstatistikleri(
            @Param("baslangicTarihi") LocalDate baslangicTarihi,
            @Param("bitisTarihi") LocalDate bitisTarihi
    );

    @Query("""
            SELECT DATE(r.tarih) as tarih, 
                   SUM(h.fiyat) as toplamKar
            FROM Randevu r
            INNER JOIN r.hizmet h
            WHERE r.deleted = false
            AND r.durum = 'TAMAMLANDI'
            AND r.tarih >= :baslangicTarihi
            GROUP BY DATE(r.tarih)
            ORDER BY DATE(r.tarih) ASC
            """)
    List<Object[]> getSonNGunKarIstatistikleri(@Param("baslangicTarihi") LocalDate baslangicTarihi);

    @Query("SELECT new com.example.randevutakip.dto.Randevudto(" +
            "r.randevuId, r.ad, r.soyad, r.tarih, r.saat, r.durum, " +
            "c.ad, c.soyad, h.ad) " +
            "FROM Randevu r " +
            "LEFT JOIN r.calisan c " +
            "LEFT JOIN r.hizmet h")
    List<Randevudto> findAllAsDto();

    @Query("SELECT r FROM Randevu r WHERE r.deleted = false AND r.durum = 'BEKLEMEDE'")
    List<Randevu> findBeklemedekiRandevular();

    List<Randevu> findAllByCalisan_IdAndTarihAndDeletedFalse(String calisanId, LocalDate tarih);

    @Query("SELECT r FROM Randevu r WHERE r.durum = 'TAMAMLANDI' and r.deleted = false ")
    List<Randevu> findAllByDurum();
}