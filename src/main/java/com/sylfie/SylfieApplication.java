package com.sylfie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SylfieApplication {

    public static void main(String[] args) {
        SpringApplication.run(SylfieApplication.class, args);
    }


}
