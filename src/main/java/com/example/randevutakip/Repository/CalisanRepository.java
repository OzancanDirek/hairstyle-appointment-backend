package com.example.randevutakip.Repository;

import com.example.randevutakip.model.Calisan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CalisanRepository extends JpaRepository<Calisan, String>
{
    List<Calisan> findAll();
}
