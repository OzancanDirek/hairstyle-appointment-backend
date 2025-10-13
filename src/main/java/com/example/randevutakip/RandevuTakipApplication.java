package com.example.randevutakip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class RandevuTakipApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(RandevuTakipApplication.class, args);
    }

}
