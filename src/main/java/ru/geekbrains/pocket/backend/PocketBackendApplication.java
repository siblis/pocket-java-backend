package ru.geekbrains.pocket.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextListener;

@SpringBootApplication//(scanBasePackages = "ru.geekbrains.pocket.backend")
public class PocketBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(PocketBackendApplication.class, args);
    }
}
