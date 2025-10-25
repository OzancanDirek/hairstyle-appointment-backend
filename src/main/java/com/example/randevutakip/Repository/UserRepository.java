package com.example.randevutakip.Repository;

import com.example.randevutakip.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByKullaniciAdi(String kullaniciAdi);

    Optional<User> findByEmail(String email);

    boolean existsByKullaniciAdi(String kullaniciAdi);

    boolean existsByEmail(String email);
}