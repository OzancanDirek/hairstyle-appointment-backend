package com.example.randevutakip.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column(length = 36)
    private String id;

    @Column(name = "kullanici_adi", unique = true, nullable = false, length = 50)
    private String kullaniciAdi;

    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @Column(nullable = false)
    private String sifre;

    @Column(length = 50, nullable = false)
    private String ad;

    @Column(length = 50, nullable = false)
    private String soyad;

    @Column(length = 20)
    private String telefon;

    @Column(nullable = false)
    private Boolean aktif = true;

    @Column(name = "olusturma_tarihi")
    private LocalDateTime olusturmaTarihi;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<com.example.randevutakip.model.Role> roles = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        if (id == null || id.isEmpty()) {
            id = java.util.UUID.randomUUID().toString();
        }
        if (olusturmaTarihi == null) {
            olusturmaTarihi = LocalDateTime.now();
        }
    }
}