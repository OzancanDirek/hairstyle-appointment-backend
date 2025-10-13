package com.example.randevutakip.Repository;

import com.example.randevutakip.model.Hizmet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HizmetRepository extends JpaRepository<Hizmet, String>
{
    List<Hizmet> findAllByDeletedFalse();
}
