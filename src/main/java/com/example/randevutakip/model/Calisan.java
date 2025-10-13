package com.example.randevutakip.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "calisan")
@Getter
@Setter
public class Calisan
{
    @Id
    @Column(name = "id", columnDefinition = "VARCHAR(36)")
    private String id = UUID.randomUUID().toString();

    @Column(name = "ad")
    private String ad;

    @Column(name = "soyad")
    private String soyad;

    @Column(name = "telefon")
    private String telefon;

    @OneToMany(mappedBy = "calisan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Randevu> randevular = new ArrayList<>();
}
