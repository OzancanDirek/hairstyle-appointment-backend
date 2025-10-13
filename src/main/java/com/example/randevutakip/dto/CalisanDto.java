package com.example.randevutakip.dto;

import com.example.randevutakip.model.Randevu;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CalisanDto
{
    private String id = UUID.randomUUID().toString();

    private String ad;

    private String soyad;

    private String telefon;

}
