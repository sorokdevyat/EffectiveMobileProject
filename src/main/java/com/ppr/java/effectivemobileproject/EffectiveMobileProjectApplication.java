package com.ppr.java.effectivemobileproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EffectiveMobileProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(EffectiveMobileProjectApplication.class, args);
    }

}
