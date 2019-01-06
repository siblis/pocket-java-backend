package ru.geekbrains.pocket.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "ru.geekbrains.pocket.backend")
//@ComponentScan({ "ru.geekbrains.pocket.backend.controller, " +
//        "ru.geekbrains.pocket.backend.service",
//        "ru.geekbrains.pocket.backend.config"})
public class PocketBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(PocketBackendApplication.class, args);
    }
}
