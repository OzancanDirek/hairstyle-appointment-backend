package com.example.randevutakip.Repository;

import com.example.randevutakip.model.yorum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface YorumRepository extends JpaRepository<yorum, String>
{
    @Query("SELECT AVG(y.yildiz) FROM yorum y WHERE y.onaylandi = true")
    Double findAverageYildiz();
}
